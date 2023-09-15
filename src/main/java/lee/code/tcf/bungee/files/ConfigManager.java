package lee.code.tcf.bungee.files;

import lee.code.tcf.bungee.TabCompleteFilter;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class ConfigManager {
  private final TabCompleteFilter tabCompleteFilter;
  private Configuration configuration;
  private File configFile;

  public ConfigManager(TabCompleteFilter tabCompleteFilter) {
    this.tabCompleteFilter = tabCompleteFilter;
  }

  public void loadConfig() {
    configFile = new File(tabCompleteFilter.getDataFolder(), "bungee-config.yml");

    if (!configFile.exists()) {
      tabCompleteFilter.getDataFolder().mkdirs();
      try (InputStream inputStream = tabCompleteFilter.getResourceAsStream("bungee-config.yml")) {
        Files.copy(inputStream, configFile.toPath());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    try {
      configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void saveConfig() {
    try {
      ConfigurationProvider.getProvider(YamlConfiguration.class).save(configuration, configFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<String> getWhiteList() {
    return configuration.getStringList("whitelist");
  }
}
