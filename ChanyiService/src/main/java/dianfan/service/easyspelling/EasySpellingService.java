package dianfan.service.easyspelling;

import dianfan.models.ResultBean;

/** @ClassName EasySpellingService
 * @Description 易拼接口
 * @author zwb
 * @date 2018年7月5日 上午11:25:53
 */ 
public interface EasySpellingService {
	
	/** @Title: findEasySpellingList
	 * @Description: 查询易拼成团列表
	 * @param goodsId
	 * @return
	 * @throws:
	 * @time: 2018年7月5日 上午11:25:50
	 */ 
	ResultBean findEasySpellingList(String goodsId,Integer pageNum,Integer count);

}
