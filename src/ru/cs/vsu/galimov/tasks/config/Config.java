package ru.cs.vsu.galimov.tasks.config;

import org.yaml.snakeyaml.Yaml;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class Config {
    private final String mainUrl;

    private final Map<String, Map<String, String>> opts;

    public Config(String mainUrl) {
        this.mainUrl = mainUrl;
        this.opts = initOpts();
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Map<String, String>> parseYamlMap(Map<String, Object> yamlMap) {
        Map<String, Map<String, String>> result = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : yamlMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            result.put(key, convertToMapString((Map<String, Object>) value));
        }
        return result;
    }

    private static Map<String, String> convertToMapString(Map<String, Object> originalMap) {
        Map<String, String> convertedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : originalMap.entrySet()) {
            convertedMap.put(entry.getKey(), entry.getValue().toString());
        }
        return convertedMap;
    }

    public static Map<String, Map<String, String>> initOpts() {
        String filePath = "src/ru/cs/vsu/galimov/tasks/config/vestnikconfig.yaml";

        try {
            Path path = Paths.get(filePath);

            String yamlContent = Files.readString(path);

            Yaml yaml = new Yaml();
            Map<String, Object> yamlMap = yaml.load(yamlContent);

            return parseYamlMap(yamlMap);
        } catch (Exception e) {
            System.err.println("Error" + e.getMessage());
        }
        return null;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public Map<String, Map<String, String>> getOpts() {
        return opts;
    }
}
