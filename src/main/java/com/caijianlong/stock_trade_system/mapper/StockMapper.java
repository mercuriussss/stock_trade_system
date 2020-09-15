package com.caijianlong.stock_trade_system.mapper;

import com.caijianlong.stock_trade_system.pojo.KlineData;
import com.caijianlong.stock_trade_system.pojo.OptionalStock;
import com.caijianlong.stock_trade_system.pojo.StockBasic;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface StockMapper {

    @Select("select * from index_daily_qfq where ts_code = #{ts_code}")
    ArrayList<KlineData> getDailyIndex(@Param("ts_code") String ts_code);

    @Select("select ts_code,trade_date,close,open,high,low,pre_close,close_chg,convert((close-pre_close)*100/pre_close,decimal(10,2)) AS pct_chg," +
            "(vol/100) AS vol,(amount/1000) AS amount  from index_weekly_qfq where ts_code = #{ts_code}")
    ArrayList<KlineData> getWeeklyIndex(@Param("ts_code") String ts_code);

    @Select("select ts_code,trade_date,close,open,high,low,pre_close,close_chg,convert((close-pre_close)*100/pre_close,decimal(10,2)) AS pct_chg," +
            "(vol/100) AS vol,(amount/1000) AS amount  from index_monthly_qfq where ts_code = #{ts_code}")
    ArrayList<KlineData> getMonthlyIndex(@Param("ts_code") String ts_code);

    @Select("(select * from index_daily_qfq where ts_code = #{ts_code} ORDER BY trade_date DESC limit #{limitNum}) ORDER BY trade_date ASC")
    ArrayList<KlineData> getWeeklyIndexPlus(@Param("ts_code") String ts_code, @Param("limitNum") int limitNum);

    @Select("select * from index_daily_qfq where date_format(trade_date,'%Y-%m') = date_format(now(),'%Y-%m') and ts_code = #{ts_code}")
    ArrayList<KlineData> getMonthlyIndexPlus(@Param("ts_code") String ts_code);

    @Select("select ts_code,trade_date,close,open,high,low,pre_close,close_chg,pct_chg,vol,amount from stock_all_daily_wfq where ts_code = #{ts_code}")
    ArrayList<KlineData> getDailyStock(@Param("ts_code") String ts_code);

    @Select("select ts_code,trade_date,close,open,high,low,pre_close,close_chg,convert((close-pre_close)*100/pre_close,decimal(10,2)) AS pct_chg," +
            "(vol/100) AS vol,(amount/1000) AS amount from stock_all_weekly_wfq where ts_code = #{ts_code}")
    ArrayList<KlineData> getWeeklyStock(@Param("ts_code") String ts_code);

    @Select("select ts_code,trade_date,close,open,high,low,pre_close,close_chg,convert((close-pre_close)*100/pre_close,decimal(10,2)) AS pct_chg," +
            "(vol/100) AS vol,(amount/1000) AS amount from stock_all_monthly_wfq where ts_code = #{ts_code}")
    ArrayList<KlineData> getMonthlyStock(@Param("ts_code") String ts_code);

    @Select("(select ts_code,trade_date,close,open,high,low,pre_close,close_chg,pct_chg,vol,amount from stock_all_daily_wfq " +
            "where ts_code = #{ts_code} ORDER BY trade_date DESC limit #{limitNum}) ORDER BY trade_date ASC")
    ArrayList<KlineData> getWeeklyStockPlus(@Param("ts_code") String ts_code, @Param("limitNum") int limitNum);

    @Select("select ts_code,trade_date,close,open,high,low,pre_close,close_chg,pct_chg,vol,amount from stock_all_daily_wfq " +
            "where date_format(trade_date,'%Y-%m') = date_format(now(),'%Y-%m') and ts_code = #{ts_code}")
    ArrayList<KlineData> getMonthlyStockPlus(@Param("ts_code") String ts_code);

    @Select("select * from stock_basic where ts_code like concat('%',#{query},'%') OR name like concat('%',#{query},'%') ORDER BY ts_code ASC limit 100")
    ArrayList<StockBasic> getQueryStock(@Param("query") String query);

    @Select("select ts_code from stock_basic ORDER BY ts_code ASC limit ${(page-1)*limit} , #{limit}")
    ArrayList<String> getStockList(@Param("page") int page, @Param("limit") int limit);

    @Select("select count(*) from stock_basic")
    int getStockTotalNum();

    @Select("select ts_code from stock_basic where ts_code like concat('%',#{keyword},'%') OR name like concat('%',#{keyword},'%') " +
            "ORDER BY ts_code ASC limit ${(page-1)*limit} , #{limit}")
    ArrayList<String> getStockListByKeyword(@Param("page") int page, @Param("limit") int limit, @Param("keyword") String keyword);

    @Select("select count(*) from stock_basic where ts_code like concat('%',#{keyword},'%') OR name like concat('%',#{keyword},'%')")
    int getStockTotalNumByKeyword(@Param("keyword") String keyword);

    @Select("select * from stock_basic where symbol = #{symbol}")
    StockBasic getStockBasicInfo(@Param("symbol") String symbol);

    @Select("select code from user_optional where username = #{username}")
    ArrayList<String> getUserAllOptionalCode(@Param("username") String username);

    @Select("select count(*) from user_optional where username = #{username}")
    int getUserOptionalTotalNum(@Param("username") String username);

    @Insert("insert into user_optional(username,code,name) values(#{username},#{code},#{name})")
    boolean addUserOptional(@Param("username") String username, @Param("code") String code, @Param("name") String name);

    @Delete("delete from user_optional where username = #{username} and code = #{code}")
    boolean deleteUserOptional(@Param("username") String username, @Param("code") String code);

    @Select("select code,name from user_optional where username = #{username} ORDER BY id ASC limit ${(page-1)*limit} , #{limit}")
    ArrayList<OptionalStock> getUserOptionalList(@Param("page") int page, @Param("limit") int limit, @Param("username") String username);

    @Select("select count(*) from user_optional where username = #{username} and code = #{code}")
    int ifExistStock(@Param("username") String username, @Param("code") String code);
}
