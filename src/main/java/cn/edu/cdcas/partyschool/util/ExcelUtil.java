package cn.edu.cdcas.partyschool.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
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
     * @return
     */
    public static Map<Integer, List<String>> getDataMap(File excelFile, int colNum) {
        Map<Integer, List<String>> map = null;

        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(excelFile));
            HSSFSheet sheet = workbook.getSheetAt(0);
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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

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
    private HSSFCellStyle createHSSFCellStyle(HSSFWorkbook workbook, String fontName, int fontSize, boolean fontBold,
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
