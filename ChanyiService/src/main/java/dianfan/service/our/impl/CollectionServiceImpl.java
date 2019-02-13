/**  
* @Title: CollectionServiceImpl.java
* @Package dianfan.service.our.impl
* @Description: TODO
* @author yl
* @date 2018年6月30日 上午10:20:25
* @version V1.0  
*/ 
package dianfan.service.our.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dianfan.constant.ConstantIF;
import dianfan.dao.mapper.our.CollectionMapper;
import dianfan.entities.our.GoodsModel;
import dianfan.entities.our.UserCollection;
import dianfan.models.ResultBean;
import dianfan.service.our.CollectionService;
import dianfan.util.PropertyUtil;

/** @ClassName CollectionServiceImpl
 * @Description 
 * @author yl
 * @date 2018年6月30日 上午10:20:25
 */
@Service
public class CollectionServiceImpl implements CollectionService{
	
	@Autowired
	private CollectionMapper collectionMapper;

	/* (non-Javadoc)
	 * <p>Title: findCollectionList</p>
	 * <p>Description: </p>
	 * @param id
	 * @return
	 * link: @see dianfan.service.our.CollectionService#findCollectionList(java.lang.String)
	 */ 
	@Override
	public ResultBean findCollectionList(String userid,Integer page,Integer pagecounts) {
		Map<String, Object> params = new HashMap<>();
		int totalcount;
		// TODO Auto-generated method stub
		List<UserCollection> userCollectionRelate = collectionMapper.findGoodsIdList(userid);
		List<String> goodsids = new ArrayList<>();
		if (userCollectionRelate !=null && userCollectionRelate .size()>0) {
			for (int i = 0; i < userCollectionRelate.size(); i++) {
				goodsids.add(userCollectionRelate.get(i).getGoodsId());
			}
		}else {
			params.put("gmlist", userCollectionRelate);
			params.put("totalcount", 0);//总条数
			return new ResultBean(params);
		}

		//统计收藏总数
		int count = collectionMapper.getGoodsListPicNum(goodsids);
		if (pagecounts == null) {
			// 设置总条数
			int start = (page - 1) * ConstantIF.PAGE_OFFSET;
			totalcount = count;
			if(totalcount == 0 || start > totalcount) {
				//没有数据
				params.put("count", 0);
				params.put("gmlist", Collections.emptyList());
			}
			// 分页启示页面
			params.put("start", start);
			// 分页偏移量 10
			params.put("offset", ConstantIF.PAGE_OFFSET);
			params.put("totalcount", totalcount);//总条数
			
		}else {
			//起始的条数
			int start = (page - 1) * pagecounts;
			totalcount = count;
			if(totalcount == 0 || start > totalcount) {
				//没有数据
				params.put("count", 0);
				params.put("gmlist", Collections.emptyList());
			}
			//有数据
			params.put("start", start);//起始的条数
			params.put("offset", pagecounts);//一页条数
			params.put("totalcount", totalcount);//总条数
		}
		params.put("goodsids", goodsids);
		List<GoodsModel> gmlist = collectionMapper.findGoodsListPic(params);
		if (gmlist !=null && gmlist.size()>0) {
			for (int i = 0; i < gmlist.size(); i++) {
				gmlist.get(i).setPicAddr(PropertyUtil.getProperty("domain")+gmlist.get(i).getPicAddr());
			}
		}
		params.put("gmlist", gmlist);
		return new ResultBean(params);
	}

	/* (non-Javadoc)
	 * <p>Title: delCollection</p>
	 * <p>Description: 取消收藏</p>
	 * @param goodsid
	 * link: @see dianfan.service.our.CollectionService#delCollection(java.lang.String)
	 */ 
	@Override
	@Transactional
	public void delCollection(String userid,String goodsid) {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userid", userid);
		String[] goodsids = goodsid.split(",");
		params.put("goodsids", goodsids);
		collectionMapper.delCollection(params);
		//商品数量减一
		collectionMapper.updateGoodCollectionNum(goodsids);
	}
}
