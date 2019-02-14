/*
 Navicat Premium Data Transfer

 Source Server         : app-server
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : 192.168.10.123:3306
 Source Schema         : slave_ssm

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 13/02/2019 09:57:42
*/
use slave_ssm;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '城市编号',
  `province_id` int(10) UNSIGNED NOT NULL COMMENT '省份编号',
  `city_name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '城市名称',
  `description` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
