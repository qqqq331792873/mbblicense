package mbblicense.client.manager;

import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import de.schlichtherle.license.LicenseParam;
import lombok.extern.slf4j.Slf4j;
import mbblicense.client.pojo.ClientProperties;
import mbblicense.client.pojo.ClientLicenseParam;
import mbblicense.client.util.LicenseUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.HashMap;

/**
 * 授权系统管理器
 *
 * @author 马冰冰
 */
@Slf4j
@Component
public class ClientLicenseManager extends LicenseManager {
	@Resource
	private ClientLicenseParam clientLicenseParam;
	@Resource
	private LicenseUtil        licenseUtil;
	@Resource
	private ClientProperties   clientProperties;
	
	/**
	 * 授权内容
	 */
	private LicenseContent licenseContent;
	
	/**
	 * 安装和验证
	 */
	public boolean installAndCheck() throws Exception {
		boolean installSuccess = install();
		if (!installSuccess) {
			return false;
		}
		return check();
	}
	
	/**
	 * 安装之后,按理说就不应该再安装了.后续只需要验证即可
	 */
	public boolean install() {
		try {
			synchronized (this) {
				log.info("开始安装客户端证书!");
				setLicenseParam(clientLicenseParam);
				ClassPathResource classPathResource = new ClassPathResource(clientProperties.getLicPath());
				File              file              = classPathResource.getFile();
				licenseContent = install(file);
				log.info("客户端证书安装成功!" + file);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	/**
	 * 验证
	 */
	public boolean check() throws Exception {
		if (licenseContent == null) {
			boolean installSuccess = install();
			if (!installSuccess) {
				return false;
			}
		}
		
		boolean isOk    = true;
		Date    dateNow = new Date();
		log.info("授权信息开始验证...");
		
		// 验证基础信息
		Date notBefore = licenseContent.getNotBefore();
		if (dateNow.getTime() > notBefore.getTime()) {
			log.info("验证通过:启用时间");
		}
		else {
			log.error("验证失败:启用时间");
			isOk = false;
		}
		
		Date notAfter = licenseContent.getNotAfter();
		if (dateNow.getTime() < notAfter.getTime()) {
			log.info("验证通过:失效时间");
		}
		else {
			log.error("验证失败:失效时间");
			isOk = false;
		}
		
		// 验证扩展信息
		HashMap<String, String> extra     = (HashMap<String, String>) licenseContent.getExtra();
		String                  ipAddress = extra.get("ipAddress");
		if (ipAddress != null && !"null".equals(ipAddress)) {
			String ipAddressLocal = licenseUtil.getIpAddress();
			if (ipAddress.equals(ipAddressLocal)) {
				log.info("验证通过:IP地址");
			}
			else {
				log.error("验证失败:IP地址");
				isOk = false;
			}
		}
		
		String macAddress = extra.get("macAddress");
		if (macAddress != null && !"null".equals(macAddress)) {
			String macAddressLocal = licenseUtil.getMacAddress();
			if (macAddress.equals(macAddressLocal)) {
				log.info("验证通过:MAC地址");
			}
			else {
				log.error("验证失败:MAC地址");
				isOk = false;
			}
		}
		
		String cpuSerial = extra.get("macAddress");
		if (cpuSerial != null && !"null".equals(cpuSerial)) {
			String cpuSerialLocal = licenseUtil.getCPUSerial();
			if (cpuSerial.equals(cpuSerialLocal)) {
				log.info("验证通过:CPU序列号");
			}
			else {
				log.error("验证失败:CPU序列号");
				isOk = false;
			}
		}
		
		String motherboardSN = extra.get("macAddress");
		if (motherboardSN != null && !"null".equals(motherboardSN)) {
			String motherboardSNLocal = licenseUtil.getMotherboardSN();
			if (motherboardSN.equals(motherboardSNLocal)) {
				log.info("验证通过:主板SN");
			}
			else {
				log.error("验证失败:主板SN");
				isOk = false;
			}
		}
		
		String hardDiskSN = extra.get("macAddress");
		if (hardDiskSN != null && !"null".equals(hardDiskSN)) {
			String hardDiskSNLocal = licenseUtil.getHardDiskSN();
			if (hardDiskSN.equals(hardDiskSNLocal)) {
				log.info("验证通过:硬盘SN");
			}
			else {
				log.error("验证失败:硬盘SN");
				isOk = false;
			}
		}
		
		String serverName = extra.get("macAddress");
		if (serverName != null && !"null".equals(serverName)) {
			String serverNameLocal = licenseUtil.getServerName();
			if (serverName.equals(serverNameLocal)) {
				log.info("验证通过:服务名");
			}
			else {
				log.error("验证失败:服务名");
				isOk = false;
			}
		}
		
		if (!isOk) {
			log.error("授权信息验证失败!!!");
		}
		
		return isOk;
	}
	
	/**
	 * 设置参数
	 */
	@Override
	public synchronized void setLicenseParam(LicenseParam param) {
		super.setLicenseParam(param);
	}
}
