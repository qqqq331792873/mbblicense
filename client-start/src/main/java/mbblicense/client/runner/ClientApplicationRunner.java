package mbblicense.client.runner;

import mbblicense.client.manager.ClientLicenseManager;
import mbblicense.client.util.SystemInfoUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * mbb 启动监听器:用于启动时检查
 */
@Component
@ConditionalOnProperty(prefix = "mbblicense.client", name = "debugger", havingValue = "true")
public class ClientApplicationRunner implements ApplicationRunner {
	@Resource
	SystemInfoUtil       systemInfoUtil;
	@Resource
	ClientLicenseManager clientLicenseManager;
	
	@Override
	public void run(ApplicationArguments args) {
		// 初始化环境信息
		systemInfoUtil.initSystemInfo();
		// 打印环境信息
		systemInfoUtil.printSystemInfo();
		// 首次安装和检测
		clientLicenseManager.installAndCheck();
	}
}