package mbblicense.client.pojo;

import lombok.Data;

/**
 * 生成许可证扩展字段
 * 不可读取默认字段,必须手动传入
 * 用哪些,就传哪些
 * @author 马冰冰
 */
@Data
public class LicenseExtra {
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
