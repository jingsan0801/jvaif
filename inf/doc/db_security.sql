/*
 Navicat Premium Data Transfer

 Source Server         : virtual-centos-31
 Source Server Type    : MySQL
 Source Server Version : 50643
 Source Host           : 192.168.31.136:3306
 Source Schema         : db_security

 Target Server Type    : MySQL
 Target Server Version : 50643
 File Encoding         : 65001

 Date: 30/07/2019 00:28:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_scy_role
-- ----------------------------
DROP TABLE IF EXISTS `t_scy_role`;
CREATE TABLE `t_scy_role` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `notes` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_scy_role
-- ----------------------------
BEGIN;
INSERT INTO `t_scy_role` VALUES (1, 'string', 'string');
INSERT INTO `t_scy_role` VALUES (2, 'string2', 'string2');
INSERT INTO `t_scy_role` VALUES (3, 'string', 'string');
COMMIT;

-- ----------------------------
-- Table structure for t_scy_user
-- ----------------------------
DROP TABLE IF EXISTS `t_scy_user`;
CREATE TABLE `t_scy_user` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_scy_user
-- ----------------------------
BEGIN;
INSERT INTO `t_scy_user` VALUES (1, 'wang', '123');
INSERT INTO `t_scy_user` VALUES (3, 'string', 'string');
INSERT INTO `t_scy_user` VALUES (4, 'string', 'string');
COMMIT;

-- ----------------------------
-- Table structure for t_scy_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_scy_user_role`;
CREATE TABLE `t_scy_user_role` (
  `id` int(12) NOT NULL AUTO_INCREMENT,
  `user_id` int(12) NOT NULL,
  `role_id` int(12) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
