package ru.cs.vsu.galimov.tasks;

import java.util.List;

public class SubMagazine {
    private final String name;

    private final String URL;

    private String type;

    private String selectOptionArchive;

    private String selectOptionArchivesByDate;

    private String archive;

    private List<String> archivesByDate;

    private List<String> allPdfLinks;

    public SubMagazine(String name, String url) {
        this.name = name;
        this.URL = url;
    }

    public String getArchive() {
        return archive;
    }

    public void setArchive(String archive) {
        this.archive = archive;
    }

    public String getName() {
        return name;
    }

    public String getURL() {
        return URL;
    }

    public String getSelectOptionArchive() {
        return selectOptionArchive;
    }

    public void setSelectOptionArchive(String selectOptionArchive) {
        this.selectOptionArchive = selectOptionArchive;
    }

    public String getSelectOptionArchivesByDate() {
        return selectOptionArchivesByDate;
    }

    public void setSelectOptionArchivesByDate(String selectOptionArchivesByDate) {
        this.selectOptionArchivesByDate = selectOptionArchivesByDate;
    }

    public List<String> getArchivesByDate() {
        return archivesByDate;
    }

    public void setArchivesByDate(List<String> archivesByDate) {
        this.archivesByDate = archivesByDate;
    }

    public List<String> getAllPdfLinks() {
        return allPdfLinks;
    }

    public void setAllPdfLinks(List<String> allPdfLinks) {
        this.allPdfLinks = allPdfLinks;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "SubMagazine{" +
                "name='" + name + '\'' +
                ", URL='" + URL + '\'' +
                ", type='" + type + '\'' +
                ", selectOptionArchive='" + selectOptionArchive + '\'' +
                ", selectOptionArchivesByDate='" + selectOptionArchivesByDate + '\'' +
                ", archivesByDate=" + archivesByDate +
                ", allPdfLinks=" + allPdfLinks +
                '}';
    }
}
