package dianfan.service.adminmanage.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import dianfan.annotations.SystemServiceLog;
import dianfan.dao.mapper.adminmanage.AdminFeedbackMapper;
import dianfan.entities.FeedBack;
import dianfan.models.ResultBean;
import dianfan.service.adminmanage.AdminFeedbackService;
import dianfan.util.PropertyUtil;
import dianfan.util.StringUtility;

/**
 * @ClassName FeedbackServiceImpl
 * @Description  意见反馈 service
 * @author sz
 * @date 2018年7月18日 下午3:01:28
 */
@Service
public class AdminFeedbackServiceImpl implements AdminFeedbackService {
	
//	@Autowired  
//	private HttpServletRequest request; 
	/**
	 * 注入： #FeedbackMapper
	 */
	@Autowired
	private AdminFeedbackMapper feedbackMapper;

	/*
	 * (non-Javadoc)
	 * <p>Title: feedbackList</p>
	 * <p>Description: 用户反馈列表</p>
	 * @param length 每页请求的条数
	 * @param page 第几页
	 * @return
	 * link: @see dianfan.service.adminmanage.FeedbackService#feedbackList(int, int)
	 */
	@SystemServiceLog(method = "feedbackList",description = "用户反馈列表 ")
	@Override
	public ResultBean feedbackList(int length, int page ,String type, String starttime, String endtime) {
		// 构建返回实体
		Map<String, Object> data = new HashMap<>();
		
		// 创建入参模型
		Map<String, Object> param = new HashMap<>();
		// 加入分页偏移量
		param.put("length", length);
		// 起始条数
		param.put("start", (page - 1) * length);
		param.put("type", type);
		param.put("starttime", starttime);
		param.put("endtime", endtime);
		
		// 获取总条数
		int count = feedbackMapper.findFeedbackList(param);
		// 设置总条数
		data.put("count", count);
		if (count == 0 || count < (page - 1) * length) {
			data.put("feedbackList", new ArrayList<>());
			return new ResultBean(data);
		}
		// 获取反馈信息
		List<FeedBack> feedbackList = feedbackMapper.feedbackList(param);
		
		for(FeedBack f : feedbackList) {
			// 上传形式是 多长图片已逗号分隔开，先将每一张图片取出来，整理好回传路劲后再返回
			String[] picUrl = f.getPicUrl().split(",");
			
			for(int i=0; i<picUrl.length; i++) {
				picUrl[i] = PropertyUtil.getProperty("domain") + picUrl[i];
			}
			
			f.setPicUrl(StringUtils.join(picUrl, ","));
			
		}
		
		data.put("feedbackList", feedbackList);
		// 成功返回
		return new ResultBean(data);
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: updataFeedbackType</p>
	 * <p>Description: 修改用户反馈的状态</p>
	 * @param type 状态
	 * @param userid 用户的ID
	 * @return
	 * link: @see dianfan.service.adminmanage.AdminFeedbackService#updataFeedbackType(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public ResultBean updataFeedbackType(String type, String userid , String id) {
		// 创建入参模型
		 Map<String, Object> param = new HashMap<>();
		 param.put("userid", userid);
		 param.put("type", type);
		 param.put("id", id);
		 // 更新操作
		 feedbackMapper.updataFeedbackType(param);
		return new ResultBean();
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: delfeedbackType</p>
	 * <p>Description: 删除操作</p>
	 * @param ids 反馈ID
	 * @param userid 用户ID
	 * @return
	 * link: @see dianfan.service.adminmanage.AdminFeedbackService#delfeedbackType(java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public ResultBean delfeedbackType(String ids, String userid) {
		// 创建入参容器
		Map<String, Object> param = new HashMap<>();
		List<String> idList = Arrays.asList(ids.split(","));
		param.put("idList", idList);
		param.put("userid", userid);
		// 删除操作
		feedbackMapper.delfeedbackType(param);
		
		return new ResultBean();
	}
	
	
	

}
