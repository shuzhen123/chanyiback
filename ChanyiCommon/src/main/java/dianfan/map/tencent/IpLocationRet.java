package dianfan.map.tencent;
/**
 * @ClassName IpLocationRet
 * @Description ip转地址信息
 * @author cjy
 * @date 2018年7月28日 上午9:45:42
 */
public class IpLocationRet {
	private int status; //请求状态
	private String message; //错误信息
	
	private String lng;//经度坐标
	private String lat;//纬度坐标

	private String nation; //国家
	private String province; //省 / 直辖市
	private String city; //市 / 地级区 及同级行政区划
	private String cityCode; //市 / 地级区 code
	
	private String district; //区 / 县级市 及同级行政区划

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	@Override
	public String toString() {
		return "IpLocationRet [status=" + status + ", message=" + message + ", lng=" + lng + ", lat=" + lat
				+ ", nation=" + nation + ", province=" + province + ", city=" + city + ", cityCode=" + cityCode
				+ ", district=" + district + "]";
	}
	
}
