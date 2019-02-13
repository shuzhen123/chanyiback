package dianfan.service.goods.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.annotations.SystemServiceLog;
import dianfan.constant.ResultBgMsg;
import dianfan.dao.mapper.goods.GoodsClassifyMapper;
import dianfan.dao.mapper.goods.GoodsMapper;
import dianfan.entities.goods.GoodsClassify;
import dianfan.models.ResultBean;
import dianfan.service.goods.ClassifyService;

/**
 * @ClassName ClassifyServiceImpl
 * @Description 商品分类service
 * @author sz
 * @date 2018年7月2日 上午10:15:21
 */
@Service
public class ClassifyServiceImpl implements ClassifyService {
	
	/**
	 * 注入： #ClassifyMapper
	 */
	@Autowired
	private GoodsMapper classifyMapper;
	@Autowired
	private GoodsClassifyMapper goodsClassifyMapper;
	
	/*
	 * (non-Javadoc)
	 * <p>Title: fildGoodsClassify</p>
	 * <p>Description: 获取商品分类信息</p>
	 * @return ResultBean
	 * link: @see dianfan.service.goodsclassify.ClassifyService#fildGoodsClassify()
	 */
	@Override
	@SystemServiceLog(method = "fildGoodsClassify",description = "获取商品分类信息 ")
	public ResultBean fildGoodsClassify() {
		//  获取商品分类信息列表
		List<GoodsClassify> goodsClassifyList = classifyMapper.fildGoodsClassify();
		// 返回分类实体列表
		return new ResultBean(goodsClassifyList);
	}
	
	/*
	 * (non-Javadoc)
	 * <p>Title: findGoodsClassifyList</p>
	 * <p>Description: 获取商品分类列表</p>
	 * @return
	 * link: @see dianfan.service.goods.ClassifyService#fildGoodsClassifyList()
	 */
	@Override
	@SystemServiceLog(method = "findGoodsClassifyList",description = "获取商品分类列表")
	public ResultBean findGoodsClassifyList(String used) {
		//  获取商品分类信息列表
		List<GoodsClassify> goodsClassifyList = goodsClassifyMapper.findGoodsClassifyList(used);
		// 返回分类实体列表
		return new ResultBean(goodsClassifyList);
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: addGoodsClassify</p>
	 * <p>Description: 添加商品分类</p>
	 * @param goodsClassify 商品分类信息
	 * @return
	 * link: @see dianfan.service.goods.ClassifyService#addGoodsClassify(dianfan.entities.goods.GoodsClassify)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "addGoodsClassify",description = "添加商品分类")
	public ResultBean addGoodsClassify(GoodsClassify goodsClassify) {
		//商品分类名称重复性检测
		boolean boo = goodsClassifyMapper.checkGoodsClassifyName(goodsClassify, 0);
		if(boo) {
			//该分类已存在
			return new ResultBean("3001", "该商品分类" + ResultBgMsg.C_3001);
		}
		//排序号检测
		boo = goodsClassifyMapper.checkGoodsClassifySort(goodsClassify);
		if(boo) {
			//该分类排序号重复
			return new ResultBean("3011", "该商品分类" + ResultBgMsg.C_3011);
		}
		
		//可新增分类
		goodsClassifyMapper.addGoodsClassify(goodsClassify);
		return new ResultBean();
	}

	/*
	 * (non-Javadoc)
	 * <p>Title: editGoodsClassify</p>
	 * <p>Description: 修改商品分类</p>
	 * @param goodsClassify 商品分类信息
	 * @return
	 * link: @see dianfan.service.goods.ClassifyService#editGoodsClassify(dianfan.entities.goods.GoodsClassify)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "editGoodsClassify",description = "修改商品分类 ")
	public ResultBean editGoodsClassify(GoodsClassify goodsClassify) {
		//商品分类名称重复性检测
		boolean boo = goodsClassifyMapper.checkGoodsClassifyName(goodsClassify, 1);
		if(boo) {
			//该分类已存在
			return new ResultBean("3001", ResultBgMsg.C_3001);
		}
		//获取分类父级
		String pClassifyid = goodsClassifyMapper.getPClassifyid(goodsClassify.getId());
		goodsClassify.setClassifyParentid(pClassifyid);
		//排序号检测
		boo = goodsClassifyMapper.checkGoodsClassifySort(goodsClassify);
		if(boo) {
			//该分类排序号重复
			return new ResultBean("3011", "该商品分类" + ResultBgMsg.C_3011);
		}
		//可以修改
		goodsClassifyMapper.editGoodsClassify(goodsClassify);
		return new ResultBean();
	}
	
	/*
	 * (non-Javadoc)
	 * <p>Title: delGoodsClassify</p>
	 * <p>Description: 删除/禁用/启用 商品分类</p>
	 * @param classifyid 商品分类id
	 * @param status 操作状态(0启用，1禁用，9删除)
	 * @param update_userid 更新者id
	 * @return
	 * link: @see dianfan.service.goods.ClassifyService#delGoodsClassify(java.lang.String[], int, java.lang.String)
	 */
	@Transactional
	@Override
	@SystemServiceLog(method = "delGoodsClassify",description = "删除/禁用 商品分类")
	public ResultBean delGoodsClassify(String classifyid, int status, String update_userid) {
		goodsClassifyMapper.delGoodsClassify(classifyid, status, update_userid);
		if(status == 0) {
			//启用，获取上级id
			String pClassifyid = goodsClassifyMapper.getPClassifyid(classifyid);
			if(!StringUtils.equals(pClassifyid, "0")) {
				//启用上级
				goodsClassifyMapper.delGoodsClassify(pClassifyid, 0, update_userid);
			}
		}
		
		if(status == 9) {
			//获取大分类下的所有子分类
			List<String> list = goodsClassifyMapper.findSubGoodsClassify(classifyid);
			
			//清空商品分类
			goodsClassifyMapper.cleanGoodsClassify(list, update_userid);
		}
		return new ResultBean();
	}
}
