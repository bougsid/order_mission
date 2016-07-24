package com.bougsid.decompte.generatedecompte;

import com.bougsid.MSG;
import com.bougsid.decompte.Decompte;
import com.bougsid.employe.Employe;
import com.bougsid.mission.Mission;
import com.bougsid.taux.ITauxService;
import com.bougsid.taux.Taux;
import com.bougsid.transport.TransportType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.format.DateTimeFormatter;

/**
 * Created by ayoub on 7/19/2016.
 */
@Component
public class Excel {
    @Autowired
    private ITauxService tauxService;
    @Autowired
    private MSG msg;
    private Decompte decompte;
    private Mission mission;
    private final String nom = "C10";
    private final String matricule = "H10";
    private final String idMission = "C12";
    private final String depart = "E12";
    private final String destination = "G12";
    private final String startDate = "C14";
    private final String startHour = "E14";
    private final String endDate = "G14";
    private final String endHour = "J14";
    private final String transportType = "C16";
    private final String distance = "H16";
    private final String distanceAjouter = "I16";
    private final String tauxDejounerDiner = "C18";
    private final String tauxPetitDejouner = "H18";
    private final String tauxHebergement = "C20";
    private final String tauxKilometrique = "H20";
    private final String petitDejouner = "C25";
    private final String dejounerDiner = "C27";
    private final String hebergement = "C31";
    private final String total = "E34";
    private final String totalOnWords = "D38";
    private final String tickAuto = "F27";
    private final String nombreTickAuto = "G27";
    private final String tauxAuto = "H27";
    private final String imputation = "D40";
    private final String exercice = "D42";
    private final String totalTransportType = "J25";

    private Sheet sheet;

    public void generateExcel(Decompte decompte) {
        this.decompte = decompte;
        this.mission = decompte.getMission();
        File downloadDir = new File(msg.getMessage("application.mission.downloaddir"));
        if (!downloadDir.exists()) {
            downloadDir.mkdir();
        }
        InputStream inp = null;
        try {
            inp = new FileInputStream(downloadDir.getPath() + "/model.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(inp);
            sheet = wb.getSheetAt(0);
            //Write Content
            this.setHeaderInformations();
            if (this.mission.getTransportType() == TransportType.PERSONNEL) {
                this.fillAuto();
            } else if (mission.getTransportType() == TransportType.TRAIN
                    || mission.getTransportType() == TransportType.CTM
                    || mission.getTransportType() == TransportType.AVION) {
                this.fillTaxi();
            }
            this.fillSejour();
            XSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
            this.fillTotalWord();
            this.setFooterInformation();
            //Generate Output
            FileOutputStream fileOut = new FileOutputStream(downloadDir.getPath() + "/" + mission.getUuid() + ".xlsx");
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHeaderInformations() {
        Employe employ = mission.getEmploye();
        Taux taux = this.tauxService.getTaux();
        this.setCellValue(nom, employ.getFullName());
        this.setCellValue(matricule, employ.getMatricule());
        this.setCellValue(idMission, mission.getIdMission());
        this.setCellValue(depart, this.decompte.getDepart());
        this.setCellValue(destination, mission.getStringfyVille());
        this.setCellValue(startDate, mission.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.setCellValue(startHour, mission.getStartDate().format(DateTimeFormatter.ofPattern("HH:mm")));
        this.setCellValue(endDate, mission.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.setCellValue(endHour, mission.getEndDate().format(DateTimeFormatter.ofPattern("HH:mm")));
        this.setCellValue(transportType, mission.getTransportType().getLabel());
        this.setCellValue(distanceAjouter, this.decompte.getDistanceAjouter());//TODO
        this.setCellValue(distance, this.decompte.getDistance());

        this.setCellValue(tauxDejounerDiner, this.decompte.getTauxDejounerDiner());
        this.setCellValue(tauxPetitDejouner, this.decompte.getTauxPetitDejouner());
        this.setCellValue(tauxHebergement, this.decompte.getTauxHebergement());
        this.setCellValue(tauxKilometrique, this.decompte.getTauxKilometrique());


    }


    private void fillSejour() {
        this.setCellValue(this.petitDejouner, this.decompte.getPetitDejouner());
        this.setCellValue(this.dejounerDiner, this.decompte.getDejounerDiner());
        this.setCellValue(this.hebergement, this.decompte.getHebergement());
    }

    private void fillAuto() {
        this.setCellValue(tickAuto, msg.getMessage("decompte.tickAuto"));
        this.setCellValue(nombreTickAuto, this.decompte.getNombreTickAuto());// TODO
        this.setCellValue(tauxAuto, this.decompte.getTauxAuto());
    }

    private void fillTaxi() {
        this.setCellValue(tickAuto, msg.getMessage("decompte.taxi"));
        this.setCellValue(nombreTickAuto, this.decompte.getDays());// TODO
        this.setCellValue(tauxAuto, this.decompte.getTauxTaxi());
    }

    private void setFooterInformation() {
        this.setCellValue(imputation, this.decompte.getImputation());
        this.setCellValue(exercice, this.decompte.getExercice());
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

    private void fillTotalWord() {
        double totalPrice = getCellValue(total);
        if (Math.ceil(totalPrice) < totalPrice + 0.001) {
            totalPrice = Math.ceil(totalPrice);
        }
        System.out.println("Total =" + totalPrice);
        long entier = (long) Math.floor(totalPrice);
        long decimal = (long) Math.floor((totalPrice - entier) * 100.0d);
        String res = NumberToWord.convert(entier);
        if (decimal != 0) {
            res += " " + decimal + " cts";
        }
        //Captlize first letter
        res = res.substring(0, 1).toUpperCase() + res.substring(1)+ " DH.";
        System.out.println(res);
        this.setCellValue(totalOnWords, res);
    }


}
