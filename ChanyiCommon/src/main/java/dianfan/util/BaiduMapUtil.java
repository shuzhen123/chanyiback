package dianfan.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * 百度地图工具类
 * 
 * @author Administrator
 *
 */
public class BaiduMapUtil {
	/**
	 * 百度MAP key
	 */
	private static final String BAIDUMAP_KEY = PropertyUtil.getProperty("baidumap_key", "");

	/**
	 * 获取两个坐标的驾车距离
	 * 
	 * @param origlat
	 *            起始纬度
	 * @param origlng
	 *            起始经度
	 * @param destlat
	 *            纬度
	 * @param destlng
	 *            经度
	 * @return resp 两点驾车距离等信息
	 */
	public static String getOrigAndDestForDriving(String origlat, String origlng, String destlat, String destlng) {
		String strUrl = "http://api.map.baidu.com/routematrix/v2/driving?output=json&origins=" + origlat + "," + origlng
				+ "&destinations=" + destlat + "," + destlng + "&ak=" + BAIDUMAP_KEY;
		String resp = httpRequest(strUrl);
		return resp;
	}

	/**
	 * 获取两个坐标的步行距离
	 * 
	 * @param origlat
	 *            起始纬度
	 * @param origlng
	 *            起始经度
	 * @param destlat
	 *            纬度
	 * @param destlng
	 *            经度
	 * @return resp 两点步行距离等信息
	 */
	public static String getOrigAndDestForWalking(String origlat, String origlng, String destlat, String destlng) {
		String strUrl = "http://api.map.baidu.com/routematrix/v2/walking?output=json&origins=" + origlat + "," + origlng
				+ "&destinations=" + destlat + "," + destlng + "&ak=" + BAIDUMAP_KEY;
		String resp = httpRequest(strUrl);
		return resp;
	}

	/**
	 * 获取两个坐标的驾车距离
	 * 
	 * @param origlat
	 *            起始纬度
	 * @param origlng
	 *            起始经度
	 * @param map
	 *            最多存放50 经度,纬度
	 * @return resp 两点驾车距离等信息
	 */
	public static String getOrigAndDestForDrivingList(String origlat, String origlng,
			LinkedHashMap<String, String> map) {
		String dests = "";
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			dests += entry.getKey() + "," + entry.getValue();
			if (it.hasNext()) {
				dests += "|";
			}
		}
		String strUrl = "http://api.map.baidu.com/routematrix/v2/driving?output=json&origins=" + origlat + "," + origlng
				+ "&destinations=" + dests + "&ak=" + BAIDUMAP_KEY;
		String resp = httpRequest(strUrl);
		return resp;
	}

	/**
	 * 获取两个坐标的步行距离
	 * 
	 * @param origlat
	 *            起始纬度
	 * @param origlng
	 *            起始经度
	 * @param map
	 *            最多存放50 经度,纬度
	 * @return resp 两点步行距离等信息
	 */
	public static String getOrigAndDestForWalkingList(String origlat, String origlng,
			LinkedHashMap<String, String> map) {
		String dests = "";
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			dests += entry.getKey() + "," + entry.getValue();
			if (it.hasNext()) {
				dests += "|";
			}
		}
		String strUrl = "http://api.map.baidu.com/routematrix/v2/walking?output=json&origins=" + origlat + "," + origlng
				+ "&destinations=" + dests + "&ak=" + BAIDUMAP_KEY;
		String resp = httpRequest(strUrl);
		return resp;
	}

	/**
	 * 获取地理编码
	 * 
	 * @param address
	 *            地址
	 * @return 返回地理编码
	 */
	public static String getGeoCoder(String address) {
		String strUrl = "http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak=" + BAIDUMAP_KEY;
		return httpRequest(strUrl);
	}

	/**
	 * 逆地理编码
	 * 
	 * @param lat
	 *            经度
	 * @param lng
	 *            纬度
	 * @return 返回逆地理编码
	 */
	public static String getReverseGeoCoder(String lat, String lng) {
		String strUrl = "http://api.map.baidu.com/geocoder/v2/?location=" + lat + "," + lng + "&output=json&pois=1&ak="
				+ BAIDUMAP_KEY;
		return httpRequest(strUrl);
	}

	/**
	 * 请求GET
	 * 
	 * @param requestUrl
	 *            请求的URL
	 * @return 返回请求结果
	 */
	public static String httpRequest(String requestUrl) {
		StringBuffer buffer = new StringBuffer();
		try {

			URL url = new URL(requestUrl);
			// http协议传输
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET）
			httpUrlConn.setRequestMethod("GET");

			httpUrlConn.connect();
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * rectangleArea 矩形区域检索
	 * 
	 * @param areaname
	 *            检索关键字
	 * @param tag
	 *            检索分类【可以为null】
	 * @param leftlat
	 *            左下角坐标纬度
	 * @param leftlng
	 *            左下角坐标经度
	 * @param rightlat
	 *            右上角坐标纬度
	 * @param rightlng
	 *            右上角坐标经度
	 * @param scope
	 *            检索结果详细程度。取值为1 或空，则返回基本信息;取值为2，返回检索POI详细信息
	 * @return 返回矩形区域检索数据
	 */
	public static String rectangleArea(String areaname, String tag, String leftlat, String leftlng, String rightlat,
			String rightlng, String scope) {
		String strUrl = null;
		if (StringUtility.isNull(tag)) {
			strUrl = "http://api.map.baidu.com/place/v2/search?query=" + areaname + "&bounds=" + leftlat + "," + leftlng
					+ "," + rightlat + "," + rightlng + "&output=json&scope=" + scope + "&ak=" + BAIDUMAP_KEY;
		} else {
			strUrl = "http://api.map.baidu.com/place/v2/search?query=" + areaname + "&tag=" + tag + "&bounds=" + leftlat
					+ "," + leftlng + "," + rightlat + "," + rightlng + "&output=json&scope=" + scope + "&ak="
					+ BAIDUMAP_KEY;
		}
		return httpRequest(strUrl);
	}

	/**
	 * radiusArea 周边检索
	 * 
	 * @param areaname
	 *            检索关键字
	 * @param tag
	 *            检索分类【可以为null】
	 * @param lat
	 *            坐标纬度
	 * @param lng
	 *            坐标经度
	 * @param radius
	 *            周围的半径
	 * @return 返回周边检索数据
	 */
	public static String radiusArea(String areaname, String tag, String lat, String lng, String radius) {
		String strUrl = null;
		if (StringUtility.isNull(tag)) {
			strUrl = "http://api.map.baidu.com/place/v2/search?query=" + areaname + "&location=" + lat + "," + lng
					+ "&output=json&radius=" + radius + "&ak=" + BAIDUMAP_KEY;
		} else {
			strUrl = "http://api.map.baidu.com/place/v2/search?query=" + areaname + "&tag=" + tag + "&location=" + lat
					+ "," + lng + "&output=json&radius=" + radius + "&ak=" + BAIDUMAP_KEY;
		}
		return httpRequest(strUrl);
	}

}
