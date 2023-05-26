package lee.code.tcf.spigot.files;

import java.io.*;
import java.util.logging.Level;

import lee.code.tcf.spigot.TabCompleteFilter;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class CustomYML extends FileManager {

    @Getter private final File file;
    @Getter private final File path;
    @Getter private YamlConfiguration yamlConfiguration;

    public CustomYML(String name, String path, TabCompleteFilter plugin) {
        super(plugin);
        this.file = new File(plugin.getDataFolder(), name);
        this.path = new File(plugin.getDataFolder() + path);
        createYamlFile();
    }

    public void reloadYamlFile() {
        yamlConfiguration = YamlConfiguration.loadConfiguration(getFile());
    }

    public YamlConfiguration getYamlFile() {
        return getYamlConfiguration();
    }

    public void saveYamlFile() {
        try {
            getYamlConfiguration().save(getFile());
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.WARNING,"Failed to save file: " + getFile().getName());
        }
    }

    private void createYamlFile() {
        if (!getPath().exists()) {
            try {
                boolean createdDir = getPath().mkdir();
            } catch(SecurityException e) {
                Bukkit.getLogger().log(Level.WARNING,"Failed to create directory for file: " + getFile().getName());
            }
        }
        if (!getFile().exists()) {
            try {
                getPlugin().saveResource(getFile().getName(), false);
            } catch(Exception e) {
                Bukkit.getLogger().log(Level.WARNING,"Failed to create file: " + getFile().getName());
            }
        }
        reloadYamlFile();
    }
}