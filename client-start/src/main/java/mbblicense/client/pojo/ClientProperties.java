package mbblicense.client.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * mbb yml所有配置映射
 */
@Data
@Component
@ConfigurationProperties(prefix = "mbblicense.client")
public class ClientProperties {
	/**
	 * mbb 公钥库位置
	 */
	public String  resource;
	/**
	 * mbb 公钥库密码
	 */
	public String  storePwd;
	/**
	 * mbb 公钥别名
	 */
	public String  alias;
	/**
	 * mbb 项目名称
	 */
	public String  subject;
	/**
	 * mbb 授权文件加载位置
	 */
	public String  licPath;
	/**
	 * mbb 开启debugger:
	 * 有系统信息等一些标准打印
	 * 同时会自动安装,并校验测试一次
	 */
	public boolean debugger;
}
