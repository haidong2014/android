/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : app_master

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2014-08-21 17:28:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_group
-- ----------------------------
DROP TABLE IF EXISTS `t_group`;
CREATE TABLE `t_group` (
  `ID` bigint(20) NOT NULL auto_increment,
  `CREATE_ID` bigint(20) NOT NULL,
  `GROUP_NAME` varchar(256) NOT NULL,
  `GROUP_MEMO` varchar(1024) default NULL,
  `GROUP_TYPE` int(11) NOT NULL,
  `GROUP_COVER` varchar(256) default NULL,
  `GROUP_TOTAL_SIZE` int(11) default NULL,
  `GROUP_LEVEL` varchar(10) default NULL,
  `RESERVE1` varchar(256) default NULL,
  `RESERVE2` varchar(256) default NULL,
  `RESERVE3` varchar(256) default NULL,
  `RESERVE4` varchar(256) default NULL,
  `RESERVE5` varchar(256) default NULL,
  `RESERVE6` varchar(256) default NULL,
  `DEL_FLAG` char(1) NOT NULL default '0',
  `CREATE_DATE` datetime NOT NULL default '1970-01-01 00:00:00',
  `UPDATE_DATE` datetime NOT NULL default '1970-01-01 00:00:00',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_group
-- ----------------------------
INSERT INTO `t_group` VALUES ('1', '9', ' 向日葵小班', '', '0', null, null, '0', null, null, null, null, null, null, '0', '2014-08-20 10:18:49', '2014-08-20 10:18:49');
INSERT INTO `t_group` VALUES ('2', '9', ' 喜洋洋班', null, '0', null, null, '0', null, null, null, null, null, null, '0', '2014-08-20 10:19:36', '2014-08-20 10:19:36');
INSERT INTO `t_group` VALUES ('3', '9', ' 苹果大班', null, '0', null, null, '0', null, null, null, null, null, null, '0', '2014-08-20 10:20:11', '2014-08-20 10:20:11');
DROP TRIGGER IF EXISTS `trg_t_group_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_t_group_insert` BEFORE INSERT ON `t_group` FOR EACH ROW   set new.create_date=now(),new.update_date=now()
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_t_group_update`;
DELIMITER ;;
CREATE TRIGGER `trg_t_group_update` BEFORE UPDATE ON `t_group` FOR EACH ROW   set new.update_date=now()
;;
DELIMITER ;
