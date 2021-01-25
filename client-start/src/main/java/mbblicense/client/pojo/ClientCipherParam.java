package mbblicense.client.pojo;

import de.schlichtherle.license.CipherParam;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 客户端密码参数
 *
 * @author 马冰冰
 */
@Data
@Component
public class ClientCipherParam implements CipherParam {
	@Resource
	private ClientProperties clientProperties;
	
	@Override
	public String getKeyPwd() {
		return clientProperties.getStorePwd();
	}
}
