create table `token`(
	`token` VARCHAR(128) NOT NULL COMMENT '访问令牌',
    `user_id` VARCHAR(32) NOT NULL COMMENT '用户id',
	`expired_time` DATETIME NOT NULL COMMENT '令牌超时时间',
	`expired_ts` BIGINT NOT NULL COMMENT '令牌超时时间戳',
	UNIQUE KEY (`token`) USING BTREE
)
COMMENT='用户令牌'
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
ROW_FORMAT=DYNAMIC
;