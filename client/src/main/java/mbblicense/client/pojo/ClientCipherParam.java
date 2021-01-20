package mbblicense.client.pojo;

import de.schlichtherle.license.CipherParam;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 客户端密码参数
 *
 * @author 马冰冰
 */
@Data
@Component
public class ClientCipherParam implements CipherParam {
	@Value("${mbblicense.client.info.storePwd}")
	private String storePwd;// 秘钥库密码
	
	@Override
	public String getKeyPwd() {
		return storePwd;
	}
}
