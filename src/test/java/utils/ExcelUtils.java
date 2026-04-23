package utils;

import org.apache.poi.xssf.usermodel.*;

import java.io.FileInputStream;
import java.util.*;

public class ExcelUtils {

    public static List<Map<String, String>> getTestData(String sheetName) {

        List<Map<String, String>> data = new ArrayList<>();

        try {
            FileInputStream file = new FileInputStream("src/test/resources/testdata/data.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet(sheetName);

            XSSFRow header = sheet.getRow(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {

                XSSFRow row = sheet.getRow(i);
                Map<String, String> map = new HashMap<>();

               for (int j = 0; j < header.getLastCellNum(); j++) {

    String key = header.getCell(j).toString();

    String value = "";
    if (row.getCell(j) != null) {
        value = row.getCell(j).toString();
    }

    map.put(key, value);
}

                data.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }
}