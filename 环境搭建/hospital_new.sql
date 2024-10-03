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

-- 导出  表 hospital2.all_id 结构
CREATE TABLE IF NOT EXISTS `all_id` (
  `id` varchar(32) NOT NULL COMMENT '自动递增的主键',
  `campus_id` varchar(32) NOT NULL COMMENT '院区编号',
  `office_id` varchar(32) NOT NULL COMMENT '科室编号',
  `ward_id` varchar(32) NOT NULL COMMENT '病区编号',
  `hospital` varchar(32) NOT NULL COMMENT '医院名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.all_id 的数据：~0 rows (大约)
REPLACE INTO `all_id` (`id`, `campus_id`, `office_id`, `ward_id`, `hospital`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('1', '第一院区', '内科', '内科一病区', '友好医院', '2024-09-26 12:00:00', '管理员', NULL, NULL),
	('2', '第二院区', '外科', '外科二病区', '仁爱医院', '2024-09-26 12:01:00', '管理员', NULL, NULL),
	('3', '第三院区', '妇产科', '妇产科三病区', '协和医院', '2024-09-26 12:02:00', '管理员', NULL, NULL),
	('4', '第四院区', '儿科', '儿科四病区', '阳光医院', '2024-09-26 12:03:00', '管理员', NULL, NULL),
	('5', '第五院区', '眼科', '眼科五病区', '中心医院', '2024-09-26 12:04:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.bed 结构
CREATE TABLE IF NOT EXISTS `bed` (
  `id` varchar(32) NOT NULL COMMENT '自动递增的主键',
  `patient_id` varchar(32) NOT NULL DEFAULT '0' COMMENT '关联患者编号-外键',
  `room_id` varchar(32) NOT NULL COMMENT '关联病房编号-外键',
  `type` varchar(255) NOT NULL COMMENT '床位类型',
  `number` varchar(255) NOT NULL COMMENT '床位编号',
  `is_null` tinyint NOT NULL COMMENT '0表示空闲，1表示占用，默认值为0，表示床位状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.bed 的数据：~0 rows (大约)
REPLACE INTO `bed` (`id`, `patient_id`, `room_id`, `type`, `number`, `is_null`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('b1', '0', 'r1', '普通床位', '1 号床', 0, '2024-09-26 12:05:00', '管理员', NULL, NULL),
	('b2', '0', 'r2', '单人床位', '2 号床', 0, '2024-09-26 12:06:00', '管理员', NULL, NULL),
	('b3', 'p1', 'r3', '高级床位', '3 号床', 1, '2024-09-26 12:07:00', '管理员', NULL, NULL),
	('b4', 'p2', 'r4', '重症床位', '4 号床', 1, '2024-09-26 12:08:00', '管理员', NULL, NULL),
	('b5', '0', 'r5', '儿科床位', '5 号床', 0, '2024-09-26 12:09:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.bedlog 结构
CREATE TABLE IF NOT EXISTS `bedlog` (
  `id` varchar(32) NOT NULL COMMENT '自动递增的主键',
  `bed_id` varchar(32) NOT NULL COMMENT '病床id-外键',
  `patient_information_id` varchar(32) NOT NULL COMMENT '病人id-外键',
  `is_using` bit(1) NOT NULL COMMENT '是否占用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.bedlog 的数据：~5 rows (大约)
REPLACE INTO `bedlog` (`id`, `bed_id`, `patient_information_id`, `is_using`, `create_time`, `create_by`, `update_time`, `update_by`, `remark`) VALUES
	('bl1', 'b1', 'p4', b'0', '2024-09-26 13:58:00', '管理员', NULL, NULL, '床位空闲'),
	('bl2', 'b2', '0', b'0', '2024-09-26 13:59:00', '管理员', NULL, NULL, '床位空闲'),
	('bl3', 'b3', 'p1', b'1', '2024-09-26 14:00:00', '管理员', NULL, NULL, '床位占用'),
	('bl4', 'b4', 'p2', b'1', '2024-09-26 14:01:00', '管理员', NULL, NULL, '床位占用'),
	('bl5', 'b5', 'p3', b'1', '2024-09-26 14:02:00', '管理员', NULL, NULL, '床位占用');

-- 导出  表 hospital2.building 结构
CREATE TABLE IF NOT EXISTS `building` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '楼栋名称',
  `campus_id` varchar(32) DEFAULT NULL COMMENT '关联院区编号-外键',
  `is_use` tinyint(1) DEFAULT NULL COMMENT '是否可用',
  `total_floor_count` int DEFAULT NULL COMMENT '楼层总数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.building 的数据：~5 rows (大约)
REPLACE INTO `building` (`id`, `name`, `campus_id`, `is_use`, `total_floor_count`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('bld1', '门诊大楼', '第一院区', 1, 10, '2024-09-26 12:10:00', '管理员', NULL, NULL),
	('bld2', '住院部大楼', '第二院区', 1, 8, '2024-09-26 12:11:00', '管理员', NULL, NULL),
	('bld3', '医技楼', '第三院区', 0, 6, '2024-09-26 12:12:00', '管理员', NULL, NULL),
	('bld4', '行政楼', '第四院区', 1, 5, '2024-09-26 12:13:00', '管理员', NULL, NULL),
	('bld5', '后勤楼', '第五院区', 1, 7, '2024-09-26 12:14:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.campus 结构
CREATE TABLE IF NOT EXISTS `campus` (
  `id` varchar(32) NOT NULL COMMENT '自动递增的主键',
  `hospital_id` varchar(32) DEFAULT NULL COMMENT '关联医院编号-外键',
  `name` varchar(255) DEFAULT NULL COMMENT '院区名称',
  `map_package` blob COMMENT '二进制大对象',
  `theme_package` blob COMMENT '二进制大对象',
  `is_authorization` tinyint(1) DEFAULT NULL COMMENT '布尔类型授权状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.campus 的数据：~0 rows (大约)
REPLACE INTO `campus` (`id`, `hospital_id`, `name`, `map_package`, `theme_package`, `is_authorization`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('c1', '友好医院', '第一院区', NULL, NULL, 1, '2024-09-26 12:15:00', '管理员', NULL, NULL),
	('c2', '仁爱医院', '第二院区', NULL, NULL, 0, '2024-09-26 12:16:00', '管理员', NULL, NULL),
	('c3', '协和医院', '第三院区', NULL, NULL, 1, '2024-09-26 12:17:00', '管理员', NULL, NULL),
	('c4', '阳光医院', '第四院区', NULL, NULL, 1, '2024-09-26 12:18:00', '管理员', NULL, NULL),
	('c5', '中心医院', '第五院区', NULL, NULL, 0, '2024-09-26 12:19:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.customer 结构
CREATE TABLE IF NOT EXISTS `customer` (
  `id` varchar(32) NOT NULL COMMENT '自动递增的主键',
  `hospital_id` varchar(32) DEFAULT NULL COMMENT '关联医院编号-外键，关联院区是否更合适？',
  `name` varchar(255) DEFAULT NULL COMMENT '客户名称',
  `logo` blob COMMENT '客户标志',
  `authorization_file` blob COMMENT '二进制大对象',
  `authorization_date` date DEFAULT NULL COMMENT '授权日期',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.customer 的数据：~0 rows (大约)
REPLACE INTO `customer` (`id`, `hospital_id`, `name`, `logo`, `authorization_file`, `authorization_date`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('cust1', '友好医院', '医药供应商 A', NULL, NULL, '2024-09-01', '2024-09-26 12:20:00', '管理员', NULL, NULL),
	('cust2', '仁爱医院', '医疗器械公司 B', NULL, NULL, '2024-08-15', '2024-09-26 12:21:00', '管理员', NULL, NULL),
	('cust3', '协和医院', '医疗服务机构 C', NULL, NULL, '2024-07-20', '2024-09-26 12:22:00', '管理员', NULL, NULL),
	('cust4', '阳光医院', '健康管理公司 D', NULL, NULL, '2024-06-25', '2024-09-26 12:23:00', '管理员', NULL, NULL),
	('cust5', '中心医院', '康复中心 E', NULL, NULL, '2024-05-30', '2024-09-26 12:24:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.floor 结构
CREATE TABLE IF NOT EXISTS `floor` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `floor_name` varchar(255) DEFAULT NULL COMMENT '楼层名称',
  `building_id` varchar(32) DEFAULT NULL COMMENT '关联楼栋编号-外键',
  `floor_level` int DEFAULT NULL COMMENT '楼层级别',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.floor 的数据：~0 rows (大约)
REPLACE INTO `floor` (`id`, `floor_name`, `building_id`, `floor_level`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('f1', '一楼', 'bld1', 1, '2024-09-26 12:30:00', '管理员', NULL, NULL),
	('f2', '二楼', 'bld2', 2, '2024-09-26 12:31:00', '管理员', NULL, NULL),
	('f3', '三楼', 'bld3', 3, '2024-09-26 12:32:00', '管理员', NULL, NULL),
	('f4', '四楼', 'bld4', 4, '2024-09-26 12:33:00', '管理员', NULL, NULL),
	('f5', '五楼', 'bld5', 5, '2024-09-26 12:34:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.floordetail 结构
CREATE TABLE IF NOT EXISTS `floordetail` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `floor_id` varchar(32) DEFAULT NULL COMMENT '关联楼层编号-外键1对1？',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.floordetail 的数据：~0 rows (大约)
REPLACE INTO `floordetail` (`id`, `floor_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('fd1', 'f1', '2024-09-26 12:25:00', '管理员', NULL, NULL),
	('fd2', 'f2', '2024-09-26 12:26:00', '管理员', NULL, NULL),
	('fd3', 'f3', '2024-09-26 12:27:00', '管理员', NULL, NULL),
	('fd4', 'f4', '2024-09-26 12:28:00', '管理员', NULL, NULL),
	('fd5', 'f5', '2024-09-26 12:29:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.his_dict 结构
CREATE TABLE IF NOT EXISTS `his_dict` (
  `id` varchar(32) NOT NULL COMMENT '自动递增的主键',
  `name` varchar(100) NOT NULL COMMENT '字典名称',
  `code` varchar(100) NOT NULL COMMENT '字典编码',
  `description` varchar(255) DEFAULT NULL COMMENT '字典描述',
  `is_del` tinyint NOT NULL COMMENT '删除状态（0为未删除，1为已删除）',
  `type` tinyint NOT NULL COMMENT '字典类型（0为String，1为number）',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.his_dict 的数据：~0 rows (大约)
REPLACE INTO `his_dict` (`id`, `name`, `code`, `description`, `is_del`, `type`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('dict1', '性别字典', 'gender', '男/女', 0, 0, '2024-09-26 14:03:00', '管理员', NULL, NULL),
	('dict2', '入院类型字典', 'admission_type', '门诊转入/预约住院/急诊入院', 0, 0, '2024-09-26 14:04:00', '管理员', NULL, NULL),
	('dict3', '疾病类型字典', 'illness_type', '各种疾病分类', 0, 0, '2024-09-26 14:05:00', '管理员', NULL, NULL),
	('dict4', '预约类型字典', 'book_type', '线上预约/线下预约', 0, 0, '2024-09-26 14:06:00', '管理员', NULL, NULL),
	('dict5', '床位类型字典', 'bed_type', '普通床位/单人床位/高级床位/重症床位/儿科床位', 0, 0, '2024-09-26 14:07:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.hospital 结构
CREATE TABLE IF NOT EXISTS `hospital` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '医院名称',
  `region` varchar(255) DEFAULT NULL COMMENT '医院所在地区',
  `logo` blob COMMENT '医院标志',
  `quota` int DEFAULT NULL COMMENT '配额',
  `is_authorization` tinyint(1) DEFAULT NULL COMMENT '布尔类型授权状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.hospital 的数据：~0 rows (大约)
REPLACE INTO `hospital` (`id`, `name`, `region`, `logo`, `quota`, `is_authorization`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('h1', '友好医院', '城市 A', NULL, 1000, 1, '2024-09-26 12:35:00', '管理员', NULL, NULL),
	('h2', '仁爱医院', '城市 B', NULL, 800, 0, '2024-09-26 12:36:00', '管理员', NULL, NULL),
	('h3', '协和医院', '城市 C', NULL, 1200, 1, '2024-09-26 12:37:00', '管理员', NULL, NULL),
	('h4', '阳光医院', '城市 D', NULL, 900, 1, '2024-09-26 12:38:00', '管理员', NULL, NULL),
	('h5', '中心医院', '城市 E', NULL, 700, 0, '2024-09-26 12:39:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.illness 结构
CREATE TABLE IF NOT EXISTS `illness` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `ill_name` varchar(255) DEFAULT NULL COMMENT '疾病名称',
  `office_id` varchar(32) DEFAULT NULL COMMENT '关联科室编号',
  `description` varchar(255) DEFAULT NULL COMMENT '疾病信息',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.illness 的数据：~0 rows (大约)
REPLACE INTO `illness` (`id`, `ill_name`, `office_id`, `description`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('ill1', '感冒', '内科', '常见呼吸道疾病', '2024-09-26 12:40:00', '管理员', NULL, NULL),
	('ill2', '阑尾炎', '外科', '外科常见疾病', '2024-09-26 12:41:00', '管理员', NULL, NULL),
	('ill3', '妊娠高血压', '妇产科', '妇产科疾病', '2024-09-26 12:42:00', '管理员', NULL, NULL),
	('ill4', '小儿肺炎', '儿科', '儿科常见疾病', '2024-09-26 12:43:00', '管理员', NULL, NULL),
	('ill5', '近视', '眼科', '眼科疾病', '2024-09-26 12:44:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.import_record 结构
CREATE TABLE IF NOT EXISTS `import_record` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `file_name` varchar(255) DEFAULT NULL COMMENT '文件名',
  `import_status` varchar(255) DEFAULT NULL COMMENT '导入状态',
  `import_type` varchar(255) DEFAULT NULL COMMENT '导入类型',
  `table_name` varchar(255) DEFAULT NULL COMMENT '表名',
  `row_count` int DEFAULT NULL COMMENT '行数',
  `success_count` int DEFAULT NULL COMMENT '成功行数',
  `fail_count` int DEFAULT NULL COMMENT '失败行数',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.import_record 的数据：~0 rows (大约)
REPLACE INTO `import_record` (`id`, `file_name`, `import_status`, `import_type`, `table_name`, `row_count`, `success_count`, `fail_count`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('imp1', '患者数据.csv', '已完成', '患者数据', 'patient_information', 100, 95, 5, '2024-09-26 12:45:00', '管理员', NULL, NULL),
	('imp2', '病历记录.xlsx', '进行中', '病历数据', 'patient_hospitalization_record', 50, 0, 0, '2024-09-26 12:46:00', '管理员', NULL, NULL),
	('imp3', '员工数据.txt', '已完成', '员工数据', 'user', 200, 190, 10, '2024-09-26 12:47:00', '管理员', NULL, NULL),
	('imp4', '床位数据.xls', '已完成', '床位数据', 'bed', 500, 490, 10, '2024-09-26 12:48:00', '管理员', NULL, NULL),
	('imp5', '病区数据.csv', '已完成', '病区数据', 'ward', 20, 18, 2, '2024-09-26 12:49:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.log 结构
CREATE TABLE IF NOT EXISTS `log` (
  `id` varchar(32) NOT NULL COMMENT '自动递增的主键',
  `model` varchar(10) NOT NULL COMMENT '模型名称',
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
  `type` varchar(10) NOT NULL COMMENT '类型',
  `message` varchar(50) NOT NULL COMMENT '消息内容',
  `time` date NOT NULL COMMENT '日期',
  `controller_ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '控制器 IP',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.log 的数据：~0 rows (大约)
REPLACE INTO `log` (`id`, `model`, `user_id`, `type`, `message`, `time`, `controller_ip`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('log1', '患者模块', 'u1', '信息', '患者记录更新', '2024-09-26', '192.168.1.1', '2024-09-26 12:50:00', '管理员', NULL, NULL),
	('log2', '床位管理', 'u2', '警告', '床位分配失败', '2024-09-26', '192.168.1.2', '2024-09-26 12:51:00', '管理员', NULL, NULL),
	('log3', '用户访问', 'u3', '错误', '登录失败', '2024-09-26', '192.168.1.3', '2024-09-26 12:52:00', '管理员', NULL, NULL),
	('log4', '病历记录', 'u4', '信息', '新增记录', '2024-09-26', '192.168.1.4', '2024-09-26 12:53:00', '管理员', NULL, NULL),
	('log5', '权限管理', 'u5', '警告', '权限被拒', '2024-09-26', '192.168.1.5', '2024-09-26 12:54:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.office 结构
CREATE TABLE IF NOT EXISTS `office` (
  `id` varchar(32) NOT NULL COMMENT '自动递增的主键',
  `name` varchar(255) DEFAULT NULL COMMENT '科室名称',
  `campus_id` varchar(32) DEFAULT NULL COMMENT '关联院区编号-外键',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `phone` varchar(255) DEFAULT NULL COMMENT '科室电话',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.office 的数据：~0 rows (大约)
REPLACE INTO `office` (`id`, `name`, `campus_id`, `create_time`, `create_by`, `update_time`, `update_by`, `remark`, `phone`) VALUES
	('o1', '内科', '第一院区', '2024-09-26 12:55:00', '管理员', NULL, NULL, '内科疾病诊治', '1234567890'),
	('o2', '外科', '第二院区', '2024-09-26 12:56:00', '管理员', NULL, NULL, '外科手术治疗', '2345678901'),
	('o3', '妇产科', '第三院区', '2024-09-26 12:57:00', '管理员', NULL, NULL, '妇产科专科', '3456789012');

-- 导出  表 hospital2.patient_hospitalization_record 结构
CREATE TABLE IF NOT EXISTS `patient_hospitalization_record` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `patient_id` varchar(32) NOT NULL COMMENT '患者id-外键',
  `bed_id` varchar(32) NOT NULL COMMENT '床位id-外键',
  `admission_time` datetime NOT NULL COMMENT '入院时间',
  `discharge_time` datetime DEFAULT NULL COMMENT '出院时间',
  `admission_diagnosis` text COMMENT '入院诊断',
  `discharge_diagnosis` text COMMENT '出院诊断',
  `symptoms` text COMMENT '主要症状',
  `treatments` text COMMENT '医疗过程描述',
  `total_cost` decimal(20,2) NOT NULL COMMENT '住院总费用',
  `doctor_id` varchar(32) NOT NULL COMMENT '医生id-外键',
  `nurse_id` varchar(32) NOT NULL COMMENT '护士id-外键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.patient_hospitalization_record 的数据：~0 rows (大约)
REPLACE INTO `patient_hospitalization_record` (`id`, `patient_id`, `bed_id`, `admission_time`, `discharge_time`, `admission_diagnosis`, `discharge_diagnosis`, `symptoms`, `treatments`, `total_cost`, `doctor_id`, `nurse_id`) VALUES
	('phr1', 'p1', 'b3', '2024-09-25 10:00:00', NULL, '感冒症状，咳嗽、流涕', NULL, '咳嗽、流涕、乏力', '给予抗病毒药物、对症治疗', 500.00, 'd1', 'n1'),
	('phr2', 'p2', 'b4', '2024-09-24 15:00:00', NULL, '急性阑尾炎', NULL, '右下腹痛', '手术治疗', 3000.00, 'd2', 'n2'),
	('phr3', 'p3', 'b5', '2024-09-26 08:00:00', NULL, '妊娠高血压综合征', NULL, '头晕、血压升高', '降压、保胎治疗', 2000.00, 'd3', 'n3'),
	('phr4', 'p4', 'b1', '2024-09-23 12:00:00', NULL, '小儿肺炎', NULL, '发热、咳嗽、呼吸急促', '抗感染、止咳平喘治疗', 1500.00, 'd4', 'n4'),
	('phr5', 'p5', 'b2', '2024-09-22 14:00:00', NULL, '近视加重', NULL, '视力模糊', '配镜建议、注意用眼卫生', 200.00, 'd5', 'n5');

-- 导出  表 hospital2.patient_information 结构
CREATE TABLE IF NOT EXISTS `patient_information` (
  `id` varchar(32) NOT NULL COMMENT '自动递增的主键，患者 id',
  `name` varchar(10) NOT NULL COMMENT '患者姓名',
  `gender` tinyint NOT NULL COMMENT '0表示女患者，1表示男患者，患者性别',
  `age` int DEFAULT NULL COMMENT '患者年龄',
  `address` varchar(100) DEFAULT NULL COMMENT '患者地址',
  `identity` varchar(32) DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(30) DEFAULT NULL COMMENT '患者手机号',
  `admission_number` varchar(255) DEFAULT NULL COMMENT '入院号',
  `admission_type` varchar(10) DEFAULT NULL COMMENT '入院类型',
  `admission_time` datetime DEFAULT NULL COMMENT '入院时间',
  `discharge_time` datetime DEFAULT NULL COMMENT '出院时间',
  `bed_id` varchar(32) DEFAULT NULL COMMENT '床位id-外键',
  `book_type` tinyint DEFAULT NULL COMMENT '0代表线上预约，1代表线下预约，预约类型',
  `is_emergency` tinyint NOT NULL DEFAULT '0' COMMENT '默认值为 0，表示是否急诊（0 否，1 是）',
  `is_vip` tinyint NOT NULL DEFAULT '0' COMMENT '默认值为 0，表示是否 vip（0 否，1 是）',
  `is_acute` tinyint NOT NULL DEFAULT '0' COMMENT '默认值为 0，表示是否重症',
  `is_inhospital` tinyint NOT NULL DEFAULT '0' COMMENT '默认值为0，表示是否在住院（0是，1否）',
  `preassignbed` varchar(32) DEFAULT NULL COMMENT '预分配床位号,为0表示未进行预分配',
  `illness` varchar(30) DEFAULT NULL COMMENT '疾病类型',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.patient_information 的数据：~0 rows (大约)

-- 导出  表 hospital2.permission 结构
CREATE TABLE IF NOT EXISTS `permission` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '权限名称',
  `is_datarule` tinyint NOT NULL COMMENT '是否启用数据权限，0为否，1为是，默认为0',
  `is_use` tinyint NOT NULL COMMENT '权限是否可用，0为否，1为是，默认为1',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.permission 的数据：~0 rows (大约)
REPLACE INTO `permission` (`id`, `name`, `is_datarule`, `is_use`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('perm1', '查看患者信息', 0, 1, '2024-09-26 13:10:00', '管理员', NULL, NULL),
	('perm2', '修改患者信息', 0, 1, '2024-09-26 13:11:00', '管理员', NULL, NULL),
	('perm3', '分配床位', 0, 1, '2024-09-26 13:12:00', '管理员', NULL, NULL),
	('perm4', '查看医疗记录', 0, 1, '2024-09-26 13:13:00', '管理员', NULL, NULL),
	('perm5', '管理科室', 0, 1, '2024-09-26 13:14:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.permission_data_rule 结构
CREATE TABLE IF NOT EXISTS `permission_data_rule` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `permission_id` varchar(32) NOT NULL COMMENT '关联权限表中的一项-外键',
  `rule_name` varchar(255) DEFAULT NULL COMMENT '规则名称',
  `rule_column` varchar(255) NOT NULL COMMENT '字段',
  `rule_conditions` varchar(255) NOT NULL COMMENT '条件',
  `rule_value` varchar(255) DEFAULT NULL COMMENT '规则值',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(255) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.permission_data_rule 的数据：~0 rows (大约)
REPLACE INTO `permission_data_rule` (`id`, `permission_id`, `rule_name`, `rule_column`, `rule_conditions`, `rule_value`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('pdr1', 'permperm1', '患者信息查看规则', 'patient_id', '=', '特定患者 ID', '2024-09-26 13:15:00', '管理员', NULL, NULL),
	('pdr2', 'perm2', '患者信息修改规则', 'patient_id', '=', '有权限修改的患者 ID', '2024-09-26 13:16:00', '管理员', NULL, NULL),
	('pdr3', 'perm3', '床位分配规则', 'bed_id', '=', '空闲床位 ID', '2024-09-26 13:17:00', '管理员', NULL, NULL),
	('pdr4', 'perm4', '医疗记录查看规则', 'patient_id', '=', '可查看医疗记录的患者 ID', '2024-09-26 13:18:00', '管理员', NULL, NULL),
	('pdr5', 'perm5', '科室管理规则', 'office_id', '=', '负责管理的科室 ID', '2024-09-26 13:19:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.role 结构
CREATE TABLE IF NOT EXISTS `role` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名称',
  `description` text COMMENT '角色描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.role 的数据：~0 rows (大约)
REPLACE INTO `role` (`id`, `name`, `description`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('role1', '医生', '负责诊断和治疗患者', '2024-09-26 13:20:00', '管理员', NULL, NULL),
	('role2', '护士', '协助医生进行护理工作', '2024-09-26 13:21:00', '管理员', NULL, NULL),
	('role3', '管理员', '管理医院系统和资源', '2024-09-26 13:22:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.role_permissions 结构
CREATE TABLE IF NOT EXISTS `role_permissions` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `role_id` varchar(32) DEFAULT NULL COMMENT '关联角色编号-外键',
  `permission_id` varchar(32) DEFAULT NULL COMMENT '关联权限编号-外键',
  `data_rule_id` varchar(32) DEFAULT NULL COMMENT '关联数据规则表-外键',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.role_permissions 的数据：~0 rows (大约)
REPLACE INTO `role_permissions` (`id`, `role_id`, `permission_id`, `data_rule_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('rp1', 'role1', 'perm1', 'pdr1', '2024-09-26 13:23:00', '管理员', NULL, NULL),
	('rp2', 'role1', 'perm2', 'pdr2', '2024-09-26 13:24:00', '管理员', NULL, NULL),
	('rp3', 'role1', 'perm4', 'pdr4', '2024-09-26 13:25:00', '管理员', NULL, NULL),
	('rp4', 'role2', 'perm1', 'pdr1', '2024-09-26 13:26:00', '管理员', NULL, NULL),
	('rp5', 'role3', 'perm3', 'pdr3', '2024-09-26 13:27:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.room 结构
CREATE TABLE IF NOT EXISTS `room` (
  `id` varchar(32) NOT NULL COMMENT '自动递增的主键',
  `number` varchar(255) DEFAULT NULL COMMENT '病房编号',
  `type` varchar(255) DEFAULT NULL COMMENT '病房类型',
  `gender` varchar(255) DEFAULT NULL COMMENT '病房性别',
  `bed_count` bigint NOT NULL COMMENT '床位数量',
  `foreign_id` varchar(255) DEFAULT NULL COMMENT 'ward_id-office_id-campus_id-floors_id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.room 的数据：~0 rows (大约)
REPLACE INTO `room` (`id`, `number`, `type`, `gender`, `bed_count`, `foreign_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('r1', '101 病房', '普通病房', '不限', 4, 'ward1-office1-campus1-floor1', '2024-09-26 13:28:00', '管理员', NULL, NULL),
	('r2', '102 病房', '单人病房', '男', 1, 'ward2-office2-campus2-floor2', '2024-09-26 13:29:00', '管理员', NULL, NULL),
	('r3', '103 病房', '高级病房', '女', 2, 'ward3-office3-campus3-floor3', '2024-09-26 13:30:00', '管理员', NULL, NULL),
	('r4', '104 病房', '儿科病房', '不限', 3, 'ward4-office4-campus4-floor4', '2024-09-26 13:31:00', '管理员', NULL, NULL),
	('r5', '105 病房', '眼科病房', '不限', 2, 'ward5-office5-campus5-floor5', '2024-09-26 13:32:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.room_user 结构
CREATE TABLE IF NOT EXISTS `room_user` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `room_id` varchar(32) DEFAULT NULL COMMENT '关联病房编号-外键',
  `user_id` varchar(32) DEFAULT NULL COMMENT '关联用户编号-外键',
  `user_name` varchar(255) DEFAULT NULL COMMENT '用户名称(这里只存主治医生)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `remark` text COMMENT '用于记录除主治医师外的医护人员，用json存储',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.room_user 的数据：~0 rows (大约)
REPLACE INTO `room_user` (`id`, `room_id`, `user_id`, `user_name`, `create_time`, `create_by`, `update_time`, `update_by`, `remark`) VALUES
	('ru1', 'r1', 'u1', '李医生', '2024-09-26 13:33:00', '管理员', NULL, NULL, '{"护士":"小王、小张"}'),
	('ru2', 'r2', 'u2', '张医生', '2024-09-26 13:34:00', '管理员', NULL, NULL, '{"护士":"小李"}'),
	('ru3', 'r3', 'u3', '王医生', '2024-09-26 13:35:00', '管理员', NULL, NULL, '{"护士":"小赵、小孙"}'),
	('ru4', 'r4', 'u4', '刘医生', '2024-09-26 13:36:00', '管理员', NULL, NULL, '{"护士":"小周"}'),
	('ru5', 'r5', 'u5', '陈医生', '2024-09-26 13:37:00', '管理员', NULL, NULL, '{"护士":"小吴"}');

-- 导出  表 hospital2.token 结构
CREATE TABLE IF NOT EXISTS `token` (
  `id` varchar(32) NOT NULL COMMENT '自动递增的主键',
  `token` varchar(100) NOT NULL COMMENT '令牌',
  `user_id` varchar(32) NOT NULL COMMENT '关联用户编号-外键',
  `over_time` date DEFAULT NULL COMMENT '过期日期',
  `role_id` varchar(32) DEFAULT NULL COMMENT '关联角色编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.token 的数据：~5 rows (大约)
REPLACE INTO `token` (`id`, `token`, `user_id`, `over_time`, `role_id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('tok1', 'token1', 'u1', '2024-10-26', 'role1', '2024-09-26 13:38:00', '管理员', NULL, NULL),
	('tok2', 'token2', 'u2', '2024-10-26', 'role1', '2024-09-26 13:39:00', '管理员', NULL, NULL),
	('tok3', 'token3', 'u3', '2024-10-26', 'role1', '2024-09-26 13:40:00', '管理员', NULL, NULL),
	('tok4', 'token4', 'u4', '2024-10-26', 'role2', '2024-09-26 13:41:00', '管理员', NULL, NULL),
	('tok5', 'token5', 'u5', '2024-10-26', 'role2', '2024-09-26 13:42:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `id` varchar(32) NOT NULL COMMENT '主键',
  `username` varchar(30) DEFAULT NULL COMMENT '用户名',
  `employee_number` varchar(255) NOT NULL COMMENT '唯一键，员工编号',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `employee_name` varchar(255) DEFAULT NULL COMMENT '用户姓名',
  `hospital_id` varchar(32) DEFAULT NULL COMMENT '关联医院编号-外键',
  `campus_id` varchar(32) DEFAULT NULL COMMENT '院区编号-外键',
  `user_type` varchar(255) DEFAULT NULL COMMENT '用户类型（医生、护士）',
  `is_use` int DEFAULT NULL COMMENT '是否可用，是否可用的字段可以改为tinyint,，只有0和1的状态，节省内存空间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.user 的数据：~0 rows (大约)
REPLACE INTO `user` (`id`, `username`, `employee_number`, `password`, `employee_name`, `hospital_id`, `campus_id`, `user_type`, `is_use`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('u1', 'user1', 'EN1', 'password1', '李医生', 'h1', '第一院区', '医生', 1, '2024-09-26 13:43:00', '管理员', NULL, NULL),
	('u2', 'user2', 'EN2', 'password2', '张医生', 'h2', '第二院区', '医生', 1, '2024-09-26 13:44:00', '管理员', NULL, NULL),
	('u3', 'user3', 'EN3', 'password3', '王医生', 'h3', '第三院区', '医生', 1, '2024-09-26 13:45:00', '管理员', NULL, NULL),
	('u4', 'user4', 'EN4', 'password4', '刘护士', 'h4', '第四院区', '护士', 1, '2024-09-26 13:46:00', '管理员', NULL, NULL),
	('u5', 'user5', 'EN5', 'password5', '陈护士', 'h5', '第五院区', '护士', 1, '2024-09-26 13:47:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.user_role 结构
CREATE TABLE IF NOT EXISTS `user_role` (
  `user_id` varchar(32) DEFAULT NULL COMMENT '关联用户编号-外键',
  `role_id` varchar(32) DEFAULT NULL COMMENT '关联角色编号-外键',
  `id` varchar(32) NOT NULL COMMENT '主键',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.user_role 的数据：~0 rows (大约)
REPLACE INTO `user_role` (`user_id`, `role_id`, `id`, `create_time`, `create_by`, `update_time`, `update_by`) VALUES
	('u1', 'role1', 'ur1', '2024-09-26 13:48:00', '管理员', NULL, NULL),
	('u2', 'role1', 'ur2', '2024-09-26 13:49:00', '管理员', NULL, NULL),
	('u3', 'role1', 'ur3', '2024-09-26 13:50:00', '管理员', NULL, NULL),
	('u4', 'role2', 'ur4', '2024-09-26 13:51:00', '管理员', NULL, NULL),
	('u5', 'role2', 'ur5', '2024-09-26 13:52:00', '管理员', NULL, NULL);

-- 导出  表 hospital2.ward 结构
CREATE TABLE IF NOT EXISTS `ward` (
  `id` varchar(32) NOT NULL COMMENT '自动递增的主键',
  `name` varchar(255) DEFAULT NULL COMMENT '病区名称',
  `code` varchar(255) DEFAULT NULL COMMENT '代码',
  `office_id` varchar(32) DEFAULT NULL COMMENT '关联科室编号',
  `room_count` int NOT NULL COMMENT '房间数量',
  `bed_count` int NOT NULL COMMENT '床位数量',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `location` varchar(255) DEFAULT NULL COMMENT '病区位置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- 正在导出表  hospital2.ward 的数据：~0 rows (大约)
REPLACE INTO `ward` (`id`, `name`, `code`, `office_id`, `room_count`, `bed_count`, `create_time`, `create_by`, `update_time`, `update_by`, `remark`, `location`) VALUES
	('ward1', '内科一病区', 'ICD1', '内科', 5, 20, '2024-09-26 13:53:00', '管理员', NULL, NULL, '内科普通病区', '医院大楼 A 座 3 楼'),
	('ward2', '外科二病区', 'ICD2', '外科', 4, 15, '2024-09-26 13:54:00', '管理员', NULL, NULL, '外科手术病区', '医院大楼 B 座 4 楼'),
	('ward3', '妇产科三病区', 'ICD3', '妇产科', 3, 12, '2024-09-26 13:55:00', '管理员', NULL, NULL, '妇产科母婴病区', '医院大楼 C 座 2 楼'),
	('ward4', '儿科四病区', 'ICD4', '儿科', 4, 18, '2024-09-26 13:56:00', '管理员', NULL, NULL, '儿科综合病区', '医院大楼 D 座 5 楼'),
	('ward5', '眼科五病区', 'ICD5', '眼科', 3, 10, '2024-09-26 13:57:00', '管理员', NULL, NULL, '眼科专科病区', '医院大楼 E 座 6 楼');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
