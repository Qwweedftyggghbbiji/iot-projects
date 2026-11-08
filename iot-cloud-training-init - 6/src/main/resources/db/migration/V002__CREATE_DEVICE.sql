CREATE TABLE `device` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '表自增ID' ,
	`iot_id` VARCHAR(32) NOT NULL COMMENT '物联网设备ID' ,
	`dev_name` VARCHAR(32) NOT NULL COMMENT '设备名称' ,
	`user_id` VARCHAR(32) NOT NULL COMMENT '拥有者用户ID' ,
	`dev_type` VARCHAR(32) NOT NULL COMMENT '设备类型' ,
	`status` VARCHAR(16) NOT NULL COMMENT '状态，启用-enabled，禁用-disabled',
	`dev_secret` VARCHAR(128) NOT NULL COMMENT '设备密钥' ,
	`description` VARCHAR(256) COMMENT '设备描述' ,
	`create_time` DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE KEY (`iot_id`) USING BTREE,
	index AUTH_INDEX(`iot_id`,`dev_secret`)
)
COMMENT='设备'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
;