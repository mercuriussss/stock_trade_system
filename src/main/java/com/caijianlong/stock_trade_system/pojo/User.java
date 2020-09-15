package com.caijianlong.stock_trade_system.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class User {
    private String id;
    private String username;
    private String password;
    private String phone;
    private String roles;
    private String card;
    private String name;
    private Date birth;
    private String sex;
    private String avatar;
    private String introduction;
    private Date create_date;
    private String checker;
    private BigDecimal avl_balance;
    private BigDecimal total_balance;
}
