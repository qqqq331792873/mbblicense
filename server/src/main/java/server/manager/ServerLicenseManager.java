package server.manager;


import de.schlichtherle.license.*;
import org.springframework.stereotype.Component;
import server.pojo.ServerLicenseParam;

import javax.annotation.Resource;
import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.prefs.Preferences;

/**
 * 生成器工具:操作的入口
 *
 * @author 马冰冰
 */
@Component
public class ServerLicenseManager {
	
	@Resource
	ServerLicenseParam ServerLicenseParam;
	
	/**
	 * 生成license
	 */
	public void generate() throws Exception {
		// 引用
		Preferences preference = Preferences.userNodeForPackage(ServerLicenseParam.class);
		// 秘钥库参数
		KeyStoreParam keyStoreParam = new DefaultKeyStoreParam(ServerLicenseParam.class, ServerLicenseParam.getResource(), ServerLicenseParam.getAlias(), ServerLicenseParam.getStorePwd(), ServerLicenseParam.getKeyPwd());
		// 秘钥参数
		CipherParam cipherParam = new DefaultCipherParam(ServerLicenseParam.getStorePwd());
		// 许可证参数
		LicenseParam licenseParams = new DefaultLicenseParam(ServerLicenseParam.getSubject(), preference, keyStoreParam, cipherParam);
		// 管理器
		LicenseManager licenseManager = new LicenseManager(licenseParams);
		
		// 配置扩展字段
		HashMap<String, String> licenseExtra = new HashMap<>();
		licenseExtra.put("ipAddress", ServerLicenseParam.getIpAddress());
		licenseExtra.put("macAddress", ServerLicenseParam.getMacAddress());
		licenseExtra.put("CPUSerial", ServerLicenseParam.getCPUSerial());
		licenseExtra.put("motherboardSN", ServerLicenseParam.getMotherboardSN());
		licenseExtra.put("hardDiskSN", ServerLicenseParam.getHardDiskSN());
		licenseExtra.put("serverName", ServerLicenseParam.getServerName());
		
		
		X500Principal DEFAULTHOLDERANDISSUER = new X500Principal("CN=shelby, OU=org, O=org, L=china, ST=china, C=china");
		
		LicenseContent content = new LicenseContent();
		content.setHolder(DEFAULTHOLDERANDISSUER);
		content.setIssuer(DEFAULTHOLDERANDISSUER);
		content.setConsumerType("user");
		content.setConsumerAmount(1);
		content.setSubject(ServerLicenseParam.getSubject());
		content.setIssued(new Date(ServerLicenseParam.getIssued()));
		content.setNotBefore(new Date(ServerLicenseParam.getNotBefore()));
		content.setNotAfter(new Date(ServerLicenseParam.getNotAfter()));
		content.setInfo(ServerLicenseParam.getInfo());
		content.setExtra(licenseExtra);
		
		licenseManager.store(content, new File(ServerLicenseParam.getLicPath()));
		System.out.println("生成成功" + licenseExtra);
	}
}
