package dianfan.service.advertorial;

import java.util.Map;

import dianfan.models.ResultBean;

/** @ClassName AdvertorialService
 * @Description 文章相关接口
 * @author zwb
 * @date 2018年6月28日 下午6:58:23
 */ 
public interface AdvertorialService {
	
	/** @Title: getAdvertorialsByClassifyId
	 * @Description: 通过分类id查询文章列表
	 * @param classifyId
	 * @return List<Advertorial>
	 * @throws:
	 * @time: 2018年6月28日 下午6:59:36
	 */ 
	Map<String,Object> findAdvertorialsByClassifyId(String classifyId,Integer pageNum,Integer count,String userid);
	
	/** @Title: getAdvertorialDetail
	 * @Description: 得到文章详情并且返回是否点赞
	 * @param classifyId
	 * @return Map<String,Object>
	 * @throws:
	 * @time: 2018年6月28日 下午8:19:11
	 */ 
	ResultBean getAdvertorialDetailAndThumbsup(String id,String userid);
	
	/** @Title: getAdvertorialById
	 * @Description: 通过文章id查询文章详情
	 * @param id
	 * @return AdvertorialModel
	 * @throws:
	 * @time: 2018年6月29日 上午11:45:48
	 */ 
	ResultBean getAdvertorialById(String id);
	
	/** @Title: confirmThumbUp
	 * @Description: 点赞
	 * @param id
	 * @param userId
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年6月29日 下午4:48:04
	 */ 
	ResultBean confirmThumbUp(String id,String userId);
	
	/** @Title: cancelThumbUp
	 * @Description: 取消点赞
	 * @param id
	 * @param userId
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年6月29日 下午4:48:17
	 */ 
	ResultBean cancelThumbUp(String id,String userId);

	/**
	 * @Title: praiseAdvertorial
	 * @Description: 点赞/取消点赞
	 * @param id 文章id
	 * @param userid 人员
	 * @throws:
	 * @time: 2018年7月30日 上午9:57:45
	 * @author cjy
	 */
	ResultBean praiseAdvertorial(String id, int action, String userid);

}
