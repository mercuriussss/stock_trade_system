package com.caijianlong.stock_trade_system.controller;

import com.caijianlong.stock_trade_system.service.TradeService;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/trade")
@EnableScheduling
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PostMapping("/entrustOrder")
    @ResponseBody
    @ApiOperation(value = "挂单")
    public String entrustOrder(String token, String code, String price, int number, String type) {
        String msg = tradeService.entrustOrder(token, code, new BigDecimal(price), number, type);
        System.out.println("entrust");
        return msg;
    }

    @PostMapping("/cancelOrder")
    @ResponseBody
    @ApiOperation(value = "撤单")
    public String cancelOrder(String token, String orderId) {
        String msg = tradeService.cancelOrder(token, orderId);
        System.out.println("cancel");
        return msg;
    }

    @GetMapping("/getUserHistoryTradeOrderList")
    @ResponseBody
    @ApiOperation(value = "查询用户历史交易单列表")
    public String getUserHistoryTradeOrderList(String token, String start, String end, Integer station, Integer page, Integer limit) {
        String msg = tradeService.getUserHistoryTradeOrderList(token, start, end, station, page, limit);
        System.out.println("getUserHistoryTradeOrderList");
        return msg;
    }

    @GetMapping("/getTodayUserTradeOrderInfo")
    @ResponseBody
    @ApiOperation(value = "查询用户未成交的委托单、今日委托单或今日成交单")
    public String getTodayUserTradeOrderInfo(String token, Integer station) {
        String msg = tradeService.getTodayUserTradeOrderInfo(token, station);
        System.out.println("getTodayUserTradeOrderInfo");
        return msg;
    }


    @GetMapping("/getCapitalStatus")
    @ResponseBody
    @ApiOperation(value = "获取用户资金现状和持仓信息")
    public String getCapitalStatus(String token){
        System.out.println(token);
        String msg = tradeService.getCapitalStatus(token);
        System.out.println("getCapitalStatus");
        return msg;
    }

    @PostMapping("/updateBalanceByBank")
    @ResponseBody
    @ApiOperation(value = "更新账号资金")
    public String updateBalanceByBank(String token,String updateValue){
        String msg = tradeService.updateBalanceByBank(token, new BigDecimal(updateValue));
        System.out.println("deleteUser");
        return msg;
    }

    @Scheduled(fixedRate = 3000)
    @ApiOperation(value = "撮合交易处理")
    public void dealingOrder() {
        tradeService.dealingOrder();
    }

    @Scheduled(cron = "0 0 16 * * ?")
    @ApiOperation(value = "每天16点自动更新数据")
    public void updateDBAndRedis() {
        tradeService.cleanRedisCache(); //清空redis缓存
        tradeService.updateDB(); //更新数据库内容
    }
}
