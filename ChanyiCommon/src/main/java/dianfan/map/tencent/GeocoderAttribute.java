/**  
* @Title: GeocoderAttribute.java
* @Package dianfan.map.tencent
* @Description: TODO
* @author yl
* @date 2018年7月18日 下午4:17:33
* @version V1.0  
*/ 
package dianfan.map.tencent;

/** @ClassName GeocoderAttribute
 * @Description 
 * @author yl
 * @date 2018年7月18日 下午4:17:33
 */
public class GeocoderAttribute {
	
	private int status; //请求状态
	private String message; //错误信息
	private String title;//标题
	private Double lng;//经度坐标
	private Double lat;//纬度坐标
	private String province;//省
	private String city;//市
	private String district;//区
	private String street;//街道
	private String streetNumber;//门牌号
	private Double similarity;//相似性
	private Integer deviation;//偏离
	private Integer reliability;//可靠性
	private Integer level;//等级
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
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
	public String getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	public Double getSimilarity() {
		return similarity;
	}
	public void setSimilarity(Double similarity) {
		this.similarity = similarity;
	}
	public Integer getDeviation() {
		return deviation;
	}
	public void setDeviation(Integer deviation) {
		this.deviation = deviation;
	}
	public Integer getReliability() {
		return reliability;
	}
	public void setReliability(Integer reliability) {
		this.reliability = reliability;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "GeocoderAttribute [status=" + status + ", message=" + message + ", title=" + title + ", lng=" + lng
				+ ", lat=" + lat + ", province=" + province + ", city=" + city + ", district=" + district + ", street="
				+ street + ", streetNumber=" + streetNumber + ", similarity=" + similarity + ", deviation=" + deviation
				+ ", reliability=" + reliability + ", level=" + level + "]";
	}
	
}
