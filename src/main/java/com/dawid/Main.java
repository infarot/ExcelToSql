package com.dawid;

import com.dawid.dao.ResultDAOImpl;
import com.dawid.dao.ShiftProductionDAO;
import com.dawid.dao.ShiftProductionDAOImpl;
import com.dawid.entity.Result;
import com.dawid.entity.ShiftProduction;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.*;
import java.nio.file.Files;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    private static Properties prop = new Properties();
    private static SessionFactory factory;

    static {
        try {
            prop.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized SessionFactory getFactory() {
        if (factory == null) {
            factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Result.class)
                    .addAnnotatedClass(ShiftProduction.class)
                    .buildSessionFactory();
            return factory;
        } else {
            return factory;
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            File source1 = new File(prop.getProperty("firstShiftPath"));
            File dest1 = new File("temp1.xlsm");
            copyFile(source1, dest1);
            executorService.submit(() -> {
                try {
                    System.out.println("first thread!");
                    getExcelData(dest1);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });


            File source2 = new File(prop.getProperty("secondShiftPath"));
            File dest2 = new File("temp2.xlsm");
            copyFile(source2, dest2);
            executorService.submit(() -> {
                try {
                    System.out.println("second thread!");
                    getExcelData(dest2);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
            executorService.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void getExcelData(File file) throws FileNotFoundException {
        Optional<List<Result>> results = calculateShiftResult(new FileInputStream(file));
        results.ifPresent(Main::saveResultToDB);
        Optional<List<ShiftProduction>> shiftProductionResults = calculateShiftProduction(new FileInputStream(file));
        shiftProductionResults.ifPresent(Main::saveProductionToDB);
        file.delete();
    }

    private static Optional<List<Result>> calculateShiftResult(FileInputStream file) {
        try {
            BufferedInputStream reader = new BufferedInputStream(file);
            Workbook workbook = new XSSFWorkbook(reader);
            Sheet sheet = workbook.getSheetAt(workbook.getSheetIndex(prop.getProperty("resultDataSourceSheet")));
            List<Result> resultList = new ArrayList<>();
            if (sheet.getLastRowNum() != 0) {
                for (Row row : sheet) {
                    resultList.add(new Result(
                            row.getCell(0).getNumericCellValue(),
                            row.getCell(1).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1),
                            (int) row.getCell(2).getNumericCellValue(),
                            row.getCell(3).getStringCellValue().charAt(0),
                            row.getCell(4).getStringCellValue()));
                }
            }
            file.close();
            return Optional.of(resultList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private static Optional<List<ShiftProduction>> calculateShiftProduction(FileInputStream file) {
        try {
            BufferedInputStream reader = new BufferedInputStream(file);
            Workbook workbook = new XSSFWorkbook(reader);
            Sheet sheet = workbook.getSheetAt(workbook.getSheetIndex(prop.getProperty("productionDataSourceSheet")));
            List<ShiftProduction> shiftProductionList = new ArrayList<>();
            if (sheet.getLastRowNum() != 0) {
                for (Row row : sheet) {
                    shiftProductionList.add(new ShiftProduction(
                            row.getCell(0).getStringCellValue(),
                            row.getCell(1).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1),
                            row.getCell(2).getStringCellValue().charAt(0),
                            row.getCell(3).getNumericCellValue(),
                            row.getCell(4).getNumericCellValue(),
                            row.getCell(5).getNumericCellValue(),
                            row.getCell(6).getNumericCellValue(),
                            row.getCell(8).getNumericCellValue(),
                            row.getCell(7).getNumericCellValue()));
                }
            }
            return Optional.of(shiftProductionList);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

    private static synchronized void saveResultToDB(List<Result> list){
        System.out.println("adding to db...");
        ResultDAOImpl resultDAO = new ResultDAOImpl();
        list.forEach(resultDAO::saveOrUpdate);
    }

    private static synchronized void saveProductionToDB(List<ShiftProduction> list){
        System.out.println("adding to db...");
        ShiftProductionDAO shiftProductionDAO = new ShiftProductionDAOImpl();
        list.forEach(shiftProductionDAO::saveOrUpdate);
    }

    private static void copyFile(File source, File dest) throws IOException {
        if (dest.exists()) {
            dest.delete();
        }
        Files.copy(source.toPath(), dest.toPath());
    }
}
