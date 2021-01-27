package mbblicense.client.pojo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 鉴权所需要的一些配置
 *
 * @author 马冰冰
 */
@Data
@Component
@ConfigurationProperties(prefix = "mbblicense.client")
public class ClientProperties {
	/**
	 * 公钥库位置
	 */
	public String  resource;
	/**
	 * 公钥库密码
	 */
	public String  storePwd;
	/**
	 * 公钥别名
	 */
	public String  alias;
	/**
	 * 项目名称
	 */
	public String  subject;
	/**
	 * 授权文件加载位置
	 */
	public String  licPath;
	/**
	 * 开启debugger:
	 * 有系统信息等一些标准打印
	 * 同时会自动安装,并校验测试一次
	 */
	public boolean debugger;
}
