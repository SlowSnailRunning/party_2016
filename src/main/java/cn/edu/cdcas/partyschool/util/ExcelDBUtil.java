package cn.edu.cdcas.partyschool.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

/**
 * the util class packaging the common methods to import and export excel.
 * @author Char Jin(Jin zhichao)
 * @date 2018-07-12
 */

public class ExcelDBUtil {
    private DBUtil dbUtil = new DBUtil();

    public ExcelDBUtil() {
    }

    /**`
     * export data of the ResultSet Object into excel from database.
     *
     * @param rs
     * @param objectFile
     */
    public void exportoExcel(ResultSet rs, String[] columnHeader, File objectFile) {
        try {
            ResultSetMetaData meta = rs.getMetaData();
            int rowCount = 0, columnCount = meta.getColumnCount();

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Info");
            HSSFRow header = sheet.createRow(rowCount++);
            sheet.setDefaultColumnWidth(14);

            if (columnHeader != null) {
                for (int i = 0; i < columnCount; i++) {
                    HSSFCell cell = header.createCell(i);
                    cell.setCellValue(columnHeader[i]);
                    cell.setCellStyle(this.createHSSFCellStyle(workbook, "宋体", 10, true, BorderStyle.THIN));
                }
            } else {
                for (int i = 0; i < columnCount; i++) {
                    HSSFCell cell = header.createCell(i);
                    cell.setCellValue(meta.getColumnLabel(i + 1));
                    cell.setCellStyle(this.createHSSFCellStyle(workbook, "宋体", 10, true, BorderStyle.THIN));
                }

            }

            HSSFCellStyle contentCellStyle = this.createHSSFCellStyle(workbook, "宋体", 10, false, BorderStyle.THIN);
            while (rs.next()) {
                HSSFRow row = sheet.createRow(rowCount++);
                for (int colNum = 0; colNum < columnCount; colNum++) {
                    HSSFCell cell = row.createCell(colNum);
                    cell.setCellValue(rs.getString(colNum + 1));
                    cell.setCellStyle(contentCellStyle);
                }
            }

            FileOutputStream fileOutput = new FileOutputStream(objectFile);
            workbook.write(fileOutput);

            rs.close();
            fileOutput.close();
            workbook.close();
        } catch (SQLException | IOException e) {
            System.out.println("Errors!" + e.getMessage());
        }
    }

    /**
     * export table provided by user into excel from database. The columnHeader
     * refers to the shown the header of column. If columnHeader is null,then it is
     * obtained from database table.
     *
     * @param tableName
     * @param columnHeader
     * @param objectFile
     */
    public void exportoExcel(String tableName, String[] columnHeader, File objectFile) {
        try {
            Map<Integer, List<String>> map = this.getDataMap(tableName, columnHeader);
            List<String> header_list = map.get(-1);
            int rowCount = map.size() - 1, columnCount = header_list.size();

            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Info");
            HSSFRow header = sheet.createRow(0);
            sheet.setDefaultColumnWidth(14);

            for (int i = 0; i < columnCount; i++) {
                HSSFCell cell = header.createCell(i);
                cell.setCellValue(header_list.get(i));
                cell.setCellStyle(this.createHSSFCellStyle(workbook, "宋体", 10, true, BorderStyle.THIN));
            }

            HSSFCellStyle contentCellStyle = this.createHSSFCellStyle(workbook, "宋体", 10, false, BorderStyle.THIN);
            for (int rowNum = 0; rowNum < rowCount; rowNum++) {
                HSSFRow row = sheet.createRow(rowNum + 1);
                List<String> row_list = map.get(rowNum);
                for (int colNum = 0; colNum < columnCount; colNum++) {
                    HSSFCell cell = row.createCell(colNum);
                    cell.setCellValue(row_list.get(colNum));
                    cell.setCellStyle(contentCellStyle);
                }
            }

            FileOutputStream fileOutput = new FileOutputStream(objectFile);
            workbook.write(fileOutput);
            fileOutput.close();
            workbook.close();
        } catch (IOException e) {
            System.out.println("Errors!" + e.getMessage());
        }
    }

    /**
     * import excel into the corresponding table in database.
     *
     * @param tableName
     * @param file
     */
    public void importoDB(String tableName, File file) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(file));
            HSSFSheet sheet = workbook.getSheetAt(0); // get the first sheet in workbook.
            Iterator<Row> rowIterator = sheet.iterator();
            int columnCount = rowIterator.next().getLastCellNum();
            String[] strRow = new String[columnCount];

            // generate update sql
            String sql = dbUtil.generateInsertSql(tableName, columnCount);

            dbUtil.update("delete from " + tableName, null); // clear table before importing into db.
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.iterator();
                int i = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    cell.setCellType(CellType.STRING);
                    strRow[i++] = cell.getStringCellValue();
                }
                dbUtil.update(sql, strRow);
            }
            dbUtil.closeAll();
            workbook.close();
        } catch (IOException e) {
            System.out.println("Errors!" + e.getMessage());
        } finally {
            dbUtil.closeAll();
        }
    }

    /**
     * transform the data of excel into map. And the list,key of which equals
     * -1,stores the header of column.
     *
     * @return
     */
    public static Map<Integer, List<String>> getDataMap(File excelFile) {
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
     * transform the corresponding table of database into map. And the list,key of
     * which equals -1,stores the header of column.And if the array of columnHeader
     * is null,the header of column will be obtained from the database.
     *
     * @param tableName the name of table from which you'd get its map.
     * @param columnHeader thd header of column you'd be shown.
     * @return
     */
    public Map<Integer, List<String>> getDataMap(String tableName, String[] columnHeader) {
        Map<Integer, List<String>> map = new HashMap<>();
        try {
            ResultSet rs = dbUtil.query("select * from " + tableName, null);
            ResultSetMetaData meta = rs.getMetaData();

            int rowCount = 0;
            int columnCount = meta.getColumnCount();

            List<String> header = new ArrayList<>();
            if (columnHeader == null) {
                for (int i = 0; i < columnCount; i++)
                    // get column name from database
                    header.add(i, meta.getColumnLabel(i + 1));
            } else {
                // if parameter "columnHeader" is not null,then use it as the column header
                for (int i = 0; i < columnHeader.length; i++) {
                    header.add(i, columnHeader[i]);
                }
            }
            map.put(-1, header);
            while (rs.next()) {
                List<String> row = new ArrayList<>();
                for (int colNum = 0; colNum < columnCount; colNum++) {
                    row.add(colNum, rs.getString(colNum + 1));
                }
                map.put(rowCount++, row);
            }
            dbUtil.closeAll();
        } catch (SQLException e) {
            System.out.println("Error! " + e.getMessage());
        }
        return map;

    }

    public static void main(String[] args) {
        new ExcelDBUtil().exportoExcel("rm_user", null, new File("D:/a.xls"));
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
