package com.roger.biz.util;

import com.roger.core.utils.FieldReflectUtil;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POIExcelUtil {

    public static final String TITLE_ROW = "titleRow";
    public static final String CONTENT_ROW = "contextRow";

    public static Map<String,CellStyle> createCellStyle(Workbook workbook) {
        Map<String,CellStyle> styles = new HashMap<>();
        styles.put(TITLE_ROW,createCellStylByType(workbook,TITLE_ROW));
        styles.put(CONTENT_ROW,createCellStylByType(workbook,null));
        return styles;
    }

    private static CellStyle createCellStylByType(Workbook workbook,String type) {
        CellStyle cellStyle = workbook.createCellStyle();
        //水平垂直居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //上下左右边框
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        if(TITLE_ROW.equals(type)){
            //设置字体
            Font font = workbook.createFont();
            font.setBold(true);
            cellStyle.setFont(font);
            //设置背景色
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.index);
        }
        return cellStyle;
    }

    public static void createTitleRow(Sheet sheet, CellStyle cellStyle, List<String> titleRowList) {
        Row row = sheet.createRow(0);
        //冻结第一行
        sheet.createFreezePane(0,1);
        for(int i = 0; i < titleRowList.size(); i ++){
            Cell cell = row.createCell(i);
            cell.setCellValue(titleRowList.get(i));
            cell.setCellStyle(cellStyle);
        }
    }

    public static void createContentRow(Row row, CellStyle cellStyle,List<String> fileNameList, Object obj) {
        if(CollectionUtils.isEmpty(fileNameList))
            return;
        for(int i = 0; i < fileNameList.size(); i ++){
            Cell cell = row.createCell(i);
            cell.setCellValue((String) FieldReflectUtil.getFieldValue(obj,fileNameList.get(i)));
            cell.setCellStyle(cellStyle);
        }
    }

    public static void makeColumnAutoSize(Sheet sheet, int columnNum) {
        for(int i = 0; i <= columnNum; i ++){
            sheet.autoSizeColumn(i);
            //解决自动设置列宽时中文失效的问题
            sheet.setColumnWidth(i,sheet.getColumnWidth(i)*15/10);
        }
    }
}
