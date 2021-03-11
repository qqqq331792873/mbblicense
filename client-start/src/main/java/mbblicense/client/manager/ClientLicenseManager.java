package mbblicense.client.manager;

import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;
import lombok.extern.slf4j.Slf4j;
import mbblicense.client.pojo.ClientLicenseParam;
import mbblicense.client.pojo.ClientProperties;
import mbblicense.client.util.SystemInfoUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * todombb 有个bug,如果用户重新安装,由于单例模式,重新安装,setLicenseParam方法注入的还是旧的clientLicenseParam,导致新的license文件无法使用最新的
 * 授权系统管理器
 */
@Slf4j
@Component
public class ClientLicenseManager extends LicenseManager {
	@Resource
	private ClientLicenseParam clientLicenseParam;
	@Resource
	private SystemInfoUtil     systemInfoUtil;
	@Resource
	private ClientProperties   clientProperties;
	
	/**
	 * mbb 授权内容
	 */
	private LicenseContent licenseContent;
	
	/**
	 * mbb 安装和验证
	 */
	public boolean installAndCheck() {
		boolean installSuccess = install();
		if (!installSuccess) {
			return false;
		}
		return check();
	}
	
	/**
	 * mbb 安装之后,按理说就不应该再安装了.后续只需要验证即可
	 */
	public boolean install() {
		try {
			synchronized (this) {
				log.info("开始安装客户端证书!");
				setLicenseParam(clientLicenseParam);
				File file = new File(clientProperties.getLicPath());
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
	 * mbb 验证
	 */
	public boolean check() {
		if (licenseContent == null) {
			boolean installSuccess = install();
			if (!installSuccess) {
				return false;
			}
		}
		
		boolean isOk    = true;
		Date    dateNow = new Date();
		log.info("授权信息开始验证...");
		
		// 1.开始时间
		Date notBefore = licenseContent.getNotBefore();
		if (dateNow.getTime() > notBefore.getTime()) {
			log.info("验证通过:启用时间");
		}
		else {
			log.error("验证失败:启用时间");
			isOk = false;
		}
		
		// 2.结束时间
		Date notAfter = licenseContent.getNotAfter();
		if (dateNow.getTime() < notAfter.getTime()) {
			log.info("验证通过:失效时间");
		}
		else {
			log.error("验证失败:失效时间");
			isOk = false;
		}
		
		HashMap<String, String> extra = (HashMap<String, String>) licenseContent.getExtra();
		// 3.IP地址
		String ipAddress = extra.get("ipAddress");
		if (!StringUtils.isEmpty(ipAddress)) {
			Set<String> ipAddressSet = systemInfoUtil.getIpAddress();
			if (ipAddressSet.contains(ipAddress)) {
				log.info("验证通过:IP地址");
			}
			else {
				log.error("验证失败:IP地址");
				isOk = false;
			}
		}
		
		// 4.mac地址
		String macAddress = extra.get("macAddress");
		if (!StringUtils.isEmpty(macAddress)) {
			Set<String> macAddressSet = systemInfoUtil.getMacAddress();
			if (macAddressSet.contains(macAddress)) {
				log.info("验证通过:MAC地址");
			}
			else {
				log.error("验证失败:MAC地址");
				isOk = false;
			}
		}
		
		// 5.cpu序列号
		String cpuSerial = extra.get("macAddress");
		if (!StringUtils.isEmpty(cpuSerial)) {
			String cpuSerialLocal = systemInfoUtil.getCPUSerial();
			if (cpuSerial.equals(cpuSerialLocal)) {
				log.info("验证通过:CPU序列号");
			}
			else {
				log.error("验证失败:CPU序列号");
				isOk = false;
			}
		}
		
		// 6.主板SN
		String motherboardSN = extra.get("macAddress");
		if (!StringUtils.isEmpty(motherboardSN)) {
			String motherboardSNLocal = systemInfoUtil.getMotherboardSN();
			if (motherboardSN.equals(motherboardSNLocal)) {
				log.info("验证通过:主板SN");
			}
			else {
				log.error("验证失败:主板SN");
				isOk = false;
			}
		}
		
		// 7.硬盘SN
		String hardDiskSN = extra.get("macAddress");
		if (!StringUtils.isEmpty(hardDiskSN)) {
			String hardDiskSNLocal = systemInfoUtil.getHardDiskSN();
			if (hardDiskSN.equals(hardDiskSNLocal)) {
				log.info("验证通过:硬盘SN");
			}
			else {
				log.error("验证失败:硬盘SN");
				isOk = false;
			}
		}
		
		// 8.服务名
		String serverName = extra.get("serverName");
		if (!StringUtils.isEmpty(serverName)) {
			String serverNameLocal = systemInfoUtil.getServerName();
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
		else {
			log.info("授权信息验证成功!!!");
		}
		
		return isOk;
	}
}
