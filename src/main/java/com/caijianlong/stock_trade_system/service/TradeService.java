package com.caijianlong.stock_trade_system.service;


import java.math.BigDecimal;

public interface TradeService {
    String entrustOrder(String token, String code, BigDecimal price, int number, String type);
    String cancelOrder(String token, String orderId);

    void dealingOrder();
    void cleanRedisCache();
    void updateDB();

    String getTodayUserTradeOrderInfo(String token, Integer station);
    String getUserHistoryTradeOrderList(String token, String start, String end, Integer station, Integer page, Integer limit);


    String getCapitalStatus(String token);

    String updateBalanceByBank(String token, BigDecimal updateValue);
}
