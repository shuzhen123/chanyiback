package dianfan.test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import dianfan.base64.Base64Util;
import dianfan.constant.ConstantIF;
import dianfan.date.DateUtility;
import dianfan.entities.FileUploadType;
import dianfan.map.tencent.GeocoderRet;
import dianfan.map.tencent.TencentMapApi;
import dianfan.util.BarAndQrUtil;
import dianfan.util.CipherTextUtil;
import dianfan.util.HttpClientHelper;
import dianfan.util.PropertyUtil;

/**
 * @ClassName MD5Test
 * @Description MD5加密测试
 * @author sz
 * @date 2018年6月29日 下午12:33:58
 */
public class MD5Test {
	// 文件域
	private String FILE_DOMAIN = PropertyUtil.getProperty("file_domain"); // D:/web_resource/
	// 时间分包文件夹 (20180630/)
	private String DATE_DIR = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "/";

	@Test
	public void test() {

		/*
		 * Date addDay = DateUtility.getAddDayToTimeEnd(new
		 * Date(),ConstantIF.CUOPON_DAY); System.out.println(addDay);
		 */
		Date addDay = DateUtility.getAddDayToTimeEnd(new Date(), ConstantIF.CUOPON_DAY);
		// 将date类型转换成Timestamp 类型
		Timestamp nowtime = new Timestamp(addDay.getTime());
		System.out.println(nowtime);

	}

	@Test
	public void testDay() throws IOException {
		String files = "https://wx.qlogo.cn/mmopen/vi_32/ajNVdqHZLLA1zMqBCfNPxIvAHZ0dx55hV4Res215q30fctHjZa3ASqjODvbgKNPcOUfSTWQ4gt7hRUFkk9gNbw/132";
		File file = new File(files);

		// 原文件名
		String filename = file.getName();
		// 新文件名称
		String newfilename = String.valueOf(System.nanoTime());
		if (filename.lastIndexOf(".") != -1) {
			// 文件名 + 后缀名
			newfilename += filename.substring(filename.lastIndexOf("."));
		} else {
			// 文件名 + 默认后缀名
			newfilename += ".jpg";
		}

		// 文件类
		FileUploadType flt = new FileUploadType();
		String file_url = flt.fileTypeSelect(flt.AVATOR); // upload/avator/

		String realPath = FILE_DOMAIN + file_url + DATE_DIR;

		File file_dir = new File(realPath);
		if (!file_dir.exists()) {
			file_dir.mkdirs();
		}

		HttpClientHelper.sendGetAndSaveFile(files, null, FILE_DOMAIN + file_url + DATE_DIR + newfilename);

		// InputStream in = new FileInputStream(file);
		// FileUtils.copyInputStreamToFile(in, new File(FILE_DOMAIN + file_url +
		// DATE_DIR, newfilename));
		System.out.println(FILE_DOMAIN + file_url + DATE_DIR + newfilename);
	}

	@Test
	public void createQR() throws IOException {
		String contents = "http://www.chanyi.store/qrcode/abc";
		FileUploadType type = new FileUploadType();
		String file_cache_url = type.fileTypeSelect(type.IDCARD);
		// 时间分包文件夹 (20180630/)
		String date_dir = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "/";

		String fileUrl = PropertyUtil.getProperty("file_domain") + file_cache_url + date_dir;
		BarAndQrUtil.createQRCode(contents, fileUrl, "123");
	}

	@Test
	public void contains() throws IOException {
		String userAgent = "Mozilla/5.0 (Linux; Android 8.0; VKY-AL00 Build/HUAWEIVKY-AL00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/57.0.2987.132 MQQBrowser/6.2 TBS/044109 Mobile Safari/537.36 MicroMessenger/6.6.7.1321(0x26060739) NetType/WIFI Language/zh_CN";
		userAgent = StringUtils.upperCase(userAgent);
		System.out.println(userAgent);
		System.out.println(StringUtils.contains(userAgent, StringUtils.upperCase("MicroMessenger")));
	}

	@Test
	public void bigdecimal() throws IOException {
		BigDecimal b1 = new BigDecimal(2.1);
		BigDecimal b2 = new BigDecimal(3.2);
		BigDecimal b3 = b1.subtract(b2);
		System.out.println(b3.setScale(1, BigDecimal.ROUND_HALF_UP));
	}

	@Test
	public void tencentMapApi() throws IOException {
		TencentMapApi tma = new TencentMapApi();
		GeocoderRet ret = tma.geocoder("120.345161", "31.558891");
		System.out.println(ret);
	}

	@Test
	public void base64() throws Exception {

		/*
		 * String req_info =
		 * "fpeAoCJo4WSD+q28hYyIm8CHPq6h0+tlN+oHx2rWZxQp1500gROz7Xhp6zCSJIuf7bjoCKqr9+UxbbadQzUMVqpmX1zXGdK7nSFernJ0qnXesNAeB9wnqkga7YEcPpFeufSw86G7U9uH6zwtCy/BiXrtJvtezYWZ/XYLiNlVskIBybbQX6aVx1zOboxoRXyHnHcG6RRzNoAPfOtwY9nRrXWNldM0YS+IobsDUDNSNDnXv0RkCsC3FJy8NU2bHxzFWUJrlDPb0PxK/3aMWt6ahdJqBL7hyjlODhkrrRuGTziqnPQyBYFXRVcm/OIpCfM1MfEyhxRHBYDeC82cAZPWRX2z3pAz0+J8G6PMNi7OHlMpXEwgiTUcw171UKjpx8CtprRPMvXUIo06u8+HbSEnk+2dpe/jw5kxS6Yxdtl7Y3HXSBDMvN+Gk0uzTdWfOeCG0PXH96aKiC4Y6dDP4F+dWJHqE2tOE/lF5r8fTy188BfvG/0A8tmP1Ivj3jxP39Inulw/KIgk7PR1fXjCDcGwIDgDDz/bn1Ypye0/EHVxsmDXvxjKAccgoy1I+vc3eB1z9riaRH+RYn90Q/pbP1h/7doi8LPRH+CrTqj0Iyvrgcubsy7tRe1WJBGimzTnYS5bx0n3D8KMu8r96PCqskyEcFPYfH9nR766xfVE+o9V5FVnUgPWJt9l42QMe3NrcjOjeLtrs82UadEormpfpIYHPC8VTkabw2jjePGUwk4xlysYmaFdftHXCHnMoxZVYXIvD1gTPye8i36cmDDFIjeF8D86CCkaHtfmeaqfB5OJC4yAPT77cP3b5G85R68EiwtQbLN9YU5fy4pOpLiXTjQ1JaUPA7kOTPa2k4Hol362RoKEGXi3GpVPgAsjuLFnjAMFR5eBskHDwRJhyDt9Mj56asxM2u1xvyQmGp3nLZ7QE7d1/wrH6nB75mz0IX/EUqsqVja0B5/Bh65P61+QzsxqRxQi+M075rw1yRmD42xb9mpgw6vL4jRmohfwsqsEUZHOKXoRhapFTPXPs27pFvPzvyLqNuZ1sgu4TBaRRpM3dT1yLbmFQnKoADZqC/omjDrqcrNRNtY0f+GcJRxLm9qACw==";
		 *//**
			 * 密钥算法
			 */
		/*
		 * String ALGORITHM = "AES";
		 *//**
			 * 加解密算法/工作模式/填充方式
			 */
		/*
		 * String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";
		 * 
		 *//**
			 * 生成key
			 *//*
				 * String strb = Base64Utils.decode(req_info); SecretKeySpec key = new
				 * SecretKeySpec("3eb753a36aba11e8914800163e0028c4".getBytes(), ALGORITHM);
				 * Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
				 * cipher.init(Cipher.DECRYPT_MODE, key);
				 * 
				 * String ret = new String(cipher.doFinal(strb.getBytes()));
				 * System.out.println(ret);
				 */
		String A = "fpeAoCJo4WSD+q28hYyIm8CHPq6h0+tlN+oHx2rWZxQp1500gROz7Xhp6zCSJIuf7bjoCKqr9+UxbbadQzUMVqpmX1zXGdK7nSFernJ0qnXesNAeB9wnqkga7YEcPpFeufSw86G7U9uH6zwtCy/BiXrtJvtezYWZ/XYLiNlVskIBybbQX6aVx1zOboxoRXyHnHcG6RRzNoAPfOtwY9nRrXWNldM0YS+IobsDUDNSNDnXv0RkCsC3FJy8NU2bHxzFWUJrlDPb0PxK/3aMWt6ahdJqBL7hyjlODhkrrRuGTziqnPQyBYFXRVcm/OIpCfM1MfEyhxRHBYDeC82cAZPWRX2z3pAz0+J8G6PMNi7OHlMpXEwgiTUcw171UKjpx8CtprRPMvXUIo06u8+HbSEnk+2dpe/jw5kxS6Yxdtl7Y3HXSBDMvN+Gk0uzTdWfOeCG0PXH96aKiC4Y6dDP4F+dWJHqE2tOE/lF5r8fTy188BfvG/0A8tmP1Ivj3jxP39Inulw/KIgk7PR1fXjCDcGwIDgDDz/bn1Ypye0/EHVxsmDXvxjKAccgoy1I+vc3eB1z9riaRH+RYn90Q/pbP1h/7doi8LPRH+CrTqj0Iyvrgcubsy7tRe1WJBGimzTnYS5bx0n3D8KMu8r96PCqskyEcFPYfH9nR766xfVE+o9V5FVnUgPWJt9l42QMe3NrcjOjeLtrs82UadEormpfpIYHPC8VTkabw2jjePGUwk4xlysYmaFdftHXCHnMoxZVYXIvD1gTPye8i36cmDDFIjeF8D86CCkaHtfmeaqfB5OJC4yAPT77cP3b5G85R68EiwtQbLN9YU5fy4pOpLiXTjQ1JaUPA7kOTPa2k4Hol362RoKEGXi3GpVPgAsjuLFnjAMFR5eBskHDwRJhyDt9Mj56asxM2u1xvyQmGp3nLZ7QE7d1/wrH6nB75mz0IX/EUqsqVja0B5/Bh65P61+QzsxqRxQi+M075rw1yRmD42xb9mpgw6vL4jRmohfwsqsEUZHOKXoRhapFTPXPs27pFvPzvyLqNuZ1sgu4TBaRRpM3dT1yLbmFQnKoADZqC/omjDrqcrNRNtY0f+GcJRxLm9qACw==";
		byte[] b = Base64Util.decode(A);
		String B = CipherTextUtil.decryptData(b, "3eb753a36aba11e8914800163e0028c4");
		System.out.println(B);
		// WxNotify notify = new WxNotify();
		// Map<String, String> xmlToMap = notify.xmlToMap(B);
		// System.out.println(xmlToMap);
	}

	@Test
	public void a() {
		BigDecimal total = new BigDecimal(2580.00).subtract(new BigDecimal(188)).multiply(new BigDecimal(0.5)).multiply(new BigDecimal(0.1)).multiply(new BigDecimal(1));
		System.err.println(new BigDecimal(0).add(new BigDecimal(50)));
	}
}
