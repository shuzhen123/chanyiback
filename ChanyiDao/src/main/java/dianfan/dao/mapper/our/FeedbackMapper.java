/**  
* @Title: FeedbackMapper.java
* @Package dianfan.dao.mapper.our
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午6:26:47
* @version V1.0  
*/ 
package dianfan.dao.mapper.our;

import org.springframework.stereotype.Repository;

import dianfan.entities.FeedBack;

/** @ClassName FeedbackMapper
 * @Description 
 * @author yl
 * @date 2018年6月28日 下午6:26:47
 */
@Repository
public interface FeedbackMapper {
	
	void addFeedback(FeedBack feedback);

}
