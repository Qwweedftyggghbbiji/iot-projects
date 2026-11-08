CREATE TABLE pwd (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    typ ENUM('fixed', 'temp') NOT NULL COMMENT '密码类型',
    pwd VARCHAR(18) NOT NULL COMMENT '密码（纯数字，6~18位）',
    status ENUM('enabled', 'disabled', 'used', 'expired') NOT NULL DEFAULT 'enabled' COMMENT '状态',
    upt_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    expired_time DATETIME NULL COMMENT '过期时间（临时密码必填，固定密码为NULL）',

    -- 约束：密码格式校验（MySQL 8.0+ 支持正则）
    CONSTRAINT chk_pwd_format CHECK (pwd REGEXP '^[0-9]{6,18}$'),

    -- 临时密码必须有 expired_time，固定密码必须为 NULL
    CONSTRAINT chk_expired_for_temp
        CHECK (
            (typ = 'temp' AND expired_time IS NOT NULL) OR
            (typ = 'fixed' AND expired_time IS NULL)
        ),

    -- 索引优化
    INDEX idx_typ_status (typ, status),
    INDEX idx_expired (expired_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='智能锁密码表';


