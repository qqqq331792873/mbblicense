package mbblicense.client;

import mbblicense.client.pojo.ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * mbb 配置文件:自动加载
 */
@Configuration
@ComponentScan("mbblicense.client")
@EnableConfigurationProperties(ClientProperties.class)
public class MBBLicenseConfiguration {
}