package com.xinyao.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {

    private final static String excel_2003L = ".xls"; // 2003- 版本的excel
    private final static String excel_2007U = ".xlsx"; // 2007+ 版本的excel

    /**
     * 将流中的Excel数据转成List<Map>
     *
     * @param in       输入流
     * @param fileName 文件名（判断Excel版本）
     * @return
     * @throws Exception
     */
    public static List<Map<String, String>> parseExcel(InputStream in, String fileName) throws Exception {
        // 根据文件名来创建Excel工作薄
        Workbook work = getWorkbook(in, fileName);
        if (null == work) {
            throw new RuntimeException("导入Excel工作薄为空！");
        }
        Sheet sheet = null;
        Row row = null;
        Cell cell = null;
        // 返回数据
        List<Map<String, String>> ls = new ArrayList<>();
        // 遍历Excel中所有的sheet
        for (int i = 0; i < 1; i++) {
            sheet = work.getSheetAt(i);
            if (sheet == null) {
                continue;
            }
            // 取第一行标题
            Row topRow = sheet.getRow(0);
            if (null == topRow) {
                break;
            }
            cell = topRow.getCell(1);
            String top = (String) getCellValue(cell);
            row = sheet.getRow(0);
            String title[] = null;
            if (row != null) {
                title = new String[row.getLastCellNum()];
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    title[y] = (String) getCellValue(cell);
                }
            } else {
                continue;
            }
            // 遍历当前sheet中的所有行
            // 坑：sheet.getLastRowNum()  最后一行行标，比行数小1
            for (int j = 1; j <= sheet.getLastRowNum(); j++) {
                row = sheet.getRow(j);
                if (null == row.getCell(0)) {
                    break;
                }
                Map<String, String> m = new HashMap<>();
                // 遍历所有的列
                for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
                    cell = row.getCell(y);
                    String key = title[y].replace(" ", "");
                    m.put(key, getCellValue(cell).toString().trim());
                }
                ls.add(m);
            }

        }
        work.close();
        return ls;
    }

    /**
     * 描述：根据文件后缀，自适应上传文件的版本
     *
     * @param inStr ,fileName
     * @return
     * @throws Exception
     */
    public static Workbook getWorkbook(InputStream inStr, String fileName) throws Exception {
        Workbook wb = null;
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if (excel_2003L.equals(fileType)) {
            wb = new HSSFWorkbook(inStr); // 2003-
        } else if (excel_2007U.equals(fileType)) {
            wb = new XSSFWorkbook(inStr); // 2007+
        } else {
            throw new RuntimeException("解析的文件格式有误！");
        }
        return wb;
    }

    public static Object getCellValue(Cell cell) {
        Object value = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd"); // 日期格式化
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                value = cell.getRichStringCellValue().getString();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                if ("General".equals(cell.getCellStyle().getDataFormatString())) {
                    value = cell.getNumericCellValue();
                } else if ("m/d/yy".equals(cell.getCellStyle().getDataFormatString())) {
                    value = sdf.format(cell.getDateCellValue());
                } else {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    value = cell.getRichStringCellValue().getString();
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                value = "";
                break;
            case Cell.CELL_TYPE_FORMULA:
                value = cell.getRichStringCellValue().getString();
                break;
            default:
                break;
        }
        return value;
    }

	public static void simpleExcelWrite(String name, File newFile, HttpServletResponse response, List<Map<String, String>> list) {
		InputStream is = null;
		Workbook workbook = null;
		Sheet sheet = null;
		Cell cell = null;
		try {

			is = new FileInputStream(newFile);// 将excel文件转为输入流
			workbook = WorkbookFactory.create(is);// 创建个workbook，
			// 获取第一个sheet
			sheet = workbook.getSheetAt(0);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new RuntimeException("导出失败");
		}
		if (sheet != null) {
			try {
				ServletOutputStream sos = response.getOutputStream();
				Row row = sheet.getRow(0);
				Row rows = null;
				if (null != list && !list.isEmpty()) {
					for (int i = 0; i < list.size(); i++) {
						rows = sheet.createRow(i + 1); // 从第三行开始
						for (int y = row.getFirstCellNum(); y < row.getLastCellNum(); y++) {
							cell = row.getCell(y);
							if (null != list.get(i).get(getCellValue(cell))) {
								createRowAndCell(list.get(i).get(getCellValue(cell)), rows, rows.getCell(y), y);
							}
						}
					}
				}
				response.setContentType("application/vnd.ms-excel");
				String newName = URLEncoder.encode(name + ".xlsx", "UTF-8");
				response.addHeader("Content-Disposition", "attachment;filename=\"" + newName + "\"");
				workbook.write(sos);
				sos.flush();
				sos.close();

				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				FileOutputStream out = new FileOutputStream("D:/" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xlsx");
				workbook.write(out);
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("生成失败");
			} finally {
				try {
					if (null != is) {
						is.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 根据当前row行，来创建index标记的列数,并赋值数据
	 */
	private static void createRowAndCell(Object obj, Row row, Cell cell, int index) {
		cell = row.getCell(index);
		if (cell == null) {
			cell = row.createCell(index);
		}

		if (obj != null) {
			cell.setCellValue(obj.toString());
		} else {
			cell.setCellValue("");
		}
	}
}
