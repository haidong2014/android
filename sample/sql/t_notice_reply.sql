/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : app_master

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2014-08-21 17:29:12
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_notice_reply
-- ----------------------------
DROP TABLE IF EXISTS `t_notice_reply`;
CREATE TABLE `t_notice_reply` (
  `ID` bigint(20) NOT NULL auto_increment,
  `NOTICE_ID` bigint(20) NOT NULL,
  `REPLY_TYPE` char(1) NOT NULL default '0',
  `REPLY_USER_ID` bigint(20) default NULL,
  `REPLY_TEXT` varchar(2048) NOT NULL,
  `DEL_FLAG` char(1) NOT NULL default '0',
  `CREATE_ID` bigint(20) NOT NULL,
  `CREATE_DATE` datetime NOT NULL default '1970-01-01 00:00:00',
  `UPDATE_DATE` datetime NOT NULL default '1970-01-01 00:00:00',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice_reply
-- ----------------------------
INSERT INTO `t_notice_reply` VALUES ('1', '64', '0', '9', '123456', '0', '9', '2014-08-21 14:18:54', '2014-08-21 14:18:54');
INSERT INTO `t_notice_reply` VALUES ('2', '64', '0', '9', '2345678', '0', '9', '2014-08-21 14:23:28', '2014-08-21 14:23:28');
INSERT INTO `t_notice_reply` VALUES ('3', '64', '0', '9', '你哥', '0', '9', '2014-08-21 16:20:30', '2014-08-21 16:20:30');
INSERT INTO `t_notice_reply` VALUES ('4', '64', '0', '9', '策划地', '0', '9', '2014-08-21 16:37:16', '2014-08-21 16:37:16');
INSERT INTO `t_notice_reply` VALUES ('5', '64', '0', '9', '333', '0', '9', '2014-08-21 16:41:33', '2014-08-21 16:41:33');
INSERT INTO `t_notice_reply` VALUES ('6', '64', '0', '9', '222', '0', '9', '2014-08-21 16:46:38', '2014-08-21 16:46:38');
INSERT INTO `t_notice_reply` VALUES ('7', '64', '0', '9', '111', '0', '9', '2014-08-21 16:49:55', '2014-08-21 16:49:55');
INSERT INTO `t_notice_reply` VALUES ('8', '66', '0', '9', '沙发', '0', '9', '2014-08-21 16:51:06', '2014-08-21 16:51:06');
INSERT INTO `t_notice_reply` VALUES ('9', '66', '0', '4', '板凳', '0', '4', '2014-08-21 16:51:36', '2014-08-21 16:51:36');
DROP TRIGGER IF EXISTS `trg_t_notice_reply_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_t_notice_reply_insert` BEFORE INSERT ON `t_notice_reply` FOR EACH ROW         set new.create_date=now(),new.update_date=now()
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_t_notice_reply_update`;
DELIMITER ;;
CREATE TRIGGER `trg_t_notice_reply_update` BEFORE UPDATE ON `t_notice_reply` FOR EACH ROW         set new.update_date=now()
;;
DELIMITER ;
