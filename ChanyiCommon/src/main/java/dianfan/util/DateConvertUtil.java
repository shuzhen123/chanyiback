package dianfan.util;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @Title: DateConvertUtil.java
 * @Package dianfan.util
 * @Description: spring中日期转换
 * @author Administrator
 * @date 2018年5月11日 上午11:59:56
 * @version V1.0
 */
public class DateConvertUtil extends PropertyEditorSupport {
	/**
	 * 转换格式
	 */
	private DateFormat format;

	/**
	 * 默认构造方法设定格式
	 */
	public DateConvertUtil() {
		this.format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 否早方法传入格式
	 * 
	 * @param format
	 *            转换格式
	 */
	public DateConvertUtil(String format) {
		this.format = new SimpleDateFormat(format);
	}

	/** Date-->String **/
	@Override
	public String getAsText() {
		if (getValue() == null) {
			return "";
		}
		return this.format.format(getValue());
	}

	/** String-->Date **/
	@Override
	public void setAsText(String text) {
		if (!StringUtils.isNotBlank(text)) {
			setValue(null);
		} else {
			try {
				setValue(this.format.parse(text));
			} catch (ParseException e) {
				throw new IllegalArgumentException("不能被转换的日期字符串，请检查!", e);
			}
		}
	}
}
