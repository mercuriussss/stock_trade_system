package com.caijianlong.stock_trade_system.service.impl;

import com.alibaba.fastjson.JSON;
import com.caijianlong.stock_trade_system.common.StatusCode;
import com.caijianlong.stock_trade_system.mapper.StockMapper;
import com.caijianlong.stock_trade_system.pojo.KlineData;
import com.caijianlong.stock_trade_system.pojo.OptionalStock;
import com.caijianlong.stock_trade_system.pojo.StockBasic;
import com.caijianlong.stock_trade_system.service.StockService;
import com.caijianlong.stock_trade_system.utils.TimeUtils;
import com.caijianlong.stock_trade_system.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockMapper stockMapper;

    @Override
    public String getIndexKlineInfo(String ts_code, String type) {
        String msg = "";
        int limitNum = TimeUtils.getCurrSunNum();
        int hours = TimeUtils.getHour(new java.util.Date());
        if (hours > 9 && hours < 18) {
            limitNum = limitNum - 1;
        }
        Map map = new HashMap();
        ArrayList<KlineData> plusIndex;
        ArrayList<KlineData> list;
        if (type.equals("Daily")) {
            list = stockMapper.getDailyIndex(ts_code);
            map.put("code", StatusCode.SUCCESS_NO_TAG);
        } else if (type.equals("Weekly")) {
            list = stockMapper.getWeeklyIndex(ts_code);
            if (limitNum > 1 && limitNum < 6) {
                plusIndex = stockMapper.getWeeklyIndexPlus(ts_code, limitNum);
                list.add(getRequireData(plusIndex, ts_code));
            }
            map.put("code", StatusCode.SUCCESS_NO_TAG);
        } else if (type.equals("Monthly")) {
            list = stockMapper.getMonthlyIndex(ts_code);
            plusIndex = stockMapper.getMonthlyIndexPlus(ts_code);
            if (!plusIndex.isEmpty()) {
                list.add(getRequireData(plusIndex, ts_code));
            }
            map.put("code", StatusCode.SUCCESS_NO_TAG);
        } else {
            list = null;
            map.put("code", StatusCode.FAILED);
        }
        map.put("data", list);
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String getStockKlineInfo(String ts_code,String type){
        String msg = "";
        int limitNum = TimeUtils.getCurrSunNum();
        int hours = TimeUtils.getHour(new java.util.Date());
        if (hours > 9 && hours < 18) {
            limitNum = limitNum - 1;
        }
        Map map = new HashMap();
        ArrayList<KlineData> plusStock;
        ArrayList<KlineData> list;
        if (type.equals("Daily")) {
            list = stockMapper.getDailyStock(ts_code);
            map.put("code", StatusCode.SUCCESS_NO_TAG);
        } else if (type.equals("Weekly")) {
            list = stockMapper.getWeeklyStock(ts_code);
            if (limitNum > 1 && limitNum < 6) { //如果当前时间是星期二到星期五中的一天
                plusStock = stockMapper.getWeeklyStockPlus(ts_code, limitNum);
                list.add(getRequireData(plusStock, ts_code));
            }
            map.put("code", StatusCode.SUCCESS_NO_TAG);
        } else if (type.equals("Monthly")) {
            list = stockMapper.getMonthlyStock(ts_code);
            plusStock = stockMapper.getMonthlyStockPlus(ts_code);
            if (!plusStock.isEmpty()) {
                list.add(getRequireData(plusStock, ts_code));
            }
            map.put("code", StatusCode.SUCCESS_NO_TAG);
        } else {
            list = null;
            map.put("code", StatusCode.FAILED);
        }
        map.put("data", list);
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String getQueryStock(String query) {
        String msg = "";
        Map map = new HashMap();
        ArrayList<StockBasic> list = stockMapper.getQueryStock(query);
        if (list != null) {
            map.put("code", StatusCode.SUCCESS_NO_TAG);
        } else {
            map.put("code", StatusCode.FAILED);
        }
        map.put("data", list);
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String getStockList(int page, int limit, String keyword) {
        String msg = "";
        Map map = new HashMap();
        Map data = new HashMap();
        ArrayList list;
        int total;
        if (keyword == null) {
            list = stockMapper.getStockList(page, limit);
            total = stockMapper.getStockTotalNum();
            if (list != null) {
                map.put("code", StatusCode.SUCCESS_NO_TAG);
            } else {
                map.put("code", StatusCode.FAILED);
            }
        } else {
            list = stockMapper.getStockListByKeyword(page, limit, keyword);
            total = stockMapper.getStockTotalNumByKeyword(keyword);
            if (list != null) {
                map.put("code", StatusCode.SUCCESS_NO_TAG);
            } else {
                map.put("code", StatusCode.FAILED);
            }
        }
        data.put("total", total);
        data.put("items", list);
        map.put("data", data);
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String getStockBasicInfo(String symbol) {
        String msg = "";
        Map map = new HashMap();
        StockBasic stock = stockMapper.getStockBasicInfo(symbol);
        if (stock != null) {
            map.put("code", StatusCode.SUCCESS_NO_TAG);
        } else {
            map.put("code", StatusCode.FAILED);
        }
        map.put("data", stock);
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String getUserAllOptionalCode(String token) {
        String msg = "";
        Map map = new HashMap();
        Map data = new HashMap();
        ArrayList<String> list = null;
        if (TokenUtils.verify(token)) {
            String username = TokenUtils.getUsernameByToken(token);
            int total = stockMapper.getUserOptionalTotalNum(username);
            if (total > 0) {
                list = stockMapper.getUserAllOptionalCode(username);
            }
            data.put("total", total);
            data.put("items", list);
            map.put("code", StatusCode.SUCCESS_NO_TAG);
        } else {
            map.put("code", StatusCode.FAILED);
        }
        map.put("data", data);
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String getUserOptionalList(int page, int limit, String token) {
        String msg = "";
        Map map = new HashMap();
        Map data = new HashMap();
        ArrayList<OptionalStock> list = null;
        String username;
        int total;
        if (TokenUtils.verify(token)) {
            username = TokenUtils.getUsernameByToken(token);
            total = stockMapper.getUserOptionalTotalNum(username);
            if (total > 0) {
                list = stockMapper.getUserOptionalList(page, limit, username);
            }
            data.put("total", total);
            data.put("items", list);
            map.put("code", StatusCode.SUCCESS_NO_TAG);
        } else {
            map.put("code", StatusCode.FAILED);
        }
        map.put("data", data);
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String updateUserOptional(String token, String code, String name, String type) {
        String msg = "";
        Map map = new HashMap();
        if (TokenUtils.verify(token)) {
            String username = TokenUtils.getUsernameByToken(token);
            if (type.equals("add")) {
                if(stockMapper.ifExistStock(username,code) == 0){
                    stockMapper.addUserOptional(username, code, name);
                    map.put("code", StatusCode.SUCCESS_NO_TAG);
                    map.put("data", "success");
                }else{
                    map.put("code", StatusCode.FAILED);
                    map.put("data", "failed");
                    map.put("message", "该股票早已加入自选股当中，请勿重复添加");
                }
            } else if (type.equals("delete") && stockMapper.deleteUserOptional(username, code)) {
                map.put("code", StatusCode.SUCCESS_NO_TAG);
                map.put("data", "success");
            } else {
                map.put("code", StatusCode.FAILED);
                map.put("data", "failed");
            }
        } else {
            map.put("code", StatusCode.FAILED);
            map.put("data", "failed");
        }
        msg = JSON.toJSONString(map);
        return msg;
    }

    // 根据日线的数据，计算周线、月线所需的当前周、当前月数据
    private KlineData getRequireData(ArrayList<KlineData> plusData, String ts_code) {
        BigDecimal high = null;
        BigDecimal low = null;
        BigDecimal vol = null;
        BigDecimal amount = null;
        KlineData plus = new KlineData();
        for (int i = 0; i < plusData.size(); i++) {
            if (i == 0) {
                plus.setTs_code(ts_code);
                plus.setOpen(plusData.get(i).getOpen());
                plus.setPre_close(plusData.get(i).getPre_close());
                high = plusData.get(i).getHigh();
                low = plusData.get(i).getLow();
                vol = plusData.get(i).getVol();
                amount = plusData.get(i).getAmount();
                continue;
            }
            if (i == plusData.size() - 1) {
                plus.setId(plusData.get(i).getId());
                plus.setClose(plusData.get(i).getClose());
                plus.setTrade_date(plusData.get(i).getTrade_date());
            }
            if (high.compareTo(plusData.get(i).getHigh()) == -1) {
                high = plusData.get(i).getHigh();
            }
            if (low.compareTo(plusData.get(i).getLow()) == 1) {
                low = plusData.get(i).getLow();
            }
            vol = vol.add(plusData.get(i).getVol());
            amount = amount.add(plusData.get(i).getAmount());
        }
        plus.setHigh(high);
        plus.setLow(low);
        plus.setVol(vol);
        plus.setAmount(amount);
        plus.setClose_chg(plus.getOpen().subtract(plus.getClose()));
        plus.setPct_chg(plus.getOpen().divide(plus.getClose(), 2, RoundingMode.HALF_UP));

        return plus;
    }
}
