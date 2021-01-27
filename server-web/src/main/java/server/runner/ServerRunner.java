package server.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import server.manager.ServerLicenseManager;
import server.pojo.ServerLicenseParam;

import javax.annotation.Resource;

/**
 * @author 马冰冰
 */
@Component
public class ServerRunner implements CommandLineRunner {
	@Resource
	ServerLicenseManager serverLicenseManager;
	@Resource
	ServerLicenseParam   serverLicenseParam;
	
	@Override
	public void run(String... args) throws Exception {
		// 初始化管理器
		serverLicenseManager.setLicenseParam(serverLicenseParam);
		
	}
}
