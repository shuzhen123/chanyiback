package dianfan.back.test;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.models.ResultBean;
import dianfan.service.adminmanage.AdminFeedbackService;
import dianfan.service.adminmanage.AdminService;
import dianfan.service.brokerage.BrokerageService;
import dianfan.service.content.ArticleService;
import dianfan.service.content.BannerAdminService;
import dianfan.service.goods.LogisticsServices;
import dianfan.service.usermanage.BasicsService;
import dianfan.service.usermanage.UserServiceManage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class test1 {


	@Autowired
	private BannerAdminService bannerAdminService;
	@Autowired
	private AdminFeedbackService AdminFeedbackService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private UserServiceManage userServiceManage;
	@Autowired
	private BasicsService basicsService;
	@Autowired
	private AdminService adminService;
	@Autowired
	private LogisticsServices logisticsServices;
	@Autowired
	private BrokerageService brokerageService;
	
	
	@Test
	public void test24() throws IOException {
		ResultBean feedbackList = basicsService.addAdminPopedoms("11", "11", "11", "11", "11", "11");
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(feedbackList);
		System.err.println(string);
		System.err.println("成功！！！！！！！！！！！！！！！！！！！");
		
	}
	@Test
	public void test25() throws IOException {
		/*ResultBean feedbackList = brokerageService.findBrokerageList("112233", 1, 10, null, null, null, null, null, null);
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(feedbackList);
		System.err.println(string);
		System.err.println("成功！！！！！！！！！！！！！！！！！！！");*/
		
	}


	
}
