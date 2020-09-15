package com.caijianlong.stock_trade_system.utils;

import java.util.UUID;

public class OrderNoUtils {

    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr = str.replace("-", "");
        return uuidStr;
    }
}
