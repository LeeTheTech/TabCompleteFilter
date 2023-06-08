package lee.code.tcf.spigot.files;

import java.io.*;
import java.util.logging.Level;

import lee.code.tcf.spigot.TabCompleteFilter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

public class CustomYML extends FileManager {

    private final File file;
    private final File path;
    private YamlConfiguration yamlConfiguration;

    /**
     * Custom yml file constructor
     * @param name Name of file.
     * @param path Path for file.
     * @param plugin The plugin using the file manager.
     */
    public CustomYML(String name, String path, TabCompleteFilter plugin) {
        super(plugin);
        this.file = new File(plugin.getDataFolder(), name);
        this.path = new File(plugin.getDataFolder() + path);
        createFile();
    }

    public void reloadFile() { yamlConfiguration = YamlConfiguration.loadConfiguration(file);}

    public YamlConfiguration getFile() { return yamlConfiguration; }

    public void saveFile() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.WARNING,"Failed to save file: " + file.getName());
        }
    }

    private void createFile() {
        if (!path.exists()) {
            try {
                boolean createdDir = path.mkdir();
            } catch(SecurityException e) {
                Bukkit.getLogger().log(Level.WARNING,"Failed to create directory for file: " + file.getName());
            }
        }
        if (!file.exists()) {
            try {
                plugin.saveResource(file.getName(), false);
            } catch(Exception e) {
                Bukkit.getLogger().log(Level.WARNING,"Failed to create file: " + file.getName());
            }
        }
        reloadFile();
    }
}