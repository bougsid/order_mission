/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bougsid.printers;

import com.bougsid.MSG;
import com.bougsid.mission.Mission;
import com.bougsid.ville.Ville;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Ayoub
 */
@org.springframework.stereotype.Component
public class PrintMission {
    @Autowired
    private MSG msg;
    final Font DEFAULT_FONT = FontFactory.getFont(FontFactory.HELVETICA, 14);
    final Font DEFAULT_FONT_BOLD = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);

    public void printMission(Mission mission) {
        Document document = new Document(PageSize.A4);
        try {
            File downloadDir = new File(msg.getMessage("application.mission.downloaddir"));
            if (!downloadDir.exists()) {
                downloadDir.mkdir();
            }
            System.out.println("Dir ="+downloadDir.getPath());
            PdfWriter.getInstance(document, new FileOutputStream(downloadDir.getPath() + "/" + mission.getUuid() + ".pdf"));
            document.open();
            //header
            Paragraph paragraph = new Paragraph(msg.getMessage("mission.pdf.school") + "       " + mission.getIdMission() + "           ", DEFAULT_FONT);
            Phrase phrase = new Phrase(msg.getMessage("mission.pdf.city") + " " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            paragraph.add(phrase);
            paragraph.setSpacingAfter(50);
            document.add(paragraph);
            //title
            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(40);
            PdfPCell cell = new PdfPCell(new Phrase(msg.getMessage("mission.pdf.title"), FontFactory.getFont(FontFactory.HELVETICA, 18)));
            cell.setBorderWidth(1);
            cell.setPadding(10);

            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.setSpacingAfter(40);
            document.add(table);


            //content
            table = new PdfPTable(2);
            table.setWidthPercentage(100);

            //line 1
            cell = new PdfPCell(new Paragraph(mission.getEmploye().getCivilite().getLabel(), DEFAULT_FONT_BOLD));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(": " + mission.getEmploye().getFullName(), DEFAULT_FONT));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);

            //line 2
            cell = new PdfPCell(new Paragraph(msg.getMessage("mission.pdf.matricule"), DEFAULT_FONT_BOLD));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(": " + mission.getEmploye().getMatricule(), DEFAULT_FONT));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);

            //line 3
            cell = new PdfPCell(new Paragraph(msg.getMessage("mission.pdf.grade"), DEFAULT_FONT_BOLD));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(": " + mission.getEmploye().getGrade().getLabel(), DEFAULT_FONT));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);

            //line 4
            cell = new PdfPCell(new Paragraph(msg.getMessage("mission.pdf.text_1"), DEFAULT_FONT_BOLD));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(msg.getMessage("mission.pdf.text_1_2"), DEFAULT_FONT_BOLD));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);

            //line 5
            cell = new PdfPCell(new Paragraph(" ", DEFAULT_FONT_BOLD));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);

            if (mission.getEntreprise() != null) {
                cell = new PdfPCell(new Paragraph(" - " + mission.getEntreprise().getNom(), DEFAULT_FONT));
                cell.setBorder(Rectangle.NO_BORDER);
                cell.setPaddingTop(10);
                table.addCell(cell);
            }

            //line 6
            cell = new PdfPCell(new Paragraph(msg.getMessage("mission.pdf.lieu"), DEFAULT_FONT_BOLD));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);
            Paragraph p = new Paragraph();
            p.setFont(DEFAULT_FONT);
            for (Ville ville : mission.getVilles()) {
                p.add(ville.getNom() + "\n");
            }
            cell = new PdfPCell(p);
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);


            //line 7
            cell = new PdfPCell(new Paragraph(msg.getMessage("mission.pdf.startdate"), DEFAULT_FONT_BOLD));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(": " + mission.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + "    Heure : " + mission.getStartDate().format(DateTimeFormatter.ofPattern("HH:mm")), DEFAULT_FONT));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);

            //line 8
            cell = new PdfPCell(new Paragraph(msg.getMessage("mission.pdf.enddate"), DEFAULT_FONT_BOLD));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(": " + mission.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + "    Heure : " + mission.getEndDate().format(DateTimeFormatter.ofPattern("HH:mm")), DEFAULT_FONT));

            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);

            //line 9
            cell = new PdfPCell(new Paragraph(msg.getMessage("mission.pdf.transport"), DEFAULT_FONT_BOLD));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);
            cell = new PdfPCell(new Paragraph(": " + mission.getTransportType().getLabel(), DEFAULT_FONT));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setPaddingTop(10);
            table.addCell(cell);

            table.setSpacingAfter(30);
            document.add(table);

            //note
            document.add(new Paragraph(msg.getMessage("mission.pdf.TEXT_2"), DEFAULT_FONT));

            //signature
//            paragraph = new Paragraph(params[12], DEFAULT_FONT);
//            paragraph.setAlignment(Element.ALIGN_RIGHT);
//            paragraph.setSpacingBefore(40);
//            document.add(paragraph);
//            paragraph = new Paragraph(params[13], DEFAULT_FONT);
//            paragraph.setAlignment(Element.ALIGN_RIGHT);
//            document.add(paragraph);


        } catch (DocumentException ex) {
            ex.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        document.close();
//        try {
//            Desktop.getDesktop().open(new File("./order.pdf"));
//        } catch (IOException ex) {
//            JOptionPane.showMessageDialog(null, "Fichier géneré mais ne peut pas etre ouvert vérifier le dossier");
//        }
    }


    /**
     * @param args the command line arguments
     */

}
