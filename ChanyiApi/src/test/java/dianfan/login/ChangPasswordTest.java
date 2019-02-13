package dianfan.login;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import dianfan.md5.MD5;
import dianfan.service.login.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:conf/spring.xml","classpath*:conf/spring-mybatis.xml"})
public class ChangPasswordTest {
	
	@Autowired
	public UserService UserServicelmpl;
	
	@Test
	public void test1 () {
		String pwd = MD5.string2MD5("123456");
		UserServicelmpl.updatePassword("4a5d41767ac411e88dd352540054a904", "18352507009", pwd);
	}
	
	
}
