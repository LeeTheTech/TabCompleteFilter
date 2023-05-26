package lee.code.tcf.spigot.files;

import java.util.concurrent.ConcurrentHashMap;

import lee.code.tcf.spigot.Core;
import lee.code.tcf.spigot.TabCompleteFilter;
import lee.code.tcf.spigot.files.files.File;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

public class FileManager {

    @Getter private final TabCompleteFilter plugin;
    @Getter private final ConcurrentHashMap<String, CustomYML> ymlFiles = new ConcurrentHashMap<>();

    public FileManager(TabCompleteFilter plugin) {
        this.plugin = plugin;
    }

    public void createYML(String name) {
        getYmlFiles().put(name, new CustomYML(name + ".yml", "", getPlugin()));
    }

    public CustomYML getYML(String name) {
        return getYmlFiles().get(name);
    }

    public String getStringFromFile(String config, String path, String[] variables) {
        final FileConfiguration fileConfig = getYmlFiles().get(config).getYamlFile();
        String value = fileConfig.getString(path);
        value = value != null ? value : "";
        if (variables == null || variables.length == 0) return Core.parseColorString(value);
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return Core.parseColorString(value);
    }

    public boolean getBooleanFromFile(String config, String path) {
        return getYmlFiles().get(config).getYamlFile().getBoolean(path);
    }

    public int getValueFromFile(String config, String path) {
        final FileConfiguration fileConfig = getYmlFiles().get(config).getYamlFile();
        final String value = fileConfig.getString(path);
        return value != null ? Integer.parseInt(value) : 0;
    }

    public void setValueInFile(String config, String path, int value) {
        final CustomYML yml = getYmlFiles().get(config);
        final FileConfiguration fileConfig = yml.getYamlFile();
        fileConfig.set(path, value);
        yml.saveYamlFile();
    }

    public void setStringInFile(String config, String path, String string) {
        final CustomYML yml = getYmlFiles().get(config);
        final FileConfiguration fileConfig = yml.getYamlFile();
        fileConfig.set(path, string);
        yml.saveYamlFile();
    }

    public void setGroupCommands(String group) {
        final CustomYML yml = getYmlFiles().get(File.CONFIG.name().toLowerCase());
        final FileConfiguration fileConfig = yml.getYamlFile();
        fileConfig.set("groups." + group, getPlugin().getData().getGroupCommands(group));
        yml.saveYamlFile();
    }
}