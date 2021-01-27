package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import server.pojo.ServerProperties;

/**
 * 授权系统服务端:用于生成授权文件(store自行准备,或使用本系统的)
 * 使用方法:
 * 1.打开resources,修改里面的配置文件serverKeys.peoperties.
 * 2.启动便会生成:license
 *
 * @author BrankoMa
 */
@SpringBootApplication
@EnableConfigurationProperties(ServerProperties.class)
public class ServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
}