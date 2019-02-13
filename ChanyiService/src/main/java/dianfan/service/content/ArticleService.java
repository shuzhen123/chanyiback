package dianfan.service.content;

import java.io.IOException;

import dianfan.models.ResultBean;

/**
 * @ClassName ArticleService
 * @Description 文章相关 接口
 * @author sz
 * @date 2018年7月20日 上午9:44:04
 */
public interface ArticleService {

	/**
	 * @Title: findArticleList
	 * @Description: 获取文章分类
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 上午10:06:38
	 */
	ResultBean findClassifyList(String desc);

	/**
	 * @Title: addClassifyList
	 * @Description: 添加文章分类
	 * @param pic 图片
	 * @param sort 排序
	 * @param name 名称
	 * @param userid  用户ID
	 * @return
	 * @throws IOException 
	 * @throws:
	 * @time: 2018年7月20日 上午11:42:52
	 */
	ResultBean addClassifyList(String userid, String name, Integer sort) throws IOException;

	/**
	 * @Title: updataClassifyList
	 * @Description: 修改文章分类
	 * @param accesstoken accesstoken
	 * @param name 分类名
	 * @param id ID
	 * @param sort 排序
	 * @param pic 图片 
	 * @return
	 * @throws IOException
	 * @throws:
	 * @time: 2018年7月20日 下午1:37:33
	 */
	ResultBean updataClassifyList(String userid, String name, String id, Integer sort) throws IOException;

	/**
	 * @Title: updataClassifyList
	 * @Description: 修改文章分类状态
	 * @param userid userid
	 * @param type 状态
	 * @param ids 分类ID
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午2:06:33
	 */
	ResultBean updataEditClassifyType(String userid, Integer type, String ids);

	/**
	 * @Title: findaAticleList
	 * @Description: 获取文章列表
	 * @param userid 用户ID
	 * @param length 
	 * @param page 
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午2:51:28
	 */
	ResultBean findAticleList(String userid, int page, int length,String title,String typeid,String starttime,String endtime,String desc);

	/**
	 * @Title: addAticleList
	 * @Description: 添加文章
	 * @param userid 用户ID
	 * @param classify 文章分类ID
	 * @param title 标题
	 * @param content 内容
	 * @param pic 图片地址
	 * @return
	 * @throws:
	 * @time: 2018年7月20日 下午4:52:31
	 */
	ResultBean addAticleList(String userid, String classify, String title, String content, String pic,Integer sort,Integer numN,int entkbn);

	/**
	 * @Title: updatAticle
	 * @Description: 修改文章
	 * @param userid 用户ID
	 * @param classify 文章分类ID
	 * @param title 标题
	 * @param content 内容
	 * @param pic 图片地址
	 * @param sort 排序
	 * @param id 文章ID
	 * @param numN 
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午9:44:21
	 */
	ResultBean updatAticle(String userid, String classify, String title, String content, String pic, Integer sort,
			String id, Integer numN);

	/**
	 * @Title: delAticle
	 * @Description: 删除文章
	 * @param userid 用户ID
	 * @param ids 文章ID
	 * @param type  
	 * @return
	 * @throws:
	 * @time: 2018年7月23日 上午10:04:04
	 */
	ResultBean delAticle(String userid, String ids, Integer type);

}
