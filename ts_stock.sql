/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 80021
 Source Host           : localhost:3306
 Source Schema         : ts_stock

 Target Server Type    : MySQL
 Target Server Version : 80021
 File Encoding         : 65001

 Date: 23/03/2021 20:15:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hold_shares
-- ----------------------------
DROP TABLE IF EXISTS `hold_shares`;
CREATE TABLE `hold_shares`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账户',
  `stock_code` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '股票代码',
  `stock_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '股票名称',
  `cost_price` decimal(12, 2) NULL DEFAULT NULL COMMENT '成本价格',
  `total_number` int(0) NULL DEFAULT NULL COMMENT '总数量',
  `avl_number` int(0) NULL DEFAULT NULL COMMENT '可交易数量',
  PRIMARY KEY (`id`, `username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for index_daily_qfq
-- ----------------------------
DROP TABLE IF EXISTS `index_daily_qfq`;
CREATE TABLE `index_daily_qfq`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `ts_code` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `trade_date` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `close` decimal(20, 2) NULL DEFAULT NULL,
  `open` decimal(20, 2) NULL DEFAULT NULL,
  `high` decimal(20, 2) NULL DEFAULT NULL,
  `low` decimal(20, 2) NULL DEFAULT NULL,
  `pre_close` decimal(20, 2) NULL DEFAULT NULL,
  `close_chg` decimal(20, 2) NULL DEFAULT NULL,
  `pct_chg` decimal(20, 2) NULL DEFAULT NULL,
  `vol` decimal(20, 2) NULL DEFAULT NULL,
  `amount` decimal(20, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_daily_qfq_idx`(`ts_code`, `trade_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 163583 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for index_monthly_qfq
-- ----------------------------
DROP TABLE IF EXISTS `index_monthly_qfq`;
CREATE TABLE `index_monthly_qfq`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `ts_code` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `trade_date` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `close` decimal(20, 2) NULL DEFAULT NULL,
  `open` decimal(20, 2) NULL DEFAULT NULL,
  `high` decimal(20, 2) NULL DEFAULT NULL,
  `low` decimal(20, 2) NULL DEFAULT NULL,
  `pre_close` decimal(20, 2) NULL DEFAULT NULL,
  `close_chg` decimal(20, 2) NULL DEFAULT NULL,
  `pct_chg` decimal(20, 2) NULL DEFAULT NULL,
  `vol` decimal(20, 2) NULL DEFAULT NULL,
  `amount` decimal(20, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_monthly_qfq_idx`(`ts_code`, `trade_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1941 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for index_weekly_qfq
-- ----------------------------
DROP TABLE IF EXISTS `index_weekly_qfq`;
CREATE TABLE `index_weekly_qfq`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `ts_code` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `trade_date` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `close` decimal(20, 2) NULL DEFAULT NULL,
  `open` decimal(20, 2) NULL DEFAULT NULL,
  `high` decimal(20, 2) NULL DEFAULT NULL,
  `low` decimal(20, 2) NULL DEFAULT NULL,
  `pre_close` decimal(20, 2) NULL DEFAULT NULL,
  `close_chg` decimal(20, 2) NULL DEFAULT NULL,
  `pct_chg` decimal(20, 2) NULL DEFAULT NULL,
  `vol` decimal(20, 2) NULL DEFAULT NULL,
  `amount` decimal(20, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_weekly_qfq_idx`(`ts_code`, `trade_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7958 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stock_all_daily_wfq
-- ----------------------------
DROP TABLE IF EXISTS `stock_all_daily_wfq`;
CREATE TABLE `stock_all_daily_wfq`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `ts_code` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `trade_date` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `close` decimal(20, 2) NULL DEFAULT NULL,
  `open` decimal(20, 2) NULL DEFAULT NULL,
  `high` decimal(20, 2) NULL DEFAULT NULL,
  `low` decimal(20, 2) NULL DEFAULT NULL,
  `pre_close` decimal(20, 2) NULL DEFAULT NULL,
  `close_chg` decimal(20, 2) NULL DEFAULT NULL,
  `pct_chg` decimal(20, 2) NULL DEFAULT NULL,
  `vol` decimal(20, 2) NULL DEFAULT NULL,
  `amount` decimal(20, 2) NULL DEFAULT NULL,
  `adj_factor` decimal(20, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `stock_all_daily_wfq_idx`(`ts_code`, `trade_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8994721 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stock_all_monthly_wfq
-- ----------------------------
DROP TABLE IF EXISTS `stock_all_monthly_wfq`;
CREATE TABLE `stock_all_monthly_wfq`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `ts_code` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `trade_date` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `close` decimal(20, 2) NULL DEFAULT NULL,
  `open` decimal(20, 2) NULL DEFAULT NULL,
  `high` decimal(20, 2) NULL DEFAULT NULL,
  `low` decimal(20, 2) NULL DEFAULT NULL,
  `pre_close` decimal(20, 2) NULL DEFAULT NULL,
  `close_chg` decimal(20, 2) NULL DEFAULT NULL,
  `pct_chg` decimal(20, 2) NULL DEFAULT NULL,
  `vol` decimal(20, 2) NULL DEFAULT NULL,
  `amount` decimal(20, 2) NULL DEFAULT NULL,
  `adj_factor` decimal(20, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `stock_all_monthly_wfq_idx`(`ts_code`, `trade_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 433424 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stock_all_weekly_wfq
-- ----------------------------
DROP TABLE IF EXISTS `stock_all_weekly_wfq`;
CREATE TABLE `stock_all_weekly_wfq`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `ts_code` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `trade_date` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `low` decimal(20, 2) NULL DEFAULT NULL,
  `close` decimal(20, 2) NULL DEFAULT NULL,
  `open` decimal(20, 2) NULL DEFAULT NULL,
  `high` decimal(20, 2) NULL DEFAULT NULL,
  `pre_close` decimal(20, 2) NULL DEFAULT NULL,
  `close_chg` decimal(20, 2) NULL DEFAULT NULL,
  `pct_chg` decimal(20, 2) NULL DEFAULT NULL,
  `vol` decimal(20, 2) NULL DEFAULT NULL,
  `amount` decimal(20, 2) NULL DEFAULT NULL,
  `adj_factor` decimal(20, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `stock_all_weekly_wfq_idx`(`ts_code`, `trade_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1814941 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stock_basic
-- ----------------------------
DROP TABLE IF EXISTS `stock_basic`;
CREATE TABLE `stock_basic`  (
  `ts_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'TS股票代码',
  `symbol` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '股票代码',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '股票名称',
  `area` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '所在地域',
  `industry` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '	所属行业',
  `market` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '市场类型 （主板/中小板/创业板/科创板）',
  `list_date` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '上市日期',
  PRIMARY KEY (`ts_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade_order
-- ----------------------------
DROP TABLE IF EXISTS `trade_order`;
CREATE TABLE `trade_order`  (
  `order_id` char(35) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '委托单编号',
  `username` char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户账号',
  `stock_code` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '股票代码',
  `stock_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '股票名称',
  `entrust_time` timestamp(0) NOT NULL COMMENT '挂单时间',
  `cancel_time` timestamp(0) NULL DEFAULT NULL COMMENT '撤单时间',
  `done_time` timestamp(0) NULL DEFAULT NULL COMMENT '成交时间',
  `entrust_price` decimal(10, 2) NOT NULL COMMENT '委托价格',
  `done_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '成交价格',
  `number` int(0) NOT NULL COMMENT '委托数量',
  `type` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '买入Buy或者卖出Sell',
  `station` tinyint(1) NOT NULL COMMENT '0为挂单，-1为撤单，1为已成交，-2为订单过期',
  INDEX `username_f`(`username`) USING BTREE,
  CONSTRAINT `username_f` FOREIGN KEY (`username`) REFERENCES `user` (`username`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号密码',
  `phone` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话号码',
  `roles` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '权限',
  `card` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '身份证',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `birth` date NOT NULL COMMENT '出生日期',
  `sex` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '性别',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '头像',
  `introduction` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '个人签名',
  `create_date` date NOT NULL COMMENT '开户日期',
  `checker` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '负责该用户的审核人员',
  `avl_balance` decimal(12, 2) UNSIGNED NULL DEFAULT NULL COMMENT '账户可用金额',
  `total_balance` decimal(12, 2) UNSIGNED NULL DEFAULT NULL COMMENT '账户总金额',
  PRIMARY KEY (`id`, `username`) USING BTREE,
  INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 207 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_optional
-- ----------------------------
DROP TABLE IF EXISTS `user_optional`;
CREATE TABLE `user_optional`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 76 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_unchecked
-- ----------------------------
DROP TABLE IF EXISTS `user_unchecked`;
CREATE TABLE `user_unchecked`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` char(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号',
  `password` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '账号密码',
  `phone` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电话号码',
  `card` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '身份证',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `birth` date NOT NULL COMMENT '出生日期',
  `register_date` date NOT NULL COMMENT '申请注册日期',
  `sex` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '性别',
  PRIMARY KEY (`id`, `username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 217 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
