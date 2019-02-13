package dianfan.login;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import dianfan.entities.FileUploadType;
import dianfan.util.HttpClientHelper;
import dianfan.util.PropertyUtil;

public class UploadFile {

	@Test
	public void upload() throws FileNotFoundException {
		String file_url = "https://wx.qlogo.cn/mmopen/vi_32/ajNVdqHZLLA1zMqBCfNPxIvAHZ0dx55hV4Res215q30fctHjZa3ASqjODvbgKNPcOUfSTWQ4gt7hRUFkk9gNbw/132";
		
		FileUploadType type = new FileUploadType();
		String file_dir = type.fileTypeSelect(type.AVATOR);

		// 检测时间分包文件夹
		String date_dir = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))+ "/"; 
		
		
		String file_save_url = file_dir + date_dir;
		String file_save_name =  System.nanoTime() + ".png";
		
		
		File f = new File(PropertyUtil.getProperty("file_domain") + file_save_url); // d:/web_resource/upload/avatar/20180630/
		if(!f.exists()) f.mkdirs();
		
		
		HttpClientHelper.sendGetAndSaveFile(file_url, null, PropertyUtil.getProperty("file_domain") + file_save_url + file_save_name);
		
		System.out.println(file_save_url + file_save_name);
		
	}
}
