package server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.manager.ServerLicenseManager;
import server.pojo.LicensePO;

import javax.annotation.Resource;

/**
 * mbb
 */
@Slf4j
@RestController
@RequestMapping("/server")
public class ServerController {
	@Resource
	ServerLicenseManager ServerLicenseManager;
	
	@PostMapping("/generate")
	public boolean generate(@RequestBody LicensePO licensePO) {
		log.info("准备生成");
		boolean generate = ServerLicenseManager.generate(licensePO);
		if (generate) {
			log.info("生成成功");
			return true;
		}
		else {
			log.error("生成失败");
			return false;
		}
	}
}
