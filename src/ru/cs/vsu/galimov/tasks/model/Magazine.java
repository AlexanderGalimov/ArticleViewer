package ru.cs.vsu.galimov.tasks.model;

import ru.cs.vsu.galimov.tasks.config.Config;

import java.util.List;

public class Magazine {
    private List<DepartmentMagazine> innerMagazines;
    private final Config config;

    public Magazine(String mainURL) {
        this.config = new Config(mainURL);
    }

    public Config getConfig() {
        return config;
    }

    public void setInnerMagazines(List<DepartmentMagazine> innerMagazines) {
        this.innerMagazines = innerMagazines;
    }

    public List<DepartmentMagazine> getInnerMagazines() {
        return innerMagazines;
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "innerMagazines=" + innerMagazines +
                ", config=" + config +
                '}';
    }
}
