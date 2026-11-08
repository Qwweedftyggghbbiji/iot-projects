-- =============================================
-- 物联网温度计项目 - 完整数据库初始化脚本
-- =============================================

-- 1. 创建数据库和用户 (如果需要的话)
-- 注意：这部分需要 root 权限执行，如果数据库已存在可以跳过
CREATE DATABASE IF NOT EXISTS iotthermo CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;

-- 2. 使用数据库
USE iotthermo;

-- 3. 创建配置表 (config)
CREATE TABLE IF NOT EXISTS `config` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `k` VARCHAR(128) NOT NULL COMMENT '键',
  `v` VARCHAR(512) NOT NULL COMMENT '值',
  `upt_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `remark` VARCHAR(256) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_config_k` (`k`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='配置表';

-- 4. 创建温度表情映射表 (tempemoji)
CREATE TABLE IF NOT EXISTS `tempemoji` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `mint` INT(3) DEFAULT NULL COMMENT '最低温度（大于等于）',
  `maxt` INT(3) DEFAULT NULL COMMENT '最高温度（小于）',
  `emoji_name` VARCHAR(256) NOT NULL COMMENT 'emoji名称',
  `upt_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='温度对应表情';

-- 5. 创建表情表 (emoji)
CREATE TABLE IF NOT EXISTS `emoji` (
  `id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` VARCHAR(256) NOT NULL COMMENT '名称',
  `face` VARCHAR(100) NOT NULL COMMENT '表情',
  `upt_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='emoji表情';

-- =============================================
-- 插入初始化数据
-- =============================================

-- 6. 初始化 config 表数据 (先删除已存在的)
DELETE FROM config WHERE k IN (
  'iotId', 'devSecret', 'token', 'userId', 'userSecret',
  'devName', 'devType', 'description',
  'devTopicPost', 'devTopicPostReply',
  'devTopicReceive', 'devTopicReceiveReply'
);

-- 插入配置数据 (显式提供 upt_time)
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES
('iotId', '', NOW(), '设备物联网平台ID'),
('devSecret', '', NOW(), '设备密钥'),
('token', '', NOW(), '用户令牌'),
('userId', '', NOW(), '用户ID'),
('userSecret', '', NOW(), '用户密钥'),
('devName', '表情温度计001', NOW(), '设备名称'),
('devType', 'thermometer', NOW(), '设备类型'),
('description', '表情温度计，检测温度', NOW(), '设备描述'),
('devTopicPost', '/iot/cloud/demo001/post', NOW(), '设备主动推送主题'),
('devTopicPostReply', '/iot/cloud/demo001/post_reply', NOW(), '设备主动推送回复主题'),
('devTopicReceive', '/iot/cloud/demo001/receive', NOW(), '设备被动接收主题'),
('devTopicReceiveReply', '/iot/cloud/demo001/receive_reply', NOW(), '设备被动接收回复主题');

-- 7. 初始化 tempemoji 表
DELETE FROM tempemoji;
INSERT INTO `tempemoji` (`mint`, `maxt`, `emoji_name`, `upt_time`) VALUES
(NULL, 18, 'freezed', NOW()),
(18, 28, 'comfort', NOW()),
(28, 35, 'hot', NOW()),
(35, NULL, 'burn', NOW());

-- 8. 初始化 emoji 表
DELETE FROM emoji;
INSERT INTO `emoji` (`name`, `face`, `upt_time`) VALUES
('freezed', '(⊙﹏⊙)', NOW()),
('comfort', '(*￣︶￣)', NOW()),
('hot', '(≧ｗ≦；)', NOW()),
('burn', 'ヽ(#`Д´)ﾉ', NOW());

-- =============================================
-- 验证数据
-- =============================================
SELECT '===== config 表数据 =====' AS '';
SELECT * FROM config;

SELECT '===== tempemoji 表数据 =====' AS '';
SELECT * FROM tempemoji;

SELECT '===== emoji 表数据 =====' AS '';
SELECT * FROM emoji;

SELECT '✅ 数据库初始化完成！' AS '';
