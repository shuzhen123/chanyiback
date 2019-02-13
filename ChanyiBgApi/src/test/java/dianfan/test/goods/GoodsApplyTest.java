package dianfan.test.goods;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.models.ResultBean;
import dianfan.service.goods.GoodsApplyService;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:spring-test.xml", "classpath:spring-redis.xml"})
public class GoodsApplyTest {
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private GoodsApplyService goodsApplyService;
	
	@Test
	public void findGoodsApplyList() throws JsonProcessingException {
		ResultBean bean = goodsApplyService.findGoodsApplyList(1, 10, null, null, null);
		String string = mapper.writeValueAsString(bean);
		System.out.println(string);
	}
	
	@Test
	public void goodsApplyApprove() throws JsonProcessingException {
		ResultBean bean = goodsApplyService.goodsApplyApprove("1", "01", "不通过", "cjy");
		String string = mapper.writeValueAsString(bean);
		System.out.println(string);
	}
	
	@Test
	public void abc()  {
		int i = 2 << 2;
		System.out.println(i);
	}
	
}
