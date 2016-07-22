package com.bougsid.mission.decompte;

import com.bougsid.MSG;
import com.bougsid.employe.Employe;
import com.bougsid.mission.Mission;
import com.bougsid.taux.ITauxService;
import com.bougsid.taux.Taux;
import com.bougsid.transport.TransportType;
import com.bougsid.ville.Ville;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by ayoub on 7/19/2016.
 */
@Component
public class Excel {
    @Autowired
    private ITauxService tauxService;
    @Autowired
    private MSG msg;
    private Mission mission;
    private final String nom = "C10";
    private final String matricule = "H10";
    private final String idMission = "C12";
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
    private final String tauxTickAuto = "H27";

    private Sheet sheet;

    public void generateExcel(Mission mission) {
        this.mission = mission;
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
            this.fillSejour(mission.getStartDate(), mission.getEndDate());
            XSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
            this.fillTotalWord();
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
        this.setCellValue(destination, this.stringfyVille(mission.getVilles()));
        this.setCellValue(startDate, mission.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.setCellValue(startHour, mission.getStartDate().format(DateTimeFormatter.ofPattern("HH:mm")));
        this.setCellValue(endDate, mission.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        this.setCellValue(endHour, mission.getEndDate().format(DateTimeFormatter.ofPattern("HH:mm")));
        this.setCellValue(transportType, mission.getTransportType().getLabel());
        this.setCellValue(distanceAjouter, 0);//TODO
        if (mission.getVilles().size() == 1) {
            this.setCellValue(distance, mission.getVilles().get(0).getDistance() * 2);
        }
        if (employ.isDerictor()) {
            this.setCellValue(tauxDejounerDiner, taux.getTauxDejounerDinerDirec());
            this.setCellValue(tauxPetitDejouner, taux.getTauxPetitDejounerDirec());
            this.setCellValue(tauxHebergement, taux.getTauxHebergementDirec());
            this.setCellValue(tauxKilometrique, taux.getTauxKilometriqueDirec());
        } else {
            this.setCellValue(tauxDejounerDiner, taux.getTauxDejounerDiner());
            this.setCellValue(tauxPetitDejouner, taux.getTauxPetitDejouner());
            this.setCellValue(tauxHebergement, taux.getTauxHebergement());
            this.setCellValue(tauxKilometrique, taux.getTauxKilometrique());

            if (mission.getTransportType() == TransportType.PERSONNEL) {
                this.setCellValue(tickAuto, msg.getMessage("decompte.tickAuto"));
                this.setCellValue(nombreTickAuto, 2);// TODO
                if (mission.getVilles().size() == 1) {
                    this.setCellValue(tauxTickAuto, mission.getVilles().get(0).getTauxAuto());
                }
            }
        }
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
        if(Math.ceil(totalPrice) < totalPrice+0.001){
            totalPrice = Math.ceil(totalPrice);
        }
        System.out.println("Total ="+totalPrice);
        long entier = (long) Math.floor(totalPrice);
        long decimal = (long) Math.floor((totalPrice - entier) * 100.0d);
        String res = NumberToWord.convert(entier);
        if (decimal != 0) {
            res += " " + decimal + " cts";
        }
        //Captlize first letter
        res = res.substring(0, 1).toUpperCase() + res.substring(1);
        System.out.println(res);
        this.setCellValue(totalOnWords, res);
    }

    private void fillSejour(LocalDateTime startDate, LocalDateTime endDate) {
        int petitDejouner = 0;
        int dejounerDiner = 0;

        int startHour = startDate.getHour();
        int endHour = endDate.getHour();
//        System.out.println("startHour = " + startHour + " endHour = " + endHour);
        //Not the same day
        if (!startDate.toLocalDate().equals(endDate.toLocalDate())) {
            if (startHour <= 8) {
                petitDejouner++;
                dejounerDiner++;
                dejounerDiner++;
            } else if (startHour <= 14) {
                dejounerDiner++;
                dejounerDiner++;
            } else if (startHour <= 22) {
                dejounerDiner++;
            }
            if (endHour > 20) {
                petitDejouner++;
                dejounerDiner++;
                dejounerDiner++;
            } else if (endHour > 12) {
                petitDejouner++;
                dejounerDiner++;
            } else if (endHour > 06) {
                petitDejouner++;
            }
        } else {
            //same day
            if (startHour <= 8) {
                petitDejouner++;
                if (endHour > 20) {
                    dejounerDiner++;
                    dejounerDiner++;
                } else if (endHour > 12) {
                    dejounerDiner++;
                }
            }
        }

        long days = ChronoUnit.DAYS.between(startDate.toLocalDate(), endDate.toLocalDate());
        //days between start and end date
        if (days >= 1) days--;
        petitDejouner += days;
        dejounerDiner += days * 2;

        this.setCellValue(this.petitDejouner, petitDejouner);
        this.setCellValue(this.dejounerDiner, dejounerDiner);
        this.setCellValue(this.hebergement, days + 1);
//        System.out.println("Petit Dejouner = " + petitDejouner + "  Dejouner diner = " + dejounerDiner + " Days = " + (days+1));
    }

    private String stringfyVille(List<Ville> villes){
        String stringfyVilles = "";
        for (Ville ville : villes) {
            stringfyVilles += ville.getNom() +",";
        }
        stringfyVilles = stringfyVilles.substring(0,stringfyVilles.length()-1);
        return stringfyVilles;
    }
}
