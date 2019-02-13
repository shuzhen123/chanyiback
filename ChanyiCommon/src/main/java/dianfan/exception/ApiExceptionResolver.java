package dianfan.exception;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import dianfan.models.ResultBean;

/**
 * 异常增强，以JSON的形式返回给客服端
 * 异常增强类型：NullPointerException,RunTimeException,ClassCastException,
 * NoSuchMethodException,IOException,IndexOutOfBoundsException
 * 以及springmvc自定义异常等，如下： SpringMVC自定义异常对应的status code Exception HTTP Status Code
 * ConversionNotSupportedException 500 (Internal Server Error)
 * HttpMessageNotWritableException 500 (Internal Server Error)
 * HttpMediaTypeNotSupportedException 415 (Unsupported Media Type)
 * HttpMediaTypeNotAcceptableException 406 (Not Acceptable)
 * HttpRequestMethodNotSupportedException 405 (Method Not Allowed)
 * NoHandlerFoundException 404 (Not Found) TypeMismatchException 400 (Bad
 * Request) HttpMessageNotReadableException 400 (Bad Request)
 * MissingServletRequestParameterException 400 (Bad Request)
 *
 */
@ControllerAdvice
@ResponseBody
public class ApiExceptionResolver {
	// 400错误
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResultBean requestNotReadable(HttpMessageNotReadableException ex) {
		return ReturnFormat.retParam("400", "不可读的请求");
	}

	// 400错误
	@ExceptionHandler({ TypeMismatchException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResultBean requestTypeMismatch(TypeMismatchException ex) {
		return ReturnFormat.retParam("400", "缺少必要参数");
	}

	// 400错误
	@ExceptionHandler({ MissingServletRequestParameterException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResultBean requestMissingServletRequest(MissingServletRequestParameterException ex) {
		return ReturnFormat.retParam("400", "参数不匹配");
	}

	// 405错误
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
	public ResultBean request405() {
		return ReturnFormat.retParam("405", "请求的方式不被允许");
	}

	// 415 不支持的媒体类型
	@ExceptionHandler({ HttpMediaTypeNotSupportedException.class })
	@ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	public ResultBean request415() {
		return ReturnFormat.retParam("415", "不支持的媒体类型");
	}

	// 406错误
	@ExceptionHandler({ HttpMediaTypeNotAcceptableException.class })
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	public ResultBean request406() {
		return ReturnFormat.retParam("406", "媒体类型不被接受");
	}

	// 500错误
	@ExceptionHandler({ ConversionNotSupportedException.class, HttpMessageNotWritableException.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResultBean server500() {
		return ReturnFormat.retParam("500", "服务器内部错误");
	}

	// 404异常
	@ExceptionHandler({ ServletException.class, NoHandlerFoundException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResultBean http404() {
		return ReturnFormat.retParam("404", "没有找到该资源");
	}

	// 空指针异常
	@ExceptionHandler(NullPointerException.class)
	public ResultBean nullPointerExceptionHandler(NullPointerException ex) {
		ex.printStackTrace();
		return ReturnFormat.retParam("1001", null);
	}

	// 类型转换异常
	@ExceptionHandler(ClassCastException.class)
	public ResultBean classCastExceptionHandler(ClassCastException ex) {
		ex.printStackTrace();
		return ReturnFormat.retParam("1002", null);
	}

	// IO异常
	@ExceptionHandler(IOException.class)
	public ResultBean iOExceptionHandler(IOException ex) {
		ex.printStackTrace();
		return ReturnFormat.retParam("1003", null);
	}

	// 未知方法异常
	@ExceptionHandler(NoSuchMethodException.class)
	public ResultBean noSuchMethodExceptionHandler(NoSuchMethodException ex) {
		ex.printStackTrace();
		return ReturnFormat.retParam("1004", null);
	}

	// 数组越界异常
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public ResultBean indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
		ex.printStackTrace();
		return ReturnFormat.retParam("1005", null);
	}

	// 未知错误
	@ExceptionHandler({ Error.class, Exception.class, Throwable.class })
	public ResultBean exception() {
		return ReturnFormat.retParam("1006", null);
	}

	// 由于除数为0引起的异常
	@ExceptionHandler(ArithmeticException.class)
	public ResultBean arithmeticExceptionHandler(ArithmeticException ex) {
		ex.printStackTrace();
		return ReturnFormat.retParam("1007", null);
	}

	// 由于数组存储空间不够引起的异常
	@ExceptionHandler(ArrayStoreException.class)
	public ResultBean arrayStoreExceptionHandler(ArrayStoreException ex) {
		ex.printStackTrace();
		return ReturnFormat.retParam("1008", null);
	}

	// 文件没有找到系统异常
	@ExceptionHandler(FileNotFoundException.class)
	public ResultBean fileNotFoundExceptionHandler(FileNotFoundException ex) {
		ex.printStackTrace();
		return ReturnFormat.retParam("1009", null);
	}

	// SQL执行异常
	@ExceptionHandler(SQLException.class)
	public ResultBean sqlExceptionHandler() {
		return ReturnFormat.retParam("1010", null);
	}

	// SQL异常
	@ExceptionHandler(DataAccessException.class)
	public ResultBean dataAccessExceptionHandler() {
		return ReturnFormat.retParam("1011", null);
	}

	// 文件上传过大异常
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResultBean maxUploadSizeExceededHandler() {
		return ReturnFormat.retParam("1012", null);
	}

	// 运行时异常
	@ExceptionHandler(RuntimeException.class)
	public ResultBean runtimeExceptionHandler(RuntimeException runtimeException) {
		return ReturnFormat.retParam("1000", null);
	}

}
