/**  
* @Title: Test.java
* @Package score.test
* @Description: TODO
* @author Administrator
* @date 2018年7月13日 上午11:21:31
* @version V1.0  
*/
package dianfan.util;

import java.io.UnsupportedEncodingException;

/**
 * @Title: Test.java
 * @Package score.test
 * @Description: TODO
 * @author Administrator
 * @date 2018年7月13日 上午11:21:31
 * @version V1.0
 */
public class Test {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String a = "🦁";
		String ret = EmojiUtil.emojiConverterUnicodeStr(a);
		System.out.println(ret.length());
		int num = a.length();
		System.out.println(num);
		a = "中文";
		num = a.length();
		a = "";
		num = a.length();
		System.out.println(num);

	}

}
