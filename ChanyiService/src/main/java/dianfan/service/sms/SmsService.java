package dianfan.service.sms;

import java.io.UnsupportedEncodingException;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.core.JsonProcessingException;

import dianfan.entities.sms.SmsTemplate;

/**
 * @ClassName SmsService
 * @Description 短信发送服务
 * @author cjy
 * @date 2018年6月29日 上午11:51:02
 */
public interface SmsService {
    
	/**
	 * @Title: sendSms
	 * @Description: 发送短信
	 * @param tpl 短信模板数据
	 * @return SmsSendRet 发送结果
	 * @throws: ClientException
	 * @throws:
	 * @time: 2018年6月28日 下午7:16:51
	 */
	SendSmsResponse sendSms(SmsTemplate tpl) throws ClientException, UnsupportedEncodingException, JsonProcessingException;
}
