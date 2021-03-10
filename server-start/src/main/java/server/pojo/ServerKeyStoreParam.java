package server.pojo;

import de.schlichtherle.license.KeyStoreParam;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * mbb
 */
@Component
public class ServerKeyStoreParam implements KeyStoreParam {
	@Resource
	private ServerProperties serverProperties;
	
	@Override
	public InputStream getStream() throws IOException {
		String resource = serverProperties.getResource();
		return new FileInputStream(new File(resource));
	}
	
	@Override
	public String getAlias() {
		return serverProperties.getAlias();
	}
	
	@Override
	public String getStorePwd() {
		return serverProperties.getStorePwd();
	}
	
	@Override
	public String getKeyPwd() {
		return serverProperties.getKeyPwd();
	}
}
