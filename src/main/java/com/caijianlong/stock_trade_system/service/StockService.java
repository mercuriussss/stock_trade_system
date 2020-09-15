package com.caijianlong.stock_trade_system.service;

public interface StockService {

    String getIndexKlineInfo(String ts_code,String type);
    String getStockKlineInfo(String ts_code,String type);

    String getQueryStock(String query);
    String getStockList(int page,int limit,String keyword);
    String getStockBasicInfo(String symbol);

    String getUserAllOptionalCode(String token);
    String getUserOptionalList(int page,int limit,String token);
    String updateUserOptional(String token,String code,String name,String type);
}
