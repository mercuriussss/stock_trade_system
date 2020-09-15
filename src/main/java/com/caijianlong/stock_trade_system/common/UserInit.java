package com.caijianlong.stock_trade_system.common;

import com.caijianlong.stock_trade_system.utils.TimeUtils;

import java.math.BigDecimal;
import java.sql.Date;

public final class UserInit {
    public static final BigDecimal INIT_BALANCE = BigDecimal.valueOf(50000);    //初始化金额为50000
    public static final String INIT_AVATAR = "https://wx4.sinaimg.cn/large/83d4fb35gy1gc7ukasno1g203s03s4qp.gif";  //初始化头像
    public static final String INIT_INTRODUCTION = "这个人很懒，什么都没有留下"; //初始化个人简介
    public static final Date INIT_CREATEDATE = TimeUtils.stringToDate(TimeUtils.getTodayDate()); //初始化开户时间
    public static final String INIT_ROLES_USER = "user";  //用户权限
    public static final String INIT_ROLES_ADMIN = "admin";  //管理员权限
}
