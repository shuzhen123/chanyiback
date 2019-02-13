package dianfan.controller.upload;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import dianfan.annotations.SystemControllerLog;
import dianfan.annotations.UnCheckedFilter;
import dianfan.constant.ConstantIF;
import dianfan.constant.ResultApiMsg;
import dianfan.entities.FileUploadBean;
import dianfan.models.ResultBean;
import dianfan.service.impl.FileUploadService;

/**
 * @ClassName UploadController
 * @Description 文件上传统一接口控制器
 * @author cjy
 * @date 2018年6月29日 下午3:17:38
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Controller
@RequestMapping(value = "/upload")
public class UploadController {

	/**
	 * 注入: #FileUploadService
	 */
	@Autowired
	private FileUploadService fileUploadService;
	
	/**
	 * @Title: uploadFile
	 * @Description: 文件上传
	 * @param type
	 * @param fileData
	 * @return
	 * @throws IOException 
	 * @throws:
	 * @time: 2018年6月29日 下午3:27:15
	 */
	@SystemControllerLog(method = "uploadFile", logtype = ConstantIF.LOG_TYPE_1, description = "文件上传")
	@ApiOperation(value = "文件上传", httpMethod = "POST", notes = "文件上传", response = ResultBean.class)
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	@UnCheckedFilter
	public ResultBean uploadFile(
			@ApiParam(value = "文件分类") @RequestParam(value = "type", required = false) String type,
			@ApiParam(value = "文件实体") @RequestParam(value = "fileData") MultipartFile fileData) throws IOException {
		
		if(fileData.isEmpty()) {
			//未上传文件
			return new ResultBean("900", ResultApiMsg.C_900);
		}
		//文件上传
		FileUploadBean saveFileData = fileUploadService.saveFileData(fileData, type);
		
		return new ResultBean(saveFileData.getFileRelativePath());
	}
}
