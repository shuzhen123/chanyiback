package dianfan.map.tencent;
/**
 * @ClassName GeocoderRet
 * @Description 逆地址解析(坐标位置描述)
 * @author cjy
 * @date 2018年7月7日 上午9:40:28
 */
public class GeocoderRet {
	private int status; //请求状态
	private String message; //错误信息
	
	private String nation_code; //国家代码
	private String adcode; //行政区划代码
	private String city_code; //
	private String name; //无锡市,新吴区",行政区划名称
	private String nation; //国家
	private String province; //省 / 直辖市
	private String city; //市 / 地级区 及同级行政区划
	private String district; //区 / 县级市 及同级行政区划
	private String street; //街道，可能为空字串
	private String street_number; //门牌，可能为空字串
	
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
	public String getNation_code() {
		return nation_code;
	}
	public void setNation_code(String nation_code) {
		this.nation_code = nation_code;
	}
	public String getAdcode() {
		return adcode;
	}
	public void setAdcode(String adcode) {
		this.adcode = adcode;
	}
	public String getCity_code() {
		return city_code;
	}
	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreet_number() {
		return street_number;
	}
	public void setStreet_number(String street_number) {
		this.street_number = street_number;
	}
	@Override
	public String toString() {
		return "GeocoderRet [status=" + status + ", message=" + message + ", nation_code=" + nation_code + ", adcode="
				+ adcode + ", city_code=" + city_code + ", name=" + name + ", nation=" + nation + ", province="
				+ province + ", city=" + city + ", district=" + district + ", street=" + street + ", street_number="
				+ street_number + "]";
	}
}
