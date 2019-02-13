/**  
* @Title: CipherTextUtil.java
* @Package dianfan.util
* @Description: TODO
* @author Administrator
* @date 2018年7月11日 下午2:00:42
* @version V1.0  
*/
package dianfan.util;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import dianfan.md5.MD5;

/**
 * @Title: CipherTextUtil.java
 * @Package dianfan.util
 * @Description: TODO
 * @author Administrator
 * @date 2018年7月11日 下午2:00:42
 * @version V1.0
 */
public class CipherTextUtil {
	/**
	 * 密钥算法
	 */
	private static final String ALGORITHM = "AES";
	/**
	 * 加解密算法/工作模式/填充方式
	 */
	private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS7Padding";

	static {
		try {
			Security.addProvider(new BouncyCastleProvider());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * AES解密
	 */
	public static String decryptData(byte[] b, String serectKey) throws Exception {
		// 生成key
		SecretKeySpec key = new SecretKeySpec(MD5.string2MD5(serectKey).toLowerCase().getBytes(), ALGORITHM);
		Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
		cipher.init(Cipher.DECRYPT_MODE, key);
		return new String(cipher.doFinal(b));
	}

}
