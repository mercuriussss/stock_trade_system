package com.caijianlong.stock_trade_system.controller;

import com.caijianlong.stock_trade_system.pojo.User;
import com.caijianlong.stock_trade_system.service.UserService;
import com.caijianlong.stock_trade_system.service.impl.UserServiceImpl;
import com.caijianlong.stock_trade_system.utils.TimeUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${file.uploadPath}")
    private String uploadFilePath;

    @Value("${file.showPath}")
    private String showFilePath;

    @PostMapping("/register")
    @ResponseBody
    @ApiOperation(value = "注册账号")
    public String register(String username, String password, String phone, String card, String name, String birth,String sex){
        String msg = userService.register(username,password,phone,card,name, TimeUtils.stringToDate(birth), TimeUtils.stringToDate(TimeUtils.getTodayDate()),sex);
        System.out.println("register");
        return msg;
    }


    @PostMapping("/login")
    @ResponseBody
    @ApiOperation(value = "登录账号")
    public String login(String username,String password){
        String msg = userService.login(username,password);
        System.out.println(username + "  " + password);
        System.out.println("login");
        return msg;
    }


    @GetMapping("/info")
    @ResponseBody
    @ApiOperation(value = "获取用户个人信息")
    public String info(String token){
        System.out.println(token);
        String msg = userService.getInfoByToken(token);
        System.out.println("info");
        return msg;
    }

    @PostMapping("/logout")
    @ResponseBody
    @ApiOperation(value = "退出账号")
    public String logout(String username){
        String msg = userService.logout(username);
        System.out.println("logout");
        return msg;
    }

    @GetMapping("/getUncheckedList")
    @ResponseBody
    @ApiOperation(value = "获取审核中的用户列表")
    public String getUncheckedList(Integer page,Integer limit,String name){
        String msg = userService.getUncheckedList(page,limit,name);
        System.out.println("getUncheckedList");
        return msg;
    }

    @GetMapping("/getUserList")
    @ResponseBody
    @ApiOperation(value = "获取用户列表")
    public String getUserList(Integer page,Integer limit,String name){
        String msg = userService.getUserList(page,limit,name);
        System.out.println("getUserList");
        return msg;
    }

    @PostMapping("/updateUncheckedUser")
    @ResponseBody
    @ApiOperation(value = "修改审核中的用户信息")
    public String updateUncheckedUser(String id,String name, String sex, String birth, String phone, String card){
        String msg = userService.updateUncheckedUser(Integer.parseInt(id),name,sex,TimeUtils.stringToDate(birth),phone,card);
        System.out.println("updateUncheckedUser");
        return msg;
    }

    @PostMapping("/updateUser")
    @ResponseBody
    @ApiOperation(value = "修改用户信息")
    public String updateUser(String id,String name, String sex, String birth, String phone, String card,String roles){
        String msg = userService.updateUser(Integer.parseInt(id),name,sex,TimeUtils.stringToDate(birth),phone,card,roles);
        System.out.println("updateUser");
        return msg;
    }

    @PostMapping("/deleteUncheckedUser")
    @ResponseBody
    @ApiOperation(value = "驳回指定用户的注册申请")
    public String updateUncheckedUser(String id){
        String msg = userService.deleteUncheckedUser(Integer.parseInt(id));
        System.out.println("deleteUncheckedUser");
        return msg;
    }

    @PostMapping("/deleteUser")
    @ResponseBody
    @ApiOperation(value = "销户")
    public String updateUser(String id){
        String msg = userService.deleteUser(Integer.parseInt(id));
        System.out.println("deleteUser");
        return msg;
    }

    @PostMapping("/passUncheckedUser")
    @ResponseBody
    @ApiOperation(value = "通过指定用户的注册申请")
    public String passUncheckedUser(String id,String username,String password,String name, String sex, String birth, String phone, String card,String register_date,String checker){
        String msg = userService.passUncheckedUser(Integer.parseInt(id),username,password,name,sex,TimeUtils.stringToDate(birth),phone,card,TimeUtils.stringToDate(register_date),checker);
        System.out.println("passUncheckedUser");
        return msg;
    }

    @PostMapping("/updatePassword")
    @ResponseBody
    @ApiOperation(value = "更新用户密码")
    public String updatePassword(String id,String new_password){
        String msg = userService.updatePassword(Integer.parseInt(id),new_password);
        System.out.println("deleteUser");
        return msg;
    }

    @PostMapping("/upload")
    @ResponseBody
    @ApiOperation(value = "上传图片或文件")
    public String upload(@RequestParam("file") MultipartFile multipartFile){
        String msg = userService.upload(multipartFile,uploadFilePath,showFilePath);
        System.out.println("upload");
        return msg;
    }

    @PostMapping("/updateUserAvatar")
    @ResponseBody
    @ApiOperation(value = "更新用户头像")
    public String upload(String token, String avatarUrl){
        String msg = userService.updateUserAvatar(token,avatarUrl);
        System.out.println("updateUserAvatar");
        return msg;
    }
}

