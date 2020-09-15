package com.caijianlong.stock_trade_system.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class TradeOrder {
    private String order_id;
    private String username;
    private String stock_code;
    private String stock_name;
    private Timestamp entrust_time;
    private Timestamp cancel_time;
    private Timestamp done_time;
    private BigDecimal entrust_price;
    private BigDecimal done_price;
    private int number;
    private String type;
    private int station;
}
