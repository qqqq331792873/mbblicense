package server.manager;


import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import server.pojo.LicensePO;
import server.pojo.ServerProperties;

import javax.annotation.Resource;
import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.util.Date;
import java.util.HashMap;

/**
 * mbb 生成器工具:操作的入口
 */
@Slf4j
@Component
public class ServerLicenseManager extends LicenseManager {
	@Resource
	ServerProperties serverProperties;
	
	/**
	 * 生成license
	 */
	public boolean generate(LicensePO licensePO) {
		// 配置扩展字段
		HashMap<String, String> licenseExtra = new HashMap<>();
		licenseExtra.put("ipAddress", licensePO.getIpAddress());
		licenseExtra.put("macAddress", licensePO.getMacAddress());
		licenseExtra.put("CPUSerial", licensePO.getCPUSerial());
		licenseExtra.put("motherboardSN", licensePO.getMotherboardSN());
		licenseExtra.put("hardDiskSN", licensePO.getHardDiskSN());
		licenseExtra.put("serverName", licensePO.getServerName());
		
		
		X500Principal DEFAULTHOLDERANDISSUER = new X500Principal("CN=shelby, OU=org, O=org, L=china, ST=china, C=china");
		
		LicenseContent content = new LicenseContent();
		content.setHolder(DEFAULTHOLDERANDISSUER);
		content.setIssuer(DEFAULTHOLDERANDISSUER);
		content.setConsumerType("user");
		content.setConsumerAmount(1);
		content.setSubject(serverProperties.getSubject());
		content.setIssued(new Date(licensePO.getIssued()));
		content.setNotBefore(new Date(licensePO.getNotBefore()));
		content.setNotAfter(new Date(licensePO.getNotAfter()));
		content.setInfo(licensePO.getInfo());
		content.setExtra(licenseExtra);
		
		try {
			store(content, new File(serverProperties.getLicPath()));
		} catch (Exception e) {
			log.error("生成授权文件失败", e);
			return false;
		}
		return true;
	}
}
