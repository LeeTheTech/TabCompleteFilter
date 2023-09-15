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

  public CustomYML(TabCompleteFilter tabCompleteFilter, String name, String path) {
    super(tabCompleteFilter);
    this.file = new File(tabCompleteFilter.getDataFolder(), name);
    this.path = new File(tabCompleteFilter.getDataFolder(), path);
    createFile();
  }

  public void reloadFile() {
    yamlConfiguration = YamlConfiguration.loadConfiguration(file);
  }

  public YamlConfiguration getFile() {
    return yamlConfiguration;
  }

  public void saveFile() {
    try {
      yamlConfiguration.save(file);
    } catch (IOException e) {
      Bukkit.getLogger().log(Level.WARNING, "Failed to save file: " + file.getName());
    }
  }

  private void createFile() {
    if (!path.exists()) {
      try {
        boolean createdDir = path.mkdir();
      } catch (SecurityException e) {
        Bukkit.getLogger().log(Level.WARNING, "Failed to create directory for file: " + file.getName());
      }
    }
    if (!file.exists()) {
      try {
        tabCompleteFilter.saveResource(file.getName(), false);
      } catch (Exception e) {
        Bukkit.getLogger().log(Level.WARNING, "Failed to create file: " + file.getName());
      }
    }
    reloadFile();
  }
}
