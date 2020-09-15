package com.caijianlong.stock_trade_system.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class HoldStockInfo {
    private String id;
    private String username;
    private String stock_code;
    private String stock_name;
    private BigDecimal cost_price;
    private int total_number;
    private int avl_number;
}
