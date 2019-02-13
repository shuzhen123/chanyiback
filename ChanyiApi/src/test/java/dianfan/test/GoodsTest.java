package dianfan.test;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.dao.mapper.order.OrderCommissionMapper;
import dianfan.entities.commission.GoodsCommission;
import dianfan.entities.commission.UserRoleDist;
import dianfan.entities.goods.JoinGoods;
import dianfan.entities.order.Order;
import dianfan.models.ResultBean;
import dianfan.service.goods.GoodsService;
import dianfan.service.goods.GoodsSpecService;
import dianfan.service.order.OrderCommissionService;
import dianfan.service.order.PayNotifyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class GoodsTest {
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private GoodsSpecService goodsSpecService;
	@Autowired
	private OrderCommissionService orderCommissionService;
	@Autowired
	private OrderCommissionMapper orderCommissionMapper;
	@Autowired
	private PayNotifyService payNotifyService;

	@Test
	public void findGoodsSpecByGoodsid() throws JsonProcessingException {
		ResultBean bean = goodsSpecService.findGoodsSpecByGoodsid("2");
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(bean);
		System.out.println(string);
	}
	@Test
	public void findGoodslist() throws JsonProcessingException {
		ResultBean g = goodsService.getGoodsDetails("8df107431e0b4588aa6fab0b2e7ba834", "1");
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(g);
		System.out.println(string);
		System.out.println("成功！！！！！！！！！！！！！！！！！！！");
	}
	@Test
	public void test22() throws JsonProcessingException {
		ResultBean g = goodsService.getGoodsDetails("8df107431e0b4588aa6fab0b2e7ba834", "1");
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(g);
		System.out.println(string);
		System.out.println("成功！！！！！！！！！！！！！！！！！！！");
	}
	
	@Test
	public void findGoodsList() throws JsonProcessingException {
		JoinGoods pam = new JoinGoods();
		pam.setSort("1");
		pam.setPage(1);
		pam.setPagesize(10);
		pam.setUserid("bf64097c2ba6440db05cb1634e3609de");
		pam.setAll("1");
		pam.setSale("1");
		goodsService.findGoodsList(pam );
	}
	
	@Test
	public void orderNotify() {
		boolean b = payNotifyService.orderNotify("7e5989731be94f3798c0663d057581b5", new BigDecimal(1), "4200000172201808105337744325");
		System.err.println(b);
	}
	
	@Test
	public void findOrderGoodsInfo() {
		List<GoodsCommission> info = orderCommissionMapper.findOrderGoodsInfo("2018071220403652862747");
		System.err.println(info);
	}
	@Test
	public void getOSPOrCMByStoreid() {
		UserRoleDist dist = orderCommissionMapper.getOSPOrCMByStoreid("ad46f9c153c34ee0af12e7bb44527023");
		System.err.println(dist);
	}
	@Test
	public void getOSPOrCMByAddr() {
		UserRoleDist dist = orderCommissionMapper.getOSPOrCMByAddr("2018071114310512717273");
		System.err.println(dist);
	}
	@Test
	public void calculateCommission() {
		orderCommissionService.calculateCommission("2018083117473951116476");
		System.err.println("-------------ok-----------");
	}
	@Test
	public void getOrderMoney() {
		Order order = orderCommissionMapper.getOrderMoney("2018082217132063040552");
		System.err.println(order);
	}

}
