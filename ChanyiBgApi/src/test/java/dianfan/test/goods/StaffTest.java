package dianfan.test.goods;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.dao.mapper.base.AreaMapper;
import dianfan.service.usermanage.StaffService;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:spring-test.xml", "classpath:spring-redis.xml"})
public class StaffTest {
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private StaffService staffService;
	@Autowired
	private AreaMapper areaMapper;
	
	@Test
	public void findStaffList() throws JsonProcessingException {
//		ResultBean bean = staffService.findStaffList(1, 10);
//		String string = mapper.writeValueAsString(bean);
//		System.err.println(string);
	}
	
	@Test
	public void map() {
		String[] ctts = {"a","b","c"};
		for(int i=0; i<ctts.length; i++) {
			ctts[i] = "http://" + ctts[i];
		}
		System.err.println(StringUtils.join(ctts, ","));
	}
	
}
