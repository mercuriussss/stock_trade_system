package com.caijianlong.stock_trade_system.mapper;

import com.caijianlong.stock_trade_system.pojo.HoldStockInfo;
import com.caijianlong.stock_trade_system.pojo.UncheckedUser;
import com.caijianlong.stock_trade_system.pojo.User;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;

@Mapper
public interface UserMapper {

    @Insert("insert into user_unchecked(username,password,phone,card,name,birth,register_date,sex) " +
            "values(#{username},#{password},#{phone},#{card},#{name},#{birth},#{register_date},#{sex})")
    boolean register(@Param("username") String username,
                     @Param("password") String password,
                     @Param("phone") String phone,
                     @Param("card") String card,
                     @Param("name") String name,
                     @Param("birth") Date birth,
                     @Param("register_date") Date register_date,
                     @Param("sex") String sex);

    @Select("select count(*) from user where username = #{username}")
    int ifExist(@Param("username") String username);

    @Select("select count(*) from user_unchecked where username = #{username}")
    int ifRegister(@Param("username") String username);

    @Select("select * from user where username = #{username}")
    User getUserInfo(@Param("username") String username);

    @Select("select count(*) from user_unchecked")
    int getUncheckedUserTotal();

    @Select("select count(*) from user_unchecked where name like concat('%',#{name},'%')")
    int getUncheckedUserTotalByName(String name);

    @Select("select count(*) from user")
    int getUserTotal();

    @Select("select count(*) from user where name like concat('%',#{name},'%')")
    int getUserTotalByName(String name);

    @Select("select * from user_unchecked limit ${(page-1)*limit} , #{limit}")
    ArrayList<UncheckedUser> getUncheckedList(@Param("page") int page, @Param("limit") int limit);

    @Select("select * from user_unchecked where name like concat('%',#{name},'%') limit ${(page-1)*limit} , #{limit}")
    ArrayList<UncheckedUser> getUncheckedListByName(@Param("page") int page, @Param("limit") int limit, @Param("name") String username);

    @Select("select * from user limit ${(page-1)*limit} , #{limit}")
    ArrayList<User> getUserList(@Param("page") int page, @Param("limit") int limit);

    @Select("select * from user where name like concat('%',#{name},'%') limit ${(page-1)*limit} , #{limit}")
    ArrayList<User> getUserListByName(@Param("page") int page, @Param("limit") int limit, @Param("name") String name);

    @Update("update user_unchecked set name=#{name}, sex=#{sex}, birth=#{birth}, phone=#{phone}, card=#{card} " +
            "where id = #{id}")
    boolean updateUncheckedUser(@Param("id") int id,
                                @Param("name") String name,
                                @Param("sex") String sex,
                                @Param("birth") Date birth,
                                @Param("phone") String phone,
                                @Param("card") String card);

    @Update("update user set name=#{name}, sex=#{sex}, birth=#{birth}, phone=#{phone}, card=#{card}, roles=#{roles}" +
            "where id = #{id}")
    boolean updateUser(@Param("id") int id,
                       @Param("name") String name,
                       @Param("sex") String sex,
                       @Param("birth") Date birth,
                       @Param("phone") String phone,
                       @Param("card") String card,
                       @Param("roles") String roles);

    @Delete("delete from user_unchecked where id = #{id}")
    boolean deleteUncheckedUser(@Param("id") int id);

    @Delete("delete from user where id = #{id}")
    boolean deleteUser(@Param("id") int id);

    @Insert("insert into user(username,password,phone,roles,card,name,birth,sex,avatar,introduction,create_date,avl_balance,total_balance,checker) " +
            "values(#{username},#{password},#{phone},#{roles},#{card},#{name},#{birth},#{sex},#{avatar},#{introduction},#{create_date},#{avl_balance},#{total_balance},#{checker})")
    boolean createUser(@Param("username") String username,
                       @Param("password") String password,
                       @Param("phone") String phone,
                       @Param("roles") String roles,
                       @Param("card") String card,
                       @Param("name") String name,
                       @Param("birth") Date birth,
                       @Param("sex") String sex,
                       @Param("avatar") String avatar,
                       @Param("introduction") String introduction,
                       @Param("create_date") Date create_date,
                       @Param("avl_balance") BigDecimal avl_balance,
                       @Param("total_balance") BigDecimal total_balance,
                       @Param("checker") String checker);

    @Select("select password from user where id = #{id}")
    String getPasswordById(@Param("id") int id);

    @Update("update user set avatar = #{avatar_url} where username = #{username}")
    boolean updateAvatar(@Param("avatar_url") String avatarUrl,@Param("username") String username);

    @Update("update user set password = #{new_password} where id = #{id}")
    boolean updatePassword(@Param("id") int id,@Param("new_password") String new_password);
}
