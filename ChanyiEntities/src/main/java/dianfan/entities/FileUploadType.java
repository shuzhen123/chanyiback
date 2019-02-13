package dianfan.entities;
/**
 * @ClassName FileType
 * @Description 文件类型
 * @author cjy
 * @date 2018年6月29日 下午3:55:45
 */
public class FileUploadType {
	public final String FILE_DIR = "upload/"; // 上传文件路径
	
	public final String DEF_TYPE = "file"; // 默认类型
	
	public final String AVATOR = "avatar"; // 头像
	public final String GOODS = "goods"; // 商品
	public final String ARTICLE = "article"; // 文章
	public final String FEEDBACK = "feedback"; // 意见反馈
	public final String IDCARD = "idcard"; // 身份证
	public final String BANNER = "banner"; // 轮播图
	public final String STORE = "store"; // 体验店
	
	public String fileTypeSelect(String type) {
		String str = FILE_DIR;
		if(type == null) {
			return str += DEF_TYPE + "/";
		}
		switch (type.toLowerCase()) {
			case AVATOR : str += AVATOR;
				break;
			case GOODS : str += GOODS;
				break;
			case ARTICLE: str += ARTICLE;
				break;
			case FEEDBACK: str += FEEDBACK;
				break;
			case IDCARD: str += IDCARD;
				break;
			case BANNER: str += BANNER;
				break;
			case STORE: str += STORE;
				break;
			default: str += DEF_TYPE;
				break;
		}
		return str += "/";
	}
}
