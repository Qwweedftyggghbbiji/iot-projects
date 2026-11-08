CREATE DATABASE if not exists iotthermo character set UTF8mb4 collate utf8mb4_bin;
CREATE user 'iotthermo'@'localhost' IDENTIFIED BY 'dpLJnVgnq@v7';
CREATE user 'iotthermo'@'%' IDENTIFIED BY 'dpLJnVgnq@v7';
GRANT ALL ON iotthermo.* TO 'iotthermo'@'localhost';
GRANT ALL ON iotthermo.* TO 'iotthermo'@'%';