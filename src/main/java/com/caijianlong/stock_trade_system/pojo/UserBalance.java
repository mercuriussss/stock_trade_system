package com.caijianlong.stock_trade_system.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserBalance {
    private BigDecimal avl_balance;
    private BigDecimal total_balance;
}
