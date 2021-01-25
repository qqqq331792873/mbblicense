package mbblicense.client.runner;

import mbblicense.client.util.LicenseUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 启动监听器:用于启动时检查
 * 项目启动的时候检查
 *
 * @author 马冰冰
 */
@Component
@ConditionalOnProperty(prefix = "mbblicense.client", name = "debugger", havingValue = "true")
public class ClientApplicationRunner implements ApplicationRunner {
	@Resource
	LicenseUtil licenseUtil;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// 我们提供了一个工具类,用于获取本机信息
		licenseUtil.PrintMachineInformation();
	}
}