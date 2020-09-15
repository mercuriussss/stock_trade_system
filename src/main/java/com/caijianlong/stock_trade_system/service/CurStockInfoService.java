package com.caijianlong.stock_trade_system.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${map.url}",name = "mapurl")
public interface CurStockInfoService {

    @RequestMapping(value = "/",method = RequestMethod.GET,consumes = "application/x-www-form-urlencoded;charset=UTF-8")
    String getStocksInfo(@RequestParam("q") String code);
}