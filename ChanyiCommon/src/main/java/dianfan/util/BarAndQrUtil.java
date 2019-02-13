package dianfan.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import dianfan.constant.ConstantIF;

/**
 * 条形码和二维码编码解码
 */
public class BarAndQrUtil {
	/**
	 * 二维码保存路径设置具体目录
	 */
	private static final String QRCONFIGPATH = PropertyUtil.getProperty("qrimgpath", "upload/qr");
	/**
	 * 二维码保存路径设置根目录
	 */
	private static final String QRSAVEPATH = PropertyUtil.getProperty("qrimgroot", "C:/") + QRCONFIGPATH;
	/**
	 * 条形码保存路径设置具体目录
	 */
	private static final String BARCONFIGPATH = PropertyUtil.getProperty("barimgpath", "upload/bar");
	/**
	 * 条形码保存路径设置根目录
	 */
	private static final String BARSAVEPATH = PropertyUtil.getProperty("barimgroot", "C:/") + BARCONFIGPATH;

	/**
	 * 条形码编码
	 * 
	 * @param contents
	 *            内容
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 * @param filename
	 *            文件名称
	 */
	public static void encode(String contents, int width, int height, String filename) {
		int codeWidth = 3 + // start guard
				(7 * 6) + // left bars
				5 + // middle guard
				(7 * 6) + // right bars
				3; // end guard
		codeWidth = Math.max(codeWidth, width);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.EAN_13, codeWidth, height, null);

			if (!new File(BARSAVEPATH + filename).exists()) {
				MatrixToImageWriter.writeToFile(bitMatrix, ConstantIF.IMGBAR_PNG, new File(BARSAVEPATH + "/" + filename));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 条形码解码
	 * 
	 * @param filename
	 *            文件名
	 * @return 内容
	 */
	public static String decode(String filename) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(BARSAVEPATH + "/" + filename));
			if (image == null) {
				return null;
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			result = new MultiFormatReader().decode(bitmap, null);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 二维码编码
	 * 
	 * @param contents 内容
	 * @param width 宽度
	 * @param height 高度
	 * @param fileUrl 文件绝对路径
	 * @param fileUrl 文件名称（无后缀名）
	 */
	public static void createQRCode(String contents, int width, int height, String fileUrl, String fileName) {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		// 指定编码格式
		hints.put(EncodeHintType.CHARACTER_SET, PropertyUtil.getProperty(ConstantIF.ENCODEING));
		// 边框间距
		hints.put(EncodeHintType.MARGIN, 2);
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
			
			File f = new File(fileUrl);
			if(!f.exists()) f.mkdirs();
			
			MatrixToImageWriter.writeToFile(bitMatrix, ConstantIF.IMGQR_PNG, new File(fileUrl + fileName + "." + ConstantIF.IMGQR_PNG));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void createQRCode(String contents, String fileUrl, String fileName) {
		createQRCode(contents, 1024, 1024, fileUrl, fileName);
	}

	/**
	 * 二维码解码
	 * 
	 * @param filename
	 *            文件名
	 * @return 内容
	 */
	public static String decode2(String filename) {
		BufferedImage image = null;
		Result result = null;
		try {
			image = ImageIO.read(new File(QRSAVEPATH + filename));
			if (image == null) {
				return null;
			}
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

			Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, PropertyUtil.getProperty(ConstantIF.ENCODEING));

			result = new MultiFormatReader().decode(bitmap, hints);
			return result.getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}