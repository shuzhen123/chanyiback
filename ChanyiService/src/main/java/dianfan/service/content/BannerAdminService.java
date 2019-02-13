package dianfan.service.content;

import java.io.IOException;

import dianfan.models.ResultBean;

/**
 * @ClassName BannerAdminService
 * @Description Banner相关 （后台接口）
 * @author sz
 * @date 2018年7月19日 下午1:28:34
 */
public interface BannerAdminService {

	/**
	 * @Title: findBannerPic
	 * @Description: 获取首页轮播图列表
	 * @param accesstoken accesstoken
	 * @param page 请求页
	 * @param pageSize 每页条数
	 * @return ResultBean
	 * @throws:
	 * @time: 2018年7月19日 下午2:34:24
	 */
	ResultBean findBannerPic(int page, int pageSize,String desc);


	/**
	 * @Title: findBannerPic
	 * @Description: 添加首页轮播图列表
	 * @param title 标题
	 * @param content 内容
	 * @param pic 图片
	 * @param desc 描述
	 * @param userid 用户的ID
	 * @return
	 * @throws IOException 
	 * @throws:
	 * @time: 2018年7月19日 下午4:26:27
	 */
	ResultBean addBannerPic(String title, String content, String pic, String desc, String userid,String sort) throws IOException;


	/**
	 * @Title: findBannerPic
	 * @Description: 修改首页轮播图列表
	 * @param title 标题
	 * @param content 内容
	 * @param pic 图片
	 * @param desc 描述
	 * @param userid 用户的ID
	 * @param id 轮播图Id
	 * @return
	 * @throws IOException 
	 * @throws:
	 * @time: 2018年7月19日 下午4:26:27
	 */
	ResultBean updataBannerPic(String id, String title, String content, String pic, String desc, String userid,String sort) throws IOException;


	/**
	 * @Title: delBannerPic
	 * @Description: 修改轮播图
	 * @param userid 用户ID
	 * @param ids 轮波图ID
	 * @return
	 * @throws:
	 * @time: 2018年7月19日 下午7:06:08
	 */
	ResultBean delBannerPic(String userid, String ids,String type);

}
