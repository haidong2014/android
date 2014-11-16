/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50018
Source Host           : localhost:3306
Source Database       : app_master

Target Server Type    : MYSQL
Target Server Version : 50018
File Encoding         : 65001

Date: 2014-08-21 17:28:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_captcha
-- ----------------------------
DROP TABLE IF EXISTS `t_captcha`;
CREATE TABLE `t_captcha` (
  `ID` bigint(20) NOT NULL auto_increment,
  `TEL` varchar(11) NOT NULL,
  `CAPTCHA` varchar(4) NOT NULL,
  `CREATE_DATE` datetime NOT NULL default '1970-01-01 00:00:00',
  `DEL_FLAG` char(1) NOT NULL default '0',
  PRIMARY KEY  (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_captcha
-- ----------------------------
INSERT INTO `t_captcha` VALUES ('1', '18041195775', '1234', '1971-01-01 00:00:00', '1');
INSERT INTO `t_captcha` VALUES ('2', '18041195775', '4556', '1971-01-01 00:00:00', '1');
INSERT INTO `t_captcha` VALUES ('3', '18041195775', '5828', '1971-01-01 00:00:00', '1');
INSERT INTO `t_captcha` VALUES ('4', '18041195775', '2728', '1971-01-01 00:00:00', '1');
INSERT INTO `t_captcha` VALUES ('5', '15566912333', '5788', '1970-01-01 00:00:00', '1');
INSERT INTO `t_captcha` VALUES ('6', '15566912333', '3448', '2014-07-29 17:15:16', '0');
INSERT INTO `t_captcha` VALUES ('7', '15566912333', '4775', '2014-07-29 17:16:17', '0');
INSERT INTO `t_captcha` VALUES ('8', '15566912333', '2127', '2014-07-29 18:32:38', '0');
INSERT INTO `t_captcha` VALUES ('9', '15566912333', '8971', '2014-07-29 18:37:45', '0');
INSERT INTO `t_captcha` VALUES ('10', '15566912333', '7311', '2014-07-29 18:42:32', '0');
INSERT INTO `t_captcha` VALUES ('11', '13333333333', '9376', '2014-07-30 14:02:02', '0');
INSERT INTO `t_captcha` VALUES ('12', '15566912333', '6039', '2014-07-30 14:02:53', '0');
INSERT INTO `t_captcha` VALUES ('13', '12345678901', '4209', '2014-07-30 14:06:43', '0');
INSERT INTO `t_captcha` VALUES ('14', '10987654321', '5481', '2014-07-30 14:21:55', '0');
INSERT INTO `t_captcha` VALUES ('15', '11111111111', '5116', '2014-07-30 14:23:22', '0');
INSERT INTO `t_captcha` VALUES ('16', '121212', '1197', '2014-07-30 17:45:57', '0');
INSERT INTO `t_captcha` VALUES ('17', '15566912333', '5758', '2014-08-01 14:47:22', '0');
INSERT INTO `t_captcha` VALUES ('18', '15566912333', '9072', '2014-08-04 11:46:24', '0');
INSERT INTO `t_captcha` VALUES ('19', '15566912333', '8584', '2014-08-04 16:42:49', '0');
INSERT INTO `t_captcha` VALUES ('20', '15566912333', '7475', '2014-08-04 16:45:04', '0');
INSERT INTO `t_captcha` VALUES ('21', '18041195775', '9787', '2014-08-04 16:45:16', '0');
INSERT INTO `t_captcha` VALUES ('22', '15566912333', '1073', '2014-08-04 16:46:58', '0');
INSERT INTO `t_captcha` VALUES ('23', '18041195775', '8619', '2014-08-04 16:47:06', '0');
INSERT INTO `t_captcha` VALUES ('24', '15566912333', '1344', '2014-08-04 16:49:41', '0');
INSERT INTO `t_captcha` VALUES ('25', '18041195775', '7974', '2014-08-04 16:49:48', '0');
INSERT INTO `t_captcha` VALUES ('26', '18041195775', '3311', '2014-08-04 16:51:12', '0');
INSERT INTO `t_captcha` VALUES ('27', '18041195775', '9561', '2014-08-04 17:02:39', '0');
INSERT INTO `t_captcha` VALUES ('28', '15566912333', '1795', '2014-08-04 17:02:50', '0');
INSERT INTO `t_captcha` VALUES ('29', '15566912333', '9377', '2014-08-04 17:05:44', '0');
INSERT INTO `t_captcha` VALUES ('30', '18041195775', '8054', '2014-08-04 17:05:56', '0');
INSERT INTO `t_captcha` VALUES ('31', '18041195775', '9406', '2014-08-04 17:08:11', '0');
INSERT INTO `t_captcha` VALUES ('32', '15566912333', '7593', '2014-08-04 17:08:17', '0');
INSERT INTO `t_captcha` VALUES ('33', '15566912333', '5918', '2014-08-04 17:09:14', '0');
INSERT INTO `t_captcha` VALUES ('34', '15566912333', '1999', '2014-08-04 17:10:11', '0');
INSERT INTO `t_captcha` VALUES ('35', '15566912333', '5316', '2014-08-04 17:10:32', '0');
INSERT INTO `t_captcha` VALUES ('36', '15566912333', '5376', '2014-08-04 17:11:57', '0');
INSERT INTO `t_captcha` VALUES ('37', '12345', '6195', '2014-08-04 17:13:08', '0');
INSERT INTO `t_captcha` VALUES ('38', '12345', '4958', '2014-08-04 17:14:16', '0');
INSERT INTO `t_captcha` VALUES ('39', '15566912333', '1345', '2014-08-04 17:14:29', '0');
INSERT INTO `t_captcha` VALUES ('40', '15566912333', '1696', '2014-08-04 17:15:01', '0');
INSERT INTO `t_captcha` VALUES ('41', '18041195775', '1904', '2014-08-04 17:15:08', '0');
INSERT INTO `t_captcha` VALUES ('42', '18041195775', '2470', '2014-08-04 18:52:32', '0');
INSERT INTO `t_captcha` VALUES ('43', '18041195775', '9330', '2014-08-04 18:52:42', '0');
INSERT INTO `t_captcha` VALUES ('44', '18041195775', '9169', '2014-08-04 18:59:14', '0');
INSERT INTO `t_captcha` VALUES ('45', '18041195775', '3190', '2014-08-04 18:59:28', '0');
INSERT INTO `t_captcha` VALUES ('46', '18041195775', '8233', '2014-08-04 19:00:21', '0');
INSERT INTO `t_captcha` VALUES ('47', '15566912333', '4933', '2014-08-04 19:00:41', '0');
INSERT INTO `t_captcha` VALUES ('48', '18041195775', '3582', '2014-08-07 17:47:01', '0');
INSERT INTO `t_captcha` VALUES ('49', '18041195775', '6763', '2014-08-07 17:47:13', '0');
INSERT INTO `t_captcha` VALUES ('50', '18041195775', '5848', '2014-08-07 17:48:51', '0');
INSERT INTO `t_captcha` VALUES ('51', '13888888888', '8618', '2014-08-11 14:05:46', '0');
INSERT INTO `t_captcha` VALUES ('52', '13888888888', '9953', '2014-08-11 14:11:01', '0');
INSERT INTO `t_captcha` VALUES ('53', '15555215554', '1223', '2014-08-12 17:17:06', '0');
INSERT INTO `t_captcha` VALUES ('54', '12', '1797', '2014-08-14 14:29:20', '0');
INSERT INTO `t_captcha` VALUES ('55', '12', '4317', '2014-08-14 14:29:21', '0');
INSERT INTO `t_captcha` VALUES ('56', '12', '3900', '2014-08-14 14:29:42', '0');
INSERT INTO `t_captcha` VALUES ('57', '1', '9135', '2014-08-14 14:32:03', '0');
INSERT INTO `t_captcha` VALUES ('58', '12', '1495', '2014-08-14 14:32:29', '0');
INSERT INTO `t_captcha` VALUES ('59', '', '9343', '2014-08-14 14:35:48', '0');
INSERT INTO `t_captcha` VALUES ('60', '13332', '4802', '2014-08-14 14:36:11', '0');
INSERT INTO `t_captcha` VALUES ('61', '18041195775', '9814', '2014-08-14 14:38:27', '0');
INSERT INTO `t_captcha` VALUES ('62', '11', '3107', '2014-08-21 11:46:26', '0');
INSERT INTO `t_captcha` VALUES ('63', '11', '1347', '2014-08-21 11:47:21', '0');
INSERT INTO `t_captcha` VALUES ('64', '', '8680', '2014-08-21 11:47:41', '0');
INSERT INTO `t_captcha` VALUES ('65', '18041195775', '2680', '2014-08-21 11:48:05', '0');
INSERT INTO `t_captcha` VALUES ('66', '18041195775', '7747', '2014-08-21 11:49:35', '0');
DROP TRIGGER IF EXISTS `trg_t_captcha_insert`;
DELIMITER ;;
CREATE TRIGGER `trg_t_captcha_insert` BEFORE INSERT ON `t_captcha` FOR EACH ROW    set new.create_date=now()
;;
DELIMITER ;
