package com.caijianlong.stock_trade_system.service.impl;

import com.alibaba.fastjson.JSON;
import com.caijianlong.stock_trade_system.common.StatusCode;
import com.caijianlong.stock_trade_system.common.Stock;
import com.caijianlong.stock_trade_system.mapper.TradeMapper;
import com.caijianlong.stock_trade_system.pojo.HoldStockInfo;
import com.caijianlong.stock_trade_system.pojo.TradeOrder;
import com.caijianlong.stock_trade_system.pojo.User;
import com.caijianlong.stock_trade_system.pojo.UserBalance;
import com.caijianlong.stock_trade_system.service.CurStockInfoService;
import com.caijianlong.stock_trade_system.service.TradeService;
import com.caijianlong.stock_trade_system.utils.OrderNoUtils;
import com.caijianlong.stock_trade_system.common.cache.RedisUtils;
import com.caijianlong.stock_trade_system.utils.TimeUtils;
import com.caijianlong.stock_trade_system.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private CurStockInfoService curStockInfoService;

    @Autowired
    private TradeMapper tradeMapper;

    @Resource
    private RedisUtils redisUtils;

    private final String REDIS_ALL_KEYS = "allKeys";
    private final long EXPIRE_TIME = 6 * 60 * 60;  //设置redis缓存数据过期时间为6小时

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String entrustOrder(String token, String code, BigDecimal price, int number, String type) {
        String msg = "";
        Map map = new HashMap();
        long time1 = System.currentTimeMillis();
        if (TokenUtils.verify(token)) {
                String name = tradeMapper.getStockNameByCode(code.substring(2, code.length()));
                if (name != null) {
                    String username = TokenUtils.getUsernameByToken(token);
                    String orderId = OrderNoUtils.getUUID();
                    Timestamp entrust = TimeUtils.getNowTimestamp();
                    System.out.println("price:" + price);
                    String redis_key = code + ":" + price + ":" + type;
                    String redis_value = orderId;
                    UserBalance userBalance = tradeMapper.getUserBalance(username);
                    HoldStockInfo holdStockInfo = tradeMapper.getHoldStockInfo(username, code);
                        //先对买卖条件做判断，买入必须满足可用资金必须大于或等于股票的买入价X买入数量，卖出必须满足股票可用数量大于或等于卖出数量
                        //满足买卖条件后，再将委托单存入数据库和redis缓存当中，然后买入要更新用户的资金状态，卖出要更新用户的持仓股票的可用数量
                        if (((type.equals(Stock.ACTION_BUY) && userBalance.getAvl_balance().compareTo(price.multiply(BigDecimal.valueOf(number))) > -1)
                                || (type.equals(Stock.ACTION_SELL) && holdStockInfo.getAvl_number() >= number))
                                && tradeMapper.entrustOrder(orderId, username, code, name, entrust, price, number, type, Stock.STATION_ENTRUST)
                                && redisUtils.lSet(redis_key, redis_value, EXPIRE_TIME)) {
                            if (type.equals(Stock.ACTION_BUY)) {
                                tradeMapper.updateUserBalance(username, price.multiply(BigDecimal.valueOf(number)).negate(), BigDecimal.valueOf(0));
                            } else {
                                tradeMapper.updateUserHoldStock(username, code, -number, 0, BigDecimal.valueOf(0));
                            }
                            redisUtils.sSetAndTime(REDIS_ALL_KEYS, EXPIRE_TIME, redis_key);
                            map.put("code", StatusCode.SUCCESS_NO_TAG);
                            map.put("data", "success");

                        } else {
                            map.put("code", StatusCode.FAILED);
                            map.put("data", "failed");
                        }
                } else {
                    map.put("code", StatusCode.FAILED);
                    map.put("message", "该股票不存在，请输入正确的股票代码");
                }
        } else {
            map.put("code", StatusCode.FAILED);
            map.put("message", "登录过期，请重新登录");
        }

        long time2 = System.currentTimeMillis();
        System.out.println("委托挂单消耗时间：" + (time2 - time1));
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String getUserHistoryTradeOrderList(String token, String start, String end, Integer station, Integer page, Integer limit) {
        String msg = "";
        Map map = new HashMap();
        Map data = new HashMap();
        ArrayList<TradeOrder> list;
        if (TokenUtils.verify(token)) {
            String username = TokenUtils.getUsernameByToken(token);
            if (start == null && end == null) {
                if (station == null) {
                    list = tradeMapper.getUserTradeOrderInfoList(username, page, limit);
                    data.put("total", tradeMapper.getUserTradeOrderTotalNum(username));
                    data.put("items", list);
                    map.put("code", StatusCode.SUCCESS_NO_TAG);
                    map.put("data", data);
                } else {
                    list = tradeMapper.getUserTradeOrderInfoListByStation(username, station, page, limit);
                    data.put("total", tradeMapper.getUserTradeOrderTotalNumByStation(username, station));
                    data.put("items", list);
                    map.put("code", StatusCode.SUCCESS_NO_TAG);
                    map.put("data", data);
                }
            } else {
                if (station == null) {
                    list = tradeMapper.getTimeQuantumUserTradeOrderInfoList(username, start, end, page, limit);
                    data.put("total", tradeMapper.getTimeQuantumUserTradeOrderTotalNum(username, start, end));
                    data.put("items", list);
                    map.put("code", StatusCode.SUCCESS_NO_TAG);
                    map.put("data", data);
                } else {
                    list = tradeMapper.getTimeQuantumUserTradeOrderInfoListByStation(username, start, end, station, page, limit);
                    data.put("total", tradeMapper.getTimeQuantumUserTradeOrderTotalNumByStation(username, start, end, station));
                    data.put("items", list);
                    map.put("code", StatusCode.SUCCESS_NO_TAG);
                    map.put("data", data);
                }
            }
        } else {
            map.put("code", StatusCode.FAILED);
            map.put("message", "登录过期，请重新登录");
        }
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String getTodayUserTradeOrderInfo(String token, Integer station) {
        String msg = "";
        Map map = new HashMap();
        ArrayList<TradeOrder> list;
        String today = TimeUtils.getTodayDate();
        if (TokenUtils.verify(token)) {
            String username = TokenUtils.getUsernameByToken(token);
            if (station == null) {
                list = tradeMapper.getTodayUserTradeOrderInfo(username, today);
                map.put("code", StatusCode.SUCCESS_NO_TAG);
                map.put("data", list);
            } else {
                list = tradeMapper.getTodayUserTradeOrderInfoByStation(username, today, station);
                map.put("code", StatusCode.SUCCESS_NO_TAG);
                map.put("data", list);
            }
        } else {
            map.put("code", StatusCode.FAILED);
            map.put("message", "登录过期，请重新登录");
        }
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String cancelOrder(String token, String orderId) {
        String msg = "";
        Map map = new HashMap();
        long time1 = System.currentTimeMillis();
        if (TokenUtils.verify(token)) {
            String username = TokenUtils.getUsernameByToken(token);
            Timestamp cancel = TimeUtils.getNowTimestamp();
            TradeOrder tradeOrder = tradeMapper.getUserTradeOrderInfo(orderId);
            String key = tradeOrder.getStock_code() + ":" + tradeOrder.getEntrust_price() + ":" + tradeOrder.getType();
            System.out.println("是否存在该值");
            System.out.println(redisUtils.lIfExistValue(key, orderId));
            System.out.println(key);
            if (redisUtils.lIfExistValue(key, orderId)) {
                if (tradeMapper.cancelOrder(orderId, username, cancel, Stock.STATION_CANCEL) && redisUtils.lRemove(key, 1, orderId)) {
                    if (tradeOrder.getType().equals(Stock.ACTION_BUY)) {
                        //如果撤的是买单，则将挂单所需资金返回给用户的可用资金
                        BigDecimal price = tradeOrder.getEntrust_price();
                        BigDecimal number = BigDecimal.valueOf(tradeOrder.getNumber());
                        tradeMapper.updateUserBalance(username, price.multiply(number), BigDecimal.valueOf(0));
                    } else if ((tradeOrder.getType().equals(Stock.ACTION_SELL))) {
                        //如果撤的是卖单，则将挂单所需股票数量返回给用户的持仓可用数量当中
                        tradeMapper.updateUserHoldStock(username, tradeOrder.getStock_code(), tradeOrder.getNumber(), 0, BigDecimal.valueOf(0));
                    } else {

                    }

                    if (redisUtils.lGetListSize(key) == 1) {
                        redisUtils.zSetRemove(REDIS_ALL_KEYS, key);
                    }
                    map.put("code", StatusCode.SUCCESS_NO_TAG);
                    map.put("data", "success");
                } else {
                    map.put("code", StatusCode.FAILED);
                    map.put("data", "failed");
                }
            } else {
                map.put("code", StatusCode.FAILED);
                map.put("message", "撤单失败，请刷新状态，确认该委托单是否已成交");
            }
        } else {
            map.put("code", StatusCode.FAILED);
            map.put("message", "登录过期，请重新登录");
        }
        long time2 = System.currentTimeMillis();
        System.out.println("撤单消耗时间：" + (time2 - time1));
        msg = JSON.toJSONString(map);
        return msg;
    }


    @Override
    public String getCapitalStatus(String token) {
        String msg = "";
        Map map = new HashMap();
        Map data = new HashMap();
        if (TokenUtils.verify(token)) {
            String username = TokenUtils.getUsernameByToken(token);
            User user = tradeMapper.getUserInfo(username);
            map.put("code", StatusCode.SUCCESS_NO_TAG);
            data.put("total_balance", user.getTotal_balance());
            data.put("avl_balance", user.getAvl_balance());
            data.put("hold_stocks", tradeMapper.getHoldStocks(username));
            map.put("data", data);
        } else {
            map.put("code", StatusCode.FAILED);
            map.put("message", "登录过期，请重新登录");
        }
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String updateBalanceByBank(String token, BigDecimal updateValue) {
        String msg = "";
        Map map = new HashMap();
        if (tradeMapper.updateBalanceByBank(TokenUtils.getUsernameByToken(token), updateValue)) {
            map.put("code", StatusCode.SUCCESS_NO_TAG);
            map.put("data", "success");
        } else {
            map.put("code", StatusCode.FAILED);
            map.put("data", "failed");
        }
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dealingOrder() {
        long time1 = System.currentTimeMillis();
        List<String> stockCodes = new ArrayList<>();
        List<BigDecimal> stockPrice = new ArrayList<>();
        List<String> stockType = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        String[] temp;
        String orderId;
        String username;
        String stockCode;
        int number;
        int totalNumber;            //持仓总数
        BigDecimal entrustPrice;    //买入或卖出价
        BigDecimal costPrice;       //成本价
        BigDecimal changePrice;     //成本价的变化价格部分，买单为正、增加成本，卖单为负、减少成本
        BigDecimal nowPrice;        //股票现价
        BigDecimal donePrice;       //成交价格
        BigDecimal fixAvlBalance;   //如果出现成交价格不等于委托价的情况，则要对用户的可用资金进行加减修补
        Timestamp nowTime;
        List<Object> orderIdList;   //某个股票某个价位的所有订单ID编号
        TradeOrder tradeOrder;
        HoldStockInfo holdStockInfo;  //用户个股的持仓信息


        Set<Object> allStocks = redisUtils.sGet(REDIS_ALL_KEYS);
        if (!allStocks.isEmpty()) {
            for (Object obj : allStocks) {
                temp = obj.toString().split(":");
                keys.add(obj.toString());
                stockCodes.add(temp[0]);
                stockPrice.add(new BigDecimal(temp[1]));
                stockType.add(temp[2]);
            }
            String msg = curStockInfoService.getStocksInfo(String.join(",", stockCodes));
            String stockArr[] = msg.split(";");
            for (int i = 0; i < stockArr.length - 1; i++) {
                temp = stockArr[i].split("~");
                nowPrice = new BigDecimal(temp[3]);
                entrustPrice = stockPrice.get(i);
                if (stockType.get(i).equals(Stock.ACTION_BUY) && entrustPrice.compareTo(nowPrice) >= 0) {
                    //如果该挂单是买单，并且买入价大于或等于现价，即买入单成交

                    orderIdList = redisUtils.lGet(keys.get(i), 0, -1);
                    nowTime = TimeUtils.getNowTimestamp();

                    //交易有两种情况，下单立即成交和挂着委托单等待,这里如果股票现价小于买入价则使用现价成交，不然使用买入价成交
                    if (entrustPrice.compareTo(nowPrice) > 0) {
                        donePrice = nowPrice;
                    } else {
                        donePrice = entrustPrice;
                    }

                    //遍历该股票该价位的所有委托单
                    for (Object obj : orderIdList) {
                        orderId = obj.toString();
                        tradeOrder = tradeMapper.getUserTradeOrderInfo(orderId);
                        username = tradeOrder.getUsername();
                        stockCode = tradeOrder.getStock_code();
                        number = tradeOrder.getNumber();
                        fixAvlBalance = entrustPrice.subtract(donePrice).multiply(BigDecimal.valueOf(number));

                        //更新委托单状态为已成交
                        tradeMapper.doneOrder(orderId, tradeOrder.getUsername(), nowTime, donePrice, Stock.STATION_DONE);

                        //更新用户总资金，减去购买股票的资金,并且根据情况修补用户可用资金
                        tradeMapper.updateUserBalance(username, fixAvlBalance, donePrice.multiply(BigDecimal.valueOf(number)).negate());

                        //如果该用户已持有该股票，则更新用户持仓总数量和成本价，若没有则插入用户持仓信息
                        System.out.println("buy: username: " + username + "  orderId:" + orderId);
                        System.out.println(tradeMapper.ifUserHoldThisStock(username, orderId));
                        if (tradeMapper.ifUserHoldThisStock(username, orderId) != 0) {
                            holdStockInfo = tradeMapper.getHoldStockInfo(username, stockCode);
                            costPrice = holdStockInfo.getCost_price();
                            totalNumber = holdStockInfo.getTotal_number();

                            //  计算成本价的变化部分
                            //  新成本 - 旧成本
                            //  (成本价*总数量 + 成交价*成交数量)/(总数量 + 买入数量) - 成本价
                            changePrice = ((costPrice.multiply(BigDecimal.valueOf(totalNumber)).add(donePrice.multiply(BigDecimal.valueOf(number))))
                                    .divide(BigDecimal.valueOf(totalNumber + number), 2, BigDecimal.ROUND_HALF_UP)).subtract(costPrice);

                            tradeMapper.updateUserHoldStock(username, stockCode, 0, number, changePrice);
                        } else {
                            tradeMapper.addUserHoldStock(username, stockCode, tradeOrder.getStock_name(), donePrice, number, 0);
                        }

                        //删去redis相应的委托单缓存
                        redisUtils.lRemove(keys.get(i), 1, orderId);
                    }

                    //删去redis缓存中allKeys关于该股票该价格的缓存
                    redisUtils.sSetRemove(REDIS_ALL_KEYS, keys.get(i));
                } else if (stockType.get(i).equals(Stock.ACTION_SELL) && entrustPrice.compareTo(nowPrice) <= 0) {
                    //如果该挂单是卖单，并且卖出价小于现价，即卖出单成交

                    orderIdList = redisUtils.lGet(keys.get(i), 0, -1);
                    nowTime = TimeUtils.getNowTimestamp();

                    //交易有两种情况，下单立即成交和挂着委托单等待,这里如果股票现价大于卖出价则使用现价成交，不然使用卖出价成交
                    if (entrustPrice.compareTo(nowPrice) < 0) {
                        donePrice = nowPrice;
                    } else {
                        donePrice = entrustPrice;
                    }

                    //遍历该股票该价位的所有委托单
                    for (Object obj : orderIdList) {
                        orderId = obj.toString();
                        tradeOrder = tradeMapper.getUserTradeOrderInfo(orderId);
                        username = tradeOrder.getUsername();
                        stockCode = tradeOrder.getStock_code();
                        number = tradeOrder.getNumber();

                        holdStockInfo = tradeMapper.getHoldStockInfo(username, stockCode);
                        costPrice = holdStockInfo.getCost_price();
                        totalNumber = holdStockInfo.getTotal_number();

                        tradeMapper.doneOrder(orderId, tradeOrder.getUsername(), nowTime, donePrice, Stock.STATION_DONE);

                        //更新用户总资金，加上卖出股票的资金
                        tradeMapper.updateUserBalance(username, BigDecimal.valueOf(0), donePrice.multiply(BigDecimal.valueOf(number)));

                        System.out.println("sell: username: " + username + "  orderId:" + orderId);
                        System.out.println(tradeMapper.ifUserHoldThisStock(username, orderId));
                        //如果该用户持有的该股票总数量不等于卖出的数量，则保留仓位信息并更新股票总数量和成本价，若等于则说明该股票已清仓，应直接清空该股票的仓位信息
                        if (holdStockInfo.getTotal_number() != number) {

                            //  计算成本价的变化部分
                            //  新成本 - 旧成本
                            //  (成本价*总数量 - 卖出价*卖出数量)/(总数量 - 卖出数量) - 成本价
                            changePrice = ((costPrice.multiply(BigDecimal.valueOf(totalNumber)).subtract(donePrice.multiply(BigDecimal.valueOf(number))))
                                    .divide(BigDecimal.valueOf(totalNumber - number), 2, BigDecimal.ROUND_HALF_UP)).subtract(costPrice);

                            tradeMapper.updateUserHoldStock(username, stockCode, 0, -number, changePrice);
                        } else {
                            tradeMapper.deleteUserHoldStock(username, stockCode);
                        }

                        //删去redis相应的委托单缓存
                        redisUtils.lRemove(keys.get(i), 1, orderId);
                    }

                    //删去redis缓存中allKeys关于该股票该价格的缓存
                    redisUtils.sSetRemove(REDIS_ALL_KEYS, keys.get(i));
                } else {
                    //出现既不是买单也不是卖单的情况，虽说委托单经过基本不可能出现该错误，不过可以留着等功能拓展
                }
            }

        }
        long time2 = System.currentTimeMillis();
        //System.out.println("撮合系统耗费时间：" + (time2 - time1));
    }

    @Override
    public void cleanRedisCache() {
        System.out.println("cleanRedisCache");
        redisUtils.clean();
    }

    @Override
    public void updateDB() {
        System.out.println("updateDB");
        tradeMapper.updateAllHoldSharesAvlNumber();     //将用户今日买入的股票的数量变为可用数量

        tradeMapper.updateAllUserAvlBalance();          //以及将用户今日冻结资金变为可用资金

        //将未成交的那些委托单的状态统统变为过期状态，即station从0更新为-2
        tradeMapper.updateAllTradeOrderStation(Stock.STATION_EXPIRE, TimeUtils.getTodayDate() + "%");
    }
}
