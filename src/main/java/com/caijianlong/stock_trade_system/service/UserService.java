package com.caijianlong.stock_trade_system.service;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.sql.Date;

public interface UserService {
    String register(String username, String password, String phone, String card, String name, Date birth, Date register_date,String sex);
    String login(String username,String password);
    String getInfoByToken(String token);
    String getInfoByUsername(String username);
    String logout(String username);

    String getUncheckedList(int page, int limit, String name);
    String updateUncheckedUser(int id, String name, String sex, Date birth, String phone, String card);
    String deleteUncheckedUser(int id);
    String passUncheckedUser(int id,String username,String password,String name, String sex, Date birth, String phone, String card,Date register_date,String checker);

    String updateUser(int id, String name, String sex, Date birth, String phone, String card,String roles);
    String getUserList(int page, int limit, String name);
    String deleteUser(int id);
    String updatePassword(int id,String new_password);

    String upload(MultipartFile multipartFile,String uploadFilePath,String showFilePath);

    String updateUserAvatar(String token, String avatarUrl);
}
