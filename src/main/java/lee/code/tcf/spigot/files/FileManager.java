package lee.code.tcf.spigot.files;

import java.util.concurrent.ConcurrentHashMap;

import lee.code.tcf.spigot.Core;
import lee.code.tcf.spigot.TabCompleteFilter;
import lee.code.tcf.spigot.files.files.File;
import org.bukkit.configuration.file.FileConfiguration;

public class FileManager {

    protected TabCompleteFilter plugin;
    protected final ConcurrentHashMap<String, CustomYML> ymlFiles = new ConcurrentHashMap<>();

    public FileManager(TabCompleteFilter plugin) { this.plugin = plugin; }

    public void createYML(String name) { ymlFiles.put(name, new CustomYML(name + ".yml", "", plugin)); }

    public CustomYML getYML(String name) { return ymlFiles.get(name); }

    public String getStringFromFile(String config, String path, String[] variables) {
        final FileConfiguration fileConfig = ymlFiles.get(config).getFile();
        String value = fileConfig.getString(path);
        value = value != null ? value : "";
        if (variables == null || variables.length == 0) return Core.parseColorString(value);
        for (int i = 0; i < variables.length; i++) value = value.replace("{" + i + "}", variables[i]);
        return Core.parseColorString(value);
    }

    public boolean getBooleanFromFile(String config, String path) {
        return ymlFiles.get(config).getFile().getBoolean(path);
    }

    public int getValueFromFile(String config, String path) {
        final FileConfiguration fileConfig = ymlFiles.get(config).getFile();
        final String value = fileConfig.getString(path);
        return value != null ? Integer.parseInt(value) : 0;
    }

    public void setValueInFile(String config, String path, int value) {
        final CustomYML yml = ymlFiles.get(config);
        final FileConfiguration fileConfig = yml.getFile();
        fileConfig.set(path, value);
        yml.saveFile();
    }

    public void setStringInFile(String config, String path, String string) {
        final CustomYML yml = ymlFiles.get(config);
        final FileConfiguration fileConfig = yml.getFile();
        fileConfig.set(path, string);
        yml.saveFile();
    }

    public void setGroupCommands(String group) {
        final CustomYML yml = ymlFiles.get(File.CONFIG.name().toLowerCase());
        final FileConfiguration fileConfig = yml.getFile();
        fileConfig.set("groups." + group, plugin.getData().getGroupCommands(group));
        yml.saveFile();
    }
}