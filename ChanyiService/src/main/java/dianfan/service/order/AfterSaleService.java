package dianfan.service.order;

import java.util.Map;

import dianfan.models.ResultBean;

/**
 * @ClassName AfterSaleService
 * @Description 售后服务
 * @author cjy
 * @date 2018年8月7日 上午10:24:46
 */
public interface AfterSaleService {

	/**
	 * @Title: findAfterSaleList
	 * @Description: 获取售后数据列表
	 * @param page
	 * @param pageSize
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年8月7日 下午1:16:35
	 * @author cjy
	 */
	ResultBean findAfterSaleList(int page, int pageSize, Map<String, Object> param);

}
