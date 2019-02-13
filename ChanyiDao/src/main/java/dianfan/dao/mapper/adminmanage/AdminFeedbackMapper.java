package dianfan.dao.mapper.adminmanage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import dianfan.entities.FeedBack;

/**
 * @ClassName FeedbackMapper
 * @Description 用户返回 dao
 * @author sz
 * @date 2018年7月18日 下午3:03:26
 */
@Repository
public interface AdminFeedbackMapper {

	/**
	 * @Title: findFeedbackList
	 * @Description: 获取意见反馈总条数
	 * @return int
	 * @throws:
	 * @time: 2018年7月18日 下午3:17:11
	 */
	int findFeedbackList(Map<String, Object> param);

	/**
	 * @Title: feedbackList
	 * @Description: 获取用户反馈列表
	 * @param param
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 下午3:24:58
	 */
	List<FeedBack> feedbackList(Map<String, Object> param);

	/**
	 * @Title: updataFeedbackType
	 * @Description: 更新反馈状态
	 * @param param
	 * @throws:
	 * @time: 2018年7月18日 下午4:53:23
	 */
	@Update("UPDATE t_feedback SET feedback_type = #{type} , update_time = NOW() , update_by = #{userid} WHERE id = #{id} ")
	void updataFeedbackType(Map<String, Object> param);

	/**
	 * @Title: delfeedbackType
	 * @Description: 删除反馈
	 * @param param
	 * @throws:
	 * @time: 2018年7月19日 上午11:48:38
	 */
	void delfeedbackType(Map<String, Object> param);
	

}
 