package dianfan.entities.sms;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName SmsTemplate
 * @Description 短信模板数据
 * @author cjy
 * @date 2018年6月28日 下午6:42:36
 */
public class SmsTemplate implements Serializable{
	/** @Fields serialVersionUID
	 */
	private static final long serialVersionUID = 1608080169651724709L;
	/*
	 * 短信接收号码,支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,
	 * 批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；
	 * 发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
	 */
	private String phoneNumbers;
	private String templateCode; //短信模板ID
	private Map<String, String> templateParam; //短信模板变量替换JSON串,友情提示:如果JSON中需要带换行符,请参照标准的JSON协议。
	private String smsUpExtendCode; //上行短信扩展码,无特殊需要此字段的用户请忽略此字段
	private String outId; //外部流水扩展字段
	public String getPhoneNumbers() {
		return phoneNumbers;
	}
	public void setPhoneNumbers(String phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}
	public String getTemplateCode() {
		return templateCode;
	}
	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}
	public Map<String, String> getTemplateParam() {
		return templateParam;
	}
	public void setTemplateParam(Map<String, String> templateParam) {
		this.templateParam = templateParam;
	}
	public String getSmsUpExtendCode() {
		return smsUpExtendCode;
	}
	public void setSmsUpExtendCode(String smsUpExtendCode) {
		this.smsUpExtendCode = smsUpExtendCode;
	}
	public String getOutId() {
		return outId;
	}
	public void setOutId(String outId) {
		this.outId = outId;
	}
	@Override
	public String toString() {
		return "SmsTemplate [phoneNumbers=" + phoneNumbers + ", templateCode=" + templateCode + ", templateParam="
				+ templateParam + ", smsUpExtendCode=" + smsUpExtendCode + ", outId=" + outId + "]";
	}
}
