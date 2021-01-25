package mbblicense.client.pojo;

import de.schlichtherle.license.CipherParam;
import de.schlichtherle.license.KeyStoreParam;
import de.schlichtherle.license.LicenseParam;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.prefs.Preferences;

/**
 * @author 马冰冰
 */
@Data
@Component
public class ClientLicenseParam implements LicenseParam {
	@Resource
	ClientKeyStoreParam clientKeyStoreParam;
	@Resource
	ClientCipherParam   clientCipherParam;
	@Resource
	private ClientProperties clientProperties;
	
	@Override
	public String getSubject() {
		return clientProperties.getSubject();
	}
	
	@Override
	public Preferences getPreferences() {
		return Preferences.userNodeForPackage(ClientLicenseParam.class);
	}
	
	@Override
	public KeyStoreParam getKeyStoreParam() {
		return clientKeyStoreParam;
	}
	
	@Override
	public CipherParam getCipherParam() {
		return clientCipherParam;
	}
}
