/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : app_master

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2014-08-21 17:29:22
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `ID` bigint(20) NOT NULL auto_increment,
  `USER_NAME` varchar(256) NOT NULL default '',
  `USER_PASS` varchar(256) NOT NULL,
  `SINA_UID` varchar(256) default '',
  `SINA_ACCESS_TOKEN` varchar(256) default '',
  `SINA_EXPIRES_IN` varchar(256) default '',
  `USER_TEL` varchar(11) default '',
  `USER_SEX` char(1) default '',
  `USER_LOGO` varchar(256) default '',
  `USER_SIGN` varchar(1024) default '',
  `USER_POINT` int(11) default '0',
  `USER_LEVEL` int(11) default '1',
  `ACTIVITY_STATE` char(1) default '0',
  `LAST_LOGIN_DATE` datetime NOT NULL default '1970-01-01 00:00:00',
  `RESERVE1` varchar(256) default '',
  `RESERVE2` varchar(256) default '',
  `RESERVE3` varchar(256) default '',
  `DEL_FLAG` char(1) NOT NULL default '0',
  `CREATE_DATE` datetime NOT NULL default '1970-01-01 00:00:00',
  `UPDATE_DATE` datetime NOT NULL default '1970-01-01 00:00:00',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('1', '张三', 'E10ADC3949BA59ABBE56E057F20F883E', '		', '', '', '15566912331', '1', '', '', '0', '1', '0', '2014-07-29 18:45:20', null, null, null, '0', '2014-07-29 18:45:20', '2014-08-01 12:13:22');
INSERT INTO `t_user` VALUES ('2', '李四', 'E10ADC3949BA59ABBE56E057F20F883E', '', '', '', '13842821106', null, null, null, null, null, null, '1970-01-01 00:00:00', null, null, null, '0', '1970-01-01 00:00:00', '1970-01-01 00:00:00');
INSERT INTO `t_user` VALUES ('3', '赵武', 'E10ADC3949BA59ABBE56E057F20F883E', '', '', '', '13333333333', '', '', '', '0', '1', '0', '2014-07-30 14:02:32', '', '', '', '0', '2014-07-30 14:02:32', '2014-07-30 14:02:32');
INSERT INTO `t_user` VALUES ('4', '爱因斯坦', 'E10ADC3949BA59ABBE56E057F20F883E', '', '', '', '13889999994', '', '', '', '0', '1', '0', '2014-07-30 14:02:32', '', '', '', '0', '2014-07-30 14:02:32', '2014-07-30 14:02:32');
INSERT INTO `t_user` VALUES ('5', '小五', 'E10ADC3949BA59ABBE56E057F20F883E', '', '', '', '13881234567', '1', '', '让我们荡起双桨', '0', '1', '0', '2014-08-01 14:49:02', '', '', '', '0', '2014-08-01 14:49:02', '2014-08-07 18:27:06');
INSERT INTO `t_user` VALUES ('6', '小王老师', 'E10ADC3949BA59ABBE56E057F20F883E', '', '', '', '22222222222', null, '', '', '0', '1', '0', '2014-07-30 14:21:14', null, null, null, '0', '2014-07-30 14:21:14', '2014-07-30 14:21:14');
INSERT INTO `t_user` VALUES ('7', '孙悟空', 'E10ADC3949BA59ABBE56E057F20F883E', '', '', '', '10987654321', null, '', '', '0', '1', '0', '2014-07-30 14:22:07', null, null, null, '0', '2014-07-30 14:22:07', '2014-07-30 14:22:07');
INSERT INTO `t_user` VALUES ('8', 'ChinaJoy', 'E10ADC3949BA59ABBE56E057F20F883E', '', '', '', '11111111111', null, '', '', '0', '1', '0', '2014-07-30 14:23:32', null, null, null, '0', '2014-07-30 14:23:32', '2014-07-30 14:23:32');
INSERT INTO `t_user` VALUES ('9', 'fish', 'E10ADC3949BA59ABBE56E057F20F883E', '', '', '', '13889999999', '1', '', 'www', '0', '1', '0', '2014-07-30 17:46:07', null, null, null, '0', '2014-07-30 17:46:07', '2014-08-04 11:48:26');
INSERT INTO `t_user` VALUES ('11', '萌萌哒', 'E10ADC3949BA59ABBE56E057F20F883E', '1852128793', '2.00D_22BCVsM4cBdef7549c860Z5VSJ', '1407610800320', '18041195775', '0', '', '我爱你中国我爱你中国我爱你中国我爱你中国我爱你中国我爱你中国', '0', '1', '0', '2014-08-01 14:49:02', null, null, null, '0', '2014-08-01 14:49:02', '2014-08-08 18:28:46');
INSERT INTO `t_user` VALUES ('12', 'fishone', '00B7691D86D96AEBD21DD9E138F90840', '', '', '', '13888888888', null, '', '', '0', '1', '0', '2014-08-11 14:09:08', null, null, null, '0', '2014-08-11 14:09:08', '2014-08-11 14:09:08');
DROP TRIGGER IF EXISTS `trg_t_user_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_t_user_insert` BEFORE INSERT ON `t_user` FOR EACH ROW      set new.create_date=now(),new.update_date=now()
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trg_t_user_update`;
DELIMITER ;;
CREATE TRIGGER `trg_t_user_update` BEFORE UPDATE ON `t_user` FOR EACH ROW      set new.update_date=now()
;;
DELIMITER ;
