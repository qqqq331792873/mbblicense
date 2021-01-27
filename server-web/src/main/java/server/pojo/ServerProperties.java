package server.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 生成许可证所需要参数
 * 默认值读取配置,需要额外设置的,请自行设置
 * 但一般情况不修改.如果要修改,记得匹配自己的keystore秘钥库.
 *
 * @author 马冰冰
 */
@Data
@Component
@Configuration
@ConfigurationProperties(prefix = "mbblicense.server")
public class ServerProperties {
	// ------------------------------------------------系统服务信息------------------------------------------------
	/**
	 * 秘钥库位置
	 */
	private String resource;
	/**
	 * 秘钥库密码
	 */
	private String storePwd;
	/**
	 * 秘钥
	 */
	private String alias;
	/**
	 * 秘钥密码
	 */
	private String keyPwd;
	/**
	 * 项目名
	 */
	private String subject;
	/**
	 * 准备在哪里生成lisence
	 */
	private String licPath;
}
