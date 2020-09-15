package com.caijianlong.stock_trade_system.controller;

import com.caijianlong.stock_trade_system.service.StockService;
import com.caijianlong.stock_trade_system.service.impl.StockServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/getIndexKlineInfo")
    @ResponseBody
    @ApiOperation(value = "获取指数K线数据")
    public String getIndexKlineInfo(String ts_code,String type){
        System.out.println(ts_code + "  " + type);
        String msg = stockService.getIndexKlineInfo(ts_code,type);
        System.out.println("indexInfo");
        return msg;
    }

    @GetMapping("/getStockKlineInfo")
    @ResponseBody
    @ApiOperation(value = "获取股票K线数据")
    public String getStockKlineInfo(String ts_code,String type){
        System.out.println(ts_code + "  " + type);
        String msg = stockService.getStockKlineInfo(ts_code,type);
        System.out.println("getStockInfo");
        return msg;
    }

    @GetMapping("/getQueryStock")
    @ResponseBody
    @ApiOperation(value = "获取查询股票信息")
    public String getQueryStock(String query){
        String msg = stockService.getQueryStock(query);
        System.out.println("getQueryStock");
        return msg;
    }

    @GetMapping("/getStockList")
    @ResponseBody
    @ApiOperation(value = "获取股票列表")
    public String getStockList(Integer page,Integer limit,String keyword){
        String msg = stockService.getStockList(page,limit,keyword);
        System.out.println("getStockList");
        return msg;
    }

    @GetMapping("/getStockBasicInfo")
    @ResponseBody
    @ApiOperation(value = "获取股票的基础信息")
    public String getStockBasicInfo(String symbol){
        String msg = stockService.getStockBasicInfo(symbol);
        System.out.println("getStockBasicInfo");
        return msg;
    }

    @GetMapping("/getUserAllOptionalCode")
    @ResponseBody
    @ApiOperation(value = "获取所有自选股代码")
    public String getUserAllOptionalCode(String token){
        String msg = stockService.getUserAllOptionalCode(token);
        System.out.println("getUserOptional");
        return msg;
    }

    @GetMapping("/getUserOptionalList")
    @ResponseBody
    @ApiOperation(value = "获取自选股列表")
    public String getUserOptionalList(Integer page,Integer limit,String token){
        String msg = stockService.getUserOptionalList(page,limit,token);
        System.out.println("getUserOptionalList");
        return msg;
    }

    @PostMapping("/updateUserOptional")
    @ResponseBody
    @ApiOperation(value = "添加或删除用户自选股")
    public String updateUserOptional(String token,String code,String name,String type){
        String msg = stockService.updateUserOptional(token,code,name,type);
        System.out.println("getUserOptional");
        return msg;
    }
}
