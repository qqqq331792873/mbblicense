package mbblicense.client.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * mbb 鉴权所需要的一些配置
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
