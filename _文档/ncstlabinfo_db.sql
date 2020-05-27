/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : ncstlabinfo_db

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-09-04 20:55:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for club_domain_list
-- ----------------------------
DROP TABLE IF EXISTS `club_domain_list`;
CREATE TABLE `club_domain_list` (
  `club_domain_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `club_domain_name` char(20) NOT NULL,
  `club_domain_descr` text,
  PRIMARY KEY (`club_domain_id`),
  UNIQUE KEY `club_domain_name` (`club_domain_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of club_domain_list
-- ----------------------------
INSERT INTO `club_domain_list` VALUES ('1', '音乐', null);
INSERT INTO `club_domain_list` VALUES ('2', '绘画', null);
INSERT INTO `club_domain_list` VALUES ('3', '运动', null);

-- ----------------------------
-- Table structure for college_list
-- ----------------------------
DROP TABLE IF EXISTS `college_list`;
CREATE TABLE `college_list` (
  `college_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `college_name` char(20) NOT NULL,
  `college_abbr` varchar(100) DEFAULT NULL,
  `college_descr` text,
  PRIMARY KEY (`college_id`),
  UNIQUE KEY `college_name` (`college_name`),
  FULLTEXT KEY `college_abbr` (`college_abbr`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of college_list
-- ----------------------------
INSERT INTO `college_list` VALUES ('1', '材料科学与工程学院', '材料 材料院', null);
INSERT INTO `college_list` VALUES ('2', '电气工程学院', '电气 电气院', null);
INSERT INTO `college_list` VALUES ('3', '公共卫生学院', '卫生', null);
INSERT INTO `college_list` VALUES ('4', '管理学院', '管理 管院', null);
INSERT INTO `college_list` VALUES ('5', '护理与康复学院', '护理', null);
INSERT INTO `college_list` VALUES ('6', '化学工程学院', '化工 化学', null);
INSERT INTO `college_list` VALUES ('7', '机械工程学院', '机械 机械院', null);
INSERT INTO `college_list` VALUES ('8', '建筑工程学院', '建工', null);
INSERT INTO `college_list` VALUES ('9', '经济学院', '经济院', null);
INSERT INTO `college_list` VALUES ('10', '口腔医学院', '口腔', null);
INSERT INTO `college_list` VALUES ('11', '矿业工程学院', '矿院', null);
INSERT INTO `college_list` VALUES ('12', '理学院', null, null);
INSERT INTO `college_list` VALUES ('13', '临床医学院', '临床', null);
INSERT INTO `college_list` VALUES ('14', '人文法律学院', '文法 文法学院', null);
INSERT INTO `college_list` VALUES ('15', '生命科学学院', '生命科学', null);
INSERT INTO `college_list` VALUES ('16', '外国语学院', '外国语', null);
INSERT INTO `college_list` VALUES ('17', '心理学院', '心理', null);
INSERT INTO `college_list` VALUES ('18', '信息工程学院', '信息 信工', null);
INSERT INTO `college_list` VALUES ('19', '药学院', '药学 药科 医药', null);
INSERT INTO `college_list` VALUES ('20', '冶金与能源学院', '冶金 能源', null);
INSERT INTO `college_list` VALUES ('21', '医学实验研究中心', '医学', null);
INSERT INTO `college_list` VALUES ('22', '以升创新教育基地', '以升', null);
INSERT INTO `college_list` VALUES ('23', '艺术学院', '艺术院', null);
INSERT INTO `college_list` VALUES ('24', '中医学院', '中医', null);

-- ----------------------------
-- Table structure for interest_list
-- ----------------------------
DROP TABLE IF EXISTS `interest_list`;
CREATE TABLE `interest_list` (
  `interest_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `club_domain_id` int(10) unsigned NOT NULL,
  `interest_name` char(20) NOT NULL,
  `interest_descr` text,
  PRIMARY KEY (`interest_id`),
  UNIQUE KEY `interest_name` (`interest_name`),
  KEY `club_domain_id` (`club_domain_id`),
  CONSTRAINT `interest_list_ibfk_1` FOREIGN KEY (`club_domain_id`) REFERENCES `club_domain_list` (`club_domain_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of interest_list
-- ----------------------------
INSERT INTO `interest_list` VALUES ('1', '1', '铜管', null);
INSERT INTO `interest_list` VALUES ('2', '1', '器乐', null);
INSERT INTO `interest_list` VALUES ('3', '1', '军乐', null);
INSERT INTO `interest_list` VALUES ('4', '1', '唱歌', null);
INSERT INTO `interest_list` VALUES ('5', '2', '国画', null);
INSERT INTO `interest_list` VALUES ('6', '2', '写实', null);
INSERT INTO `interest_list` VALUES ('7', '2', '现代', null);
INSERT INTO `interest_list` VALUES ('8', '2', '油画', null);
INSERT INTO `interest_list` VALUES ('9', '2', '漫画', null);
INSERT INTO `interest_list` VALUES ('10', '3', '篮球', null);
INSERT INTO `interest_list` VALUES ('11', '3', '足球', null);

-- ----------------------------
-- Table structure for lab_domain_list
-- ----------------------------
DROP TABLE IF EXISTS `lab_domain_list`;
CREATE TABLE `lab_domain_list` (
  `lab_domain_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `lab_domain_name` char(20) NOT NULL,
  `lab_domain_descr` text,
  PRIMARY KEY (`lab_domain_id`),
  UNIQUE KEY `lab_domain_name` (`lab_domain_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lab_domain_list
-- ----------------------------
INSERT INTO `lab_domain_list` VALUES ('0', '默认', null);
INSERT INTO `lab_domain_list` VALUES ('1', '设计艺术', null);
INSERT INTO `lab_domain_list` VALUES ('2', '信息技术', null);
INSERT INTO `lab_domain_list` VALUES ('3', '智能制造', null);
INSERT INTO `lab_domain_list` VALUES ('4', '创新实践（工学）', null);
INSERT INTO `lab_domain_list` VALUES ('5', '创新实践（理学）', null);
INSERT INTO `lab_domain_list` VALUES ('6', '创新实践（设计）', null);

-- ----------------------------
-- Table structure for lab_list
-- ----------------------------
DROP TABLE IF EXISTS `lab_list`;
CREATE TABLE `lab_list` (
  `lab_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `lab_name` char(20) NOT NULL,
  `lab_label` varchar(100) DEFAULT NULL,
  `lab_descr` text,
  `lab_domain_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`lab_id`),
  UNIQUE KEY `lab_name` (`lab_name`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lab_list
-- ----------------------------
INSERT INTO `lab_list` VALUES ('0', 'default', '默认', '常指代全部实验室或者无明确所属', '0');
INSERT INTO `lab_list` VALUES ('1', 'GIS设计与研发实验室', null, null, '6');
INSERT INTO `lab_list` VALUES ('2', '产品设计创新工作室', null, null, '6');
INSERT INTO `lab_list` VALUES ('3', '创客空间实验室', null, null, '6');
INSERT INTO `lab_list` VALUES ('4', '电子科技创新实验室', null, null, '4');
INSERT INTO `lab_list` VALUES ('5', '动画与数字艺术工作室', null, null, '1');
INSERT INTO `lab_list` VALUES ('6', '工程计算与模拟创新实验室', null, null, '4');
INSERT INTO `lab_list` VALUES ('7', '光电创新设计工作室', null, null, '4');
INSERT INTO `lab_list` VALUES ('8', '环艺空间设计工作室', null, null, '1');
INSERT INTO `lab_list` VALUES ('9', '机电创新设计实验室', null, null, '4');
INSERT INTO `lab_list` VALUES ('10', '建筑设计创新工作室', null, null, '6');
INSERT INTO `lab_list` VALUES ('11', '节能减排实验室', null, null, '3');
INSERT INTO `lab_list` VALUES ('12', '矿业实践作品创新工作室', null, null, '4');
INSERT INTO `lab_list` VALUES ('13', '力学与结构模型创新思维空间', null, null, '5');
INSERT INTO `lab_list` VALUES ('14', '模拟炼钢实验室', null, null, '3');
INSERT INTO `lab_list` VALUES ('15', '嵌入式系统及物联网应用实验室', null, null, '2');
INSERT INTO `lab_list` VALUES ('16', '认知机器人实验室', null, null, '3');
INSERT INTO `lab_list` VALUES ('17', '数学建模实验室', null, null, '5');
INSERT INTO `lab_list` VALUES ('18', '外语情景模拟实训室', null, null, '1');
INSERT INTO `lab_list` VALUES ('19', '新视觉设计工作室', null, null, '1');
INSERT INTO `lab_list` VALUES ('20', '信息化管理与商务智能实验室', null, null, '2');
INSERT INTO `lab_list` VALUES ('21', '移动及互联网软件工作室', null, null, '2');
INSERT INTO `lab_list` VALUES ('22', '智慧应用软件研发工作室', null, null, '2');

-- ----------------------------
-- Table structure for lab_passage
-- ----------------------------
DROP TABLE IF EXISTS `lab_passage`;
CREATE TABLE `lab_passage` (
  `passage_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `passage_index` char(20) NOT NULL,
  `lab_id` int(10) unsigned NOT NULL,
  `passage_title` varchar(40) DEFAULT NULL,
  `passage_create_date` date DEFAULT NULL,
  `passage_author` varchar(20) DEFAULT NULL,
  `passage_content` mediumtext NOT NULL,
  PRIMARY KEY (`passage_id`),
  KEY `lab_id` (`lab_id`,`passage_index`),
  CONSTRAINT `lab_passage_ibfk_1` FOREIGN KEY (`lab_id`, `passage_index`) REFERENCES `lab_passage_indexlist` (`lab_id`, `index_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lab_passage
-- ----------------------------

-- ----------------------------
-- Table structure for lab_passage_indexlist
-- ----------------------------
DROP TABLE IF EXISTS `lab_passage_indexlist`;
CREATE TABLE `lab_passage_indexlist` (
  `lab_id` int(10) unsigned NOT NULL,
  `index_name` char(20) NOT NULL,
  `index_descr` text,
  PRIMARY KEY (`lab_id`,`index_name`),
  CONSTRAINT `lab_passage_indexlist_ibfk_1` FOREIGN KEY (`lab_id`) REFERENCES `lab_list` (`lab_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lab_passage_indexlist
-- ----------------------------
INSERT INTO `lab_passage_indexlist` VALUES ('0', 'mainIntro', '实验室概况介绍，用于展示和公示');

-- ----------------------------
-- Table structure for lab_weight_item
-- ----------------------------
DROP TABLE IF EXISTS `lab_weight_item`;
CREATE TABLE `lab_weight_item` (
  `speciality_id` int(10) unsigned NOT NULL,
  `lab_id` int(10) unsigned NOT NULL,
  `score` tinyint(3) unsigned DEFAULT NULL,
  KEY `speciality_id` (`speciality_id`),
  KEY `lab_id` (`lab_id`),
  CONSTRAINT `lab_weight_item_ibfk_1` FOREIGN KEY (`speciality_id`) REFERENCES `speciality_list` (`speciality_id`),
  CONSTRAINT `lab_weight_item_ibfk_2` FOREIGN KEY (`lab_id`) REFERENCES `lab_list` (`lab_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of lab_weight_item
-- ----------------------------
INSERT INTO `lab_weight_item` VALUES ('1', '21', '10');
INSERT INTO `lab_weight_item` VALUES ('1', '22', '10');
INSERT INTO `lab_weight_item` VALUES ('1', '20', '8');
INSERT INTO `lab_weight_item` VALUES ('1', '17', '8');
INSERT INTO `lab_weight_item` VALUES ('1', '16', '6');
INSERT INTO `lab_weight_item` VALUES ('1', '15', '8');
INSERT INTO `lab_weight_item` VALUES ('2', '17', '10');
INSERT INTO `lab_weight_item` VALUES ('2', '21', '7');
INSERT INTO `lab_weight_item` VALUES ('2', '21', '5');

-- ----------------------------
-- Table structure for meta_data
-- ----------------------------
DROP TABLE IF EXISTS `meta_data`;
CREATE TABLE `meta_data` (
  `data_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `handle_index` varchar(255) NOT NULL,
  `content` mediumtext,
  PRIMARY KEY (`data_id`),
  UNIQUE KEY `handle_index` (`handle_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of meta_data
-- ----------------------------

-- ----------------------------
-- Table structure for profession_list
-- ----------------------------
DROP TABLE IF EXISTS `profession_list`;
CREATE TABLE `profession_list` (
  `profession_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `college_id` int(10) unsigned NOT NULL,
  `profession_name` char(20) NOT NULL,
  `profession_domain` char(20) DEFAULT NULL,
  `profession_abbr` varchar(100) DEFAULT NULL,
  `profession_desrc` text,
  PRIMARY KEY (`profession_id`),
  UNIQUE KEY `profession_name` (`profession_name`),
  KEY `college_id` (`college_id`),
  FULLTEXT KEY `profession_abbr` (`profession_abbr`),
  CONSTRAINT `profession_list_ibfk_1` FOREIGN KEY (`college_id`) REFERENCES `college_list` (`college_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of profession_list
-- ----------------------------
INSERT INTO `profession_list` VALUES ('1', '1', '材料化学', null, null, null);
INSERT INTO `profession_list` VALUES ('2', '1', '高分子材料与工程', null, null, null);
INSERT INTO `profession_list` VALUES ('3', '1', '无机非金属材料工程（水泥混凝土方向）', '水泥混凝土方向', null, null);
INSERT INTO `profession_list` VALUES ('4', '1', '无机非金属材料工程（陶瓷耐火材料方向）', '陶瓷耐火材料方向', null, null);
INSERT INTO `profession_list` VALUES ('5', '2', '测控技术与仪器', null, null, null);
INSERT INTO `profession_list` VALUES ('6', '2', '电气工程及其自动化', null, null, null);
INSERT INTO `profession_list` VALUES ('7', '2', '电气工程及其自动化H', null, null, null);
INSERT INTO `profession_list` VALUES ('8', '2', '电子科学与技术', null, null, null);
INSERT INTO `profession_list` VALUES ('9', '2', '自动化', null, null, null);
INSERT INTO `profession_list` VALUES ('10', '3', '预防医学', null, null, null);
INSERT INTO `profession_list` VALUES ('11', '4', '工商管理', null, null, null);
INSERT INTO `profession_list` VALUES ('12', '4', '公共事业管理（土管方向）', '土管方向', null, null);
INSERT INTO `profession_list` VALUES ('13', '4', '公共事业管理（卫管方向）', '卫管方向', null, null);
INSERT INTO `profession_list` VALUES ('14', '4', '会计学', null, null, null);
INSERT INTO `profession_list` VALUES ('15', '4', '劳动与社会保障（社保方向）', '社保方向', null, null);
INSERT INTO `profession_list` VALUES ('16', '4', '劳动与社会保障（医保方向）', '医保方向', null, null);
INSERT INTO `profession_list` VALUES ('17', '4', '信息管理与信息系统', null, null, null);
INSERT INTO `profession_list` VALUES ('18', '5', '护理学', null, null, null);
INSERT INTO `profession_list` VALUES ('19', '5', '康复治疗学', null, null, null);
INSERT INTO `profession_list` VALUES ('20', '6', '化学', null, null, null);
INSERT INTO `profession_list` VALUES ('21', '6', '化学工程与工艺', null, null, null);
INSERT INTO `profession_list` VALUES ('22', '6', '环境工程', null, null, null);
INSERT INTO `profession_list` VALUES ('23', '6', '应用化学', null, null, null);
INSERT INTO `profession_list` VALUES ('24', '7', '包装工程', null, null, null);
INSERT INTO `profession_list` VALUES ('25', '7', '车辆工程', null, null, null);
INSERT INTO `profession_list` VALUES ('26', '7', '工业工程', null, null, null);
INSERT INTO `profession_list` VALUES ('27', '7', '工业设计', null, null, null);
INSERT INTO `profession_list` VALUES ('28', '7', '过程装备与控制工程', null, null, null);
INSERT INTO `profession_list` VALUES ('29', '7', '机械设计制造及其自动化', null, null, null);
INSERT INTO `profession_list` VALUES ('30', '7', '机械设计制造及其自动化H', null, null, null);
INSERT INTO `profession_list` VALUES ('31', '8', '城乡规划', null, null, null);
INSERT INTO `profession_list` VALUES ('32', '8', '给排水科学与工程', null, null, null);
INSERT INTO `profession_list` VALUES ('33', '8', '工程管理', null, null, null);
INSERT INTO `profession_list` VALUES ('34', '8', '建筑环境与能源应用工程', null, null, null);
INSERT INTO `profession_list` VALUES ('35', '8', '建筑学', null, null, null);
INSERT INTO `profession_list` VALUES ('36', '8', '交通工程', null, null, null);
INSERT INTO `profession_list` VALUES ('37', '8', '交通运输', null, null, null);
INSERT INTO `profession_list` VALUES ('38', '8', '土木工程', null, null, null);
INSERT INTO `profession_list` VALUES ('39', '8', '物流工程', null, null, null);
INSERT INTO `profession_list` VALUES ('40', '9', '国际经济与贸易', null, null, null);
INSERT INTO `profession_list` VALUES ('41', '9', '金融学', null, null, null);
INSERT INTO `profession_list` VALUES ('42', '9', '经济统计学', null, null, null);
INSERT INTO `profession_list` VALUES ('43', '10', '口腔医学', null, null, null);
INSERT INTO `profession_list` VALUES ('44', '11', '安全工程', null, null, null);
INSERT INTO `profession_list` VALUES ('45', '11', '安全工程（综合改革）', '综合改革', null, null);
INSERT INTO `profession_list` VALUES ('46', '11', '采矿工程（金属矿开采方向）', '金属矿开采方向', null, null);
INSERT INTO `profession_list` VALUES ('47', '11', '采矿工程（煤矿开采方向）', '煤矿开采方向', null, null);
INSERT INTO `profession_list` VALUES ('48', '11', '测绘工程', null, null, null);
INSERT INTO `profession_list` VALUES ('49', '11', '地理信息科学', null, null, null);
INSERT INTO `profession_list` VALUES ('50', '11', '矿物加工工程', null, null, null);
INSERT INTO `profession_list` VALUES ('51', '11', '石油工程（开发地质方向）', '开发地质方向', null, null);
INSERT INTO `profession_list` VALUES ('52', '11', '石油工程（开发工程方向）', '开发工程方向', null, null);
INSERT INTO `profession_list` VALUES ('53', '11', '资源勘查工程', null, null, null);
INSERT INTO `profession_list` VALUES ('54', '12', '数学与应用数学', null, null, null);
INSERT INTO `profession_list` VALUES ('55', '12', '信息与计算科学', null, null, null);
INSERT INTO `profession_list` VALUES ('56', '12', '应用统计学', null, null, null);
INSERT INTO `profession_list` VALUES ('57', '12', '应用物理学', null, null, null);
INSERT INTO `profession_list` VALUES ('58', '12', '智能科学与技术', null, null, null);
INSERT INTO `profession_list` VALUES ('59', '13', '临床医学', null, null, null);
INSERT INTO `profession_list` VALUES ('60', '13', '麻醉学', null, null, null);
INSERT INTO `profession_list` VALUES ('61', '13', '医学检验技术', null, null, null);
INSERT INTO `profession_list` VALUES ('62', '13', '医学影像学', null, null, null);
INSERT INTO `profession_list` VALUES ('63', '14', '法学（法学方向）', '法学方向', null, null);
INSERT INTO `profession_list` VALUES ('64', '14', '法学（医事司法方向）', '医事司法方向', null, null);
INSERT INTO `profession_list` VALUES ('65', '14', '社会工作', null, null, null);
INSERT INTO `profession_list` VALUES ('66', '15', '生物技术（生物化工技术）', '生物化工技术', null, null);
INSERT INTO `profession_list` VALUES ('67', '15', '生物技术（生物医学科学）', '生物医学科学', null, null);
INSERT INTO `profession_list` VALUES ('68', '15', '生物信息', null, null, null);
INSERT INTO `profession_list` VALUES ('69', '16', '翻译', null, null, null);
INSERT INTO `profession_list` VALUES ('70', '16', '日语', null, null, null);
INSERT INTO `profession_list` VALUES ('71', '16', '英语', null, null, null);
INSERT INTO `profession_list` VALUES ('72', '17', '应用心理学', null, null, null);
INSERT INTO `profession_list` VALUES ('73', '18', '电子信息工程', null, null, null);
INSERT INTO `profession_list` VALUES ('74', '18', '电子信息工程（卓越班）', '卓越班', null, null);
INSERT INTO `profession_list` VALUES ('75', '18', '电子信息科学与技术', null, null, null);
INSERT INTO `profession_list` VALUES ('76', '18', '海洋技术', null, null, null);
INSERT INTO `profession_list` VALUES ('77', '18', '计算机科学与技术', null, null, null);
INSERT INTO `profession_list` VALUES ('78', '18', '通信工程', null, null, null);
INSERT INTO `profession_list` VALUES ('79', '18', '网络工程', null, null, null);
INSERT INTO `profession_list` VALUES ('80', '19', '药物制剂', null, null, null);
INSERT INTO `profession_list` VALUES ('81', '19', '药学', null, null, null);
INSERT INTO `profession_list` VALUES ('82', '19', '中药学', null, null, null);
INSERT INTO `profession_list` VALUES ('83', '20', '材料成型及控制工程', null, null, null);
INSERT INTO `profession_list` VALUES ('84', '20', '金属材料工程', null, null, null);
INSERT INTO `profession_list` VALUES ('85', '20', '能源与动力工程', null, null, null);
INSERT INTO `profession_list` VALUES ('86', '20', '冶金工程', null, null, null);
INSERT INTO `profession_list` VALUES ('87', '21', '医学实验技术', null, null, null);
INSERT INTO `profession_list` VALUES ('88', '22', '以升班采矿工程专业', null, null, null);
INSERT INTO `profession_list` VALUES ('89', '22', '以升班电气工程及其自动化专业', null, null, null);
INSERT INTO `profession_list` VALUES ('90', '22', '以升班化学工程与工艺专业', null, null, null);
INSERT INTO `profession_list` VALUES ('91', '22', '以升班机械设计制造及其自动化专业', null, null, null);
INSERT INTO `profession_list` VALUES ('92', '22', '以升班金属材料工程专业', null, null, null);
INSERT INTO `profession_list` VALUES ('93', '22', '以升班土木工程专业', null, null, null);
INSERT INTO `profession_list` VALUES ('94', '22', '以升班冶金工程专业', null, null, null);
INSERT INTO `profession_list` VALUES ('95', '22', '以升班自动化专业', null, null, null);
INSERT INTO `profession_list` VALUES ('96', '23', '产品设计', null, null, null);
INSERT INTO `profession_list` VALUES ('97', '23', '动画', null, null, null);
INSERT INTO `profession_list` VALUES ('98', '23', '环境设计', null, null, null);
INSERT INTO `profession_list` VALUES ('99', '23', '绘画（国画方向）', '国画方向', null, null);
INSERT INTO `profession_list` VALUES ('100', '23', '绘画（油画方向）', '油画方向', null, null);
INSERT INTO `profession_list` VALUES ('101', '23', '视觉传达设计', null, null, null);
INSERT INTO `profession_list` VALUES ('102', '24', '针灸推拿学', null, null, null);
INSERT INTO `profession_list` VALUES ('103', '24', '中西医临床医学', null, null, null);
INSERT INTO `profession_list` VALUES ('104', '24', '中医学', null, null, null);

-- ----------------------------
-- Table structure for speciality_list
-- ----------------------------
DROP TABLE IF EXISTS `speciality_list`;
CREATE TABLE `speciality_list` (
  `speciality_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `speciality_name` char(20) NOT NULL,
  `speciality_descr` text,
  `correlated_college_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`speciality_id`),
  UNIQUE KEY `speciality_name` (`speciality_name`),
  KEY `correlated_college_id` (`correlated_college_id`),
  CONSTRAINT `speciality_list_ibfk_1` FOREIGN KEY (`correlated_college_id`) REFERENCES `college_list` (`college_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of speciality_list
-- ----------------------------
INSERT INTO `speciality_list` VALUES ('1', '计算机编程', null, null);
INSERT INTO `speciality_list` VALUES ('2', '数学与应用', null, null);
INSERT INTO `speciality_list` VALUES ('3', '视觉效果与设计', null, null);
INSERT INTO `speciality_list` VALUES ('4', '物理原理', null, null);
INSERT INTO `speciality_list` VALUES ('5', '化学实践', null, null);
INSERT INTO `speciality_list` VALUES ('6', '电子机械', null, null);
INSERT INTO `speciality_list` VALUES ('7', '文学艺术与设计', null, null);

-- ----------------------------
-- Table structure for user_data_interest
-- ----------------------------
DROP TABLE IF EXISTS `user_data_interest`;
CREATE TABLE `user_data_interest` (
  `user_id` int(10) unsigned NOT NULL,
  `interest_id` int(10) unsigned NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `interest_id` (`interest_id`),
  CONSTRAINT `user_data_interest_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info_main` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `user_data_interest_ibfk_2` FOREIGN KEY (`interest_id`) REFERENCES `interest_list` (`interest_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_data_interest
-- ----------------------------
INSERT INTO `user_data_interest` VALUES ('22', '2');
INSERT INTO `user_data_interest` VALUES ('22', '3');
INSERT INTO `user_data_interest` VALUES ('22', '8');

-- ----------------------------
-- Table structure for user_data_speciality
-- ----------------------------
DROP TABLE IF EXISTS `user_data_speciality`;
CREATE TABLE `user_data_speciality` (
  `user_id` int(10) unsigned NOT NULL,
  `speciality_id` int(10) unsigned NOT NULL,
  KEY `user_id` (`user_id`),
  KEY `speciality_id` (`speciality_id`),
  CONSTRAINT `user_data_speciality_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info_main` (`user_id`) ON DELETE CASCADE,
  CONSTRAINT `user_data_speciality_ibfk_2` FOREIGN KEY (`speciality_id`) REFERENCES `speciality_list` (`speciality_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_data_speciality
-- ----------------------------
INSERT INTO `user_data_speciality` VALUES ('22', '1');
INSERT INTO `user_data_speciality` VALUES ('22', '2');
INSERT INTO `user_data_speciality` VALUES ('22', '4');

-- ----------------------------
-- Table structure for user_info_basic
-- ----------------------------
DROP TABLE IF EXISTS `user_info_basic`;
CREATE TABLE `user_info_basic` (
  `user_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `openid` char(30) NOT NULL,
  `unionid` char(30) DEFAULT NULL,
  `registered` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info_basic
-- ----------------------------
INSERT INTO `user_info_basic` VALUES ('22', 'oHb0a1iDVrtVPiyPgAz7PEAyurvo', null, '1');

-- ----------------------------
-- Table structure for user_info_main
-- ----------------------------
DROP TABLE IF EXISTS `user_info_main`;
CREATE TABLE `user_info_main` (
  `user_id` int(10) unsigned NOT NULL,
  `student_id` char(12) DEFAULT NULL,
  `legal_name` char(10) DEFAULT NULL,
  `college_id` int(10) unsigned DEFAULT NULL,
  `profession_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `college_id` (`college_id`),
  KEY `profession_id` (`profession_id`),
  CONSTRAINT `user_info_main_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info_basic` (`user_id`),
  CONSTRAINT `user_info_main_ibfk_2` FOREIGN KEY (`college_id`) REFERENCES `college_list` (`college_id`),
  CONSTRAINT `user_info_main_ibfk_3` FOREIGN KEY (`profession_id`) REFERENCES `profession_list` (`profession_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info_main
-- ----------------------------
INSERT INTO `user_info_main` VALUES ('22', '201614590130', '魏兴源', '18', '76');

-- ----------------------------
-- Table structure for user_info_wechat
-- ----------------------------
DROP TABLE IF EXISTS `user_info_wechat`;
CREATE TABLE `user_info_wechat` (
  `user_id` int(10) unsigned NOT NULL,
  `openid` char(30) NOT NULL,
  `unionid` char(30) DEFAULT NULL,
  `nickname` char(20) DEFAULT NULL,
  `sex` tinyint(4) DEFAULT NULL,
  `city` char(20) DEFAULT NULL,
  `country` char(20) DEFAULT NULL,
  `province` char(10) DEFAULT NULL,
  `language` char(10) DEFAULT NULL,
  `headimgurl` char(152) DEFAULT NULL,
  `subscribe_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` char(30) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `user_info_wechat_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user_info_basic` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info_wechat
-- ----------------------------
INSERT INTO `user_info_wechat` VALUES ('22', 'oHb0a1iDVrtVPiyPgAz7PEAyurvo', null, '灰啊灰啊飞！', '2', '南平', '中国', '福建', 'zh_CN', 'http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKlz9j1LBNR8uPaONuYV8MtFzN5ibj05zKgkAMBNfPTYg96l0rSmepPiciaOWnjD2qPibBYicuXPhb7IWw/0', null, null);
SET FOREIGN_KEY_CHECKS=1;
