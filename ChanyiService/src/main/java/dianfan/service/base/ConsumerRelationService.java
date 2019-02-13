package dianfan.service.base;

import dianfan.models.ResultBean;

/**
 * @ClassName ConsumerRelationService
 * @Description 消费关系服务
 * @author cjy
 * @date 2018年7月3日 上午9:43:28
 */
public interface ConsumerRelationService {

	/**
	 * @Title: bindConsumerRelation
	 * @Description: 绑定消费关系
	 * @param userid 用户id
	 * @param qr_num 对方二维码数据
	 * @throws:
	 * @time: 2018年7月3日 上午9:42:39
	 */
	void bindConsumerRelation(String userid, String qr_num);
	
	ResultBean bindUpLowRelation(String userid, String qr_num);
}
