package dianfan.test;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.entities.store.ESSearchParam;
import dianfan.models.ResultBean;
import dianfan.service.store.EasyRegimentStoreService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-context.xml", "classpath:spring-redis.xml"})
public class StoreTest {
	@Autowired
	private EasyRegimentStoreService easyRegimentService;

	@Test
	public void findEasyRegimentStore() throws IOException {
		ESSearchParam essp = new ESSearchParam();
		essp.setPage(1);
		essp.setPagesize(8);
//		essp.setAreaCode("320214");
		essp.setOpen(1);
		essp.setLongitude("120.31237");
		essp.setLatitude("31.49099");
//		essp.setGoodsClassifyId("8dc4a1317db111e88dd352540054a904");
		essp.setDistanceSort(1);
		essp.setMapShow(1);
		ResultBean bean = easyRegimentService.findEasyRegimentStore(essp);
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(bean);
		System.out.println(string);
	}
	
	@Test
	public void findStoreFiltrate() throws JsonProcessingException {
		ResultBean bean = easyRegimentService.findStoreFiltrate("320200","",null);
		ObjectMapper mapper = new ObjectMapper();
		String string = mapper.writeValueAsString(bean);
		System.out.println(string);
	}
}
