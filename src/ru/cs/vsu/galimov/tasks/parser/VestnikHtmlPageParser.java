package ru.cs.vsu.galimov.tasks.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.cs.vsu.galimov.tasks.model.InnerMagazine;
import ru.cs.vsu.galimov.tasks.model.Magazine;

import java.util.*;

public class VestnikHtmlPageParser {
    private final Magazine magazine;

    public VestnikHtmlPageParser(Magazine magazine) {
        this.magazine = magazine;
    }

    public List<String> parseGreetingPage() {
        return parsePage(magazine.getConfig().getMainUrl(), magazine.getConfig().getOpts().get("mainURL").get("opt"));
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
            result.add(magazine.getConfig().getMainUrl() + content);
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

    public String parseJournalArchive(InnerMagazine innerMagazine) {
        String result = "";

        List<String> archiveType1 = parsePage(innerMagazine.getURL(), magazine.getConfig().getOpts().get("selectOptionsArchives").get("type1"));

        List<String> archiveType2 = parsePage(innerMagazine.getURL(), magazine.getConfig().getOpts().get("selectOptionsArchives").get("type2"));

        if (!archiveType1.isEmpty()) {
            innerMagazine.setType("type1");
            result = archiveType1.get(0);
        } else if (!archiveType2.isEmpty()) {
            innerMagazine.setType("type2");
            result = magazine.getConfig().getMainUrl() + archiveType2.get(0);
        }
        return result;
    }

    public List<String> parseArchiveByDates(InnerMagazine innerMagazine, String content) {
        List<String> archives = parsePage(innerMagazine.getArchive(), magazine.getConfig().getOpts().get("selectOptionsArchivesByDate").get(innerMagazine.getType()));

        if (Objects.equals(innerMagazine.getType(), "type2")) {
            return makeURlArchives(content, archives);
        } else {
            return archives;
        }
    }

    public List<String> makeURlArchives(String content, List<String> archivesByDate) {
        List<String> result = new ArrayList<>();
        for (String archive : archivesByDate) {
            result.add(magazine.getConfig().getMainUrl() + content + archive);
        }
        return result;
    }

    public List<String> parsePDFPage(String datedArchiveURL, InnerMagazine innerMagazine) {
        List<String> pdfLinksByDate = new ArrayList<>();

        if (Objects.equals(innerMagazine.getType(), "type1")) {
            pdfLinksByDate = parsePage(datedArchiveURL, magazine.getConfig().getOpts().get("selectOptionsPDFLinks").get("type1"));
        }
        else {
            for (String page: parsePage(datedArchiveURL, magazine.getConfig().getOpts().get("selectOptionsPDFLinks").get("type2"))){
                pdfLinksByDate.add(magazine.getConfig().getMainUrl() + page);
            }
        }
        return pdfLinksByDate;
    }

    public String parsePDFDownloadLink(String url, InnerMagazine innerMagazine){
        if (Objects.equals(innerMagazine.getType(), "type1")) {
            return parsePage(url, magazine.getConfig().getOpts().get("selectOptionsPDFDownload").get("type1")).get(0);
        }
        else {
            return null;
        }
    }
}
