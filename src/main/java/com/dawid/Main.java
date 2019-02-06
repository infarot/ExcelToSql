package com.dawid;

import com.dawid.dao.ResultDAOImpl;
import com.dawid.entity.Result;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main {
    private static Properties prop = new Properties();
    public static SessionFactory factory;
    static {
        try {
            factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Result.class)
                    .buildSessionFactory();

            prop.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            File source1 = new File(prop.getProperty("firstShiftPath"));
            File dest1 = new File("temp1.xlsm");
            File source2 = new File(prop.getProperty("secondShiftPath"));
            File dest2 = new File("temp2.xlsm");
            copyFile(source1, dest1);
            copyFile(source2, dest2);

            new Main().addShiftResult(new FileInputStream(dest1));
            new Main().addShiftResult(new FileInputStream(dest2));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addShiftResult(FileInputStream file) {
        try {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(workbook.getSheetIndex(prop.getProperty("dataSourceSheet")));
            List<Result> resultList = new ArrayList<>();
            if (sheet.getLastRowNum() != 0) {
                for (Row row : sheet) {
                    resultList.add(new Result(
                            row.getCell(0).getNumericCellValue(),
                            LocalDate.ofInstant(row.getCell(1).getDateCellValue().toInstant(), ZoneId.systemDefault()).plusDays(1),
                            (int) row.getCell(2).getNumericCellValue(),
                            row.getCell(3).getStringCellValue().charAt(0),
                            row.getCell(4).getStringCellValue()));
                }
                ResultDAOImpl resultDAO = new ResultDAOImpl();
                resultList.forEach(resultDAO::saveOrUpdate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(File source, File dest) throws IOException {
        if (dest.exists()) {
            dest.delete();
        }
        Files.copy(source.toPath(), dest.toPath());
    }
}
