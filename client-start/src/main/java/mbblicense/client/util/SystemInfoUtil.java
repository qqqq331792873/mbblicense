package mbblicense.client.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.*;

/**
 * mbb 工具类,用于获取当前机器的信息
 */
@Data
@Slf4j
@Component
public class SystemInfoUtil {
	/**
	 * mbb 微服务名字
	 */
	@Value("${spring.application.name:null}")
	private String      serverName    = "";
	/**
	 * mbb ip地址集合
	 */
	private Set<String> ipAddress     = new HashSet<>();
	/**
	 * mbb mac地址集合
	 */
	private Set<String> macAddress    = new HashSet<>();
	/**
	 * mbb cpu序列号
	 */
	private String      CPUSerial     = "";
	/**
	 * mbb 主板序列号
	 */
	private String      motherboardSN = "";
	/**
	 * mbb 硬盘SN
	 */
	private String      hardDiskSN    = "";
	/**
	 * mbb 初始化标识
	 */
	private boolean     initOK        = false;
	
	/**
	 * mbb 根据系统类别初始化系统信息
	 */
	public void initSystemInfo() {
		initIpAddress();
		initMacAddress();
		initCPUSerial();
		initMotherboardSN();
		initHardDiskSN();
		initOK = true;
	}
	
	/**
	 * mbb 初始化IP地址
	 */
	private void initIpAddress() {
		Set<String> ipAddress = new HashSet<>();
		
		// windows系统
		if (isWindowsOS()) {
			try {
				ipAddress.add(InetAddress.getLocalHost().getHostAddress());
			} catch (Exception e) {
				log.error("获取IP地址异常", e);
			}
		}
		// linux系统
		else {
			try {
				Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
				while (networkInterfaces.hasMoreElements()) {
					NetworkInterface         ni   = networkInterfaces.nextElement();
					Enumeration<InetAddress> nias = ni.getInetAddresses();
					while (nias.hasMoreElements()) {
						InetAddress ia = nias.nextElement();
						if (!ia.isLinkLocalAddress() && !ia.isLoopbackAddress() && ia instanceof Inet4Address) {
							ipAddress.add(ia.getHostAddress());
						}
					}
				}
			} catch (Exception e) {
				log.error("获取IP地址异常", e);
			}
		}
		setIpAddress(ipAddress);
	}
	
	/**
	 * mbb 初始化Mac地址
	 */
	private void initMacAddress() {
		Set<String> macAddress = new HashSet<>();
		if (isWindowsOS()) {
			try {
				byte[]        mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
				StringBuilder sb  = new StringBuilder();
				if (mac != null) {
					for (int i = 0; i < mac.length; i++) {
						if (i != 0) {
							sb.append("-");
						}
						//字节转换为整数
						int    temp = mac[i] & 0xff;
						String str  = Integer.toHexString(temp);
						//System.out.println("每8位:"+str);
						if (str.length() == 1) {
							sb.append("0").append(str);
						}
						else {
							sb.append(str);
						}
					}
				}
				macAddress.add(sb.toString().toUpperCase());
			} catch (Exception e) {
				log.error("获取Mac地址异常", e);
			}
		}
		else {
			try {
				java.util.Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
				StringBuilder                           sb = new StringBuilder();
				while (en.hasMoreElements()) {
					NetworkInterface       iface = en.nextElement();
					List<InterfaceAddress> addrs = iface.getInterfaceAddresses();
					for (InterfaceAddress addr : addrs) {
						InetAddress      ip      = addr.getAddress();
						NetworkInterface network = NetworkInterface.getByInetAddress(ip);
						if (network == null) {
							continue;
						}
						byte[] mac = network.getHardwareAddress();
						if (mac == null) {
							continue;
						}
						sb.delete(0, sb.length());
						for (int i = 0; i < mac.length; i++) {
							sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
						}
						macAddress.add(sb.toString());
					}
				}
			} catch (Exception e) {
				log.error("获取Mac地址异常", e);
			}
		}
		setMacAddress(macAddress);
	}
	
	/**
	 * mbb 获取CPU序列号
	 */
	private void initCPUSerial() {
		String CPUSerial = "";
		if (isWindowsOS()) {
			try {
				Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
				process.getOutputStream().close();
				Scanner sc = new Scanner(process.getInputStream());
				sc.next();
				CPUSerial = sc.next();
			} catch (Exception e) {
				log.error("获取CPU序列号异常", e);
			}
		}
		else {
			try {
				// 管道
				Process        process        = Runtime.getRuntime().exec(new String[]{"sh", "-c", "dmidecode"});
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String         line;
				int            index;
				while ((line = bufferedReader.readLine()) != null) {
					// 寻找标示字符串[hwaddr]
					index = line.toLowerCase().indexOf("uuid");
					// 找到了
					if (index >= 0) {
						// 取出mac地址并去除2边空格
						CPUSerial = line.substring(index + "uuid".length() + 1).trim();
						break;
					}
				}
				
			} catch (Exception e) {
				log.error("获取CPU序列号异常", e);
			}
		}
		setCPUSerial(CPUSerial);
	}
	
	/**
	 * mbb 获取主板序列号
	 */
	private void initMotherboardSN() {
		if (isWindowsOS()) {
			try {
				StringBuilder result = new StringBuilder();
				File          file   = File.createTempFile("realhowto", ".vbs");
				file.deleteOnExit();
				FileWriter fw = new java.io.FileWriter(file);
				
				String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n" + "Set colItems = objWMIService.ExecQuery _ \n" + "   (\"Select * from Win32_BaseBoard\") \n" + "For Each objItem in colItems \n" + "    Wscript.Echo objItem.SerialNumber \n" + "    exit for  ' do the first cpu only! \n" + "Next \n";
				
				fw.write(vbs);
				fw.close();
				String         path  = file.getPath().replace("%20", " ");
				Process        p     = Runtime.getRuntime().exec("cscript //NoLogo " + path);
				BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
				String         line;
				while ((line = input.readLine()) != null) {
					result.append(line);
				}
				input.close();
				
				motherboardSN = result.toString().trim();
			} catch (Exception e) {
				log.error("获取主板序列号异常", e);
			}
		}
		else {
			try {
				String         result       = "";
				String         maniBord_cmd = "dmidecode | grep 'Serial Number' | awk '{print $3}' | tail -1";
				Process        process      = Runtime.getRuntime().exec(new String[]{"sh", "-c", maniBord_cmd});// 管道
				BufferedReader br           = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String         line;
				while ((line = br.readLine()) != null) {
					result += line;
					break;
				}
				br.close();
				motherboardSN = result;
			} catch (IOException e) {
				log.error("获取主板信息错误", e);
			}
		}
		setMotherboardSN(motherboardSN);
	}
	
	/**
	 * mbb 获取硬盘序列号
	 */
	private void initHardDiskSN() {
		String hardDiskSN = "";
		if (isWindowsOS()) {
			try {
				String         line;
				String         HdSerial   = "";//定义变量 硬盘序列号
				Process        proces     = Runtime.getRuntime().exec("cmd /c dir c:");//获取命令行参数
				BufferedReader buffreader = new BufferedReader(new InputStreamReader(proces.getInputStream(), "gbk"));
				
				while ((line = buffreader.readLine()) != null) {
					if (line.contains("卷的序列号是 ")) {  //读取参数并获取硬盘序列号
						HdSerial = line.substring(line.indexOf("卷的序列号是 ") + "卷的序列号是 ".length());
						break;
					}
				}
				
				hardDiskSN = HdSerial;
			} catch (Exception e) {
				log.error("获取硬盘序列号异常", e);
			}
		}
		// linux不支持硬盘序列号
		else {
			hardDiskSN = "";
		}
		setHardDiskSN(hardDiskSN);
	}
	
	/**
	 * mbb 是否为windows
	 * 不是windows一律当做linux,其他系统不支持.
	 */
	private boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String  osName      = System.getProperty("os.name");
		if (osName.toLowerCase().contains("windows")) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}
	
	/**
	 * mbb 打印本机信息
	 */
	public void printSystemInfo() {
		if (!initOK) {
			initSystemInfo();
		}
		System.out.println("--------------------------本机信息start--------------------------");
		System.out.println("IpAddress:" + getIpAddress());
		System.out.println("MacAddress:" + getMacAddress());
		System.out.println("CPUSerial:" + getCPUSerial());
		System.out.println("MotherboardSN:" + getMotherboardSN());
		System.out.println("HardDiskSN:" + getHardDiskSN());
		System.out.println("ServerName:" + getServerName());
		System.out.println("--------------------------本机信息end--------------------------");
	}
}
