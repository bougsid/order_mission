package com.bougsid.printers;

import com.bougsid.MSG;
import com.bougsid.employe.Employe;
import com.bougsid.employe.IEmployeService;
import com.bougsid.mission.IMissionService;
import com.bougsid.mission.Mission;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * Created by ayoub on 7/26/2016.
 */
@Component
public class OrderVirementPrinter {
    @Autowired
    private MSG msg;
    @Autowired
    private IEmployeService employeService;
    @Autowired
    private IMissionService missionService;
    private XSSFSheet sheet;
    private int startRow = 6;
    private String day = "B1";
    private String hour = "B2";
    private double total;
    private XSSFCellStyle style;

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
            style = wb.createCellStyle();
            style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            style.setBorderTop(XSSFCellStyle.BORDER_THIN);
            style.setBorderRight(XSSFCellStyle.BORDER_THIN);
            style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            sheet = wb.getSheetAt(0);
            //iterating r number of rows
            int i = 0;
            System.out.println("mission size = " + missions.size());
            this.setCellValue(day, LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            this.setCellValue(hour, LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            for (Mission mission : missions) {
                System.out.println("Ok" + i);
                System.out.println("last row num" + sheet.getLastRowNum());
                this.fillRowWithMission(startRow + i, mission);
                this.missionService.validateMission(mission);
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

    private void fillRowWithMission(int indexRow, Mission mission) {
        Employe employe = mission.getEmploye();
        double montant = this.employeService.updateEmployeAvoir(employe, mission.getDecompte().getTotal());
        if (montant == 0.0) return;
        sheet.shiftRows(indexRow, sheet.getLastRowNum(), 1);
        XSSFRow row = sheet.createRow(indexRow);
        XSSFCell c0 = row.createCell(0);
        c0.setCellValue(employe.getMatricule());
        c0.setCellStyle(style);
        XSSFCell c1 = row.createCell(1);
        c1.setCellValue(employe.getNom());
        c1.setCellStyle(style);
        XSSFCell c2 = row.createCell(2);
        c2.setCellValue(employe.getPrenom());
        c2.setCellStyle(style);
        XSSFCell c3 = row.createCell(3);
        c3.setCellValue(montant);
        c3.setCellStyle(style);
        XSSFCell c4 = row.createCell(4);
        c4.setCellValue(employe.getRib());
        c4.setCellStyle(style);
        XSSFCell c5 = row.createCell(5);
        c5.setCellValue(employe.getAgence().getFullName());
        c5.setCellStyle(style);
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
