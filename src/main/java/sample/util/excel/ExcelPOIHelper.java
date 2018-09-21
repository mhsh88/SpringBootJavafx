package sample.util.excel;


import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ExcelPOIHelper {

    public Map<Integer, List<MyCell>> readExcel(String fileLocation) throws IOException {

        Map<Integer, List<MyCell>> data = new HashMap<>();
        FileInputStream fis = new FileInputStream(new File(fileLocation));

        if (fileLocation.endsWith(".xls")) {
            data = readHSSFWorkbook(fis);
        } else if (fileLocation.endsWith(".xlsx")) {
            data = readXSSFWorkbook(fis);
        } else if (fileLocation.endsWith(".xlsm")) {
            data = readXSSFWorkbook(fis);
        }

        int maxNrCols = data.values().stream()
                .mapToInt(List::size)
                .max()
                .orElse(0);

        data.values().stream()
                .filter(ls -> ls.size() < maxNrCols)
                .forEach(ls -> {
                    IntStream.range(ls.size(), maxNrCols)
                            .forEach(i -> ls.add(new MyCell("")));
                });

        return data;
    }

    private String readCellContent(Cell cell) {
//        CellValue cell = evaluator.evaluate(cell);
        String content = "";
        switch (cell.getCellTypeEnum()) {
            case STRING:
                content = cell.getStringCellValue();
                break;
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    content = cell.getDateCellValue() + "";
                } else {
                    content = cell.getNumericCellValue() + "";
                }
                break;
            case BOOLEAN:
                content = cell.getBooleanCellValue() + "";
                break;
            default:
                content = "";
        }
        return content;
    }

    private Map<Integer, List<MyCell>> readHSSFWorkbook(FileInputStream fis) throws IOException {
        Map<Integer, List<MyCell>> data = new HashMap<>();
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(fis);

            HSSFSheet sheet = workbook.getSheetAt(0);
            for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
                HSSFRow row = sheet.getRow(i);
                data.put(i, new ArrayList<>());
                if (row != null) {
                    for (int j = 0; j < row.getLastCellNum(); j++) {
                        HSSFCell cell = row.getCell(j);
                        if (cell != null) {
                            HSSFCellStyle cellStyle = cell.getCellStyle();

                            MyCell myCell = new MyCell();

                            HSSFColor bgColor = cellStyle.getFillForegroundColorColor();
                            if (bgColor != null) {
                                short[] rgbColor = bgColor.getTriplet();
                                myCell.setBgColor("rgb(" + rgbColor[0] + "," + rgbColor[1] + "," + rgbColor[2] + ")");
                            }
                            HSSFFont font = cell.getCellStyle()
                                    .getFont(workbook);
                            myCell.setTextSize(font.getFontHeightInPoints() + "");

                            HSSFColor textColor = font.getHSSFColor(workbook);
                            if (textColor != null) {
                                short[] rgbColor = textColor.getTriplet();
                                myCell.setTextColor("rgb(" + rgbColor[0] + "," + rgbColor[1] + "," + rgbColor[2] + ")");
                            }
                            myCell.setContent(readCellContent(cell));
                            data.get(i)
                                    .add(myCell);
                        } else {
                            data.get(i)
                                    .add(new MyCell(""));
                        }
                    }
                }
            }
        } finally {
            if (workbook != null) {
                try (OutputStream out = new FileOutputStream("temp.xlsx")) {
                    workbook.write(out);
                    out.close();
                }
//                workbook.close();

            }
        }
        return data;
    }

    private Map<Integer, List<MyCell>> readXSSFWorkbook(FileInputStream fis) throws IOException {
        XSSFWorkbook workbook = null;
        Map<Integer, List<MyCell>> data = new HashMap<>();
        try {

            workbook = new XSSFWorkbook(fis);

            int m = 0;
            for(int k = 0; k < workbook.getNumberOfSheets(); k++) {

                XSSFSheet sheet = workbook.getSheetAt(k);

                for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
                    XSSFRow row = sheet.getRow(i);
                    data.put(m, new ArrayList<>());
                    if (row != null) {
                        for (int j = 0; j < row.getLastCellNum(); j++) {
                            XSSFCell cell = row.getCell(j);
                            if (cell != null) {
                                XSSFCellStyle cellStyle = cell.getCellStyle();
                                MyCell myCell = new MyCell();
                                myCell.setContent(readCellContent(cell));
                                data.get(m)
                                        .add(myCell);
                            } else {
                                data.get(m)
                                        .add(new MyCell(""));
                            }
                        }
                        m++;
                    }
                }
            }
        } finally {
            if (workbook != null) {
                workbook.close();
            }
        }
        return data;
    }

}
