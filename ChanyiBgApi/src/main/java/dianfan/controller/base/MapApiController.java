package dianfan.controller.base;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultBgMsg;
import dianfan.map.tencent.GeocoderAttribute;
import dianfan.map.tencent.GeocoderRet;
import dianfan.map.tencent.IpLocationRet;
import dianfan.map.tencent.TencentMapApi;
import dianfan.models.ResultBean;

/**
 * @ClassName MapApiController
 * @Description 地图接口
 * @author cjy
 * @date 2018年7月26日 下午6:14:23
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/map/tencent")
public class MapApiController {
	private TencentMapApi mapApi = new TencentMapApi();
	
	/**
	 * @Title: geocoder
	 * @Description: 逆地址解析(坐标位置描述)
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param get_poi 是否返回周边POI列表：true.返回；false不返回(默认)
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 下午6:24:10
	 * @author cjy
	 */
	@SystemControllerLog(method = "findAllArea", logtype = ConstantIF.LOG_TYPE_1, description = "逆地址解析")
	@ApiOperation(value = "逆地址解析", httpMethod = "GET", notes = "逆地址解析", response = ResultBean.class)
	@RequestMapping(value = "/geocoder", method = RequestMethod.GET)
	public @ResponseBody ResultBean geocoder(
			@ApiParam(value="经度") @RequestParam(value="longitude") String longitude,
			@ApiParam(value="纬度") @RequestParam(value="latitude") String latitude,
			@ApiParam(value="是否返回周边POI列表(0否，1是)") @RequestParam(value="get_poi", required=false, defaultValue="0") int get_poi) {
		if(StringUtils.isEmpty(longitude.trim()) || StringUtils.isEmpty(latitude.trim())) {
			return new ResultBean("3010", "经纬度" + ResultBgMsg.C_3010);
		}
		
		GeocoderRet ret;
		
		if(get_poi == 1) {
			ret = mapApi.geocoder(longitude, latitude);
		}else {
			ret = mapApi.geocoder(longitude, latitude, true);
		}
		
		if(ret.getStatus() == 0) {
			return new ResultBean(ret);
		}else {
			return new ResultBean(String.valueOf(ret.getStatus()), ret.getMessage());
		}
	}
	
	/**
	 * @Title: getLongAndLat
	 * @Description: 地址转经纬度
	 * @param addr 地址
	 * @return
	 * @throws:
	 * @time: 2018年7月26日 下午6:43:02
	 * @author cjy
	 */
	@SystemControllerLog(method = "getLongAndLat", logtype = ConstantIF.LOG_TYPE_1, description = "地址转经纬度")
	@ApiOperation(value = "地址转经纬度", httpMethod = "GET", notes = "地址转经纬度", response = ResultBean.class)
	@RequestMapping(value = "/getLongAndLat", method = RequestMethod.GET)
	public @ResponseBody ResultBean getLongAndLat(
			@ApiParam(value="地址") @RequestParam(value="addr") String addr) {
		if(StringUtils.isEmpty(addr.trim())) {
			return new ResultBean("3010", "地址" + ResultBgMsg.C_3010);
		}
		
		GeocoderAttribute lngLatLaction = mapApi.getLngLatLaction(addr);
		if(lngLatLaction.getStatus() == 0) {
			return new ResultBean(lngLatLaction);
		}else {
			return new ResultBean(String.valueOf(lngLatLaction.getStatus()), lngLatLaction.getMessage());
		}
	}
	
	/**
	 * @Title: getAreaByIp
	 * @Description: ip定位
	 * @param ip
	 * @return
	 * @throws:
	 * @time: 2018年7月28日 上午9:59:14
	 * @author cjy
	 */
	@SystemControllerLog(method = "getLongAndLat", logtype = ConstantIF.LOG_TYPE_1, description = "ip定位")
	@ApiOperation(value = "ip定位", httpMethod = "GET", notes = "ip定位", response = ResultBean.class)
	@RequestMapping(value = "/ipLocation", method = RequestMethod.GET)
	public @ResponseBody ResultBean ipLocation(@ApiParam(value="ip") @RequestParam(value="ip") String ip) {
		if(StringUtils.isEmpty(ip.trim())) {
			return new ResultBean("3010", "ip" + ResultBgMsg.C_3010);
		}
		
		IpLocationRet locationRet = mapApi.getAreaByIp(ip);
		if(locationRet.getStatus() == 0) {
			return new ResultBean(locationRet);
		}else {
			return new ResultBean(String.valueOf(locationRet.getStatus()), locationRet.getMessage());
		}
	}
	
	
}