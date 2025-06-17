package dev.lee.tcf.files;

import java.io.*;
import java.util.logging.Level;
import dev.lee.tcf.TabCompleteFilter;
import org.bukkit.configuration.file.YamlConfiguration;

public class CustomYML extends FileManager {
  private final File file;
  private final File path;
  private YamlConfiguration yamlConfiguration;

  public CustomYML(String name, String path) {
    this.file = new File(TabCompleteFilter.getInstance().getDataFolder(), name);
    this.path = new File(TabCompleteFilter.getInstance().getDataFolder(), path);
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
      TabCompleteFilter.getInstance().getLogger().log(Level.WARNING, "Failed to save file: " + file.getName());
    }
  }

  private void createFile() {
    if (!path.exists()) {
      try {
        boolean createdDir = path.mkdir();
      } catch (SecurityException e) {
        TabCompleteFilter.getInstance().getLogger().log(Level.WARNING, "Failed to create directory for file: " + file.getName());
      }
    }
    if (!file.exists()) {
      try {
        TabCompleteFilter.getInstance().saveResource(file.getName(), false);
      } catch (Exception e) {
        TabCompleteFilter.getInstance().getLogger().log(Level.WARNING, "Failed to create file: " + file.getName());
      }
    }
    reloadFile();
  }
}
