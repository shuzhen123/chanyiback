/**  
* @Title: FeedbackServiceImpl.java
* @Package dianfan.service.our.impl
* @Description: TODO
* @author yl
* @date 2018年6月28日 下午6:23:46
* @version V1.0  
*/ 
package dianfan.service.our.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.dao.mapper.our.FeedbackMapper;
import dianfan.entities.FeedBack;
import dianfan.service.our.FeedbackService;

/** @ClassName FeedbackServiceImpl
 * @Description 
 * @author yl
 * @date 2018年6月28日 下午6:23:46
 */
@Service
public class FeedbackServiceImpl implements FeedbackService{
	
	@Autowired
	private FeedbackMapper feedbackMapper;

	/* (non-Javadoc)
	 * <p>Title: addFeedback</p>
	 * <p>Description: </p>
	 * @param userid
	 * @param picurl
	 * @param feedbacktype
	 * @param content
	 * link: @see dianfan.service.our.FeedbackService#addFeedback(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */ 
	@Override
	@Transactional
	public void addFeedback(String userid, String picurl,String telno, String content) {
		
		FeedBack fb = new FeedBack();
		// 修改图片的路径
		int indexOf = picurl.indexOf("upload");
		String picurls = picurl.substring(indexOf);
		fb.setPicUrl(picurls);
		fb.setTelno(telno);
		fb.setFeedbackType("01");
		fb.setContent(content);
		fb.setUserId(userid);
		fb.setCreateBy(userid);
		feedbackMapper.addFeedback(fb);
		
	}

}
