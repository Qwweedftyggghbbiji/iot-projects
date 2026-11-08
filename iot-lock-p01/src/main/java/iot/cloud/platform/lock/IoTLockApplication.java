package iot.cloud.platform.lock;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("iot.cloud.platform.lock.mapper")
public class IoTLockApplication {
	public static void main(String[] args) {
		SpringApplication.run(IoTLockApplication.class, args);
	}
}
