package dianfan.map.tencent;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import dianfan.util.HttpClientHelper;
import dianfan.util.PropertyUtil;

/**
 * @ClassName TencentMapApi
 * @Description 腾讯地图api接口
 * @author cjy
 * @date 2018年7月7日 上午9:17:49
 */
public class TencentMapApi {
	// 开发密钥（Key）
	private String API_KEY = PropertyUtil.getProperty("tencent_map_key");

	private ObjectMapper mapper = new ObjectMapper();

	/**
	 * @Title: geocoder
	 * @Description: 逆地址解析(坐标位置描述)
	 * @param longitude
	 *            经度
	 * @param latitude
	 *            纬度
	 * @param get_poi
	 *            是否返回周边POI列表：true.返回；false不返回(默认)
	 * @throws:
	 * @time: 2018年7月7日 上午9:35:18
	 */
	public GeocoderRet geocoder(String longitude, String latitude) {
		return geocoder(longitude, latitude, false);
	}

	public GeocoderRet geocoder(String longitude, String latitude, boolean get_poi) {
		Map<String, Object> params = new HashMap<>();
		params.put("location", latitude + "," + longitude);
		params.put("key", API_KEY);
		if (get_poi)
			params.put("get_poi", 1);
		String ret_str = HttpClientHelper.sendGet(PropertyUtil.getProperty("tencent_map_geocoder"), params, "UTF-8");
		GeocoderRet gret = new GeocoderRet();
		try {
			Map map = mapper.readValue(ret_str, Map.class);
			int status = (int) map.get("status");
			gret.setStatus(status);
			gret.setMessage((String) map.get("message"));
			if (status == 0) {
				// 请求成功

				Map result = (Map) map.get("result");
				// 获取地址组成部分
				Map address_component = (Map) result.get("address_component");
				gret.setStreet((String) address_component.get("street"));
				gret.setStreet_number((String) address_component.get("street_number"));
				// 获取地址信息
				Map ad_info = (Map) result.get("ad_info");
				gret.setNation_code((String) ad_info.get("nation_code"));
				gret.setAdcode((String) ad_info.get("adcode"));
				gret.setCity_code((String) ad_info.get("city_code"));
				gret.setName((String) ad_info.get("name"));
				gret.setNation((String) ad_info.get("nation"));
				gret.setProvince((String) ad_info.get("province"));
				gret.setCity((String) ad_info.get("city"));
				gret.setDistrict((String) ad_info.get("district"));
				return gret;
			}
		} catch (IOException e) {
			// 数据解析出错
			gret.setStatus(-1);
			gret.setMessage("数据解析出错");
		}
		return gret;
	}

	public GeocoderAttribute getLngLatLaction(String addressdes) {
		Map<String, Object> params = new HashMap<>();
		try {
			params.put("address", URLEncoder.encode(addressdes, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		params.put("key", API_KEY);
		String ret_str = HttpClientHelper.sendGet(PropertyUtil.getProperty("tencent_map_geocoder"), params, "UTF-8");
		GeocoderAttribute ga = new GeocoderAttribute();
		try {
			Map map = mapper.readValue(ret_str, Map.class);
			int status = (int) map.get("status");
			ga.setStatus(status);
			ga.setMessage((String) map.get("message"));
			if (status == 0) {
				// 请求成功
				Map result = (Map) map.get("result");
				// 获取地址组成部分
				Map address_component = (Map) result.get("address_components");
				// 获取地址信息
				Map locations = (Map) result.get("location");
				ga.setLng((Double) locations.get("lng"));
				ga.setLat((Double) locations.get("lat"));
				ga.setProvince((String) address_component.get("province"));
				ga.setCity((String) address_component.get("city"));
				ga.setDistrict((String) address_component.get("district"));
				ga.setStreet((String) address_component.get("street"));
				ga.setStreetNumber((String) address_component.get("street_number"));
				ga.setSimilarity((Double) result.get("similarity"));
				ga.setDeviation((Integer) result.get("deviation"));
				ga.setReliability((Integer) result.get("reliability"));
				ga.setLevel((Integer) result.get("level"));
				return ga;
			}
		} catch (IOException e) {
			// 数据解析出错
			ga.setStatus(-1);
			ga.setMessage("数据解析出错");
		}
		return ga;
	}

	/**
	 * @Title: getAreaByIp
	 * @Description: ip定位
	 * @param ip
	 * @return
	 * @throws:
	 * @time: 2018年7月28日 上午9:57:27
	 * @author cjy
	 */
	public IpLocationRet getAreaByIp(String ip) {
		Map<String, Object> params = new HashMap<>();
		params.put("ip", ip);
		params.put("key", API_KEY);
		String ret_str = HttpClientHelper.sendGet(PropertyUtil.getProperty("tencent_map_ip_location"), params, "UTF-8");
		IpLocationRet loc = new IpLocationRet();
		try {
			Map map = mapper.readValue(ret_str, Map.class);
			int status = (int) map.get("status");
			loc.setStatus(status);
			loc.setMessage((String) map.get("message"));
			if (status == 0) {
				// 请求成功
				Map result = (Map) map.get("result");
				// 获取经纬度
				Map location = (Map) result.get("location");
				loc.setLng(String.valueOf(location.get("lng")));
				loc.setLat(String.valueOf(location.get("lat")));
				// 获取地址信息
				Map ad_info = (Map) result.get("ad_info");
				loc.setNation(String.valueOf(ad_info.get("nation")));
				loc.setProvince(String.valueOf(ad_info.get("province")));
				loc.setCity(String.valueOf(ad_info.get("city")));
				loc.setCityCode(String.valueOf(ad_info.get("adcode")));
				loc.setDistrict(String.valueOf(ad_info.get("district")));
			}
		} catch (IOException e) {
			// 数据解析出错
			loc.setStatus(-1);
			loc.setMessage("数据解析出错");
		}
		return loc;
	}

}
