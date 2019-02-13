package dianfan.service.order.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dianfan.dao.mapper.order.AfterSaleMapper;
import dianfan.entities.order.AfterSaleList;
import dianfan.models.ResultBean;
import dianfan.service.order.AfterSaleService;
import dianfan.util.PropertyUtil;

/**
 * @ClassName AfterSaleServiceImpl
 * @Description 售后服务实现
 * @author cjy
 * @date 2018年8月7日 上午10:27:46
 */
@Service
public class AfterSaleServiceImpl implements AfterSaleService {

	@Autowired
	private AfterSaleMapper afterSaleMapper;

	/*
	 * (non-Javadoc)
	 * <p>Title: findAfterSaleList</p>
	 * <p>Description: 获取售后数据列表</p>
	 * @param page
	 * @param pageSize
	 * @param param
	 * @return
	 * @author cjy
	 * link: @see dianfan.service.order.AfterSaleService#findAfterSaleList(int, int, java.util.Map)
	 */
	@Override
	public ResultBean findAfterSaleList(int page, int pageSize, Map<String, Object> param) {
		Map<String, Object> ret = new HashMap<>();
		
		int count = afterSaleMapper.getAfterSaleCount(param);
		ret.put("count", count);
		if(count <= (page - 1) * pageSize) {
			//未筛选到数据
			ret.put("orderlist", new ArrayList<>());
			return new ResultBean(ret);
		}
		
		param.put("page", (page - 1) * pageSize);
		param.put("pageSize", pageSize);
		
		List<AfterSaleList> afterSaleList = afterSaleMapper.findAfterSaleList(param);
		for(AfterSaleList asl : afterSaleList) {
			String urls = asl.getPicUrls();
			if(StringUtils.isNotEmpty(urls)) {
				String[] s = urls.split(",");
				for(int i=0; i<s.length; i++) {
					s[i] = PropertyUtil.getProperty("domain") + s[i];
				}
				asl.setPicUrls(StringUtils.join(s, ","));
			}
		}
		ret.put("orderlist", afterSaleList);
		
		return new ResultBean(ret);
	}

	
}