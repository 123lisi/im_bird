/*
Navicat MySQL Data Transfer

Source Server         : base
Source Server Version : 50725
Source Host           : localhost:3306
Source Database       : im_bird

Target Server Type    : MYSQL
Target Server Version : 50725
File Encoding         : 65001

Date: 2021-05-10 14:58:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for chat_msg
-- ----------------------------
DROP TABLE IF EXISTS `chat_msg`;
CREATE TABLE `chat_msg` (
  `id` varchar(64) NOT NULL,
  `send_user_id` varchar(64) NOT NULL,
  `accept_user_id` varchar(64) NOT NULL,
  `msg` varchar(255) NOT NULL,
  `sign_flag` int(1) NOT NULL COMMENT '消息是否签收状态\r\n1：签收\r\n0：未签收\r\n',
  `create_time` datetime NOT NULL COMMENT '发送请求的事件',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of chat_msg
-- ----------------------------
INSERT INTO `chat_msg` VALUES ('201114DSTKWM0P6W', '200924FGT29S2B7C', '200927AR71174754', '？？', '1', '2020-11-14 11:21:30');
INSERT INTO `chat_msg` VALUES ('201114DSYRTXSZHH', '200927AR71174754', '200924FGT29S2B7C', '？；', '0', '2020-11-14 11:21:50');
INSERT INTO `chat_msg` VALUES ('201114DT01R9F3C0', '200927AR71174754', '200924FGT29S2B7C', '急急急', '0', '2020-11-14 11:21:58');
INSERT INTO `chat_msg` VALUES ('201114DT0TK83DYW', '200924FGT29S2B7C', '200927AR71174754', '大大方方', '1', '2020-11-14 11:22:03');
INSERT INTO `chat_msg` VALUES ('201123G2TMZH6894', '200924FGT29S2B7C', '200927AR71174754', '？？', '1', '2020-11-23 13:09:36');
INSERT INTO `chat_msg` VALUES ('201123G4FH5R1DD4', '200924FGT29S2B7C', '200927AR71174754', '啦啦啦', '1', '2020-11-23 13:14:37');
INSERT INTO `chat_msg` VALUES ('201123G66TT14ZC0', '200924FGT29S2B7C', '200927AR71174754', 'hello', '1', '2020-11-23 13:19:48');
INSERT INTO `chat_msg` VALUES ('201123G84G2T0ARP', '200924FGT29S2B7C', '200927AR71174754', '你好', '1', '2020-11-23 13:25:33');
INSERT INTO `chat_msg` VALUES ('201123G8959S39AW', '200924FGT29S2B7C', '200927AR71174754', '看看', '1', '2020-11-23 13:26:03');
INSERT INTO `chat_msg` VALUES ('201123G95937RFCH', '200924FGT29S2B7C', '200927AR71174754', 'hello', '1', '2020-11-23 13:28:39');
INSERT INTO `chat_msg` VALUES ('201123GC7AKCCHM8', '200924FGT29S2B7C', '200927AR71174754', '？？', '1', '2020-11-23 13:37:52');
INSERT INTO `chat_msg` VALUES ('201123GCK19W1SY8', '200927AR71174754', '200924FGT29S2B7C', '？？', '0', '2020-11-23 13:38:55');
INSERT INTO `chat_msg` VALUES ('201123GCYMAR0Z2W', '200927AR71174754', '200924FGT29S2B7C', '？？', '0', '2020-11-23 13:39:56');
INSERT INTO `chat_msg` VALUES ('201123GF0HB8ZMFW', '200927AR71174754', '200924FGT29S2B7C', '啊哈哈哈', '0', '2020-11-23 13:43:09');
INSERT INTO `chat_msg` VALUES ('201123GF3GXSZHH0', '200927AR71174754', '200924FGT29S2B7C', '&：/贺州', '0', '2020-11-23 13:43:28');
INSERT INTO `chat_msg` VALUES ('201123GHBPY0AXKP', '200927AR71174754', '200924FGT29S2B7C', '啦啦啦', '0', '2020-11-23 13:50:21');
INSERT INTO `chat_msg` VALUES ('201123GS789XRRKP', '200924FGT29S2B7C', '200927AR71174754', '？', '1', '2020-11-23 14:07:53');
INSERT INTO `chat_msg` VALUES ('201123GS9N3FPN7C', '200924FGT29S2B7C', '200927AR71174754', '你好', '1', '2020-11-23 14:08:09');
INSERT INTO `chat_msg` VALUES ('201123GSC3N2H9S8', '200924FGT29S2B7C', '200927AR71174754', 'hello', '1', '2020-11-23 14:08:24');
INSERT INTO `chat_msg` VALUES ('201123GSW9TDXA5P', '200924FGT29S2B7C', '200927AR71174754', 'hello', '1', '2020-11-23 14:09:43');
INSERT INTO `chat_msg` VALUES ('201123GXPT0BX1P0', '200924FGT29S2B7C', '200927AR71174754', '个咯！', '1', '2020-11-23 14:18:21');
INSERT INTO `chat_msg` VALUES ('201123GYNDXZD9WH', '200924FGT29S2B7C', '200927AR71174754', '计算机视觉', '1', '2020-11-23 14:21:12');
INSERT INTO `chat_msg` VALUES ('201123GYYF4W670H', '200924FGT29S2B7C', '200927AR71174754', '就是说', '1', '2020-11-23 14:21:57');
INSERT INTO `chat_msg` VALUES ('201123H057DKN72W', '200924FGT29S2B7C', '200927AR71174754', '牛逼', '1', '2020-11-23 14:25:41');
INSERT INTO `chat_msg` VALUES ('201123H0GW13HC28', '200927AR71174754', '200924FGT29S2B7C', '啦啦啦', '0', '2020-11-23 14:26:49');
INSERT INTO `chat_msg` VALUES ('201123H0PWKPSH4H', '200927AR71174754', '201014C5W8875WX4', '啊哈哈哈', '0', '2020-11-23 14:27:22');
INSERT INTO `chat_msg` VALUES ('201123H26054FPPH', '200927AR71174754', '200924FGT29S2B7C', '啦啦啦', '0', '2020-11-23 14:31:46');
INSERT INTO `chat_msg` VALUES ('201123H2722S05WH', '200927AR71174754', '201014C5W8875WX4', '你好', '0', '2020-11-23 14:31:53');
INSERT INTO `chat_msg` VALUES ('201123H7P768BGXP', '200927AR71174754', '200924FGT29S2B7C', '啦啦啦', '0', '2020-11-23 14:48:19');
INSERT INTO `chat_msg` VALUES ('201123H82HHK6WZC', '200927AR71174754', '201014C5W8875WX4', '哈哈哈', '0', '2020-11-23 14:49:25');

-- ----------------------------
-- Table structure for friends_request
-- ----------------------------
DROP TABLE IF EXISTS `friends_request`;
CREATE TABLE `friends_request` (
  `id` varchar(64) NOT NULL,
  `send_user_id` varchar(64) NOT NULL,
  `accept_user_id` varchar(64) NOT NULL,
  `request_date_time` datetime NOT NULL COMMENT '发送请求的事件',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of friends_request
-- ----------------------------

-- ----------------------------
-- Table structure for my_friends
-- ----------------------------
DROP TABLE IF EXISTS `my_friends`;
CREATE TABLE `my_friends` (
  `id` varchar(64) NOT NULL,
  `my_user_id` varchar(64) NOT NULL COMMENT '用户id',
  `my_friend_user_id` varchar(64) NOT NULL COMMENT '用户的好友id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `my_user_id` (`my_user_id`,`my_friend_user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of my_friends
-- ----------------------------
INSERT INTO `my_friends` VALUES ('201101H7Y9C3NAA8', '200924FGT29S2B7C', '200927AR71174754');
INSERT INTO `my_friends` VALUES ('201101H7Y9KZCZC0', '200927AR71174754', '200924FGT29S2B7C');
INSERT INTO `my_friends` VALUES ('201102GZMWM9DS80', '200927AR71174754', '201014C5W8875WX4');
INSERT INTO `my_friends` VALUES ('201102GZMWN5AFW0', '201014C5W8875WX4', '200927AR71174754');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` varchar(64) NOT NULL,
  `username` varchar(20) NOT NULL COMMENT '用户名，账号，慕信号',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `face_image` varchar(255) NOT NULL COMMENT '我的头像，如果没有默认给一张',
  `face_image_big` varchar(255) NOT NULL,
  `nickname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称',
  `qrcode` varchar(255) NOT NULL COMMENT '新用户注册后默认后台生成二维码，并且上传到fastdfs',
  `cid` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('200924FGT29S2B7C', 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', 'M00/00/00/rBEAAl-J10qAUdzlAAHEg6jYtDg215_150x150.png', 'M00/00/00/rBEAAl-J10qAUdzlAAHEg6jYtDg215.png', '张三', '', null);
INSERT INTO `users` VALUES ('200927AR71174754', 'wangwu', 'e10adc3949ba59abbe56e057f20f883e', 'M00/00/00/rBEAAl-F0naAYTNwALnntaH82jw428_150x150.png', 'M00/00/00/rBEAAl-F0naAYTNwALnntaH82jw428.png', '张三', '', '173e9ac7edd155056a0d2166d475962d');
INSERT INTO `users` VALUES ('201014C5W8875WX4', 'lisi', 'e10adc3949ba59abbe56e057f20f883e', 'M00/00/00/rBEAAl-Gv-6AHIeMAPcBwddJuUk250_150x150.png', 'M00/00/00/rBEAAl-Gv-6AHIeMAPcBwddJuUk250.png', '张三', 'M00/00/00/rBEAAl-Gv5OAHL4AAAABn9rfaFE060.png', '173e9ac7edd155056a0d2166d475962d');
