package dianfan.test.goods;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.models.ResultBean;
import dianfan.service.goods.GoodsService;
import dianfan.service.goods.GoodsSpecService;
import dianfan.service.order.OrderClassService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class Goodstest {
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private OrderClassService orderClassService;
	@Autowired
	private GoodsSpecService goodsSpecService;
	
	
	
	@Test
	public void findGoodslist() throws JsonProcessingException {
		ResultBean g = goodsService.getGoodsDetails("8df107431e0b4588aa6fab0b2e7ba834", "1");
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(g);
		System.out.println(string);
		System.out.println("成功！！！！！！！！！！！！！！！！！！！");
	}
	@Test
	public void findGoodslist2() throws JsonProcessingException {
		ResultBean g = orderClassService.fildOrderInfo("304407019a714d29b592aabe2035d7cc", "2018071016450928464226");
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(g);
		System.out.println(string);
		System.out.println("成功！！！！！！！！！！！！！！！！！！！");
	}
	
	@Test
	public void test3() throws JsonProcessingException {
		ResultBean g = goodsService.delGoodsFavorites("bf64097c2ba6440db05cb1634e3609de", "15");
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(g);
		System.out.println(string);
		System.out.println("成功！！！！！！！！！！！！！！！！！！！");
	}
	
	@Test
	public void test4() throws JsonProcessingException {
		ResultBean g = orderClassService.fildOrderList("bf64097c2ba6440db05cb1634e3609de", "1", 2, 2);
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(g);
		System.out.println(string);
		System.out.println("成功！！！！！！！！！！！！！！！！！！！");
	}
	
	@Test
	public void test5() throws JsonProcessingException {
		ResultBean g = goodsSpecService.findGoodsSpecByGoodsid("1");
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(g);
		System.out.println(string);
		System.out.println("成功！！！！！！！！！！！！！！！！！！！");
	}
	
	@Test
	public void test6() throws JsonProcessingException {
		ResultBean g = goodsService.getGoodsDetails("8496a7a2d200479ab8a4fcb6b5fd1e83", "d96a4967832011e88dd352540054a904");
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(g);
		System.out.println(string);
		System.out.println("成功！！！！！！！！！！！！！！！！！！！");
	}
	

	

	@Test
	public void test8() throws JsonProcessingException {
		ResultBean g = goodsService.getGoodsDetails("8496a7a2d200479ab8a4fcb6b5fd1e83", "d96a4967832011e88dd352540054a904");
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(g);
		System.out.println(string);
		System.out.println("成功！！！！！！！！！！！！！！！！！！！");
	}

	
	@Test
	public void test7() throws JsonProcessingException {
		ResultBean g = orderClassService.fildOrderList("304407019a714d29b592aabe2035d7cc", "00",1, 5);
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(g);
		System.out.println(string);
		System.out.println("成功！！！！！！！！！！！！！！！！！！！");
	}
	
	
	

	@Test
	public void test9() throws JsonProcessingException {
		ResultBean g = orderClassService.fildOrderList("304407019a714d29b592aabe2035d7cc", "00",1, 5);
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(g);
		System.out.println(string);
		System.out.println("成功！！！！！！！！！！！！！！！！！！！");
	}
	@Test
	public void test10() throws JsonProcessingException {
		ResultBean g = orderClassService.fildOrderInfo("8df107431e0b4588aa6fab0b2e7ba834", "2018071022015907008241");
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(g);
		System.out.println(string);
		System.out.println("成功！！！！！！！！！！！！！！！！！！！");
	}
	
	@Test
	public void test11() throws JsonProcessingException {
		ResultBean g = orderClassService.fildOrderInfo("8496a7a2d200479ab8a4fcb6b5fd1e83", "2018071118060410520424");
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(g);
		System.out.println(string);
		System.out.println("成功！！！！！！！！！！！！！！！！！！！");
	}
	
}
