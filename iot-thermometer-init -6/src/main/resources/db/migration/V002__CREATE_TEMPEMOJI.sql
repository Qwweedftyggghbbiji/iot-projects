CREATE TABLE `tempemoji` (
	`id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
	`mint` INT(3)  COMMENT '最低温度（大于等于）',
	`maxt` INT(3)  COMMENT '最高温度（小于）',
	`emoji_name` VARCHAR(256) NOT NULL COMMENT 'emoji名称' COLLATE 'utf8mb4_general_ci',
	`upt_time` DATETIME NOT NULL COMMENT '更新时间',
	PRIMARY KEY (`id`) USING BTREE
)
COMMENT='温度对应表情'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
;