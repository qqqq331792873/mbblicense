package server.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 生成许可证所需要参数
 * 默认值读取配置,需要额外设置的,请自行设置
 * 但一般情况不修改.如果要修改,记得匹配自己的keystore秘钥库.
 *
 * @author 马冰冰
 */
@Data
@Component
public class ServerLicenseParam {
	// ------------------------------------------------系统服务信息------------------------------------------------
	@Value("${mbblicense.server.info.resource}")
	private String resource;// 秘钥库位置
	@Value("${mbblicense.server.info.storePwd}")
	private String storePwd;// 秘钥库密码
	@Value("${mbblicense.server.info.alias}")
	private String alias;// 秘钥
	@Value("${mbblicense.server.info.keyPwd}")
	private String keyPwd;// 秘钥密码
	@Value("${mbblicense.server.info.subject}")
	private String subject;// 项目名
	@Value("${mbblicense.server.info.licPath}")
	private String licPath;// 准备在哪里生成lisence
	
	// ------------------------------------------------生成信息:必选部分------------------------------------------------
	@Value("${mbblicense.server.manager.issued}")
	private Long   issued;//授权文件生成时间
	@Value("${mbblicense.server.manager.notBefore}")
	private Long   notBefore;//有效开始时间
	@Value("${mbblicense.server.manager.notAfter}")
	private Long   notAfter;//有效结束时间
	@Value("${mbblicense.server.manager.info}")
	private String info;//授权文件简介信息
	
	// ------------------------------------------------生成信息:扩展部分------------------------------------------------
	@Value("${mbblicense.server.manager.ipAddress:null}")
	private String ipAddress;//ip地址
	@Value("${mbblicense.server.manager.macAddress:null}")
	private String macAddress;//网卡Mac地址
	@Value("${mbblicense.server.manager.CPUSerial:null}")
	private String CPUSerial;//CPU序列号
	@Value("${mbblicense.server.manager.motherboardSN:null}")
	private String motherboardSN;//主板SN号
	@Value("${mbblicense.server.manager.hardDiskSN:null}")
	private String hardDiskSN;//硬盘SN号
	@Value("${mbblicense.server.manager.serverName:null}")
	private String serverName;//服务名称
}
