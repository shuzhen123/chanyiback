/**  
* @Title: FeedbackService.java
* @Package dianfan.service.our
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午6:14:02
* @version V1.0  
*/ 
package dianfan.service.our;

/** @ClassName FeedbackService
 * @Description 
 * @author yl
 * @date 2018年6月28日 下午6:14:02
 */
public interface FeedbackService {
	
	/**
	 * @Title: addFeedback
	 * @Description: 新增数据
	 * @param userid 用户id
	 * @throws:
	 * @time: 2018年6月28日 上午11:18:48
	 */
	void addFeedback(String userid,String picurl,String telno,String content);

}
