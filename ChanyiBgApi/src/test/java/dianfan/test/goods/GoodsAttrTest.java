package dianfan.test.goods;

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.entities.goods.GoodsClassify;
import dianfan.models.ResultBean;
import dianfan.service.goods.GoodsSpecService;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class GoodsAttrTest {
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private GoodsSpecService goodsSpecService;
	
	@Test
	public void findGoodsAttrList() throws JsonProcessingException {
		ResultBean bean = goodsSpecService.findGoodsAttrList();
		String string = mapper.writeValueAsString(bean);
		System.out.println(string);
//		System.out.println(bean);
	}
	
	@Test
	public void addGoodsAttr() {
		ResultBean bean = goodsSpecService.addGoodsAttr("A", "a", "aaa");
		System.out.println(bean);
	}
	
	@Test
	public void editGoodsAttr() {
		ResultBean bean = goodsSpecService.editGoodsAttr("1ee597a989a911e88dd352540054a904", "A", "a", "bbb");
		System.out.println(bean);
	}
	
	@Test
	public void delGoodsAttr() {
		String[] ids = {"1ee597a989a911e88dd352540054a904", "4dbb475b89a911e88dd352540054a904"};
		ResultBean bean = goodsSpecService.delGoodsAttr(ids ,"ccc");
		System.out.println(bean);
	}
	
	@Test
	public void json() throws JsonParseException, JsonMappingException, IOException {
		String json = "{\"jingdong_eclp_co_queryLwbByCondition_responce\":{\"code\":\"0\",\"coResult\":{\"resultCode\":1,\"resultData\":{\"lwbNo\":\"LD1003311462\",\"orderNo\":\"201807191627987654323\",\"lwbStatusInfo\":[{\"operation\":\"您的揽件单已经安排车辆，预计2018-07-20上门揽件\",\"operateDate\":1532054992000,\"status\":1130,\"operator\":\"gzmoshaohui\",\"operateSystem\":\"大件调度系统\"},{\"operation\":\"司机接收到上门揽件任务\",\"operateDate\":1532054992000,\"status\":1138,\"operator\":\"李绮红\",\"operateSystem\":\"大件承运系统\"},{\"operation\":\"上门揽件单预约成功，预计2018-07-20上门揽件\",\"operateDate\":1532054777000,\"status\":1120,\"operator\":\"荣锦物流运输\",\"operateSystem\":\"大件调度系统\"},{\"operation\":\"运单已下传\",\"operateDate\":1531997033000,\"status\":1040,\"operator\":\"system\"},{\"operation\":\"新建\",\"operateDate\":1531997003000,\"status\":101,\"operator\":\"chanyiwangluo\",\"operateSystem\":\"商家管理平台\"},{\"operation\":\"已下发大件中间件\",\"operateDate\":1531997003000,\"status\":103,\"operator\":\"system\",\"operateSystem\":\"商家管理平台\"},{\"operation\":\"修改\",\"operateDate\":1531997003000,\"status\":101,\"operator\":\"system\",\"operateSystem\":\"商家管理平台\"}]},\"resultMsg\":\"操作成功！\"}}}";
		Map map = mapper.readValue(json, Map.class);
		System.out.println(map);
	}
	
}
