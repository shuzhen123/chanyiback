package dianfan.service.adminmanage;

import dianfan.models.ResultBean;

/**
 * @ClassName FeedbackService
 * @Description 意见反馈 接口
 * @author sz
 * @date 2018年7月18日 下午3:00:43
 */
public interface AdminFeedbackService {

	/**
	 * @Title: feedbackList
	 * @Description: 用户反馈列表
	 * @param length 一页请求的条数
	 * @param page 请求的第几页
	 * @return 
	 * @throws:
	 * @time: 2018年7月18日 下午3:13:11
	 */
	ResultBean feedbackList(int length, int page ,String type, String starttime, String endtime);

	/**
	 * @Title: updataFeedbackType
	 * @Description: 更新反馈状态
	 * @param type 状态
	 * @param userid 用户的ID
	 * @return
	 * @throws:
	 * @time: 2018年7月18日 下午4:49:58
	 */
	ResultBean updataFeedbackType(String type, String userid, String id);

	/**
	 * @Title: delfeedbackType
	 * @Description: 删除反馈
	 * @param ids
	 * @param userid
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 上午11:34:01
	 */
	ResultBean delfeedbackType(String ids, String userid);

}
