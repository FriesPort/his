-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        9.0.0 - MySQL Community Server - GPL
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  12.7.0.6850
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- 导出  表 hospital.all_id 结构
CREATE TABLE IF NOT EXISTS `all_id` (
  `id` int NOT NULL AUTO_INCREMENT,
  `patient_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `campus_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `office_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ward_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `hospital` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `doctor_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.all_id 的数据：~0 rows (大约)

-- 导出  表 hospital.beds 结构
CREATE TABLE IF NOT EXISTS `beds` (
  `bed_id` bigint NOT NULL AUTO_INCREMENT,
  `patient_id` bigint DEFAULT NULL,
  `room_id` bigint DEFAULT NULL,
  `bed_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `bed_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '空闲',
  PRIMARY KEY (`bed_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.beds 的数据：~19 rows (大约)
REPLACE INTO `beds` (`bed_id`, `patient_id`, `room_id`, `bed_type`, `bed_number`, `status`) VALUES
	(2, NULL, 1, '男床', '02床', '空闲'),
	(3, 7, 1, '男床', '03床', '占用'),
	(4, NULL, 2, '女床', '01床', '空闲'),
	(5, 48, 2, '女床', '02床', '占用'),
	(11, 14, 1, '女床', '06床', '占用'),
	(13, NULL, 1, '男床', '04床', '空闲'),
	(14, NULL, 1, '男床', '05床', '空闲'),
	(17, NULL, 1, '正床', '01床', '空闲'),
	(18, NULL, 3, '正床', '07床', '空闲'),
	(22, NULL, 1, '正床', '07床', '空闲'),
	(23, NULL, 1, '女床', '201床', '空闲'),
	(24, NULL, 11, '女床', '101床', '空闲'),
	(25, NULL, 11, '女床', '02床', '空闲'),
	(26, NULL, 11, '女床', '01床', '空闲'),
	(27, NULL, 22, '正床', '01床', '空闲'),
	(28, NULL, 6, '男床', '01床', '空闲'),
	(29, NULL, 1, '正床', NULL, '空闲'),
	(30, NULL, 2, '正床', '04床', '空闲'),
	(31, NULL, 2, '正床', '08床', '空闲');

-- 导出  表 hospital.buildings 结构
CREATE TABLE IF NOT EXISTS `buildings` (
  `building_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `building_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `campus_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`building_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.buildings 的数据：~0 rows (大约)

-- 导出  表 hospital.campus 结构
CREATE TABLE IF NOT EXISTS `campus` (
  `campus_id` bigint NOT NULL AUTO_INCREMENT,
  `hospital_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `map_package` blob,
  `theme_package` blob,
  `authorization_status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`campus_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.campus 的数据：~3 rows (大约)
REPLACE INTO `campus` (`campus_id`, `hospital_id`, `name`, `map_package`, `theme_package`, `authorization_status`) VALUES
	(1, NULL, '广州院区', NULL, NULL, NULL),
	(2, NULL, '深圳院区', NULL, NULL, NULL),
	(3, NULL, '珠海院区', NULL, NULL, NULL);

-- 导出  表 hospital.customers 结构
CREATE TABLE IF NOT EXISTS `customers` (
  `customer_id` bigint NOT NULL AUTO_INCREMENT,
  `hospital_id` bigint DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `authorization_file` blob,
  `authorization_date` date DEFAULT NULL,
  PRIMARY KEY (`customer_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.customers 的数据：~0 rows (大约)

-- 导出  表 hospital.floordetails 结构
CREATE TABLE IF NOT EXISTS `floordetails` (
  `floor_detail_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `floor_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `total_floor_count` int DEFAULT NULL,
  PRIMARY KEY (`floor_detail_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.floordetails 的数据：~0 rows (大约)

-- 导出  表 hospital.floors 结构
CREATE TABLE IF NOT EXISTS `floors` (
  `floor_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `floor_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `building_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `floor_level` int DEFAULT NULL,
  PRIMARY KEY (`floor_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.floors 的数据：~0 rows (大约)

-- 导出  表 hospital.hospitals 结构
CREATE TABLE IF NOT EXISTS `hospitals` (
  `hospital_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `region` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `logo` blob,
  `quota` int DEFAULT NULL,
  `authorization_status` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`hospital_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.hospitals 的数据：~0 rows (大约)

-- 导出  表 hospital.illness 结构
CREATE TABLE IF NOT EXISTS `illness` (
  `ill_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `ill_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `office_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ill_msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ill_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.illness 的数据：~0 rows (大约)

-- 导出  表 hospital.import_records 结构
CREATE TABLE IF NOT EXISTS `import_records` (
  `record_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `import_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `import_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `table_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `row_count` int DEFAULT NULL,
  `success_count` int DEFAULT NULL,
  `fail_count` int DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  PRIMARY KEY (`record_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.import_records 的数据：~0 rows (大约)

-- 导出  表 hospital.logs 结构
CREATE TABLE IF NOT EXISTS `logs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `model` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `userid` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `message` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `time` date NOT NULL,
  `controller_ip` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.logs 的数据：~0 rows (大约)

-- 导出  表 hospital.office 结构
CREATE TABLE IF NOT EXISTS `office` (
  `office_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `campus_id` bigint DEFAULT NULL,
  PRIMARY KEY (`office_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.office 的数据：~6 rows (大约)
REPLACE INTO `office` (`office_id`, `name`, `campus_id`) VALUES
	(1, '内科', 1),
	(2, '外科', 1),
	(3, 's内科', 2),
	(4, 's外科', 2),
	(5, 'z内科', 3),
	(6, 'z外科', 3);

-- 导出  表 hospital.patient_information 结构
CREATE TABLE IF NOT EXISTS `patient_information` (
  `patient_id` bigint NOT NULL AUTO_INCREMENT COMMENT '患者id',
  `name` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '患者姓名',
  `gender` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '患者性别',
  `age` int DEFAULT NULL COMMENT '患者年龄',
  `address` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '患者地址',
  `phone` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '患者手机号',
  `admission_number` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '入院号',
  `admission_type` varchar(10) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '入院类型',
  `admission_time` datetime DEFAULT NULL COMMENT '入院时间',
  `discharge_time` datetime DEFAULT NULL COMMENT '出院时间',
  `book_type` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '预约类型',
  `is_emergency` int DEFAULT '0' COMMENT '是否急诊（0否，1是）',
  `is_vip` int DEFAULT '0' COMMENT '是否vip（0否，1是）',
  `is_acute` int DEFAULT '0' COMMENT '是否重症',
  `economic_impact` int DEFAULT '0' COMMENT '经济效益',
  `academic_value` int DEFAULT '0' COMMENT '学术价值',
  `illness` varchar(30) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '疾病类型',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `room_number_requirement` int DEFAULT NULL COMMENT '患者房间人数需求',
  `room_type_requirement` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '患者房间类型需求',
  `room_gender_requirement` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '患者性别类型需求',
  `ward_id` bigint DEFAULT NULL COMMENT '病区id',
  `office_id` bigint DEFAULT NULL COMMENT '科室id',
  `campus_id` bigint DEFAULT NULL COMMENT '院区id',
  `doctor` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '主治医生',
  `nurse` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '责任护士',
  `status` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT '待入院' COMMENT '患者状态（“待入院”“已入院”“已出院”）',
  `del` int DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`patient_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.patient_information 的数据：~18 rows (大约)
REPLACE INTO `patient_information` (`patient_id`, `name`, `gender`, `age`, `address`, `phone`, `admission_number`, `admission_type`, `admission_time`, `discharge_time`, `book_type`, `is_emergency`, `is_vip`, `is_acute`, `economic_impact`, `academic_value`, `illness`, `create_time`, `update_time`, `room_number_requirement`, `room_type_requirement`, `room_gender_requirement`, `ward_id`, `office_id`, `campus_id`, `doctor`, `nurse`, `status`, `del`) VALUES
	(1, '丁香', '女', 99, '广州航海学院', '13613606672', 'NB11', '急诊', '2024-04-29 20:28:59', NULL, '线下预约', 1, 1, 1, 2, 1, 'null', '2024-05-06 13:06:47', NULL, 6, '普通病房', '女性病房', 1, 1, 1, '黄奕湛', NULL, '已入院', 1),
	(6, '青莲', '男', 18, '广州航海学院', '13722600751', 'NB12', '门诊', '2024-04-29 20:28:59', '2024-05-14 00:59:27', '线上预约', 0, 1, 1, 2, 1, '精神病', '2024-05-06 13:06:50', NULL, 6, '普通病房', '男性病房', 1, 1, 1, '黄奕湛', NULL, '已出院', 0),
	(7, '李白', '男', 17, '太医院', '13613606671', 'NB13', '急诊', '2024-04-29 20:28:59', NULL, '', 1, 1, 0, 2, 1, '夜盲症', '2024-05-06 13:06:52', '2024-05-14 02:03:30', 6, '男性病房', '男性病房', 1, 1, 1, '黄奕湛', NULL, '已入院', 0),
	(8, '张三丰', '男', 44, NULL, '187655609987', 'NB14', '门诊', '2024-05-11 14:13:43', '2024-05-18 16:28:14', '线下预约', 1, 1, 0, 2, 1, '白化病', '2024-05-01 13:13:17', '2024-05-11 14:13:43', NULL, '普通病房', '男性病房', 1, 1, 1, NULL, NULL, '已出院', 0),
	(13, '杜甫', '男', 999, '武夷山', '11111135271', 'NB15', '门诊', '2024-05-10 21:36:20', '2024-05-18 16:28:24', '线下预约', 0, 0, 0, NULL, NULL, '老年痴呆', '2024-05-10 15:52:45', '2024-05-10 21:36:20', 6, '隔离病房', '男性病房', 1, 1, 1, '黄奕湛', NULL, '已出院', 0),
	(14, '兰陵王', '女', -1, '兰陵', '00000000000', 'NB16', '门诊', '2024-05-14 01:38:33', '2024-05-14 01:14:15', '线下预约', 1, 1, 1, NULL, NULL, '老年痴呆', '2024-05-10 15:52:45', '2024-05-14 01:38:33', 6, '普通病房', '女性病房', 1, 1, 1, '黄奕湛', NULL, '已入院', 0),
	(17, '嬴政', '男', 999, '大秦', '11111135272', 'A001001000017', '急诊', '2024-05-12 22:00:00', '2024-05-13 15:03:16', '', 1, 0, 1, NULL, NULL, 'null', '2024-05-12 15:35:42', '2024-05-14 02:18:23', 1, '普通病房', '女性病房', 1, 1, 1, '黄奕湛', NULL, '待入院', 0),
	(18, '嬴政', '女', 999, '大秦', '11111135272', 'A001001000017', '急诊', '2024-05-18 03:11:08', '2024-08-04 16:40:11', '', 0, 0, 0, NULL, NULL, 'null', '2024-05-12 15:35:42', '2024-05-18 03:11:08', 6, '普通病房', '女性病房', 2, 1, 1, '黄奕湛', NULL, '已出院', 0),
	(19, '嬴政', '男', 999, '大秦', '11111135272', 'A001001000017', '急诊', '2024-05-12 22:00:00', '2024-05-13 15:03:16', '线下预约', 1, 0, 1, NULL, NULL, 'null', '2024-05-12 15:35:42', '2024-05-12 22:00:00', 6, '普通病房', '男性病房', 1, 1, 1, '黄奕湛', NULL, '待入院', 1),
	(40, '你好', '男', 12, NULL, '12345678910', NULL, '预约入院', NULL, NULL, '线上预约', 0, 0, 0, 0, 0, '没病', '2024-05-14 02:19:34', '2024-05-14 02:19:34', 1, '普通病房', '男性病房', NULL, NULL, NULL, '老李', NULL, '待入院', 0),
	(41, 'text', '女', 12, NULL, '12345678910', NULL, '预约入院', NULL, NULL, '线上预约', 0, 0, 0, 0, 0, '无', '2024-05-18 03:12:13', '2024-05-18 03:12:28', NULL, '普通病房', '女性病房', 1, 1, 1, '11', NULL, '待入院', 0),
	(42, '1111', '男', 1111, NULL, '12345678910', NULL, '预约入院', NULL, NULL, '线下预约', 0, 0, 0, 0, 0, '111', '2024-05-18 03:58:11', '2024-05-18 03:58:25', NULL, '普通病房', '男性病房', 4, 2, 1, '111', NULL, '待入院', 0),
	(43, '1', '男', 1, NULL, '11111111111', NULL, '预约入院', NULL, NULL, '线上预约', 0, 0, 0, 0, 0, '1', '2024-05-18 14:07:56', '2024-05-18 14:07:56', NULL, '普通病房', '男性病房', 1, 3, 2, '1', NULL, '待入院', 0),
	(44, '11', '男', 1, NULL, '11111111111', NULL, '预约入院', NULL, NULL, '线上预约', 0, 0, 0, 0, 0, '1', '2024-05-18 14:08:54', '2024-05-18 14:08:54', NULL, '普通病房', '男性病房', 1, 5, 3, '1', NULL, '待入院', 0),
	(45, 'af', '男', 12, NULL, '13823900031', NULL, '预约入院', NULL, NULL, '线上预约', 0, 0, 0, 0, 0, '12', '2024-08-04 14:21:25', '2024-08-04 14:25:32', 4, '普通病房', '男性病房', 1, 3, 2, 'diandian', NULL, '待入院', 0),
	(46, 'huaz', '男', 12, NULL, '12345678901', NULL, '预约入院', NULL, NULL, '线上预约', 0, 0, 0, 0, 0, 'qq', '2024-08-04 14:23:37', '2024-08-04 14:23:37', 1, '普通病房', '男性病房', 1, 1, 1, 'aqaq', NULL, '待入院', 0),
	(47, 'qqqqaa', '男', 44, NULL, '12345678901', NULL, '预约入院', NULL, NULL, '线上预约', 0, 0, 0, 0, 0, 'fggs', '2024-08-04 14:24:12', '2024-08-04 14:24:12', 1, '普通病房', '男性病房', 1, 3, 2, 'fafafggh', NULL, '待入院', 0),
	(48, 'eee', '女', 12, NULL, '12345678901', NULL, '预约入院', '2024-08-04 14:27:21', NULL, '线上预约', 0, 0, 0, 0, 0, 'qqqzzz', '2024-08-04 14:27:13', '2024-08-04 14:27:21', NULL, '普通病房', '女性病房', 2, 1, 1, 'wqw', NULL, '已入院', 0);

-- 导出  表 hospital.permissions 结构
CREATE TABLE IF NOT EXISTS `permissions` (
  `permission_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `permission_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `permission_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`permission_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.permissions 的数据：~4 rows (大约)
REPLACE INTO `permissions` (`permission_id`, `permission_name`, `permission_type`) VALUES
	('ac', '床位分配', '对床位进行分配'),
	('b1', '床位管理', '对床位进行管理'),
	('p1', '用户管理', '对用户进行管理'),
	('pt1', '患者管理', '对患者进行管理');

-- 导出  表 hospital.roles 结构
CREATE TABLE IF NOT EXISTS `roles` (
  `role_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.roles 的数据：~5 rows (大约)
REPLACE INTO `roles` (`role_id`, `role_name`, `description`) VALUES
	('1b6cc7bd-9585-4a82-9b29-2457db728c5f', '床位管理员', '拥有对床位的增加、删除、和查看权限'),
	('4c92bf25-4279-4e67-b45e-058ca794efbd', '床位调度员', '拥有对床位进行分配与释放的权限'),
	('58ed6486-2ae9-4ccf-b709-6b903a21674d', '患者管理员', '拥有患者的增删改查权限'),
	('5935db24-4cbc-4404-a4ad-7a63ea400f76', '超级管理员', '拥有全部权限'),
	('ce06df54-07c7-4cd4-9de2-ca6215ff48df', '系统管理员', '拥有对用户及角色的所有权限');

-- 导出  表 hospital.role_permissions 结构
CREATE TABLE IF NOT EXISTS `role_permissions` (
  `rp_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `role_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `permission_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`rp_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.role_permissions 的数据：~8 rows (大约)
REPLACE INTO `role_permissions` (`rp_id`, `role_id`, `permission_id`) VALUES
	('06c7ffd4-2c77-4ac2-b8c9-17b5556751a7', '4c92bf25-4279-4e67-b45e-058ca794efbd', 'ac'),
	('235f21c2-369e-497a-9afb-1105a545b95d', '5935db24-4cbc-4404-a4ad-7a63ea400f76', 'pt1'),
	('48f1b27d-4772-4716-a42e-caf44acf0306', '58ed6486-2ae9-4ccf-b709-6b903a21674d', 'pt1'),
	('58283ad3-b085-4c19-ac9e-4814d9c30173', '1b6cc7bd-9585-4a82-9b29-2457db728c5f', 'b1'),
	('81424db1-6c92-4922-9c90-d0c5e137bd05', '5935db24-4cbc-4404-a4ad-7a63ea400f76', 'ac'),
	('af7d5dc4-9113-4812-a3a5-ed22c703e2cc', 'ce06df54-07c7-4cd4-9de2-ca6215ff48df', 'p1'),
	('c24da31c-1801-4584-b306-c3069c51401e', '5935db24-4cbc-4404-a4ad-7a63ea400f76', 'p1'),
	('f8c386ed-19ef-4e29-a892-e686cf2f0d5c', '5935db24-4cbc-4404-a4ad-7a63ea400f76', 'b1');

-- 导出  表 hospital.rooms 结构
CREATE TABLE IF NOT EXISTS `rooms` (
  `room_id` bigint NOT NULL AUTO_INCREMENT,
  `room_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '病房编号',
  `room_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '病房类型',
  `room_gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '病房性别',
  `bed_count` bigint DEFAULT NULL COMMENT '床位数量',
  `ward_id` bigint DEFAULT NULL COMMENT '病区id',
  `office_id` bigint DEFAULT NULL COMMENT '科室id',
  `campus_id` bigint DEFAULT NULL COMMENT '院区id',
  `floors_id` bigint DEFAULT NULL COMMENT '楼层id',
  PRIMARY KEY (`room_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.rooms 的数据：~21 rows (大约)
REPLACE INTO `rooms` (`room_id`, `room_number`, `room_type`, `room_gender`, `bed_count`, `ward_id`, `office_id`, `campus_id`, `floors_id`) VALUES
	(1, '101房', '普通病房', '混合病房', 9, 1, 1, 1, 1),
	(2, '102房', '普通病房', '混合病房', 4, 1, 1, 1, 1),
	(3, '103房', '普通病房', '混合病房', 1, 2, 1, 1, 1),
	(4, '101房', '隔离病房', '男性病房', 0, 4, 2, 1, 1),
	(5, '102房', '隔离病房', '女性病房', 0, 5, 2, 1, 1),
	(6, '101房', '隔离病房', '男性病房', 1, 7, 3, 2, 1),
	(7, '102房', '隔离病房', '混合病房', 0, 7, 3, 2, 1),
	(8, '103房', '隔离病房', '男性病房', 0, 8, 3, 2, 1),
	(9, '104房', '隔离病房', '女性病房', 0, 8, 3, 2, 1),
	(10, '105房', '隔离病房', '男性病房', 0, 9, 3, 2, 1),
	(11, '101房', '隔离病房', '女性病房', 3, 10, 4, 2, 1),
	(12, '102房', 'ICU', '混合病房', 0, 10, 4, 2, 1),
	(13, '103房', 'ICU', '男性病房', 0, 11, 4, 2, 1),
	(14, '105房', 'ICU', '混合病房', 0, 12, 4, 2, 1),
	(15, '101房', 'ICU', '女性病房', 0, 13, 5, 3, 1),
	(17, '102房', 'ICU', '男性病房', 0, 14, 5, 3, 1),
	(18, '103房', 'ICU', '混合病房', 0, 14, 5, 3, 1),
	(19, '101房', 'ICU', '混合病房', 0, 15, 6, 3, 1),
	(20, '102房', 'ICU', '女性病房', 0, 15, 6, 3, 1),
	(21, '103房', 'ICU', '男性病房', 0, 15, 6, 3, 1),
	(22, '104房', 'ICU', '混合病房', 1, 15, 6, 3, 1);

-- 导出  表 hospital.room_user 结构
CREATE TABLE IF NOT EXISTS `room_user` (
  `ru_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `room_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `user_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`ru_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.room_user 的数据：~0 rows (大约)

-- 导出  表 hospital.token 结构
CREATE TABLE IF NOT EXISTS `token` (
  `id` int NOT NULL AUTO_INCREMENT,
  `token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `create_time` date DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `over_time` date DEFAULT NULL,
  `role_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.token 的数据：~0 rows (大约)

-- 导出  表 hospital.users 结构
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `employee_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `hospital_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `campus_id` int DEFAULT NULL,
  `user_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `Enable` int DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `employee_number` (`employee_number`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.users 的数据：~5 rows (大约)
REPLACE INTO `users` (`user_id`, `username`, `employee_number`, `password`, `name`, `hospital_id`, `campus_id`, `user_type`, `status`, `create_time`, `update_time`, `Enable`, `phone`) VALUES
	('22798a2d-7579-48d4-a283-8feef806d8ec', 'huanguan', 'E417673', '$2a$10$eFYuvZz.2FJhm4WXRxV.IeFO7iruGnmTS7UMrckH6kdYrRx5TBkkG', '患者管理员', 'H789', 1, NULL, 'active', '2024-05-18 01:49:40', NULL, 1, '10086111008'),
	('5067719e-f0f5-4679-933d-8a64c383008a', 'fenpeiyuan', 'E214757', '$2a$10$jRBvBxAqIwz61fAXvgygB.h8lh3mRnYVtBwP/c4plz7rWGA/BISLm', '床位分配员', 'H789', 1, NULL, 'active', '2024-05-18 01:54:53', NULL, 1, '12345676543'),
	('a433e299-8ab4-4a9d-ba52-f91314dca315', 'xiguan', 'E572859', '$2a$10$vncYH9l.cIJ/8wFRqdNv2ei8XjoFxJ007UhB1KaNVmou2Xk6x1LYW', '系统管理员', 'H789', 1, NULL, 'active', '2024-05-18 01:46:27', NULL, 1, '12345678910'),
	('a5c3bec6-8578-4dfd-bccb-abbb12a9268d', 'chuangguan', 'E234831', '$2a$10$tPXCkz1dP.Dd8YxQ8lCmj.vFbuk9HWq5VFUC/fVGYs2of5.2nIZxy', '床位管理员', 'H789', 1, NULL, 'active', '2024-05-18 01:48:25', NULL, 1, '10987654321'),
	('d0e3c968-c470-4fb3-af6f-2ba1cf18a8c2', 'hua', 'E806798', '$2a$10$2wp/di7X5P23s3IiBmWbbe8OhyfbX.7FYnSiF/erFj9pnG8zS2C.y', '超级管理员', 'H789', 3, 'employee', 'active', '2024-08-04 09:11:01', NULL, 1, '123425234');

-- 导出  表 hospital.user_roles 结构
CREATE TABLE IF NOT EXISTS `user_roles` (
  `user_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `role_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ur_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`ur_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.user_roles 的数据：~7 rows (大约)
REPLACE INTO `user_roles` (`user_id`, `role_id`, `ur_id`) VALUES
	('a433e299-8ab4-4a9d-ba52-f91314dca315', 'ce06df54-07c7-4cd4-9de2-ca6215ff48df', '0c899c11-78dc-4c53-8cbf-3695acdd1110'),
	('a5c3bec6-8578-4dfd-bccb-abbb12a9268d', '1b6cc7bd-9585-4a82-9b29-2457db728c5f', '1f710b5c-8615-4296-acc5-664d88690aaf'),
	('d0e3c968-c470-4fb3-af6f-2ba1cf18a8c2', '5935db24-4cbc-4404-a4ad-7a63ea400f76', '3fcb5f8e-188a-42f6-9d3b-ec4a63ea2079'),
	('22798a2d-7579-48d4-a283-8feef806d8ec', '58ed6486-2ae9-4ccf-b709-6b903a21674d', '69ef1409-1236-41ed-9237-61b68b885313'),
	('5067719e-f0f5-4679-933d-8a64c383008a', '4c92bf25-4279-4e67-b45e-058ca794efbd', '74a0331c-d674-43ea-95c2-6422cd1924ae'),
	('22798a2d-7579-48d4-a283-8feef806d8ec', '4c92bf25-4279-4e67-b45e-058ca794efbd', 'ce3c702f-e795-4f79-98c9-7a1ca0cd5e12'),
	('7c38f3a6-5caa-46d9-85bf-d4d6a4de34df', '5935db24-4cbc-4404-a4ad-7a63ea400f76', 'ur1');

-- 导出  表 hospital.ward 结构
CREATE TABLE IF NOT EXISTS `ward` (
  `ward_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `office_id` bigint DEFAULT NULL,
  `room_count` int DEFAULT NULL,
  `bed_count` int DEFAULT NULL,
  PRIMARY KEY (`ward_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC;

-- 正在导出表  hospital.ward 的数据：~16 rows (大约)
REPLACE INTO `ward` (`ward_id`, `name`, `code`, `office_id`, `room_count`, `bed_count`) VALUES
	(1, '内科一区', NULL, 1, NULL, NULL),
	(2, '内科二区', NULL, 1, NULL, NULL),
	(3, '内科三区', NULL, 1, NULL, NULL),
	(4, '外科一区', NULL, 2, NULL, NULL),
	(5, '外科二区', NULL, 2, NULL, NULL),
	(6, '外科三区', NULL, 2, NULL, NULL),
	(7, '内科一区', NULL, 3, NULL, NULL),
	(8, '内科二区', NULL, 3, NULL, NULL),
	(9, '内科三区', NULL, 3, NULL, NULL),
	(10, '外科一区', NULL, 4, NULL, NULL),
	(11, '外科二区', NULL, 4, NULL, NULL),
	(12, '外科三区', NULL, 4, NULL, NULL),
	(13, '内科一区', NULL, 5, NULL, NULL),
	(14, '内科二区', NULL, 5, NULL, NULL),
	(15, '外科一区', NULL, 6, NULL, NULL),
	(16, '外科二区', NULL, 6, NULL, NULL);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
