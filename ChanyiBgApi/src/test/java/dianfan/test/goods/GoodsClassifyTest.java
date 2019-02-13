package dianfan.test.goods;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.entities.goods.GoodsClassify;
import dianfan.models.ResultBean;
import dianfan.service.goods.ClassifyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class GoodsClassifyTest {
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private ClassifyService classifyService;
	
	@Test
	public void findGoodsClassifyList() throws JsonProcessingException {
		ResultBean bean = classifyService.findGoodsClassifyList("1");
		String string = mapper.writeValueAsString(bean);
		System.out.println(string);
	}
	
	@Test
	public void addGoodsClassify() {
		GoodsClassify gc = new GoodsClassify();
		gc.setClassifyName("aaa小类");
		gc.setClassifyNameEn("aaa classify name");
		gc.setClassifyParentid("24366833896311e88dd352540054a904");
		gc.setClassifyLevel("1");
		gc.setSort(2);
		gc.setCreateBy("aaa");
		ResultBean bean = classifyService.addGoodsClassify(gc);
		System.out.println(bean);
	}
	
	@Test
	public void editGoodsClassify() {
		GoodsClassify gc = new GoodsClassify();
		gc.setId("b4b5dc76896111e88dd352540054a904");
		gc.setClassifyName("aaa小类a");
		gc.setClassifyNameEn("aaaaaaaa classify name");
		gc.setClassifyParentid("476e768c896111e88dd352540054a904");
		gc.setSort(10);
		gc.setUpdateBy("bbb");
		classifyService.editGoodsClassify(gc);
	}
	
	@Test
	public void delGoodsClassify() {
		String ids = "24366833896311e88dd352540054a904";
		classifyService.delGoodsClassify(ids , 1, "aaa");
	}
}
