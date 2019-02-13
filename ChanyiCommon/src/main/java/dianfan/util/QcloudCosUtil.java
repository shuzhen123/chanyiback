package dianfan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.request.DelFileRequest;
import com.qcloud.cos.request.GetFileLocalRequest;
import com.qcloud.cos.request.ListFolderRequest;
import com.qcloud.cos.request.StatFileRequest;
import com.qcloud.cos.request.UploadFileRequest;
import com.qcloud.cos.sign.Credentials;

/**
 * @Title: QcloudCosUtil.java
 * @Package dianfan.util
 * @Description: 腾讯云cos存储工具类
 * @author Administrator
 * @date 2018年5月11日 下午1:55:20
 * @version V1.0
 */
public class QcloudCosUtil {
	/**
	 * Q_COS_APPID
	 */
	private static final long Q_COS_APPID = Long.parseLong(PropertyUtil.getProperty("q_cos_appid"));
	/**
	 * Q_COS_SECRETID
	 */
	private static final String Q_COS_SECRETID = PropertyUtil.getProperty("q_cos_secretid");
	/**
	 * Q_COS_SECRETKEY
	 */
	private static final String Q_COS_SECRETKEY = PropertyUtil.getProperty("q_cos_secretkey");
	/**
	 * 设置bucket所在的区域,比如华南园区：gz; 华北园区：tj;华东园区：sh ;
	 */
	private static final String Q_COS_BUCKETAREA = PropertyUtil.getProperty("q_cos_bucketarea");
	/**
	 * 下载防盗链设置
	 */
	private static final String Q_COS_REFER = PropertyUtil.getProperty("q_cos_refer", null);

	/**
	 * @Title: getCOSClient
	 * @Description: 生成客户端对象
	 * @return COSClient 对象
	 */
	public static COSClient getCOSClient() {

		// 初始化秘钥信息
		Credentials cred = new Credentials(Q_COS_APPID, Q_COS_SECRETID, Q_COS_SECRETKEY);
		// 初始化客户端配置
		ClientConfig clientConfig = new ClientConfig();
		// 设置bucket所在的区域，比如华南园区：gz； 华北园区：tj；华东园区：sh ；
		clientConfig.setRegion(Q_COS_BUCKETAREA);
		// 生成客户端
		// 初始化cosClient
		COSClient cosClient = new COSClient(clientConfig, cred);
		return cosClient;
	}

	/**
	 * @Title: uploadFile
	 * @Description:上传文件
	 * @param bucketName
	 *            区域
	 * @param cosFileName
	 *            文件名
	 * @param fileContent
	 *            文件字节数组
	 * @return 文件路径
	 */
	public static String uploadFile(String bucketName, String cosFileName, byte[] fileContent) {

		UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, "/" + cosFileName, fileContent);
		String uploadFileRet = getCOSClient().uploadFile(uploadFileRequest);
		return uploadFileRet;
	}

	/**
	 * @Title: uploadFile
	 * @Description:上传文件
	 * @param bucketName
	 *            区域
	 * @param cosFileName
	 *            文件名
	 * @param localfilepath
	 *            文件字节数组
	 * @return 文件路径
	 */
	public static String uploadFile(String bucketName, String cosFileName, String localfilepath) {
		UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, "/" + cosFileName, localfilepath);
		String uploadFileRet = getCOSClient().uploadFile(uploadFileRequest);
		return uploadFileRet;
	}

	/**
	 * 文件转换为字节数组
	 * 
	 * @param file
	 *            文件
	 * @return 字节数组
	 * @throws Exception
	 *             异常
	 */
	public static byte[] getByte(File file) throws Exception {
		byte[] bytes = null;
		if (file != null) {
			InputStream is = new FileInputStream(file);
			int length = (int) file.length();
			// 当文件的长度超过了int的最大值
			if (length > Integer.MAX_VALUE) {
				return null;
			}
			bytes = new byte[length];
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}
			// 如果得到的字节长度和file实际的长度不一致就可能出错了
			if (offset < bytes.length) {
				return null;
			}
			is.close();
		}
		return bytes;
	}

	/**
	 * @Title: downFile
	 * @Description: 下载文件
	 * @param bucketName
	 *            区域
	 * @param cosFilePath
	 *            文件名
	 * @param localPathDown
	 *            文件字节数组
	 * @return 文件地址
	 */
	public static String downFile(String bucketName, String cosFilePath, String localPathDown) {
		GetFileLocalRequest getFileLocalRequest = new GetFileLocalRequest(bucketName, cosFilePath, localPathDown);
		getFileLocalRequest.setUseCDN(false);
		if (!StringUtility.isNull(Q_COS_REFER)) {
			getFileLocalRequest.setReferer(Q_COS_REFER);
		}
		String getFileResult = getCOSClient().getFileLocal(getFileLocalRequest);
		return getFileResult;
	}

	/**
	 * @Title: getFileProp
	 * @Description: 获取文件
	 * @param bucketName
	 *            区域
	 * @param cosFileName
	 *            指定文件
	 * @return 文件地址
	 */
	public static String getFileProp(String bucketName, String cosFileName) {
		StatFileRequest statFileRequest = new StatFileRequest(bucketName, "/" + cosFileName);
		String statFileRet = getCOSClient().statFile(statFileRequest);
		return statFileRet;
	}

	/**
	 * @Title: deleteFile
	 * @Description: 删除文件
	 * @param bucketName
	 *            区域
	 * @param cosFileName
	 *            指定文件
	 * @return 删除文件
	 */
	public static String deleteFile(String bucketName, String cosFileName) {
		DelFileRequest delFileRequest = new DelFileRequest(bucketName, "/" + cosFileName);
		String delFileRet = getCOSClient().delFile(delFileRequest);
		return delFileRet;
	}

	/**
	 * @Title: allFileList
	 * @Description: 获取文件列表
	 * @param bucketName
	 *            区域
	 * @param dir
	 *            '/dir/src/'目录
	 * @return listFolderRet
	 */
	public static String allFileList(String bucketName, String dir) {
		ListFolderRequest listFolderRequest = new ListFolderRequest(bucketName, dir);
		String listFolderRet = getCOSClient().listFolder(listFolderRequest);
		return listFolderRet;
	}
}
