package lee.code.tcf.files;

import java.util.concurrent.ConcurrentHashMap;

import lee.code.tcf.Core;
import lee.code.tcf.TabCompleteFilter;
import lee.code.tcf.files.files.File;
import org.bukkit.configuration.file.FileConfiguration;

public class FileManager {

    protected TabCompleteFilter plugin;
    protected final ConcurrentHashMap<String, CustomYML> ymlFiles = new ConcurrentHashMap<>();

    public FileManager(TabCompleteFilter plugin) {
        this.plugin = plugin;
    }

    public void createYML(String name) {
        ymlFiles.put(name, new CustomYML(name + ".yml", "", plugin));
    }

    public CustomYML getYML(String name) {
        return ymlFiles.get(name);
    }

    public String getStringFromFile(String config, String path, String[] variables) {
        FileConfiguration fileConfig = ymlFiles.get(config).getFile();
        String value = fileConfig.getString(path);
        value = value != null ? value : "";
        if (variables == null || variables.length == 0) return Core.parseColorString(value);
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return Core.parseColorString(value);
    }

    public int getValueFromFile(String config, String path) {
        FileConfiguration fileConfig = ymlFiles.get(config).getFile();
        String value = fileConfig.getString(path);
        return value != null ? Integer.parseInt(value) : 0;
    }

    public void setValueInFile(String config, String path, int value) {
        CustomYML yml = ymlFiles.get(config);
        FileConfiguration fileConfig = yml.getFile();
        fileConfig.set(path, value);
        yml.saveFile();
    }

    public void setStringInFile(String config, String path, String string) {
        CustomYML yml = ymlFiles.get(config);
        FileConfiguration fileConfig = yml.getFile();
        fileConfig.set(path, string);
        yml.saveFile();
    }

    public void setGroupCommands(String group) {
        CustomYML yml = ymlFiles.get(File.CONFIG.name().toLowerCase());
        FileConfiguration fileConfig = yml.getFile();
        fileConfig.set("groups." + group, plugin.getData().getGroupCommands(group));
        yml.saveFile();
    }
}