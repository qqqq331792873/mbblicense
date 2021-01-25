package server.listen;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import server.manager.ServerLicenseManager;

import javax.annotation.Resource;

/**
 * 启动监听器:用于启动时检查
 * 监听器,启动的时候用于监听授权文件
 *
 * @author 马冰冰
 */
@Component
public class ServerApplicationRunner implements ApplicationRunner {
	@Resource
	ServerLicenseManager serverLicenseManager;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		serverLicenseManager.generate();
		System.out.println("生成成功");
	}
}
