package com.management.yuvro.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

public class AppUtils {
    public static String getPercentage(double value, double total) {
        if (total == 0) {
            return "0%";
        }
        double percentage = (value / total) * 100;
        return String.format("%.2f%%", percentage);
    }

    public static <T> ByteArrayInputStream generateExcel(List<T> dataList) throws IOException {
        if (dataList == null || dataList.isEmpty()) {
            throw new IllegalArgumentException("No data to generate excel file.");
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Sheet1");

            // Get all fields from the class
            Class<?> clazz = dataList.get(0).getClass();
            Field[] fields = clazz.getDeclaredFields();

            // Create Header Row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                headerRow.createCell(i).setCellValue(fields[i].getName());
            }

            // Create Data Rows
            int rowIdx = 1;
            for (T item : dataList) {
                Row row = sheet.createRow(rowIdx++);
                for (int i = 0; i < fields.length; i++) {
                    Object value = fields[i].get(item);
                    row.createCell(i).setCellValue(value != null ? value.toString() : "");
                }
            }

            // Auto-size columns
            for (int i = 0; i < fields.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access fields", e);
        }
    }
}
