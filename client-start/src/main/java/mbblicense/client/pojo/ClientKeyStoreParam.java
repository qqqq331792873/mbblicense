package mbblicense.client.pojo;

import de.schlichtherle.license.KeyStoreParam;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 秘钥库参数
 *
 * @author 马冰冰
 */
@Component
public class ClientKeyStoreParam implements KeyStoreParam {
	@Resource
	private ClientProperties clientProperties;
	
	@Override
	public InputStream getStream() throws IOException {
		String            resource = clientProperties.getResource();
		final InputStream in       = ClientLicenseParam.class.getResourceAsStream(resource);
		if (null == in) {
			throw new FileNotFoundException(resource);
		}
		return in;
	}
	
	@Override
	public String getStorePwd() {
		return clientProperties.getStorePwd();
	}
	
	@Override
	public String getAlias() {
		return clientProperties.getAlias();
	}
	
	@Override
	public String getKeyPwd() {
		return null;
	}
}
