package dianfan.test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.constant.ConstantIF;
import dianfan.entities.sms.SmsTemplate;
import dianfan.service.impl.RedisService;
import dianfan.service.sms.SmsService;
import dianfan.util.PropertyUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class SmsTest {
	@Autowired
	private SmsService smsService;
	@Autowired
	private RedisService redisService;
	
	ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void smsSendTest () throws JsonProcessingException, com.aliyuncs.exceptions.ClientException, UnsupportedEncodingException {
		SmsTemplate tpl = new SmsTemplate();
		tpl.setPhoneNumbers("18861528826");
		tpl.setTemplateCode(PropertyUtil.getProperty("alisms_tplCode_1"));
		Map<String, String> map = new HashMap<>();
		map.put("code", "1234");
		tpl.setTemplateParam(map);
		SendSmsResponse sendSms = smsService.sendSms(tpl);
		
		System.out.println("-----------------------------------");
		System.out.println(sendSms);
		
	}
	
	@Test
	public void sex() {
		redisService.setEx("32df96483c0745728f71e7ed62907adb", ConstantIF.TOKEN_EXPIRES_SECONDS);
	}
	
}
