package ru.cs.vsu.galimov.tasks;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            HtmlPageParser pageParser = HtmlPageParser.getINSTANCE();

            List<String> journalsURlPieces = pageParser.parseGreetingPage();

            List<String> names = new ArrayList<>();

            for (String journal : journalsURlPieces) {
                names.add(journal.split("/")[2]);
            }

            List<String> journals = pageParser.getAllMagazinesURLs(journalsURlPieces);

            List<SubMagazine> innerMagazines = new ArrayList<>();

            for (int i = 0; i < journals.size(); i++) {
                innerMagazines.add(new SubMagazine(names.get(i), journals.get(i)));
            }

            for (SubMagazine innerMagazine : innerMagazines) {
                innerMagazine.setArchive(pageParser.parseJournalArchive(innerMagazine));
            }

            System.out.println(pageParser.parseArchiveByDates(innerMagazines.get(0), journalsURlPieces.get(0)));
            innerMagazines.get(0).setArchivesByDate(pageParser.parseArchiveByDates(innerMagazines.get(0), journalsURlPieces.get(0)));
//            System.out.println(pageParser.parseArchiveByDates(innerMagazines.get(7), journalsURlPieces.get(7)));

            System.out.println(pageParser.parsePDFLinks(innerMagazines.get(0).getArchivesByDate().get(0), innerMagazines.get(0)));

        } catch (NullPointerException e) {
            System.exit(-1);
        }
    }
}
