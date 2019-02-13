package dianfan.back.test;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.models.ResultBean;
import dianfan.service.adminlogin.AdminLoginService;
import dianfan.service.base.RegionService;
import dianfan.service.brokerage.BrokerageService;
import dianfan.service.store.EasyRegimentStoreService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-redis.xml" })
public class test2 {

	@Autowired
	private BrokerageService brokerageService;
	@Autowired
	private RegionService regionService;
	@Autowired
	private AdminLoginService adminLoginService;
	@Autowired
	private EasyRegimentStoreService easyRegimentStoreService;

	@Test
	public void test24() throws IOException {
		// ResultBean feedbackList = brokerageService.updatawithdrawalApproval("11",
		// "4", "793604e8800d11e88dd352540054a904");
		// ObjectMapper mapper = new ObjectMapper();
		// String string = mapper.writeValueAsString(feedbackList);
		// System.err.println(string);
		// System.err.println("成功！！！！！！！！！！！！！！！！！！！");

	}

	@Test
	public void test25() throws IOException {
		ResultBean feedbackList = adminLoginService.findOperateLogList("1111", 1, 2, null, null, null, null);
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(feedbackList);
		System.err.println(string);
		System.err.println("成功！！！！！！！！！！！！！！！！！！！");

	}
	
	@Test
	public void test26() throws IOException {
		ResultBean feedbackList = easyRegimentStoreService.findExperienceInfo("1");
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(feedbackList);
		System.err.println(string);
		System.err.println("成功！！！！！！！！！！！！！！！！！！！");
		
	}

}
