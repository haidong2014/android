/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : app_master

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2014-08-21 17:28:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_notice_category
-- ----------------------------
DROP TABLE IF EXISTS `t_notice_category`;
CREATE TABLE `t_notice_category` (
  `ID` int(11) NOT NULL,
  `CATEGORY` varchar(256) NOT NULL,
  `DEL_FLAG` char(1) NOT NULL default '0',
  `CREATE_DATE` datetime NOT NULL default '1970-01-01 00:00:00',
  `UPDATE_DATE` datetime NOT NULL default '1970-01-01 00:00:00',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice_category
-- ----------------------------
DROP TRIGGER IF EXISTS `trg_t_notice_category_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_t_notice_category_insert` BEFORE INSERT ON `t_notice_category` FOR EACH ROW     set new.create_date=now(),new.update_date=now()
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_t_notice_category_update`;
DELIMITER ;;
CREATE TRIGGER `trg_t_notice_category_update` BEFORE UPDATE ON `t_notice_category` FOR EACH ROW     set new.update_date=now()
;;
DELIMITER ;
