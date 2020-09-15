package com.caijianlong.stock_trade_system.service.impl;

import com.alibaba.fastjson.JSON;
import com.caijianlong.stock_trade_system.common.StatusCode;
import com.caijianlong.stock_trade_system.common.UserInit;
import com.caijianlong.stock_trade_system.mapper.UserMapper;
import com.caijianlong.stock_trade_system.pojo.UncheckedUser;
import com.caijianlong.stock_trade_system.pojo.User;
import com.caijianlong.stock_trade_system.service.UserService;
import com.caijianlong.stock_trade_system.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String register(String username, String password, String phone, String card, String name, Date birth, Date register_date, String sex) {
        String msg = "";
        Map map = new HashMap();
        int ifExist = userMapper.ifExist(username);
        int ifRegister = userMapper.ifRegister(username);
        if (ifExist != 0) {
            map.put("code", StatusCode.FAILED);
            map.put("message", "该用户名已被使用，请重新输入");
        } else if (ifRegister != 0) {
            map.put("code", StatusCode.FAILED);
            map.put("message", "该用户名已提交过注册申请，请耐心等候管理员审核");
        } else {
            if (userMapper.register(username, password, phone, card, name, birth, register_date, sex)) {
                map.put("code", StatusCode.SUCCESS);
                map.put("message", "注册申请提交成功，请耐心等待管理员审核");
            } else {
                map.put("code", StatusCode.FAILED);
                map.put("message", "注册出错");
            }
        }
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String login(String username, String password) {
        String msg = "";
        Map map = new HashMap();
        int ifExist = userMapper.ifExist(username);
        if (ifExist == 0) {
            map.put("code", StatusCode.FAILED);
            map.put("message", "该账号不存在，请重新输入");
        } else {
            User user = userMapper.getUserInfo(username);
            Map data = new HashMap();
            System.out.println(user);
            //  验证密码是否正确
            if (user.getPassword().equals(password)) {
                map.put("code", StatusCode.SUCCESS);
                map.put("message", "登录成功");
                data.put("token", TokenUtils.sign(user));
                map.put("data", data);
            } else {
                map.put("code", StatusCode.FAILED);
                map.put("message", "账号与密码不一致，请重新输入");
            }
        }
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String getInfoByToken(String token) {
        String msg = "";
        Map map = new HashMap();
        if (TokenUtils.verify(token)) {
            msg = getInfoByUsername(TokenUtils.getUsernameByToken(token));
        } else {
            map.put("code", StatusCode.FAILED);
            map.put("message", "登录过期，请重新登录");
            msg = JSON.toJSONString(map);
        }
        return msg;
    }

    @Override
    public String getInfoByUsername(String username) {
        String msg = "";
        Map map = new HashMap();
        Map data = new HashMap();
        User user = userMapper.getUserInfo(username);
        map.put("code", StatusCode.SUCCESS_NO_TAG);
        map.put("data", user);
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String logout(String username) {
        String msg = "";
        Map map = new HashMap();
        map.put("code", StatusCode.SUCCESS);
        map.put("message", "成功退出账号");
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String getUncheckedList(int page, int limit, String name) {
        String msg = "";
        Map map = new HashMap();
        Map data = new HashMap();
        ArrayList<UncheckedUser> list;
        if (name == null) {
            list = userMapper.getUncheckedList(page, limit);
            data.put("total", userMapper.getUncheckedUserTotal());
        } else {
            list = userMapper.getUncheckedListByName(page, limit, name);
            data.put("total", userMapper.getUncheckedUserTotalByName(name));
        }
        data.put("items", list);
        map.put("code", StatusCode.SUCCESS_NO_TAG);
        map.put("data", data);
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String getUserList(int page, int limit, String name) {
        String msg = "";
        Map map = new HashMap();
        Map data = new HashMap();
        ArrayList<User> list;
        if (name == null) {
            list = userMapper.getUserList(page, limit);
            data.put("total", userMapper.getUserTotal());
        } else {
            list = userMapper.getUserListByName(page, limit, name);
            data.put("total", userMapper.getUserTotalByName(name));
        }
        data.put("items", list);
        map.put("code", StatusCode.SUCCESS_NO_TAG);
        map.put("data", data);
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String updateUncheckedUser(int id, String name, String sex, Date birth, String phone, String card) {
        String msg = "";
        Map map = new HashMap();
        if (userMapper.updateUncheckedUser(id, name, sex, birth, phone, card)) {
            map.put("code", StatusCode.SUCCESS);
            map.put("data", "success");
        } else {
            map.put("code", StatusCode.FAILED);
            map.put("data", "failed");
        }
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String updateUser(int id, String name, String sex, Date birth, String phone, String card, String roles) {
        String msg = "";
        Map map = new HashMap();
        if (userMapper.updateUser(id, name, sex, birth, phone, card, roles)) {
            map.put("code", StatusCode.SUCCESS);
            map.put("data", "success");
        } else {
            map.put("code", StatusCode.FAILED);
            map.put("data", "failed");
        }
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String deleteUncheckedUser(int id) {
        String msg = "";
        Map map = new HashMap();
        if (userMapper.deleteUncheckedUser(id)) {
            map.put("code", StatusCode.SUCCESS);
            map.put("data", "success");
        } else {
            map.put("code", StatusCode.FAILED);
            map.put("data", "failed");
        }
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String deleteUser(int id) {
        String msg = "";
        Map map = new HashMap();
        if (userMapper.deleteUser(id)) {
            map.put("code", StatusCode.SUCCESS);
            map.put("data", "success");
        } else {
            map.put("code", StatusCode.FAILED);
            map.put("data", "failed");
        }
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    @Transactional
    public String passUncheckedUser(int id, String username, String password, String name, String sex, Date birth, String phone, String card, Date registerDate, String checker) {
        String msg = "";
        Map map = new HashMap();
        boolean flag = userMapper.createUser(username, password, phone, UserInit.INIT_ROLES_USER,
                card, name, birth, sex, UserInit.INIT_AVATAR, UserInit.INIT_INTRODUCTION, UserInit.INIT_CREATEDATE,
                UserInit.INIT_BALANCE, UserInit.INIT_BALANCE, checker);
        if (flag == true && userMapper.deleteUncheckedUser(id)) {
            map.put("code", StatusCode.SUCCESS);
            map.put("data", "success");
        } else {
            map.put("code", StatusCode.FAILED);
            map.put("data", "failed");
        }
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String updatePassword(int id, String new_password) {
        String msg = "";
        Map map = new HashMap();
        if (userMapper.updatePassword(id, new_password)) {
            map.put("code", StatusCode.SUCCESS_NO_TAG);
            map.put("data", "success");
        } else {
            map.put("code", StatusCode.FAILED);
            map.put("data", "failed");
        }
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String upload(MultipartFile multipartFile, String uploadFilePath, String showFilePath) {
        String fileName = multipartFile.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String uuidFileName = UUID.randomUUID().toString().replace("-", "") + suffix;
        String msg = "";
        Map map = new HashMap();
        if (!multipartFile.isEmpty()) {
            try {
                multipartFile.transferTo(new File(uploadFilePath + "\\" + uuidFileName));
                map.put("code", StatusCode.SUCCESS);
                map.put("data", showFilePath + uuidFileName);
            } catch (Exception e) {
                map.put("code", StatusCode.FAILED);
                map.put("message", "上传失败");
            }
        } else {
            map.put("code", StatusCode.FAILED);
            map.put("message", "上传失败");
        }
        msg = JSON.toJSONString(map);
        return msg;
    }

    @Override
    public String updateUserAvatar(String token, String avatarUrl) {
        String msg = "";
        Map map = new HashMap();
        if (TokenUtils.verify(token)) {
            String username = TokenUtils.getUsernameByToken(token);
            if(userMapper.updateAvatar(avatarUrl,username)){
                map.put("code", StatusCode.SUCCESS);
                map.put("message", "更新头像成功，可刷新页面查看");
            }else{
                map.put("code", StatusCode.FAILED);
                map.put("message", "更新头像失败");
            }
        } else {
            map.put("code", StatusCode.FAILED);
            map.put("message", "登录过期，请重新登录");
        }
        msg = JSON.toJSONString(map);
        return msg;
    }
}
