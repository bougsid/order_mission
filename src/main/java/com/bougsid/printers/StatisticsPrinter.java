package com.bougsid.printers;

import com.bougsid.MSG;
import com.bougsid.employe.Employe;
import com.bougsid.employe.IEmployeService;
import com.bougsid.entreprise.Entreprise;
import com.bougsid.mission.IMissionService;
import com.bougsid.mission.Mission;
import com.bougsid.missiontype.MissionType;
import com.bougsid.service.Dept;
import com.bougsid.statistics.DateType;
import com.bougsid.statistics.StatisticsType;
import com.bougsid.ville.Ville;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.*;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumn;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableColumns;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTableStyleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ayoub on 7/26/2016.
 */
@Component
public class StatisticsPrinter {
    @Autowired
    private MSG msg;
    @Autowired
    private IEmployeService employeService;
    @Autowired
    private IMissionService missionService;
    private XSSFSheet sheet;
    private int startRow = 5;
    private String start = "E2";
    private String end = "F2";
    private String type = "E3";
    private String filter = "F3";
    private double total;
    private XSSFCellStyle style;
    private Workbook wb;
    private File downloadDir;
    private String fileName;

    public String printStatistics(List<Mission> missions, StatisticsType filter, DateType date) {
        System.out.println("Print1");
        this.createWB();
        if (date != DateType.ALL) {
            this.setCellValue(start, msg.getMessage("statistics.start") + " " + date.getStart().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            this.setCellValue(end, msg.getMessage("statistics.end") + date.getEnd().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } else {
            this.setCellValue(start, msg.getMessage("statistics.begin"));
            this.setCellValue(end, msg.getMessage("statistics.finish") + " " + date.getEnd().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        if (filter != StatisticsType.ALL)
            this.setCellValue(type, filter.getLabel());

        switch (filter) {
            case TYPE: {
                MissionType type = (MissionType) filter.getCriteria();
                this.setCellValue(this.filter, type.getLabel());
            }
            break;
            case ENTREPRISE: {
                Entreprise entreprise = (Entreprise) filter.getCriteria();
                this.setCellValue(this.filter, entreprise.getNom());
            }
            break;
            case SERVICE: {
                Dept dept = (Dept) filter.getCriteria();
                this.setCellValue(this.filter, dept.getNom());
            }
            break;
            case VILLE: {
                Ville ville = (Ville) filter.getCriteria();
                this.setCellValue(this.filter, ville.getNom());
            }
            break;
        }
        return this.printStatistics(missions);
    }

    public String printStatistics(List<Mission> missions) {
        if (wb == null) this.createWB();
        this.total = 0;
        fileName = UUID.randomUUID().toString();
        downloadDir = new File(msg.getMessage("application.mission.downloaddir"));
        if (!downloadDir.exists()) {
            downloadDir.mkdir();
        }
        XSSFTable table = sheet.createTable();
        table.setDisplayName("bougsid.com");
        CTTable cttable = table.getCTTable();

        //Style configurations
        CTTableStyleInfo style = cttable.addNewTableStyleInfo();
        style.setName("TableStyleMedium2");
        style.setShowColumnStripes(true);
        style.setShowRowStripes(true);

        //Set which area the table should be placed in
        AreaReference reference = new AreaReference(new CellReference(4, 0),
                new CellReference(missions.size() + 5, 6));
        cttable.setRef(reference.formatAsString());
        cttable.setId(1);
        cttable.setName("bougsid.com");

        cttable.setTotalsRowCount(missions.size() + 1);
        CTTableColumns columns = cttable.addNewTableColumns();
        this.createColumns(columns);

        //fill data
        int i = startRow;
        for (Mission mission : missions) {
            this.fillRowWithMission(i, mission);
            i++;
        }
        //Set Tortal
        XSSFRow row = sheet.createRow(i);
        row.createCell(5).setCellValue(msg.getMessage("statistics.total"));
        row.createCell(6).setCellValue(this.total);
        this.autoResizeColumns();
        this.closeWB();
        return fileName;
    }

    private void autoResizeColumns() {
        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void createColumns(CTTableColumns columns) {
        columns.setCount(7);
        CTTableColumn column;
        List<String> cols = new ArrayList<>();
        cols.add(msg.getMessage("statistics.column.matricule"));
        cols.add(msg.getMessage("statistics.column.nom"));
        cols.add(msg.getMessage("statistics.column.prenom"));
        cols.add(msg.getMessage("statistics.column.objet"));
        cols.add(msg.getMessage("statistics.column.startDate"));
        cols.add(msg.getMessage("statistics.column.endDate"));
        cols.add(msg.getMessage("statistics.column.montant"));
        int i = 0;
        XSSFRow row = sheet.createRow(startRow - 1);
        for (String col : cols) {
            column = columns.addNewTableColumn();
            System.out.println("col=" + col);
            column.setName(col);
            column.setId(i + 1);
            row.createCell(i).setCellValue(col);
            i++;
        }
    }

    private void createWB() {
        wb = new XSSFWorkbook();
        sheet = (XSSFSheet) wb.createSheet();
    }

    public void closeWB() {
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(downloadDir.getPath() + "/" + fileName + ".xlsx");
            //write this workbook to an Outputstream.
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillRowWithMission(int indexRow, Mission mission) {
        Employe employe = mission.getEmploye();
        double montant = mission.getDecompte().getTotal();
//        double montant = this.employeService.updateEmployeAvoir(employe, mission.getDecompte().getTotal());
//        if (montant == 0.0) return;
//        sheet.shiftRows(indexRow, sheet.getLastRowNum(), 1);
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

        XSSFCell c4 = row.createCell(3);
        c4.setCellValue(mission.getObjet());
        c4.setCellStyle(style);
        XSSFCell c5 = row.createCell(4);
        c5.setCellValue(mission.getStartDate().format(DateTimeFormatter.ofPattern(msg.getMessage("date.pattern"))));
        c5.setCellStyle(style);
        XSSFCell c6 = row.createCell(5);
        c6.setCellValue(mission.getEndDate().format(DateTimeFormatter.ofPattern(msg.getMessage("date.pattern"))));
        c6.setCellStyle(style);
        XSSFCell c3 = row.createCell(6);
        c3.setCellValue(montant);
        c3.setCellStyle(style);
        this.total += montant;
    }

    private double getCellValue(String ref) {
        CellReference cr = new CellReference(ref);
        Cell cell = sheet.getRow(cr.getRow()).getCell(cr.getCol());
        return cell.getNumericCellValue();
    }

    private void setCellValue(String ref, String value) {
        CellReference cr = new CellReference(ref);
        XSSFRow row = sheet.getRow(cr.getRow());
        XSSFCell cell;
        if (row == null) {
            row = sheet.createRow(cr.getRow());
        }
        cell = row.createCell(cr.getCol());
//        Cell cell = sheet.getRow(cr.getRow()).getCell(cr.getCol());
//        Cell cell = sheet.createRow(cr.getRow()).createCell(cr.getCol());
        System.out.printf(cr.getRow() + ";" + cr.getCol() + ";" + value);
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
