package ru.cs.vsu.galimov.tasks;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlPageParser {
    private static final String mainURL = "http://www.vestnik.vsu.ru/index_ru.asp";
    private static final String greetingURL = "http://www.vestnik.vsu.ru";
    private static final Map<String, String> selectOptionsArchives = new HashMap<>();
    private static final Map<String, String> selectOptionsArchivesByDate = new HashMap<>();
    private static final Map<String, String> selectOptionsPDFLinks = new HashMap<>();
    private static final Map<String, String> selectOptionsPDFDownload = new HashMap<>();
    public static HtmlPageParser INSTANCE;

    public static HtmlPageParser getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new HtmlPageParser();
            // todo from config
            selectOptionsArchives.put("type1", ".read_more");
            selectOptionsArchives.put("type2", "a:contains(Архив номеров)");
            selectOptionsArchivesByDate.put("type1", "a.title");
            selectOptionsArchivesByDate.put("type2", "table a[href]");
            selectOptionsPDFLinks.put("type1", ".obj_galley_link.pdf");
            selectOptionsPDFLinks.put("type2", "li a:containsOwn(Текст (PDF))");
            selectOptionsPDFDownload.put("type1", "a.download");
            selectOptionsPDFDownload.put("type2", "#plugin");
        }
        return INSTANCE;
    }

    public List<String> parseGreetingPage() {
        return parsePage(mainURL, "div.col-md-8 a[href]");
    }

    public List<String> getAllMagazinesURLs(List<String> names) {
        if (!names.isEmpty()) {
            return makeURlJournal(names);
        } else {
            return new ArrayList<>();
        }
    }

    public List<String> makeURlJournal(List<String> links) {
        List<String> result = new ArrayList<>();
        for (String content : links) {
            result.add(greetingURL + content);
        }
        return result;
    }

    private List<String> parsePage(String url, String selectOption) {
        try {
            List<String> result = new ArrayList<>();

            Document document = Jsoup.connect(url).get();

            Elements elements = document.select(selectOption);

            for (Element link : elements) {
                String currURl = link.attr("href");
                result.add(currURl);
            }
            return result;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    private String parse(String url, String selectOption) {
        try {
            Document document = Jsoup.connect(url).get();

            // Преобразование HTML-кода в строку
            String htmlContent = document.outerHtml();

            // Путь к файлу, в который мы хотим сохранить HTML-код
            String filePath = "output.html";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(htmlContent);
            }
//            Document document = Jsoup.connect(url).get();
//
//            String html = Jsoup.connect(url).ignoreContentType(true).execute().body();
//
//            // Регулярное выражение для извлечения ссылки на PDF
//            String regex = "original-url=\"([^\"]+)\"";
//            Pattern pattern = Pattern.compile(regex);
//            Matcher matcher = pattern.matcher(html);
//
//            // Поиск и вывод ссылки на PDF
//            if (matcher.find()) {
//                String pdfUrl = matcher.group(1);
//                System.out.println("Ссылка на PDF: " + pdfUrl);
//                return pdfUrl;
//            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String parseJournalArchive(SubMagazine journal) {
        String result = "";

        List<String> archiveType1 = parsePage(journal.getURL(), selectOptionsArchives.get("type1"));

        List<String> archiveType2 = parsePage(journal.getURL(), selectOptionsArchives.get("type2"));

        if (!archiveType1.isEmpty()) {
            journal.setType("type1");
            journal.setSelectOptionArchive(selectOptionsArchives.get("type1"));
            result = archiveType1.get(0);
        } else if (!archiveType2.isEmpty()) {
            journal.setType("type2");
            journal.setSelectOptionArchive(selectOptionsArchives.get("type2"));
            result = greetingURL + archiveType2.get(0);
        }
        return result;
    }

    public List<String> parseArchiveByDates(SubMagazine magazine, String content) {
        List<String> archives = parsePage(magazine.getArchive(), selectOptionsArchivesByDate.get(magazine.getType()));

        if (Objects.equals(magazine.getType(), "type2")) {
            return makeURlArchives(content, archives);
        } else {
            return archives;
        }
    }

    public List<String> makeURlArchives(String content, List<String> archivesByDate) {
        List<String> result = new ArrayList<>();
        for (String archive : archivesByDate) {
            result.add(greetingURL + content + archive);
        }
        return result;
    }

    public List<String> parsePDFPage(String datedArchiveURL, SubMagazine magazine) {
        List<String> allArchivesByDate = new ArrayList<>();

        if (Objects.equals(magazine.getType(), "type1")) {
            allArchivesByDate = parsePage(datedArchiveURL, selectOptionsPDFLinks.get("type1"));
        }
        else {
            for (String page: parsePage(datedArchiveURL, selectOptionsPDFLinks.get("type2"))) {
                allArchivesByDate.add(greetingURL + page);
            }
        }
        return allArchivesByDate;
    }

    public String parsePDFDownloadLink(String url, SubMagazine magazine){
        if (Objects.equals(magazine.getType(), "type1")) {
            return parsePage(url, selectOptionsPDFDownload.get("type1")).get(0);
        }
        else{
            return parse(url, selectOptionsPDFDownload.get("type2"));
        }
    }
}
