package mbblicense.client.runner;

import mbblicense.client.manager.ClientLicenseManager;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 启动监听器:用于启动时检查
 * 监听器,启动的时候用于监听授权文件
 *
 * @author 马冰冰
 */
@PropertySource("classpath:clientKeys.properties")
@Component
public class ClientApplicationRunner implements ApplicationRunner {
	@Resource
	ClientLicenseManager clientLicenseManager;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		clientLicenseManager.installAndVerify();
	}
}
