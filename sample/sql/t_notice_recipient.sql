/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : app_master

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2014-08-21 17:29:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_notice_recipient
-- ----------------------------
DROP TABLE IF EXISTS `t_notice_recipient`;
CREATE TABLE `t_notice_recipient` (
  `NOTICE_ID` bigint(20) NOT NULL,
  `RECIPIENT_ID` bigint(20) NOT NULL,
  `READ_FLAG` char(1) NOT NULL default '0',
  `PUSH_FLAG` char(1) NOT NULL default '0',
  `DEL_FLAG` char(1) NOT NULL default '',
  PRIMARY KEY  (`NOTICE_ID`,`RECIPIENT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_notice_recipient
-- ----------------------------
INSERT INTO `t_notice_recipient` VALUES ('31', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('31', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('31', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('31', '4', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('31', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('31', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('31', '9', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('32', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('32', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('32', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('32', '4', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('32', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('32', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('32', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('33', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('33', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('33', '4', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('33', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('33', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('33', '9', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('34', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('34', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('34', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('34', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('35', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('35', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('35', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('35', '4', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('35', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('35', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('35', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('36', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('36', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('36', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('37', '4', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('37', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('37', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('37', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('38', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('38', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('38', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('38', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('39', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('39', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('39', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('39', '4', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('39', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('39', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('39', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('40', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('40', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('40', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('40', '4', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('40', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('40', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('40', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('41', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('41', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('41', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('41', '4', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('41', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('41', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('41', '9', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('42', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('42', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('42', '4', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('42', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('42', '9', '0', '0', '1');
INSERT INTO `t_notice_recipient` VALUES ('43', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('43', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('43', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('43', '4', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('43', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('43', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('43', '9', '0', '0', '1');
INSERT INTO `t_notice_recipient` VALUES ('44', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('44', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('44', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('44', '4', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('44', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('44', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('44', '9', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('45', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('45', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('45', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('45', '4', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('45', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('45', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('45', '9', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('46', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('46', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('46', '9', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('48', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('48', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('48', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('48', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('48', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('49', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('49', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('49', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('49', '4', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('49', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('49', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('49', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('51', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('52', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('52', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('52', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('53', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('54', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('55', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('55', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('55', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('55', '4', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('55', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('55', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('55', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('56', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('56', '4', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('56', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('56', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('56', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('57', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('57', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('57', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('57', '4', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('57', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('57', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('57', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('58', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('58', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('58', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('58', '4', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('58', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('58', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('58', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('59', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('59', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('59', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('59', '4', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('59', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('59', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('59', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('60', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('60', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('60', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('60', '4', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('60', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('60', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('60', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('62', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('62', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('62', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('62', '4', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('62', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('62', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('62', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('63', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('63', '11', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('63', '12', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('64', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('64', '11', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('64', '12', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('65', '9', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('65', '11', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('65', '12', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('66', '1', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('66', '2', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('66', '3', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('66', '4', '1', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('66', '5', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('66', '6', '0', '0', '0');
INSERT INTO `t_notice_recipient` VALUES ('66', '9', '1', '0', '0');
