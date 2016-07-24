package com.bougsid.decompte;

import com.bougsid.decompte.generatedecompte.Excel;
import com.bougsid.mission.Mission;
import com.bougsid.taux.ITauxService;
import com.bougsid.taux.Taux;
import com.bougsid.transport.TransportType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by ayoub on 7/24/2016.
 */
@Service
public class DecompteService implements IDecompteService {
    @Autowired
    private DecompteRepository decompteRepository;
    @Autowired
    private ITauxService tauxService;
    @Autowired
    private Excel excel;
    private Decompte decompte;

    public DecompteService() {
        this.decompte = new Decompte();
    }

    @Override
    public List<Decompte> findAll() {
        return this.decompteRepository.findAll();
    }

    @Override
    public Decompte save(Decompte decompte) {
        return this.decompteRepository.save(decompte);
    }

    @Override
    public void delete(Decompte decompte) {
        this.decompteRepository.delete(decompte);
    }

    @Override
    public void setDecompteWithMission(Mission mission) {
        this.decompte.setMission(mission);
        this.fillSejour(mission.getStartDate(), mission.getEndDate());
        //Set Taux
        this.fillTaux(mission);
        System.out.println("ok2");
    }

    @Override
    public Decompte getDecompte() {
        return decompte;
    }

    private void fillTaux(Mission mission) {
        Taux taux = this.tauxService.getTaux();

        if (mission.getEmploye().isDerictor()) {
            this.decompte.setTauxDejounerDiner(taux.getTauxDejounerDinerDirec());
            this.decompte.setTauxPetitDejouner(taux.getTauxPetitDejounerDirec());
            this.decompte.setTauxHebergement(taux.getTauxHebergementDirec());
            this.decompte.setTauxKilometrique(taux.getTauxKilometriqueDirec());
        } else {
            this.decompte.setTauxDejounerDiner(taux.getTauxDejounerDiner());
            this.decompte.setTauxPetitDejouner(taux.getTauxPetitDejouner());
            this.decompte.setTauxHebergement(taux.getTauxHebergement());
            this.decompte.setTauxKilometrique(taux.getTauxKilometrique());
            if (mission.getTransportType() == TransportType.PERSONNEL) {
                if (mission.getVilles().size() == 1) {
                    this.decompte.setTauxAuto(mission.getVilles().get(0).getTauxAuto());
                    this.decompte.setDistance(mission.getVilles().get(0).getDistance());
                }
            }
        }
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

        this.decompte.setPetitDejouner(petitDejouner);
        this.decompte.setDejounerDiner(dejounerDiner);
        if (!startDate.toLocalDate().equals(endDate.toLocalDate())) {
            days++;
        }
        this.decompte.setHebergement(days);
//        System.out.println("Petit Dejouner = " + petitDejouner + "  Dejouner diner = " + dejounerDiner + " Days = " + (days+1));
    }
    @Override
    public void printDecompte(Decompte decompte)  {
        System.out.println("staaaaart");
        this.excel.generateExcel(decompte);
    }
//    private double getDistance(List<Ville> villes) {
//        double distance = 0;
//        for (Ville ville : villes) {
//            distance += ville.getDistance();
//        }
//        return distance;
//    }
}