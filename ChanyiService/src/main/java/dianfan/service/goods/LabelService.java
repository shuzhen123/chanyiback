package dianfan.service.goods;

import java.util.List;

import dianfan.entities.goods.GoodsLabels;
import dianfan.models.ResultBean;

/**
 * @ClassName LabelService
 * @Description 商品标签服务
 * @author cjy
 * @date 2018年7月17日 上午11:11:52
 */
public interface LabelService {

	/**
	 * @Title: findGoodsLabelList
	 * @Description: 获取商品标签列表
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 上午11:14:15
	 */
	List<GoodsLabels> findGoodsLabelList();

	/**
	 * @Title: addGoodsLabel
	 * @Description: 新增商品标签
	 * @param labelName 商品标签名称
	 * @param userid 添加者userid
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 上午11:27:38
	 */
	ResultBean addGoodsLabel(String labelName, String userid);

	/**
	 * @Title: editGoodsLabel
	 * @Description: 商品标签修改
	 * @param labelid  商品标签id
	 * @param labelName 商品标签名称
	 * @param userid 添加者userid
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午12:13:42
	 */
	ResultBean editGoodsLabel(String labelid, String labelName, String userid);

	/**
	 * @Title: delGoodsLabel
	 * @Description: 商品标签删除
	 * @param labelids 商品标签id数组
	 * @param userid 更新者userid
	 * @return
	 * @throws:
	 * @time: 2018年7月17日 下午1:05:48
	 */
	ResultBean delGoodsLabel(String[] labelids, String userid);

}
