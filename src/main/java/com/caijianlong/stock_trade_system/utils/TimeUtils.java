package com.caijianlong.stock_trade_system.utils;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;


public class TimeUtils {

    private static final String EXPIRE_TIME = "16:00:00";

    public static Timestamp getNowTimestamp() {
        Timestamp sqlDate = new Timestamp(System.currentTimeMillis());
        return sqlDate;
    }

    public static long getNowExpire() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String str = sdf.format(new java.util.Date());
        long expire = 0;
        try {
            expire = (sdf.parse(EXPIRE_TIME).getTime() - sdf.parse(str).getTime()) / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(str);
        return expire;
    }

    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");//设置日期格式
        String str = sdf.format(new java.util.Date());
        return str;
    }

    public static Date stringToDate(String str) {
        Date date = null;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = new Date(sf.parse(str).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static int getCurrSunNum() {
        java.util.Date date = new java.util.Date();
        SimpleDateFormat dateFm = new SimpleDateFormat("EEE", Locale.CHINA);
        String currSun = dateFm.format(date);
        System.out.println(currSun);
        int num = 0;
        switch (currSun) {
            case "星期一":
                num = 1;
                break;
            case "星期二":
                num = 2;
                break;
            case "星期三":
                num = 3;
                break;
            case "星期四":
                num = 4;
                break;
            case "星期五":
                num = 5;
                break;
            case "星期六":
                num = 6;
                break;
            case "星期日":
                num = 7;
                break;
        }
        return num;
    }

    public static int getHour(java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static void main(String[] args) {
        System.out.println(getNowExpire());
    }
}
