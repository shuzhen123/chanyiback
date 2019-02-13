package dianfan.entities.store;

import java.util.List;

/**
 * @ClassName ESSearchParam
 * @Description 体验店搜索参数
 * @author cjy
 * @date 2018年7月6日 下午2:50:15
 */
public class ESSearchParam {

	private Integer page=1; //当前请求第几页
	private Integer pagestart=0; //起始条数
	private Integer pagesize=5; //每页多少条
	
	private String sInput; //搜索内容（店名或地点）
	private String areaCode; //筛选区县区域code（此项与nowAddrCode互斥）
	private List<String> allAreaCode; //默认市区下的全部区县code（此项与areaCode互斥）
	private Integer distanceSort; //按距离排序（0否，1按距离由近到远排序）
	
	private String goodsClassifyId; //商品分类的顶级分类id
	private Integer open; //是否营业中（1营业中，0关门，null此项不做筛选）
	
	private String longitude; //经度
	private String latitude; //纬度
	
	private Integer mapShow = 0; //是否按地图展示（1是，0否）

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPagestart() {
		return pagestart;
	}

	public void setPagestart(Integer pagestart) {
		this.pagestart = pagestart;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public String getsInput() {
		return sInput;
	}

	public void setsInput(String sInput) {
		this.sInput = sInput;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public List<String> getAllAreaCode() {
		return allAreaCode;
	}

	public void setAllAreaCode(List<String> allAreaCode) {
		this.allAreaCode = allAreaCode;
	}

	public Integer getDistanceSort() {
		return distanceSort;
	}

	public void setDistanceSort(Integer distanceSort) {
		this.distanceSort = distanceSort;
	}

	public String getGoodsClassifyId() {
		return goodsClassifyId;
	}

	public void setGoodsClassifyId(String goodsClassifyId) {
		this.goodsClassifyId = goodsClassifyId;
	}

	public Integer getOpen() {
		return open;
	}

	public void setOpen(Integer open) {
		this.open = open;
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

	public Integer getMapShow() {
		return mapShow;
	}

	public void setMapShow(Integer mapShow) {
		this.mapShow = mapShow;
	}

	@Override
	public String toString() {
		return "ESSearchParam [page=" + page + ", pagestart=" + pagestart + ", pagesize=" + pagesize + ", sInput="
				+ sInput + ", areaCode=" + areaCode + ", allAreaCode=" + allAreaCode + ", distanceSort=" + distanceSort
				+ ", goodsClassifyId=" + goodsClassifyId + ", open=" + open + ", longitude=" + longitude + ", latitude="
				+ latitude + ", mapShow=" + mapShow + "]";
	}
	
}
