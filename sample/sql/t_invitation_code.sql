/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : app_master

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2014-08-21 17:28:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_invitation_code
-- ----------------------------
DROP TABLE IF EXISTS `t_invitation_code`;
CREATE TABLE `t_invitation_code` (
  `ID` bigint(20) NOT NULL,
  `GROUP_ID` bigint(20) NOT NULL,
  `INVITATION_CODE` varchar(15) NOT NULL,
  `CREATE_ID` bigint(20) NOT NULL,
  `CREATE_DATE` datetime NOT NULL default '1970-01-01 00:00:00',
  `DEL_FLAG` char(1) NOT NULL default '0',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_invitation_code
-- ----------------------------
DROP TRIGGER IF EXISTS `trg_t_invitation_code_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_t_invitation_code_insert` BEFORE INSERT ON `t_invitation_code` FOR EACH ROW     set new.create_date=now()
;;
DELIMITER ;
