package dianfan.entities.store;
/**
 * @ClassName ExperienceStore
 * @Description 体验店
 * @author cjy
 * @date 2018年7月6日 下午2:49:05
 */
public class ExperienceStore {
	private String storeid; //门店id
	private String storeName; //门店名称
	private String storePic; //门头图片
	private String openTime; //开门时间
	private String closeTime; //关门时间
	private String addr; //地址
	private String longitude; //经度
	private String latitude; //纬度
	
	private String distance; //距离
	/*体验店列表显示需要*/
	private String voucher; //该体验店下是否有优惠券可以领

	
	
	public String getVoucher() {
		return voucher;
	}

	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStorePic() {
		return storePic;
	}

	public void setStorePic(String storePic) {
		this.storePic = storePic;
	}

	public String getOpenTime() {
		return openTime;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public String getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	@Override
	public String toString() {
		return "ExperienceStore [storeid=" + storeid + ", storeName=" + storeName + ", storePic=" + storePic
				+ ", openTime=" + openTime + ", closeTime=" + closeTime + ", addr=" + addr + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", distance=" + distance + ", voucher=" + voucher + "]";
	}

	
	
}
