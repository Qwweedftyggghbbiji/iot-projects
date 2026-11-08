CREATE TABLE `device_msg` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '表自增ID' ,
	`iot_id` VARCHAR(32) NOT NULL COMMENT '物联网设备ID' ,
	`msg` TEXT NOT NULL COMMENT '消息',
	`tag` VARCHAR(256) NOT NULL COMMENT '消息标签' ,
	`create_time` DATETIME NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`id`) USING BTREE,
	index DEVMSG_TAG_INDEX(`iot_id`,`tag`)
)
COMMENT='设备消息'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
;