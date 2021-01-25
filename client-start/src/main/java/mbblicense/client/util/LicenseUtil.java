package mbblicense.client.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Scanner;

/**
 * 工具类,用于获取当前机器的信息
 *
 * @author 马冰冰
 */
@Slf4j
@Component
public class LicenseUtil {
	@Value("${spring.application.name:null}")
	private String serverName = "";
	
	/**
	 * 获取当前的ip地址
	 */
	public String getIpAddress() throws Exception {
		return InetAddress.getLocalHost().getHostAddress();
	}
	
	/**
	 * 获取当前MAC地址
	 */
	public String getMacAddress() throws Exception {
		byte[]        mac = NetworkInterface.getByInetAddress(InetAddress.getLocalHost()).getHardwareAddress();
		StringBuilder sb  = new StringBuilder();
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
		return sb.toString().toUpperCase();
	}
	
	/**
	 * 获取CPU序列号
	 */
	public String getCPUSerial() throws Exception {
		Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
		process.getOutputStream().close();
		Scanner sc = new Scanner(process.getInputStream());
		sc.next();
		return sc.next();
	}
	
	/**
	 * 获取主板序列号
	 */
	public String getMotherboardSN() throws Exception {
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
		
		return result.toString().trim();
	}
	
	/**
	 * 获取硬盘序列号
	 */
	public String getHardDiskSN() throws Exception {
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
		
		return HdSerial;
	}
	
	/**
	 * 获取服务名字
	 */
	public String getServerName() {
		return serverName;
	}
	
	/**
	 * 打印本机信息
	 */
	public void PrintMachineInformation() throws Exception {
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
