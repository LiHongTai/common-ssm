package com.roger.biz.export;

import com.roger.biz.entity.City;
import com.roger.biz.util.POIExcelUtil;
import com.roger.core.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
public class CityExportUtil {

    private static List<String> titleRowList = Arrays.asList("省份","城市名称","描述");
    private static List<String> fileNameList = Arrays.asList("provinceId","cityName","description");
    private static String EXCEL_FILE_NAME = "城市{0}.xlsx";
    private static String SHEET_NAME = "城市名称";
    public static void export(HttpServletResponse response, List<City> cityList) {
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        String excelFileName = MessageFormat.format(EXCEL_FILE_NAME,DateUtil.getNoWTime(DateUtil.SIMPLE_DATE_FORMAT));
        try {
            String encodeFileName = URLEncoder.encode(excelFileName,"utf-8");
            response.setHeader("Content-Disposition","attachment;filename="+encodeFileName);
        } catch (UnsupportedEncodingException e) {
            log.error("文件名编码异常:" + e.getMessage());
        }
        OutputStream outputStream = null;

        try {
            Workbook cityWorkbook = generateCityWorkbook(cityList,"xlsx");
            outputStream = response.getOutputStream();
            cityWorkbook.write(outputStream);
            cityWorkbook.close();

            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            log.error("生成excel表格异常:" + e.getMessage());
        }

    }

    private static Workbook generateCityWorkbook(List<City> cityList, String style) {
        Workbook cityWorkbook = new XSSFWorkbook();
        if(style != null && "XLS".equals(style.toUpperCase())){
            cityWorkbook = new HSSFWorkbook();
        }
        Sheet sheet = cityWorkbook.createSheet(SHEET_NAME);
        Map<String,CellStyle> styles = POIExcelUtil.createCellStyle(cityWorkbook);

        //创建标题行
        POIExcelUtil.createTitleRow(sheet,styles.get(POIExcelUtil.TITLE_ROW),titleRowList);
        //创建表格内容
        for(int i = 1; i <= cityList.size(); i ++){
            POIExcelUtil.createContentRow(sheet.createRow(i),styles.get(POIExcelUtil.CONTENT_ROW),fileNameList,cityList.get(i-1));
        }
        //表格列宽自适应
        POIExcelUtil.makeColumnAutoSize(sheet,titleRowList.size());
        return cityWorkbook;
    }
}
