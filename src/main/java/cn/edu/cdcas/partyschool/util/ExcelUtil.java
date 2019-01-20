package cn.edu.cdcas.partyschool.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * the util class packaging the common methods to import and export excel.
 *
 * @author Char Jin(Jin zhichao)
 * @date 2018-07-12
 */

public class ExcelUtil {

    /**
     * transform the data of excel into map. And the list,key of which equals
     * -1,stores the header of column.
     *
     * @return the map transformed by excel.
     */
    public Map<Integer, List<String>> getDataMap(CommonsMultipartFile excelFile, int colNum) throws IOException {
        Map<Integer, List<String>> map = null;
        String suffix = excelFile.getName().substring(excelFile.getName().lastIndexOf("."));

        Workbook workbook;
        if (suffix.equals(".xls"))
            workbook = new HSSFWorkbook(excelFile.getInputStream());
        else
            workbook = new XSSFWorkbook(excelFile.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();

        map = new HashMap<>();
        int i = -1;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.iterator();
            List<String> list = new ArrayList<>();
            int j = 0;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                cell.setCellType(CellType.STRING);
                list.add(j++, cell.getStringCellValue());
                if (j == colNum) break;
            }
            map.put(i++, list);
        }
        workbook.close();


        return map;
    }


    /**
     * set cell style.
     *
     * @param workbook
     * @param fontName
     * @param fontSize
     * @param fontBold
     * @param borderStyle
     * @return HSSFCellStyle
     */
    public HSSFCellStyle createHSSFCellStyle(HSSFWorkbook workbook, String fontName, int fontSize, boolean fontBold,
                                             BorderStyle borderStyle) {
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints((short) fontSize);
        font.setBold(fontBold);
        style.setFont(font);

        style.setBorderBottom(borderStyle);
        style.setBorderTop(borderStyle);
        style.setBorderRight(borderStyle);
        style.setBorderLeft(borderStyle);

        return style;
    }
}
