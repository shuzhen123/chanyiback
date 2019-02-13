package dianfan.test.goods;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.entities.store.Store;
import dianfan.map.tencent.IpLocationRet;
import dianfan.map.tencent.TencentMapApi;
import dianfan.models.ResultBean;
import dianfan.service.store.EasyRegimentStoreService;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:spring-test.xml", "classpath:spring-redis.xml"})
public class StoreTest {
	private ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private EasyRegimentStoreService storeService;
	
	@Test
	public void findStoreList() throws JsonProcessingException {
		Store store = new Store();
//		store.setApplPhoneNum("186");
//		store.setApplyName("瑞德");
//		store.setCityCode("510112");
		store.setBusinessWeeklyStart("1");
		store.setBusinessWeeklyEnd("7");
		ResultBean bean = storeService.findStoreList(1, 10, store);
		System.err.println(mapper.writeValueAsString(bean));
	}
	
	@Test
	public void addStore() {
		Store store = new Store();
		
		store.setApplyName("我的体验店");
		store.setCityCode("320214");
		store.setApplPhoneNum("18899998888");
		store.setApplyAddr("长江北路106号");
		store.setArea("160");
		store.setDoorheadUrl("upload/store/20180724/abc.png");
		store.setInnerUrl00("upload/store/20180724/def.png");
		store.setInnerUrl01("upload/store/20180724/ghi.png");
		store.setBusinessLicenceUrl("upload/store/20180724/jkl.png");
		store.setBusinessWeeklyStart("1");
		store.setBusinessWeeklyEnd("7");
		store.setBusinessTimeEnd("21:30");
		store.setBusinessTimeStart("8:00");
		store.setHandleIdcard("upload/store/20180724/idcard.png");
		store.setApplyUserid("02a6b521832d11e88dd352540054a904");
		store.setCreateBy("cjy");
		store.setApplyCurrentBusiness("主营床垫");
		
		String[] classifyids = {"8dc943957db111e88dd352540054a904", "92aba0d2898a11e88dd352540054a904", "9a06c9867db111e88dd352540054a904"};
		storeService.addStore(store, classifyids, null);
	}
	
	@Test
	public void getStoreDetail() throws JsonProcessingException {
		ResultBean bean = storeService.getStoreDetail("69fd9ccf841811e8936d00163e0076b2");
		System.out.println(mapper.writeValueAsString(bean));
	}
	
	@Test
	public void editStore() {
		Store store = new Store();
		
		store.setId("8def42fd8bc74da0861248fd742c557d");
		store.setApplyName("我的体验店aaa");
		store.setCityCode("320214");
		store.setApplPhoneNum("18899998888");
		store.setApplyAddr("长江北路106号");
		store.setArea("160");
		store.setDoorheadUrl("upload/store/20180724/abc.png");
		store.setInnerUrl00("upload/store/20180724/def.png");
		store.setInnerUrl01("upload/store/20180724/ghi.png");
		store.setBusinessLicenceUrl("upload/store/20180724/jkl.png");
		store.setBusinessWeeklyStart("1");
		store.setBusinessWeeklyEnd("7");
		store.setBusinessTimeEnd("21:30");
		store.setBusinessTimeStart("8:00");
		store.setHandleIdcard("upload/store/20180724/idcard.png");
		store.setUpdateBy("cjy");
		store.setApplyCurrentBusiness("主营床垫aaa");
		
		String[] classifyids = {"92aba0d2898a11e88dd352540054a904", "9a06c9867db111e88dd352540054a904"};
		storeService.editStore(store, classifyids);
	}
	
	@Test
	public void delStore() {
		String[] storeids = {"8def42fd8bc74da0861248fd742c557d"};
	}
	
	@Test
	public void ip() {
		TencentMapApi tma = new TencentMapApi();
		IpLocationRet ret = tma.getAreaByIp("49.80.161.116");
		System.err.println(ret);
	}
}
