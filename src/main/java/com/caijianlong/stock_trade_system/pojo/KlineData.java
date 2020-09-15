package com.caijianlong.stock_trade_system.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class KlineData {
    private String id;
    private String ts_code;
    private String trade_date;
    private BigDecimal close;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal pre_close;
    private BigDecimal close_chg;
    private BigDecimal pct_chg;
    private BigDecimal vol;
    private BigDecimal amount;
}
