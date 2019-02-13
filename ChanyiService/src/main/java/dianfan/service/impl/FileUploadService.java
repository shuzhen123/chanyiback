package dianfan.service.impl;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dianfan.entities.FileUploadBean;
import dianfan.entities.FileUploadType;
import dianfan.util.HttpClientHelper;
import dianfan.util.PropertyUtil;

/**
 * @ClassName FileUploadService
 * @Description 文件上传服务
 * @author cjy
 * @date 2018年6月30日 上午11:22:06
 */
@Service
public class FileUploadService {
	//域名
	private String DOMAIN = PropertyUtil.getProperty("domain"); // 
	//文件域
	private String FILE_DOMAIN = PropertyUtil.getProperty("file_domain"); // D:/web_resource/
	// 时间分包文件夹 (20180630/)
//	private String DATE_DIR = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))+ "/"; 
	
	/**
	 * @Title: saveFileData
	 * @Description: MultipartFile类型文件上传
	 * @param fileData MultipartFile类型文件数据
	 * @param type 上传文件类型
	 * @return
	 * @throws IOException
	 * @throws:
	 * @time: 2018年6月30日 上午11:42:18
	 */
	public FileUploadBean saveFileData(MultipartFile fileData, String type) throws IOException {
		String DATE_DIR = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))+ "/";
		//文件名
        String filename = fileData.getOriginalFilename();
    	//新文件名称
    	String newfilename = String.valueOf(System.nanoTime()) ;
		if(filename.lastIndexOf(".") != -1) {
			//文件名 + 后缀名
			newfilename += filename.substring(filename.lastIndexOf("."));
		}else {
			//文件名 + 默认后缀名
			newfilename += PropertyUtil.getProperty("file_default_ext"); 
		}
    	
		//文件类
		FileUploadType flt= new FileUploadType();
		String file_url = flt.fileTypeSelect(type); // upload/avator/
		//文件存储
		FileUtils.copyInputStreamToFile(fileData.getInputStream(), new File(FILE_DOMAIN + file_url + DATE_DIR, newfilename));
		//文件数据类
		FileUploadBean file_data = new FileUploadBean();
		file_data.setFileAbsolutePath(DOMAIN + file_url + DATE_DIR + newfilename);
		file_data.setFileRelativePath(file_url + DATE_DIR + newfilename);
		file_data.setFileName(newfilename);
		return file_data;
	}
	
	/**
	 * @Title: saveFileData
	 * @Description: 文件地址类型文件上传
	 * @param fileUrl 文件地址
	 * @param type 上传文件类型
	 * @return
	 * @throws IOException
	 * @throws:
	 * @time: 2018年6月30日 下午12:10:31
	 */
	public FileUploadBean saveFileData(String fileUrl, String type) throws IOException {
		String DATE_DIR = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))+ "/";
		File file = new File(fileUrl);
		//原文件名
		String filename = file.getName();
		//新文件名称
    	String newfilename = String.valueOf(System.nanoTime()) ;
		if(filename.lastIndexOf(".") != -1) {
			//文件名 + 后缀名
			newfilename += filename.substring(filename.lastIndexOf("."));
		}else {
			//文件名 + 默认后缀名
			newfilename += PropertyUtil.getProperty("file_default_ext"); 
		}
		
		//文件类
		FileUploadType flt= new FileUploadType();
		String file_url = flt.fileTypeSelect(type); // upload/avator/
		
		//文件绝对路径
		String realPath = FILE_DOMAIN + file_url + DATE_DIR;
		
		//检测文件夹是否存在
		File file_dir = new File(realPath);
		if(!file_dir.exists()) file_dir.mkdirs();
		//下载文件上传至服务器
		HttpClientHelper.sendGetAndSaveFile(fileUrl, null, FILE_DOMAIN + file_url + DATE_DIR + newfilename);
		
		//文件数据类
		FileUploadBean file_data = new FileUploadBean();
		file_data.setFileAbsolutePath(DOMAIN + file_url + DATE_DIR + newfilename);
		file_data.setFileRelativePath(file_url + DATE_DIR + newfilename);
		file_data.setFileName(newfilename);
		return file_data;
	}
}
