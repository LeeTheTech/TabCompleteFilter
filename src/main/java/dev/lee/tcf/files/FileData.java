package dev.lee.tcf.files;

import dev.lee.tcf.files.files.Args;
import dev.lee.tcf.files.files.Config;
import dev.lee.tcf.files.files.Lang;
import dev.lee.tcf.TabCompleteFilter;
import dev.lee.tcf.data.CustomArgData;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;
import java.util.Map;

public class FileData {

  public FileData() {
    loadFiles();
  }

  public void loadFiles() {
    loadConfig();
    loadLang();
    loadArgs();
  }

  private void loadConfig() {
    FileManager fileManager = TabCompleteFilter.getInstance().getFileManager();
    fileManager.createYML("config");
    CustomYML customYML = fileManager.getYML("config");
    YamlConfiguration yaml = customYML.getFile();

    for (Config config : Config.values()) {
      if (yaml.contains(config.getPath())) continue;
      yaml.set(config.getPath(), config.getObject());
    }
    customYML.saveFile();
    TabCompleteFilter.getInstance().getTabManager().getGroupDataMap().clear();

    for (Map.Entry<String, List<String>> groupData : fileManager.getAllGroupData().entrySet()) {
      TabCompleteFilter.getInstance().getTabManager().addGroupData(groupData.getKey(), groupData.getValue());
    }
  }

  private void loadLang() {
    FileManager fileManager = TabCompleteFilter.getInstance().getFileManager();
    fileManager.createYML("lang");
    CustomYML customYML = fileManager.getYML("lang");
    YamlConfiguration yaml = customYML.getFile();

    for (Lang lang : Lang.values()) {
      if (yaml.contains(lang.getPath())) continue;
      yaml.set(lang.getPath(), lang.getObject());
    }
    customYML.saveFile();
  }

  private void loadArgs() {
    FileManager fileManager = TabCompleteFilter.getInstance().getFileManager();
    fileManager.createYML("args");
    CustomYML customYML = fileManager.getYML("args");
    YamlConfiguration yaml = customYML.getFile();

    for (Args args : Args.values()) {
      if (yaml.contains(args.getPath())) continue;
      yaml.set(args.getPath(), args.getObject());
    }
    customYML.saveFile();
    TabCompleteFilter.getInstance().getTabManager().getCustomArgDataMap().clear();

    for (Map.Entry<String, CustomArgData> argData : fileManager.getAllCustomArgData().entrySet()) {
      TabCompleteFilter.getInstance().getTabManager().addCustomArgData(argData.getKey(), argData.getValue());
    }
  }
}
