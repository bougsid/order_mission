package com.bougsid.printers;

import com.bougsid.MSG;
import com.bougsid.employe.Employe;
import com.bougsid.mission.Mission;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * Created by ayoub on 7/26/2016.
 */
@Component
public class OrderVirementPrinter {
    @Autowired
    private MSG msg;
    private XSSFSheet sheet;
    private int startRow = 6;
    private double total;

    public String printOrderVirement(List<Mission> missions) {
        this.total = 0;
        String fileName = UUID.randomUUID().toString();
        File downloadDir = new File(msg.getMessage("application.mission.downloaddir"));
        if (!downloadDir.exists()) {
            downloadDir.mkdir();
        }
        InputStream inp = null;
        try {
            inp = new FileInputStream(downloadDir.getPath() + "/modelOV.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(inp);
            sheet = wb.getSheetAt(0);
            //iterating r number of rows
            int i = 0;
            System.out.println("mission size = "+missions.size());
            for (Mission mission : missions) {
                System.out.println("Ok"+i);
                System.out.println("last row num"+sheet.getLastRowNum());
                sheet.shiftRows(startRow + i, sheet.getLastRowNum(), 1);
                XSSFRow row = sheet.createRow(startRow + i);
                this.fillRowWithMission(row, mission);
                i++;
            }

//Set Tortal
            XSSFRow row = sheet.createRow(startRow + i);
            row.createCell(3).setCellValue(this.total);
            FileOutputStream fileOut = new FileOutputStream(downloadDir.getPath() + "/" + fileName + ".xlsx");
            //write this workbook to an Outputstream.
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    private void fillRowWithMission(XSSFRow row, Mission mission) {
        Employe employe = mission.getEmploye();
        double montant = mission.getDecompte().getTotal();
        row.createCell(0).setCellValue(employe.getMatricule());
        row.createCell(1).setCellValue(employe.getNom());
        row.createCell(2).setCellValue(employe.getPrenom());
        row.createCell(3).setCellValue(montant);
        row.createCell(4).setCellValue(employe.getRib());
        row.createCell(5).setCellValue(employe.getBank().getName());
        this.total += montant;
    }

    private double getCellValue(String ref) {
        CellReference cr = new CellReference(ref);
        Cell cell = sheet.getRow(cr.getRow()).getCell(cr.getCol());
        return cell.getNumericCellValue();
    }

    private void setCellValue(String ref, String value) {
        CellReference cr = new CellReference(ref);
        Cell cell = sheet.getRow(cr.getRow()).getCell(cr.getCol());
        cell.setCellValue(value);
    }

    private void setCellValue(String ref, int value) {
        CellReference cr = new CellReference(ref);
        Cell cell = sheet.getRow(cr.getRow()).getCell(cr.getCol());
        cell.setCellValue(value);
    }

    private void setCellValue(String ref, double value) {
        CellReference cr = new CellReference(ref);
        Cell cell = sheet.getRow(cr.getRow()).getCell(cr.getCol());
        cell.setCellValue(value);
    }
}
