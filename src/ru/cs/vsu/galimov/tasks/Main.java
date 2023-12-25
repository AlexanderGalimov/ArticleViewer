package ru.cs.vsu.galimov.tasks;

import ru.cs.vsu.galimov.tasks.model.InnerMagazine;
import ru.cs.vsu.galimov.tasks.model.Magazine;
import ru.cs.vsu.galimov.tasks.parser.VestnikHtmlPageParser;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        VestnikHtmlPageParser pageParser = new VestnikHtmlPageParser(new Magazine("http://www.vestnik.vsu.ru"));

        List<String> journalsURlPieces = pageParser.parseGreetingPage();

        List<String> names = new ArrayList<>();

        for (String journal : journalsURlPieces) {
            names.add(journal.split("/")[2]);
        }

        List<String> journals = pageParser.getAllMagazinesURLs(journalsURlPieces);

        List<InnerMagazine> innerMagazines = new ArrayList<>();

        for (int i = 0; i < journals.size(); i++) {
            innerMagazines.add(new InnerMagazine(names.get(i), journals.get(i)));
        }

        for (InnerMagazine innerMagazine : innerMagazines) {
            innerMagazine.setArchive(pageParser.parseJournalArchive(innerMagazine));
        }

        for (int i = 0; i < innerMagazines.size(); i++) {
            innerMagazines.get(i).setArchivesByDate(pageParser.parseArchiveByDates(innerMagazines.get(i), journalsURlPieces.get(i)));
        }

        // 1 type 1st
        List<String> pdfLinks = new ArrayList<>();

        for (int i = 0; i < innerMagazines.get(0).getArchivesByDate().size(); i++) {
            pdfLinks.addAll(pageParser.parsePDFPage(innerMagazines.get(0).getArchivesByDate().get(i), innerMagazines.get(0)));
        }

        List<String> pdfs = new ArrayList<>();
        for (String link: pdfLinks) {
            pdfs.add(pageParser.parsePDFDownloadLink(link, innerMagazines.get(0)));
        }

        System.out.println(pdfs);



//        innerMagazines.get(0).setArchivesByDate(pageParser.parseArchiveByDates(innerMagazines.get(0), journalsURlPieces.get(0)));
//        List<String> pagesToDownload = pageParser.parsePDFPage(innerMagazines.get(0).getArchivesByDate().get(0), innerMagazines.get(0));
//
//        System.out.println();
//
//        System.out.println("---------");
//
//        String link = pageParser.parsePDFDownloadLink(pagesToDownload.get(0), innerMagazines.get(0));
//        System.out.println(link);

//            System.out.println(pageParser.parseArchiveByDates(innerMagazines.get(7), journalsURlPieces.get(7)));
//            innerMagazines.get(7).setArchivesByDate(pageParser.parseArchiveByDates(innerMagazines.get(7), journalsURlPieces.get(7)));
//            List<String> pagesToDownload = pageParser.parsePDFPage(innerMagazines.get(7).getArchivesByDate().get(0), innerMagazines.get(7));
//
//            System.out.println(pagesToDownload);
//
//            System.out.println("---------");
//            System.out.println(pagesToDownload.get(0));
//
//            String link = pageParser.parsePDFDownloadLink(pagesToDownload.get(0), innerMagazines.get(7));
//            System.out.println(link);
    }
}


