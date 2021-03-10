package server.pojo;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseParam;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.prefs.Preferences;

/**
 * mbb
 */
@Component
public class ServerLicenseParam implements LicenseParam {
	@Resource
	private ServerCipherParam   serverCipherParam;
	@Resource
	private ServerKeyStoreParam serverKeyStoreParam;
	@Resource
	private ServerProperties    serverProperties;
	
	@Override
	public String getSubject() {
		return serverProperties.getSubject();
	}
	
	@Override
	public Preferences getPreferences() {
		return Preferences.userNodeForPackage(ServerLicenseParam.class);
	}
	
	@Override
	public KeyStoreParam getKeyStoreParam() {
		return serverKeyStoreParam;
	}
	
	@Override
	public CipherParam getCipherParam() {
		return serverCipherParam;
	}
}
