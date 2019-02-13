package dianfan.entities.banner;

/** @ClassName BannerModel
 * @Description 轮播图Model类
 * @author zwb
 * @date 2018年7月3日 下午1:39:07
 */ 
public class BannerModel {
	//软文id
	private String advertorialId;
	//图片地址
	private String picAddr;
	
	public String getAdvertorialId() {
		return advertorialId;
	}
	public void setAdvertorialId(String advertorialId) {
		this.advertorialId = advertorialId;
	}
	public String getPicAddr() {
		return picAddr;
	}
	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}
	@Override
	public String toString() {
		return "BannerModel [advertorialId=" + advertorialId + ", picAddr=" + picAddr + "]";
	}

	

	
	

}
