package dianfan.test.goods;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.entities.goods.GoodsClassify;
import dianfan.entities.goods.GoodsLabels;
import dianfan.models.ResultBean;
import dianfan.service.goods.LabelService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class GoodsLabelTest {
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private LabelService labelService;
	
	@Test
	public void findGoodsLabelList() throws JsonProcessingException {
		List<GoodsLabels> labelList = labelService.findGoodsLabelList();
		ResultBean bean = new ResultBean(labelList);
		String string = mapper.writeValueAsString(bean);
		System.out.println(string);
	}
	
	@Test
	public void addGoodsLabel() {
		labelService.addGoodsLabel("标签B", "aaa");
	}
	
	@Test
	public void editGoodsLabel() {
		labelService.editGoodsLabel("62ea60df897711e88dd352540054a904", "3年质保", "bbb");
	}
	
	@Test
	public void delGoodsLabel() {
		String[] ids = {"009513f3898511e88dd352540054a904", "62ea60df897711e88dd352540054a904"};
		labelService.delGoodsLabel(ids , "bbb");
	}
	
}
