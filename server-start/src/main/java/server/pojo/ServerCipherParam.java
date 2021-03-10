package server.pojo;

import de.schlichtherle.license.CipherParam;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * mbb 密码参数
 */
@Component
public class ServerCipherParam implements CipherParam {
	@Resource
	private ServerProperties serverProperties;
	
	@Override
	public String getKeyPwd() {
		return serverProperties.getStorePwd();
	}
}
