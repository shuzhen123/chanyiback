package dianfan.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;

import dianfan.dao.mapper.order.OrderClassMapper;
import dianfan.dao.mapper.order.OrderCommissionMapper;
import dianfan.entities.commission.RoleCommissionPercentage;
import dianfan.entities.commission.UserBindRealtion;
import dianfan.models.ResultBean;
import dianfan.service.order.OrderClassService;
import dianfan.service.order.PayNotifyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class OrderTest {
	@Autowired
	private OrderCommissionMapper orderCommissionMapper;
	
	@Autowired
	private OrderClassService orderClassService;
	
	@Autowired
	private OrderClassMapper orderClassMapper;
	@Autowired
	private PayNotifyService payNotifyService;
	
	
	@Test
	public void getUserConsumeRelation() throws JsonProcessingException {
		UserBindRealtion realtion = orderCommissionMapper.getUserConsumeRelation("20180705124578965412");
		System.out.println(realtion);
	}
	
	@Test
	public void testOrderclose() throws JsonProcessingException {
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		param.put("userid", "111");
		param.put("orderid", "111");
		
		// 首先确认该订单的状态（非 （01-待付款）情况下，不可以关闭订单）
		String state = orderClassMapper.getOrderState(param);
		System.out.println(state);
		System.out.println("成功");
		
	}
	
	
	@Test
	public void alipayNotify() {
		BigDecimal total_fee = new BigDecimal("1000000");
		total_fee = total_fee.divide(new BigDecimal(100));
		payNotifyService.orderNotify("877d754372714fc09daa847c1958ab15", total_fee, "edfs");
	}
	
	@Test
	public void orderRefund() {
		List<String> list = new ArrayList<>();
		list.add("2018071115442546204403");
		list.add("2018071117393683568433");
		list.add("2018071110585185271628");
		list.add("2018071115453146144846");
		list.add("2018071020185304564435");
		list.add("2018071021021355077121");
		list.add("2018071209334414828680");
		list.add("2018071209264151334020");
		list.add("2018071021391380332025");
		list.add("2018071209341313627453");
		list.add("2018071123041871272167");
		list.add("2018071118110073173247");
		list.add("2018071209274421003862");
		list.add("2018071114083226158280");
		list.add("2018071209494903632347");
		list.add("2018071021051010547460");
		list.add("2018071119243247555840");
		list.add("2018071114310512717273");
		orderClassService.orderRefund(list);
	}
	

}
