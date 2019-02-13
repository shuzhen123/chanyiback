package dianfan.entities;
/**
 * @ClassName FileUploadBean
 * @Description 文件上传返回值
 * @author cjy
 * @date 2018年6月30日 上午11:37:13
 */
public class FileUploadBean {
	
	private String fileAbsolutePath; // 文件地址绝对路径
	private String fileRelativePath; // 文件地址相对路径
	private String fileName; // 文件名
	public String getFileAbsolutePath() {
		return fileAbsolutePath;
	}
	public void setFileAbsolutePath(String fileAbsolutePath) {
		this.fileAbsolutePath = fileAbsolutePath;
	}
	public String getFileRelativePath() {
		return fileRelativePath;
	}
	public void setFileRelativePath(String fileRelativePath) {
		this.fileRelativePath = fileRelativePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public String toString() {
		return "FileUploadBean [fileAbsolutePath=" + fileAbsolutePath + ", fileRelativePath=" + fileRelativePath
				+ ", fileName=" + fileName + "]";
	}
}
