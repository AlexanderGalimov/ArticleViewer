package ru.cs.vsu.galimov.tasks.model;

import java.util.List;

public class DepartmentMagazine {
    private final String name;

    private final String URL;

    private String type;

    private String archive;

    private List<String> archivesByDate;

    private List<String> allPdfLinks;

    private List<String> allPDFs;

    public DepartmentMagazine(String name, String url) {
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

    public List<String> getAllPDFs() {
        return allPDFs;
    }

    public void setAllPDFs(List<String> allPDFs) {
        this.allPDFs = allPDFs;
    }

    @Override
    public String toString() {
        return "InnerMagazine{" +
                "name='" + name + '\'' +
                ", URL='" + URL + '\'' +
                ", type='" + type + '\'' +
                ", archive='" + archive + '\'' +
                ", archivesByDate=" + archivesByDate +
                ", allPdfLinks=" + allPdfLinks +
                '}';
    }
}
