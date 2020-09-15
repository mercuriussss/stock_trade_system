package com.caijianlong.stock_trade_system.pojo;

import lombok.Data;

import java.sql.Date;

@Data
public class UncheckedUser {
    private String id;
    private String username;
    private String password;
    private String phone;
    private String card;
    private String name;
    private Date birth;
    private Date register_date;
    private String sex;
}
