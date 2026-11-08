-- INIT config
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('iotId', '', now(), '设备物联网平台ID');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('devSecret', '', now(), '设备密钥');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('token', '', now(), '用户令牌');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('userId', '', now(), '用户ID');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('userSecret', '', now(), '用户密钥');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('devName', '表情温度计001', now(), '设备名称');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('devType', 'thermometer', now(), '设备类型');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('description', '表情温度计，检测温度', now(), '设备描述');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('devTopicPost', '', now(), '设备主动推送主题');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('devTopicPostReply', '', now(), '设备主动推送回复主题');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('devTopicReceive', '', now(), '设备被动接收主题');
INSERT INTO `config` (`k`, `v`, `upt_time`, `remark`) VALUES ('devTopicReceiveReply', '', now(), '设备被动接收回复主题');

-- INIT tempemoji
INSERT INTO `tempemoji` (`mint`, `maxt`, `emoji_name`,`upt_time`) VALUES (null, 18, 'freezed', NOW());
INSERT INTO `tempemoji` (`mint`, `maxt`, `emoji_name`,`upt_time`) VALUES (18, 28, 'comfort', NOW());
INSERT INTO `tempemoji` (`mint`, `maxt`, `emoji_name`,`upt_time`) VALUES (28, 35, 'hot', NOW());
INSERT INTO `tempemoji` (`mint`, `maxt`, `emoji_name`,`upt_time`) VALUES (35, null, 'burn', NOW());

-- INIT emoji
INSERT INTO `emoji` (`name`, `face`) VALUES ('freezed', '(⊙﹏⊙)');
INSERT INTO `emoji` (`name`, `face`) VALUES ('hot', '(≧ｗ≦；)');
INSERT INTO `emoji` (`name`, `face`) VALUES ('comfort', '(*￣︶￣)');
INSERT INTO `emoji` (`name`, `face`) VALUES ('burn', 'ヽ(#`Д´)ﾉ');
