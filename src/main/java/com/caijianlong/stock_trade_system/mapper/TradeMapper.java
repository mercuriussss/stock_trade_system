package com.caijianlong.stock_trade_system.mapper;

import com.caijianlong.stock_trade_system.pojo.HoldStockInfo;
import com.caijianlong.stock_trade_system.pojo.TradeOrder;
import com.caijianlong.stock_trade_system.pojo.User;
import com.caijianlong.stock_trade_system.pojo.UserBalance;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

@Mapper
public interface TradeMapper {

    @Insert("insert into trade_order(order_id,username,stock_code,stock_name,entrust_time,entrust_price,number,type,station) " +
            "values(#{order_id},#{username},#{stock_code},#{stock_name},#{entrust_time},#{entrust_price},#{number},#{type},#{station})")
    boolean entrustOrder(@Param("order_id") String orderId,
                         @Param("username") String username,
                         @Param("stock_code") String code,
                         @Param("stock_name") String name,
                         @Param("entrust_time") Timestamp entrust,
                         @Param("entrust_price") BigDecimal entrustPrice,
                         @Param("number") int number,
                         @Param("type") String type,
                         @Param("station") int station);

    @Select("select name from stock_basic where ts_code like concat(#{stock_code},'%')")
    String getStockNameByCode(@Param("stock_code") String stockCode);

    @Update("update trade_order set cancel_time = #{cancel_time}, station = #{station} where order_id = #{order_id} and username = #{username}")
    boolean cancelOrder(@Param("order_id") String order_id,
                        @Param("username") String username,
                        @Param("cancel_time") Timestamp cancel,
                        @Param("station") int station);

    @Update("update trade_order set done_time = #{done_time}, station = #{station} , done_price = #{done_price} where order_id = #{order_id} and username = #{username}")
    boolean doneOrder(@Param("order_id") String order_id,
                      @Param("username") String username,
                      @Param("done_time") Timestamp done,
                      @Param("done_price") BigDecimal donePrice,
                      @Param("station") int station);

    @Update("update hold_shares set avl_number = total_number")
    boolean updateAllHoldSharesAvlNumber();

    @Update("update user set avl_balance = total_balance")
    boolean updateAllUserAvlBalance();

    @Update("update trade_order set station = #{station} where station = 0 and entrust_time like #{today_date}")
    boolean updateAllTradeOrderStation(@Param("station") int station,@Param("today_date") String todayDate);

    @Select("select avl_balance,total_balance from user where username = #{username}")
    UserBalance getUserBalance(@Param("username") String username);

    @Select("select count(*) from hold_shares where username = #{username} and stock_code = " +
            "(select stock_code from trade_order where order_id = #{order_id})")
    int ifUserHoldThisStock(@Param("username") String username, @Param("order_id") String order_id);

    @Select("select * from hold_shares where username = #{username} and stock_code = #{stock_code}")
    HoldStockInfo getHoldStockInfo(@Param("username") String username,@Param("stock_code") String stock_code);

    @Update("update user set avl_balance = avl_balance + #{avl_change_balance}," +
            "total_balance = total_balance + #{total_change_balance} where username = #{username}")
    boolean updateUserBalance(@Param("username") String username,
                              @Param("avl_change_balance") BigDecimal avlChangeBalance,
                              @Param("total_change_balance") BigDecimal totalChangeBalance);

    @Insert("insert into hold_shares(username,stock_code,stock_name,cost_price,total_number,avl_number) " +
            "values(#{username},#{stock_code},#{stock_name},#{cost_price},#{total_number},#{avl_number})")
    boolean addUserHoldStock(@Param("username") String username,
                             @Param("stock_code") String stockCode,
                             @Param("stock_name") String stockName,
                             @Param("cost_price") BigDecimal costPrice,
                             @Param("total_number") int totalNumber,
                             @Param("avl_number") int avlNumber);

    @Update("update hold_shares set total_number = total_number + #{total_change_number}, avl_number = avl_number + #{avl_change_number}," +
            "cost_price = cost_price + #{change_price} " +
            "where username = #{username} and stock_code = #{stock_code}")
    boolean updateUserHoldStock(@Param("username") String username,
                                @Param("stock_code") String stockCode,
                                @Param("avl_change_number") int avlChangeNumber,
                                @Param("total_change_number") int totalChangeNumber,
                                @Param("change_price") BigDecimal changePrice);

    @Delete("delete from hold_shares where username = #{username} and stock_code = #{stock_code}")
    boolean deleteUserHoldStock(@Param("username") String username, @Param("stock_code") String stockCode);

    @Select("select username,stock_code,stock_name,entrust_time,cancel_time,done_time,entrust_price,done_price,number,type,station " +
            "from trade_order where order_id = #{order_id}")
    TradeOrder getUserTradeOrderInfo(@Param("order_id") String orderId);


    @Select("select count(*) from trade_order where username = #{username}")
    int getUserTradeOrderTotalNum(@Param("username") String username);

    @Select("select count(*) from trade_order where username = #{username} and station = #{station}")
    int getUserTradeOrderTotalNumByStation(@Param("username") String username,@Param("station") int station);

    @Select("select order_id,stock_code,stock_name,entrust_time,cancel_time,done_time,entrust_price,done_price,number,type,station " +
            "from trade_order where username = #{username} order by entrust_time desc limit ${(page-1)*limit} , #{limit}")
    ArrayList<TradeOrder> getUserTradeOrderInfoList(@Param("username") String username, @Param("page") int page, @Param("limit") int limit);

    @Select("select order_id,stock_code,stock_name,entrust_time,cancel_time,done_time,entrust_price,done_price,number,type,station " +
            "from trade_order where username = #{username} and station = #{station} " +
            "order by entrust_time desc limit ${(page-1)*limit} , #{limit}")
    ArrayList<TradeOrder> getUserTradeOrderInfoListByStation(@Param("username") String username,
                                                             @Param("station") int station,
                                                             @Param("page") int page,
                                                             @Param("limit") int limit);

    @Select("select count(*) from trade_order where username = #{username} and entrust_time between #{start} and #{end}")
    int getTimeQuantumUserTradeOrderTotalNum(@Param("username") String username,
                                             @Param("start") String start,
                                             @Param("end") String end);

    @Select("select count(*) from trade_order where username = #{username} and station = #{station} and entrust_time between #{start} and #{end}")
    int getTimeQuantumUserTradeOrderTotalNumByStation(@Param("username") String username,
                                                      @Param("start") String start,
                                                      @Param("end") String end,
                                                      @Param("station") int station);

    @Select("select stock_code,stock_name,entrust_time,cancel_time,done_time,entrust_price,done_price,number,type,station " +
            "from trade_order where username = #{username} and entrust_time between #{start} and #{end} order by entrust_time desc limit ${(page-1)*limit} , #{limit}")
    ArrayList<TradeOrder> getTimeQuantumUserTradeOrderInfoList(@Param("username") String username,
                                                               @Param("start") String start,
                                                               @Param("end") String end,
                                                               @Param("page") int page,
                                                               @Param("limit") int limit);

    @Select("select stock_code,stock_name,entrust_time,cancel_time,done_time,entrust_price,done_price,number,type,station " +
            "from trade_order where username = #{username} and station = #{station} and entrust_time between #{start} and #{end} " +
            "order by entrust_time desc limit ${(page-1)*limit} , #{limit}")
    ArrayList<TradeOrder> getTimeQuantumUserTradeOrderInfoListByStation(@Param("username") String username,
                                                             @Param("start") String start,
                                                             @Param("end") String end,
                                                             @Param("station") int station,
                                                             @Param("page") int page,
                                                             @Param("limit") int limit);

    @Select("select * from trade_order where username = #{username} and entrust_time like concat(#{today},'%') order by entrust_time desc")
    ArrayList<TradeOrder> getTodayUserTradeOrderInfo(@Param("username") String username, @Param("today") String today);

    @Select("select * from trade_order where username = #{username} and entrust_time like concat(#{today},'%') " +
            "and station = #{station} order by entrust_time desc")
    ArrayList<TradeOrder> getTodayUserTradeOrderInfoByStation(@Param("username") String username, @Param("today") String today, @Param("station") int station);


    @Select("select * from hold_shares where username=#{username}")
    ArrayList<HoldStockInfo> getHoldStocks(@Param("username") String username);

    @Update("update user set total_balance = total_balance+#{updateValue}, avl_balance = avl_balance+#{updateValue} where username = #{username}")
    boolean updateBalanceByBank(@Param("username") String username, @Param("updateValue") BigDecimal updateValue);

    @Select("select * from user where username = #{username}")
    User getUserInfo(@Param("username") String username);

}
