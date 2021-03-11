package server.pojo;

import lombok.Data;
import lombok.ToString;

/**
 * mbb 授权文件
 */
@Data
@ToString
public class LicensePO {
	// ------------------------------------------------生成信息:必选部分------------------------------------------------
	/**
	 * 授权文件生成时间
	 */
	private Long   issued;
	/**
	 * 有效开始时间
	 */
	private Long   notBefore;
	/**
	 * 有效结束时间
	 */
	private Long   notAfter;
	/**
	 * 授权文件简介信息
	 */
	private String info;
	
	// ------------------------------------------------生成信息:扩展部分------------------------------------------------
	/**
	 * ip地址
	 */
	private String ipAddress;
	/**
	 * 网卡Mac地址
	 */
	private String macAddress;
	/**
	 * CPU序列号
	 */
	private String CPUSerial;
	/**
	 * 主板SN号
	 */
	private String motherboardSN;
	/**
	 * 硬盘SN号
	 */
	private String hardDiskSN;
	/**
	 * 服务名称
	 */
	private String serverName;
}
