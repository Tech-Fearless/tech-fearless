package com.tech.fearless.boot.util;

import com.tech.fearless.boot.dao.StudentMapper;
import com.tech.fearless.boot.entity.ExcelExportModel;
import com.tech.fearless.boot.entity.Student;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Repository
public class ExcelUtil {

    private final static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    @Resource
    private StudentMapper mapper;

    public void contextLoads() {

        // 导出的excel,全文件名
        final String excelExportDestfilepath = "C:/Users/xiexinjia/Desktop/abc.xlsx";

        FileOutputStream fos = null;
        SXSSFWorkbook sxssfWorkbook = null;
        try {
            /// -> 从数据库中查询出要进行excel导出的数据
            String timeLeft = "2018-10-10 00:00:00";
            String timeRight = "2018-10-11 00:00:00";
            long startTime0 = System.currentTimeMillis();
            //List<ExcelExportModel> list = mapper.queryData(timeLeft, timeRight);
            List<Student> list = Arrays.asList(mapper.loadStudentById(12));
            long endTime0 = System.currentTimeMillis();
            logger.info("查询数据总耗时:{} 毫秒; list数量为 {}", endTime0 - startTime0, list.size());

            /// -> excel到处逻辑
            long startTime = System.currentTimeMillis();
            // 获取SXSSFWorkbook实例
            sxssfWorkbook = new SXSSFWorkbook();
            Sheet sheet = sxssfWorkbook.createSheet("我是Sheet");
            // 冻结最左边的两列、冻结最上面的一行
            // 即：滚动横向滚动条时，左边的第一、二列固定不动;滚动纵向滚动条时，上面的第一行固定不动。
            sheet.createFreezePane(2, 1);
            // 设置并获取到需要的样式
            XSSFCellStyle xssfCellStyleHeader = getAndSetXSSFCellStyleHeader(sxssfWorkbook);
            XSSFCellStyle xssfCellStyleOne = getAndSetXSSFCellStyleOne(sxssfWorkbook);
            XSSFCellStyle xssfCellStyleTwo = getAndSetXSSFCellStyleTwo(sxssfWorkbook);
            // 创建第一行,作为header表头
            Row header = sheet.createRow(0);
            // 循环创建header单元格(根据实际情况灵活创建即可)
            for (int cellnum = 0; cellnum < 11; cellnum++) {
                Cell cell = header.createCell(cellnum);
                cell.setCellStyle(xssfCellStyleHeader);
                // 判断单元格
                if (cellnum == 0) {
                    cell.setCellValue("通话ID");
                } else if (cellnum == 1) {
                    cell.setCellValue("绑定关系ID");
                } else if (cellnum == 2) {
                    cell.setCellValue("主叫号码");
                } else if (cellnum == 3) {
                    cell.setCellValue("被叫号码");
                } else if (cellnum == 4) {
                    cell.setCellValue("中间号码");
                } else if (cellnum == 5) {
                    cell.setCellValue("通话发生时间");
                } else if (cellnum == 6) {
                    cell.setCellValue("通话开始时间");
                } else if (cellnum == 7) {
                    cell.setCellValue("通话结束时间");
                } else if (cellnum == 8) {
                    cell.setCellValue("通话时长(秒)");
                } else if (cellnum == 9) {
                    cell.setCellValue("结束发起方");
                } else {
                    cell.setCellValue("结束状态（即挂断原因）");
                }
            }

            // 遍历创建行,导出数据
            for (int rownum = 1; rownum <= list.size(); rownum++) {
                Row row = sheet.createRow(rownum);
                // 循环创建单元格
                for (int cellnum = 0; cellnum < 11; cellnum++) {
                    Cell cell = row.createCell(cellnum);
                    // 根据行数,设置该行内的单元格样式
                    if (rownum % 2 == 1) { // 奇数
                        cell.setCellStyle(xssfCellStyleOne);
                    } else { // 偶数
                        cell.setCellStyle(xssfCellStyleTwo);
                    }
                    // 根据单元格所属,录入相应内容
                    // 将部分数字类型的字符串,转换为Long;以免导出excel后,单元格左上角有三
                    //    角形(这是excel检查到该单元格内的内容均为数字,但是单元格类型却不是
                    //    数字,给出的提示),转不转看自己需求灵活处理
                    if (cellnum == 0) {
                        cell.setCellValue((list.get(rownum - 1).getId()));
                    } else if (cellnum == 1) {
                        cell.setCellValue(list.get(rownum - 1).getName());
                    } else if (cellnum == 2) {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Long.parseLong(list.get(rownum - 1).getId()));
                    } else if (cellnum == 3) {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Long.parseLong(list.get(rownum - 1).getId()));
                    } else if (cellnum == 4) {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Long.parseLong(list.get(rownum - 1).getId()));
                    } else if (cellnum == 5) {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Long.parseLong(list.get(rownum - 1).getId()));
                    } else if (cellnum == 6) {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Long.parseLong(list.get(rownum - 1).getId()));
                    } else if (cellnum == 7) {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Long.parseLong(list.get(rownum - 1).getId()));
                    } else if (cellnum == 8) {
                        cell.setCellValue(list.get(rownum - 1).getId());
                    } else if (cellnum == 9) {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Long.parseLong(list.get(rownum - 1).getId()));
                    } else {
                        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                        cell.setCellValue(Long.parseLong(list.get(rownum - 1).getId()));

                    }
                }
            }
            // 在后面设置sheet
            setSheet(sheet);
            fos = new FileOutputStream(excelExportDestfilepath);
            sxssfWorkbook.write(fos);
            long endTime = System.currentTimeMillis();
            logger.info("数据全部导出至excel总耗时:{} 毫秒!", endTime - startTime, list.size());
        } catch (Exception e) {
            logger.error("发生异常咯！", e);
        } finally {
            try {
                if(sxssfWorkbook != null) {
                    // dispose of temporary files backing this workbook on disk -> 处
                    //     理SXSSFWorkbook导出excel时，产生的临时文件
                    sxssfWorkbook.dispose();
                }
                if(fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    /**
     * 设置sheet
     */
    private void setSheet(Sheet sheet) {
        // 设置各列宽度(单位为:字符宽度的1/256)
        sheet.setColumnWidth(0, 32 * 256);
        sheet.setColumnWidth(1, 32 * 256);
        sheet.setColumnWidth(2, 20 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(5, 20 * 256);
        sheet.setColumnWidth(6, 20 * 256);
        sheet.setColumnWidth(7, 20 * 256);
        sheet.setColumnWidth(8, 20 * 256);
        sheet.setColumnWidth(9, 20 * 256);
        sheet.setColumnWidth(10, 32 * 256);
    }

    /**
     * 获取并设置header样式
     */
    private XSSFCellStyle getAndSetXSSFCellStyleHeader(SXSSFWorkbook sxssfWorkbook) {
        XSSFCellStyle xssfCellStyle = (XSSFCellStyle) sxssfWorkbook.createCellStyle();
        Font font = sxssfWorkbook.createFont();
        // 字体大小
        font.setFontHeightInPoints((short) 14);
        // 字体粗细
        font.setBoldweight((short) 20);
        // 将字体应用到样式上面
        xssfCellStyle.setFont(font);
        // 是否自动换行
        xssfCellStyle.setWrapText(false);
        // 水平居中
        xssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        xssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return xssfCellStyle;
    }

    /**
     * 获取并设置样式一
     */
    private XSSFCellStyle getAndSetXSSFCellStyleOne(SXSSFWorkbook sxssfWorkbook) {
        XSSFCellStyle xssfCellStyle = (XSSFCellStyle) sxssfWorkbook.createCellStyle();
        XSSFDataFormat format = (XSSFDataFormat)sxssfWorkbook.createDataFormat();
        // 是否自动换行
        xssfCellStyle.setWrapText(false);
        // 水平居中
        xssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 垂直居中
        xssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 前景颜色
        xssfCellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        xssfCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        // 边框
        xssfCellStyle.setBorderBottom(BorderStyle.THIN);
        xssfCellStyle.setBorderRight(BorderStyle.THIN);
        xssfCellStyle.setBorderTop(BorderStyle.THIN);
        xssfCellStyle.setBorderLeft(BorderStyle.THIN);
        xssfCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        xssfCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        xssfCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        xssfCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        // 防止数字过长,excel导出后,显示为科学计数法,如:防止8615192053888被显示为8.61519E+12
        xssfCellStyle.setDataFormat(format.getFormat("0"));
        return xssfCellStyle;
    }

    /**
     * 获取并设置样式二
     */
    private XSSFCellStyle getAndSetXSSFCellStyleTwo(SXSSFWorkbook sxssfWorkbook) {
        XSSFCellStyle xssfCellStyle = (XSSFCellStyle) sxssfWorkbook.createCellStyle();
        XSSFDataFormat format = (XSSFDataFormat)sxssfWorkbook.createDataFormat();
        // 是否自动换行
        xssfCellStyle.setWrapText(false);
        // 水平居中
        xssfCellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 边框
        xssfCellStyle.setBorderBottom(BorderStyle.THIN);
        xssfCellStyle.setBorderRight(BorderStyle.THIN);
        xssfCellStyle.setBorderTop(BorderStyle.THIN);
        xssfCellStyle.setBorderLeft(BorderStyle.THIN);
        xssfCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        xssfCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        xssfCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        xssfCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        // 垂直居中
        xssfCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 防止数字过长,excel导出后,显示为科学计数法,如:防止8615192053888被显示为8.61519E+12
        xssfCellStyle.setDataFormat(format.getFormat("0"));
        return xssfCellStyle;
    }


}
