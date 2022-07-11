package com.xinyao.util;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class ExportExcelUtil {

    /**
     * @param response   响应
     * @param title   导出文件名
     * @param dataset 数据集合
     */
    public static void exportExcel(HttpServletResponse response, String title, List<Map<String, String>> dataset) {
        if (dataset == null || dataset.size() == 0) {
            throw new RuntimeException("暂无有效库存数据");
        }
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            responseInit(response, title);

            // 声明一个工作薄
            HSSFWorkbook workbook = new HSSFWorkbook();
            // 生成一个表格
            HSSFSheet sheet = workbook.createSheet(title);

            //标题
            List<String> headList = new ArrayList<>();
            headList.add("品种名称");
            headList.add("生产厂家");
            headList.add("品种代码");
            headList.add("批次");
            headList.add("实际生产日期");
            headList.add("仓库");
            headList.add("库存量");
            headList.add("基本计量单位");
            headList.add("锁定数量（20%）");
            headList.add("锁定数量（100%）");
            headList.add("临时锁定数量");
            headList.add("有批号未售数量");
            headList.add("合同占用量");
            headList.add("合同占用量（100%支付）");
            headList.add("无批号未售量");
            headList.add("待出库");
            headList.add("临时提货数量");
            headList.add("销售顺序");
            // 产生表格标题行
            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i < headList.size(); i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headList.get(i));
                cell.setCellValue(text);
            }
            String oldNameAndManufacturer = null;
            int start = 1;
            int end = 1;
            for (int i = 0; i < dataset.size(); i++) {
                Map<String, String> productMap = dataset.get(i);
                String newNameAndManufacturer = productMap.get("品种名称").trim() + productMap.get("生产厂家").trim();
                if (StringUtils.isNullOrBlank(oldNameAndManufacturer)) {
                    //第一次进来 给暂存的品种名称生产厂家赋值
                    oldNameAndManufacturer = newNameAndManufacturer;
                    continue;
                }
                if (oldNameAndManufacturer.equals(newNameAndManufacturer)) {
                    //暂存的品种名称生产厂家和当前行的一致 则合并
                    end ++;
                } else {
                    //创建合并单元格
                    creatMergeCell(sheet, start, end);
                    //将合并起始行、暂存的品种名称生产厂家重新赋值
                    start = end + 1;
                    end ++;
                    oldNameAndManufacturer = newNameAndManufacturer;
                }
                //处理最后一条数据时
                if (dataset.size() - 1 == i) {
                    //创建合并单元格
                    creatMergeCell(sheet, start, end);
                }
            }

            // 遍历集合数据，产生数据行
            for (int i = 0; i < dataset.size(); i++) {
                row = sheet.createRow(i + 1);
                Map<String, String> map = dataset.get(i);
                for (int j = 0; j < headList.size(); j++) {
                    Object value = map.get(headList.get(j));
                    String textValue = null;
                    if (value == null) {
                        row.createCell(j).setCellValue("");
                    }
                    if (value instanceof Integer) {
                        int intValue = (Integer) value;
                        row.createCell(j).setCellValue(intValue);
                    } else if (value instanceof Float) {
                        float fValue = (Float) value;
                        row.createCell(j).setCellValue(fValue);
                    } else if (value instanceof Double) {
                        double dValue = (Double) value;
                        row.createCell(j).setCellValue(dValue);
                    } else if (value instanceof Long) {
                        long longValue = (Long) value;
                        row.createCell(j).setCellValue(longValue);
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        textValue = sdf.format(date);
                        row.createCell(j).setCellValue(textValue);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        textValue = value == null ? "" : value.toString();
                        row.createCell(j).setCellValue(textValue);
                    }
                }
            }
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            log.error("exportExcel exception:", e);
        }
    }

    public static void creatMergeCell(HSSFSheet sheet, int start, int end) {
        CellRangeAddress cra12 = new CellRangeAddress(start, end, 12, 12);
        CellRangeAddress cra13 = new CellRangeAddress(start, end, 13, 13);
        CellRangeAddress cra14 = new CellRangeAddress(start, end, 14, 14);
        //在sheet里增加合并单元格
        sheet.addMergedRegion(cra12);
        sheet.addMergedRegion(cra13);
        sheet.addMergedRegion(cra14);
    }

    public static void responseInit(HttpServletResponse response, String title) {
        response.reset();
        //设置content-disposition响应头控制浏览器以下载的形式打开文件
        try {
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(title, "UTF-8") + ".xlsx");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        //让服务器告诉浏览器它发送的数据属于excel文件类型
        response.setContentType("application/x-xlsx");
    }

    /**
     * 导出excel表格
     * @param response 响应
     * @param lists 数据
     * @param fileName 文件名称
     * @param titles 表头
     * @param merges 合并单元格数据
     */
    public static void exportExcel(HttpServletResponse response, List<List<Object>> lists, String fileName, String[] titles, Map<Integer, List<Integer>> merges) {
        try {
            responseInit(response, fileName);
            ServletOutputStream out = response.getOutputStream();
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
            cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
//            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
            cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
            XSSFSheet sheet = workbook.createSheet();
            // 添加表头
            XSSFRow row = sheet.createRow(0);
            for (int i = 0; i < titles.length; i++) {
                XSSFCell cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(titles[i]);
            }
            mergeCell(sheet, merges);
            fillData(lists, sheet, cellStyle);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            log.error("exportExcel exception:", e);
        }
    }

    /**
     * 根据模板导出excel
     * @param response 响应
     * @param lists 数据
     * @param fileName 文件名称
     * @param templateName 模板路径
     * @param merges 合并单元格数据
     */
    public static void exportExcelByTemplate(HttpServletResponse response, List<List<Object>> lists, String fileName, String templateName, Map<Integer, List<Integer>> merges) {
        try {
            responseInit(response,fileName);
            ServletOutputStream out = response.getOutputStream();
            Resource resource = new DefaultResourceLoader().getResource(templateName);
            InputStream inputStream = resource.getInputStream();
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
            cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);//水平居中
            cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//垂直居中
            XSSFSheet sheet = workbook.getSheetAt(0);
            mergeCell(sheet, merges);
            fillData(lists, sheet, cellStyle);
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            log.error("exportExcel exception:", e);
        }
    }

    /**
     * 合并单元格
     * @param sheet 表
     * @param merges 合并数据
     */
    private static void mergeCell(XSSFSheet sheet, Map<Integer, List<Integer>> merges) {
        if (merges != null && merges.size() != 0) {
            merges.forEach((column, merge) -> {
                int start = 1;
                for (Integer count : merge) {
                    int end = start + count - 1;
                    sheet.addMergedRegion(new CellRangeAddress(start, end, column, column));
                    start += count;
                }
            });
        }
    }

    /**
     * 填充表格数据
     * @param lists 数据
     * @param sheet 表格
     * @param cellStyle 单元格样式
     */
    private static void fillData(List<List<Object>> lists, XSSFSheet sheet, XSSFCellStyle cellStyle) {
        int rowIndex = 1;
        for (List<Object> list : lists) {
            XSSFRow dataRow = sheet.createRow(rowIndex++);
            dataRow.setHeight((short) (26*20));
            int cellIndex = 0;
            for (Object value : list) {
                XSSFCell cell = dataRow.createCell(cellIndex);
                cell.setCellStyle(cellStyle);
                if (value == null) {
                    cell.setCellValue("-");
                } else if (value instanceof Integer) {
                    cell.setCellValue((Integer)value);
                } else if (value instanceof Float) {
                    cell.setCellValue((Float)value);
                } else if (value instanceof Double) {
                    cell.setCellValue((Double)value);
                } else if (value instanceof Long) {
                    cell.setCellValue((Long)value);
                } else if (value instanceof Date) {
                    cell.setCellValue(new SimpleDateFormat("yyyy/MM/dd").format((Date)value));
//                    cell.setCellValue((Date) value);
                } else if (value instanceof BigDecimal) {
//                    if (BigDecimal.ZERO.compareTo((BigDecimal) value) == 0) {
//                        cell.setCellValue("-");
//                    }else {
//                        cell.setCellValue(((BigDecimal)value).doubleValue());
//                    }
                    cell.setCellValue(((BigDecimal)value).stripTrailingZeros().toPlainString());
                } else {
                    cell.setCellValue(value.toString());
                }
                cellIndex++;
            }
        }
    }

}