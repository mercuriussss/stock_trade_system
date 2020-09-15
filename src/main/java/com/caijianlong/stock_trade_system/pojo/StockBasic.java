package com.caijianlong.stock_trade_system.pojo;

import lombok.Data;

@Data
public class StockBasic {
    private String ts_code;
    private String symbol;
    private String name;
    private String area;
    private String industry;
    private String market;
    private String list_date;
}
