package dianfan.entities.advertorialclassify;

/** @ClassName AdvertorialClassifyModel
 * @Description 文章分类Model
 * @author zwb
 * @date 2018年7月3日 下午1:57:03
 */ 
public class AdvertorialClassifyModel {
	//分类id
	private String classifyId;
	//分类名称
	private String classifyName;
	//图片地址
	private String picAddr;
	
	private String createBy;

	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public String getPicAddr() {
		return picAddr;
	}

	public void setPicAddr(String picAddr) {
		this.picAddr = picAddr;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Override
	public String toString() {
		return "AdvertorialClassifyModel [classifyId=" + classifyId + ", classifyName=" + classifyName + ", picAddr="
				+ picAddr + ", createBy=" + createBy + "]";
	}

	
	
	

}
