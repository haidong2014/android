/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : app_master

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2014-08-21 17:28:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_group_user
-- ----------------------------
DROP TABLE IF EXISTS `t_group_user`;
CREATE TABLE `t_group_user` (
  `GROUP_ID` bigint(20) NOT NULL,
  `USER_ID` bigint(20) NOT NULL,
  `PRIVILEGE` varchar(2) NOT NULL default '0',
  `USER_NICKNAME` varchar(256) NOT NULL,
  `RESERVE1` varchar(256) default NULL,
  `RESERVE2` varchar(256) default NULL,
  `RESERVE3` varchar(256) default NULL,
  `DEL_FLAG` char(1) NOT NULL default '0',
  `CREATE_DATE` datetime NOT NULL default '1970-01-01 00:00:00',
  `UPDATE_DATE` datetime NOT NULL default '1970-01-01 00:00:00',
  PRIMARY KEY  (`GROUP_ID`,`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_group_user
-- ----------------------------
INSERT INTO `t_group_user` VALUES ('1', '1', '0', '1', null, null, null, '0', '2014-08-20 10:21:36', '2014-08-20 10:21:36');
INSERT INTO `t_group_user` VALUES ('1', '2', '0', '2', null, null, null, '0', '2014-08-20 10:21:59', '2014-08-20 10:22:06');
INSERT INTO `t_group_user` VALUES ('1', '3', '0', '3', null, null, null, '0', '2014-08-20 10:22:20', '2014-08-20 10:22:20');
INSERT INTO `t_group_user` VALUES ('1', '4', '0', '4', null, null, null, '0', '2014-08-20 10:22:29', '2014-08-20 10:22:29');
INSERT INTO `t_group_user` VALUES ('1', '5', '0', '5', null, null, null, '0', '2014-08-20 10:22:37', '2014-08-20 10:22:37');
INSERT INTO `t_group_user` VALUES ('1', '6', '0', '6', null, null, null, '0', '2014-08-20 10:22:43', '2014-08-20 10:22:43');
INSERT INTO `t_group_user` VALUES ('1', '9', '0', '9', null, null, null, '0', '2014-08-20 10:34:11', '2014-08-20 10:34:11');
INSERT INTO `t_group_user` VALUES ('2', '7', '0', '7', null, null, null, '0', '2014-08-20 10:22:53', '2014-08-20 10:22:53');
INSERT INTO `t_group_user` VALUES ('2', '8', '0', '8', null, null, null, '0', '2014-08-20 10:23:01', '2014-08-20 10:23:01');
INSERT INTO `t_group_user` VALUES ('2', '9', '0', '9', null, null, null, '0', '2014-08-20 10:23:08', '2014-08-20 10:23:08');
INSERT INTO `t_group_user` VALUES ('3', '9', '0', '9', null, null, null, '0', '2014-08-20 10:34:22', '2014-08-20 10:34:22');
INSERT INTO `t_group_user` VALUES ('3', '11', '0', '11', null, null, null, '0', '2014-08-20 10:23:19', '2014-08-20 10:23:55');
INSERT INTO `t_group_user` VALUES ('3', '12', '0', '12', null, null, null, '0', '2014-08-20 10:23:25', '2014-08-20 10:23:25');
DROP TRIGGER IF EXISTS `trg_t_group_user_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_t_group_user_insert` BEFORE INSERT ON `t_group_user` FOR EACH ROW    set new.create_date=now(),new.update_date=now()
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_t_group_user_update`;
DELIMITER ;;
CREATE TRIGGER `trg_t_group_user_update` BEFORE UPDATE ON `t_group_user` FOR EACH ROW    set new.update_date=now()
;;
DELIMITER ;
