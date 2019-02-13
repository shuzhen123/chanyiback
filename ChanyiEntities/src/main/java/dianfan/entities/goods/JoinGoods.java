package dianfan.entities.goods;

import java.util.List;

/**
 * @ClassName JoinGoods
 * @Description 接收商品分类列表的入参项
 * @author sz
 * @date 2018年7月3日 下午2:27:48
 */
public class JoinGoods {

	private String accesstoken; // 否 string 用户token 
	private String userid; // 不需要前端传入，如果有accesstoken，后台自己获取后 再set进来
	
	private List<String> goodsids; //已拥有的商品id列表
	
	private Integer page; // 否 string 当前请求第几页 
	private Integer pagestart; // 开始位置
	private Integer pagesize; // 否 string 每页多少条 
	
	private String spelling; // 是否易拼，是1，否0
	private String classifyid; // 是 string 商品分类id 
	private String all; // 否 string 是否按综合排序，是1，否0 
	private String sale; // 否 string 是否按销量排序，是1，否0 
	private String news; // 否 string 是否按新品排序，是1，否0 
	private String sort; // 否 string 是否按自定义排序，是1，否0 
	private String price; // 否 string 是否按价格排序，升序1，降序2，否0 
	private String pricegt; // 否 string 价格区间下限 
	private String pricelt; // 否 string 价格区间上限 
	private String attr; // 商品属性id 
	private String[] attr_arr; // 商品属性id列表、
	
	public String[] getAttr_arr() {
		return attr_arr;
	}
	public void setAttr_arr(String[] attr_arr) {
		this.attr_arr = attr_arr;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public List<String> getGoodsids() {
		return goodsids;
	}
	public void setGoodsids(List<String> goodsids) {
		this.goodsids = goodsids;
	}
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
	public String getSpelling() {
		return spelling;
	}
	public void setSpelling(String spelling) {
		this.spelling = spelling;
	}
	public String getClassifyid() {
		return classifyid;
	}
	public void setClassifyid(String classifyid) {
		this.classifyid = classifyid;
	}
	public String getAll() {
		return all;
	}
	public void setAll(String all) {
		this.all = all;
	}
	public String getSale() {
		return sale;
	}
	public void setSale(String sale) {
		this.sale = sale;
	}
	public String getNews() {
		return news;
	}
	public void setNews(String news) {
		this.news = news;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getPricegt() {
		return pricegt;
	}
	public void setPricegt(String pricegt) {
		this.pricegt = pricegt;
	}
	public String getPricelt() {
		return pricelt;
	}
	public void setPricelt(String pricelt) {
		this.pricelt = pricelt;
	}
	public String getAttr() {
		return attr;
	}
	public void setAttr(String attr) {
		this.attr = attr;
	}
	@Override
	public String toString() {
		return "JoinGoods [accesstoken=" + accesstoken + ", userid=" + userid + ", goodsids=" + goodsids + ", page="
				+ page + ", pagestart=" + pagestart + ", pagesize=" + pagesize + ", spelling=" + spelling
				+ ", classifyid=" + classifyid + ", all=" + all + ", sale=" + sale + ", news=" + news + ", sort=" + sort
				+ ", price=" + price + ", pricegt=" + pricegt + ", pricelt=" + pricelt + ", attr=" + attr + "]";
	}
	
}
