package server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.manager.ServerLicenseManager;

import javax.annotation.Resource;

/**
 * @author 马冰冰
 */
@Slf4j
@RestController
@RequestMapping("/server")
public class ServerController {
	@Resource
	ServerLicenseManager ServerLicenseManager;

	@GetMapping("/generate")
	public void generate() {
		try {
			log.info("准备生成");
			ServerLicenseManager.generate();
			log.info("生成完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
