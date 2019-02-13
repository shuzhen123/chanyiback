package dianfan.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.dao.mapper.goods.GoodsSpecMapper;
import dianfan.dao.mapper.order.OrderClassMapper;
import dianfan.models.ResultBean;
import dianfan.service.goods.GoodsService;
import dianfan.service.goods.GoodsSpecService;
import dianfan.service.order.OrderClassService;
import dianfan.service.order.OrderCommissionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class GoodsInfoTest {
	

	@Autowired
	private OrderClassService orderClassService;
	@Autowired
	private OrderClassMapper orderClassMapper;
	

}
