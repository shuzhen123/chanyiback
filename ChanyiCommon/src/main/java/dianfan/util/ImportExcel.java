package dianfan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 功能: [POI实现把Excel数据导入到数据库] 作者: JML 版本: 1.0
 */
public class ImportExcel {

	/**
	 * @Title: importExcelForNotNull（不需要数据与属性一一对应去除为空的情况。）
	 * @Description: Excel数据导入到数据库
	 * @param originUrl
	 *            Excel表的所在路径
	 * @param startRow
	 *            从第几行开始
	 * @param endRow
	 *            到第几行结束 (0表示所有行; 正数表示到第几行结束; 负数表示到倒数第几行结束)
	 * @param clazz
	 *            要返回的对象集合的类型
	 * @return 列表
	 * @throws IOException
	 *             异常
	 * @throws:
	 * @time: 2018年5月11日 下午12:13:02
	 */
	public static List<?> importExcelForNotNull(String originUrl, int startRow, int endRow, Class<?> clazz)
			throws IOException {
		// 是否打印提示信息
		boolean showInfo = true;
		return doImportExcelForNotNull(originUrl, startRow, endRow, showInfo, clazz);
	}

	/**
	 * @Title: importExcelForNotNull（不需要数据与属性一一对应去除为空的情况。）
	 * @Description: 私有Excel数据导入到数据库
	 * @param originUrl
	 *            Excel表的所在路径
	 * @param startRow
	 *            从第几行开始
	 * @param endRow
	 *            到第几行结束 (0表示所有行; 正数表示到第几行结束; 负数表示到倒数第几行结束)
	 * @param showInfo
	 *            是否打印提示信息
	 * @param clazz
	 *            要返回的对象集合的类型
	 * @return 列表
	 * @throws IOException
	 *             异常
	 * @throws:
	 * @time: 2018年5月11日 下午12:13:02
	 */
	private static List<?> doImportExcelForNotNull(String originUrl, int startRow, int endRow, boolean showInfo,
			Class<?> clazz) throws IOException {
		// 判断文件是否存在
		File file = new File(originUrl);
		if (!file.exists()) {
			throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
		}
		HSSFWorkbook wb = null;
		FileInputStream fis = null;
		List<Row> rowList = new ArrayList<Row>();
		try {
			fis = new FileInputStream(file);
			// 去读Excel
			wb = new HSSFWorkbook(fis);
			Sheet sheet = wb.getSheetAt(0);
			// 获取最后行号
			int lastRowNum = sheet.getLastRowNum();
			if (lastRowNum > 0) { // 如果>0，表示有数据
				out("\n开始读取名为【" + sheet.getSheetName() + "】的内容：", showInfo);
			}
			Row row = null;
			// 循环读取
			for (int i = startRow; i <= lastRowNum + endRow; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					rowList.add(row);
					out("第" + (i + 1) + "行：", showInfo, false);
					// 获取每一单元格的值
					for (int j = 0; j < row.getLastCellNum(); j++) {
						String value = getCellValue(row.getCell(j));
						if (!"".equals(value)) {
							out(value + " | ", showInfo, false);
						}
					}
					out("", showInfo);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (wb != null) {
				wb.close();
			}
		}
		return rowList;
	}

	/**
	 * @Title: importExcelForNull(在数据入库时需要数据与属性一一对应。)
	 * @Description: Excel数据导入到数据库
	 * @param originUrl
	 *            Excel表的所在路径
	 * @param startRow
	 *            从第几行开始
	 * @param endRow
	 *            到第几行结束 (0表示所有行; 正数表示到第几行结束; 负数表示到倒数第几行结束)
	 * @param clazz
	 *            要返回的对象集合的类型
	 * @return 数据
	 * @throws IOException
	 *             异常
	 * @throws:
	 * @time: 2018年5月11日 下午12:19:14
	 */
	public static List<?> importExcelForNull(String originUrl, int startRow, int endRow, Class<?> clazz)
			throws IOException {
		// 是否打印提示信息
		boolean showInfo = true;
		return doImportExcelForNull(originUrl, startRow, endRow, showInfo, clazz);
	}

	/**
	 * @Title: importExcelForNull(在数据入库时需要数据与属性一一对应。)
	 * @Description: Excel数据导入到数据库
	 * @param originUrl
	 *            Excel表的所在路径
	 * @param startRow
	 *            从第几行开始
	 * @param endRow
	 *            到第几行结束 (0表示所有行; 正数表示到第几行结束; 负数表示到倒数第几行结束)
	 * @param showInfo
	 *            是否打印提示信息
	 * @param clazz
	 *            要返回的对象集合的类型
	 * @return 数据
	 * @throws IOException
	 *             异常
	 * @throws:
	 * @time: 2018年5月11日 下午12:19:14
	 */
	private static List<?> doImportExcelForNull(String originUrl, int startRow, int endRow, boolean showInfo,
			Class<?> clazz) throws IOException {
		// 判断文件是否存在
		File file = new File(originUrl);
		if (!file.exists()) {
			throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");
		}
		Workbook wb = null;
		FileInputStream fis = null;
		List<Row> rowList = new ArrayList<Row>();
		try {
			fis = new FileInputStream(file);
			// 去读Excel
			boolean isExcel2003 = originUrl.toLowerCase().endsWith("xls") ? true : false;
			if (isExcel2003) {
				wb = new HSSFWorkbook(fis);
			} else {
				wb = new XSSFWorkbook(fis);
			}
			Sheet sheet = wb.getSheetAt(0);
			// 获取最后行号
			int lastRowNum = sheet.getLastRowNum();
			if (lastRowNum > 0) { // 如果>0，表示有数据
				out("\n开始读取名为【" + sheet.getSheetName() + "】的内容：", showInfo);
			}
			Row row = null;
			// 循环读取
			for (int i = startRow; i <= lastRowNum + endRow; i++) {
				row = sheet.getRow(i);
				if (row != null) {
					rowList.add(row);
					out("第" + (i + 1) + "行：", showInfo, false);
					// 获取每一单元格的值
					for (int j = 0; j < row.getLastCellNum(); j++) {
						String value = getCellValue(row.getCell(j));

						out(value + " | ", showInfo, false);

					}
					out("", showInfo);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (wb != null) {
				wb.close();
			}
		}
		return rowList;
	}

	/**
	 * @Title: getCellValue
	 * @Description: 功能:获取单元格的值
	 * @param cell
	 *            单元格
	 * @return 返回类型
	 * @throws:
	 * @time: 2018年5月11日 下午12:21:42
	 */
	private static String getCellValue(Cell cell) {
		Object result = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				result = cell.getStringCellValue();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				result = cell.getNumericCellValue();
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				result = cell.getBooleanCellValue();
				break;
			case Cell.CELL_TYPE_FORMULA:
				result = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_ERROR:
				result = cell.getErrorCellValue();
				break;
			case Cell.CELL_TYPE_BLANK:
				break;
			default:
				break;
			}
		}
		return result.toString();
	}

	/**
	 * @Title: out
	 * @Description: 功能:输出提示信息(普通信息打印)
	 * @param info
	 *            信息
	 * @param showInfo
	 *            是否打印提示信息
	 * @throws:
	 * @time: 2018年5月11日 下午12:21:20
	 */
	private static void out(String info, boolean showInfo) {
		if (showInfo) {
			System.out.print(info + (showInfo ? "\n" : ""));
		}
	}

	/**
	 * @Title: out
	 * @Description: 功能:输出提示信息(同一行的不同单元格信息打印)
	 * @param info
	 *            信息
	 * @param showInfo
	 *            是否打印提示信息
	 * @param nextLine
	 *            换行
	 * @throws:
	 * @time: 2018年5月11日 下午12:22:21
	 */
	private static void out(String info, boolean showInfo, boolean nextLine) {
		if (showInfo) {
			if (nextLine) {
				System.out.print(info + (showInfo ? "\n" : ""));
			} else {
				System.out.print(info);
			}
		}
	}
}
