package ru.cs.vsu.galimov.tasks;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HtmlPageParser {
    private static final String mainURL = "http://www.vestnik.vsu.ru";
    private static final List<String> contents = new ArrayList<>();
    private static final List<String> journals = new ArrayList<>();
    private static final List<String> journalArchives = new ArrayList<>();
    private static final List<String> journalArchivesByDate = new ArrayList<>();

    private static final List<String> pdfLinks = new ArrayList<>();

    public static void parseGreetingPage(String siteURL){
        try{
            Document doc = Jsoup.connect(siteURL).get();

            Elements elements = doc.select("div.col-md-8 a[href]");

            for (Element element: elements){
                String resultLink = element.attr("href");
                contents.add(resultLink);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void makeURl(){
        for (String content: contents) {
            journals.add(mainURL + content);
        }
    }

    public static String parseJournalArchive(String journal){
        try {

            Document document = Jsoup.connect(journal).get();

            Elements elements = document.select(".read_more");

            return elements.attr("href");
        }

        catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void getArchives(){
        for (String journal: journals) {
            journalArchives.add(parseJournalArchive(journal));
        }
    }

    public static void parseArchiveByDates(String archiveURL){
        try {
            Document document = Jsoup.connect(archiveURL).get();

            Elements links = document.select(".title");

            for (Element link : links) {
                String url = link.attr("href");
                journalArchivesByDate.add(url);
            }
        }

        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void getArchivesByDate(){
        for (String link: journalArchives) {
            parseArchiveByDates(link);
        }
    }

    public static void parsePDFLinks(String archiveURL){
        try {
            Document document = Jsoup.connect(archiveURL).get();

            Elements links = document.select(".obj_galley_link.pdf");

            for (Element pdfLink : links) {
                String pdfUrl = pdfLink.attr("href");
                pdfLinks.add(pdfUrl);
            }
        }

        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        parseGreetingPage("http://www.vestnik.vsu.ru/index_ru.asp");
        makeURl();
        System.out.println(journals);
        getArchives();

        System.out.println(journalArchives);
        getArchivesByDate();
        System.out.println(journalArchivesByDate);
    }
}
