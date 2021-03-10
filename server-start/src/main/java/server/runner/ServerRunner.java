package server.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import server.manager.ServerLicenseManager;
import server.pojo.ServerLicenseParam;

import javax.annotation.Resource;

/**
 * mbb 启动监听器
 */
@Component
public class ServerRunner implements CommandLineRunner {
	@Resource
	ServerLicenseManager serverLicenseManager;
	@Resource
	ServerLicenseParam   serverLicenseParam;
	
	/**
	 * mbb 启动初始化管理器
	 */
	@Override
	public void run(String... args) throws Exception {
		// 初始化管理器
		serverLicenseManager.setLicenseParam(serverLicenseParam);
	}
}
