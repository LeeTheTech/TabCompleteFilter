package dev.lee.tcf.files;

import java.util.*;
import java.util.logging.Level;

import dev.lee.tcf.TabCompleteFilter;
import dev.lee.tcf.data.CustomArgData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
  protected final HashMap<String, CustomYML> ymlFiles = new HashMap<>();

  public FileManager() {
    Locale.setDefault(Locale.ENGLISH);
  }

  public void createYML(String name) {
    ymlFiles.put(name, new CustomYML(name + ".yml", ""));
  }

  public CustomYML getYML(String name) {
    return ymlFiles.get(name);
  }

  public String getString(String file, String path) {
    return ymlFiles.get(file).getFile().getString(path);
  }

  public boolean getBoolean(String config, String path) {
    return ymlFiles.get(config).getFile().getBoolean(path);
  }

  public int getInt(String config, String path) {
    return ymlFiles.get(config).getFile().getInt(path);
  }

  public void setInt(String config, String path, int value) {
    CustomYML yml = ymlFiles.get(config);
    FileConfiguration fileConfig = yml.getFile();
    fileConfig.set(path, value);
    yml.saveFile();
  }

  public void setString(String config, String path, String string) {
    CustomYML yml = ymlFiles.get(config);
    FileConfiguration fileConfig = yml.getFile();
    fileConfig.set(path, string);
    yml.saveFile();
  }

  public void setGroupCommands(String group, List<String> commands) {
    CustomYML yml = getYML("config");
    YamlConfiguration yaml = yml.getFile();
    yaml.set("groups." + group, commands);
    yml.saveFile();
  }

  public Map<String, List<String>> getAllGroupData() {
    Map<String, List<String>> groupData = new HashMap<>();
    CustomYML customYML = getYML("config");
    YamlConfiguration yaml = customYML.getFile();

    for (String key : yaml.getConfigurationSection("groups").getKeys(false)) {
      try {
        groupData.put(key, yaml.getStringList("groups." + key));
      } catch (Exception e) {
        TabCompleteFilter.getInstance().getLogger().log(Level.WARNING, "Failed to load group " + key + ": ", e);
      }
    }
    return groupData;
  }

  public Map<String, CustomArgData> getAllCustomArgData() {
    Map<String, CustomArgData> argData = new HashMap<>();
    CustomYML customYML = getYML("args");
    YamlConfiguration yaml = customYML.getFile();

    for (String key : yaml.getConfigurationSection("custom-args").getKeys(false)) {
      try {
        String base = "custom-args." + key;
        boolean permissionCheck = yaml.getBoolean(base + ".permission-check");
        String permission = yaml.getString(base + ".permission");
        String command = yaml.getString(base + ".command");
        Map<Integer, List<String>> args = new HashMap<>();
        for (String argKey : yaml.getConfigurationSection(base + ".args").getKeys(false)) {
          int number = Integer.parseInt(argKey);
          List<String> numberArgs = yaml.getStringList(base + ".args." + argKey);
          args.put(number, numberArgs);
        }

        CustomArgData customArgData = CustomArgData.builder()
          .permissionCheck(permissionCheck)
          .permission(permission)
          .command(command)
          .args(args)
          .build();

        argData.put(command, customArgData);
      } catch (Exception e) {
        TabCompleteFilter.getInstance().getLogger().log(Level.WARNING, "Failed to load custom arg " + key + ": ", e);
      }
    }
    return argData;
  }
}
