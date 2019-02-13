package dianfan.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName RegexUtils
 * @Description 匹配器工具类
 * @author cjy
 * @date 2017年12月21日 上午9:04:43
 */
public class RegexUtils {
	
	private static String regex;

	private RegexUtils() {
	}

	/**
	 * @Title: phoneRegex
	 * @Description: 校验手机号码
	 * @param phone 手机号码
	 * @return Boolean
	 * @throws
	 */
	public static boolean phoneRegex(String phone) {
		if (phone == null || "".equals(phone.trim())) {
			return false;
		}
		//yingjun 电话号码校验规则修改 start
		//regex = "^1(3[0-9]|4[57]|5[0-3 5-9]|7[01678]|8[0-9])\\d{8}$";
		regex = "^1[3-9]\\d{9}$";
		//yingjun 电话号码校验规则修改 end
		return checked(phone);
	}
	
	/**
	 * @Title: pwdRegex
	 * @Description: 密码位数校验
	 * @param pwd 密码字符串
	 * @return
	 * @throws
	 */
	public static boolean pwdRegex(String pwd) {
		if (pwd == null || "".equals(pwd)) {
			return false;
		}
		regex = "^.{4,16}$";
		return checked(pwd);
	}
	
	/**
	 * @Title: banknoRegex
	 * @Description: 
	 * @param bankno 银行卡号校验
	 * @return
	 * @throws:
	 * @time: 2018年7月10日 上午11:23:46
	 */
	public static boolean banknoRegex(String bankno) {
		if (bankno == null || "".equals(bankno)) {
			return false;
		}
		regex = "(^\\d{19}$)|(^\\d{16}$)";
		return checked(bankno);
	}
	
	/**
     * 校验身份证
     * 
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
    	if (idCard == null || "".equals(idCard.trim())) {
			return false;
		}
    	regex = "(^\\d{18}$)|(^\\d{15}$)";
        return checked(idCard);
    }
    
    /**
    * 验证邮箱
    * 
    * @param 待验证的字符串
    * @return 如果是符合的字符串,返回 <b>true </b>,否则为 <b>false </b>
    */
    public static boolean isEmail(String email) {
    	if (email == null || "".equals(email.trim())) {
			return false;
		}
       regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    return checked(email);
    }

	/**
	 * @Title: checked
	 * @Description: 执行正则判断
	 * @param attr 待校验字符串
	 * @return boolean
	 * @throws
	 */
	private static boolean checked(String attr) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(attr);
		return matcher.matches();
	}

}
