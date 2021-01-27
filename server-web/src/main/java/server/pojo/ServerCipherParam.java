package server.pojo;

import de.schlichtherle.license.CipherParam;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 马冰冰
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
