/**  
* @Title: EmojiUtil.java
* @Package score.test
* @Description: TODO
* @author Administrator
* @date 2018年7月13日 上午11:47:02
* @version V1.0  
*/
package dianfan.util;

import com.github.binarywang.java.emoji.EmojiConverter;

/**
 * @Title: EmojiUtil.java
 * @Package dianfan.util
 * @Description: TODO
 * @author Administrator
 * @date 2018年7月13日 上午11:47:02
 * @version V1.0
 */
public class EmojiUtil {
	private static EmojiConverter emojiConverter = EmojiConverter.getInstance();

	/**
	 * 将emojiStr转为 带有表情的字符
	 * 
	 * @param emojiStr
	 * @return
	 */
	public static String emojiConverterUnicodeStr(String emojiStr) {
		String result = emojiConverter.toUnicode(emojiStr);
		return result;
	}

	/**
	 * 带有表情的字符串转换为编码
	 * 
	 * @param str
	 * @return
	 */
	public static String emojiConverterToAlias(String str) {
		String result = emojiConverter.toAlias(str);
		return result;
	}
}
