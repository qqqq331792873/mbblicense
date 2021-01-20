package mbblicense.client.pojo;

import de.schlichtherle.license.KeyStoreParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
	@Value("${mbblicense.client.info.resource}")
	private String resource;// 秘钥库位置
	@Value("${mbblicense.client.info.storePwd}")
	private String storePwd;// 秘钥库密码
	@Value("${mbblicense.client.info.alias}")
	private String alias;// 秘钥
	
	
	@Override
	public InputStream getStream() throws IOException {
		final InputStream in = ClientLicenseParam.class.getResourceAsStream(resource);
		if (null == in) {
			throw new FileNotFoundException(resource);
		}
		return in;
	}
	
	@Override
	public String getStorePwd() {
		return storePwd;
	}
	
	@Override
	public String getAlias() {
		return alias;
	}
	
	@Override
	public String getKeyPwd() {
		return null;
	}
}
