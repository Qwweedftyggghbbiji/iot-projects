CREATE TABLE `device` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '行编号，数字自增',
  `iot_id` VARCHAR(32) NOT NULL COMMENT '物联网设备ID',
  `dev_name` VARCHAR(32) NOT NULL COMMENT '设备名称',
  `user_id` VARCHAR(32) NOT NULL COMMENT '拥有者用户ID',
  `dev_type` VARCHAR(32) NOT NULL COMMENT '设备类型',
  `status` VARCHAR(16) NOT NULL COMMENT '设备状态',
  `dev_secret` VARCHAR(128) NOT NULL COMMENT '设备密钥',
  `description` VARCHAR(256) NOT NULL COMMENT '描述',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_iot_id` (`iot_id`) -- 建议给设备ID加唯一索引
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备信息表';