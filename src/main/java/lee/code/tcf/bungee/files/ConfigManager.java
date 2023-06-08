package lee.code.tcf.bungee.files;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

public class ConfigManager {
    private final Plugin plugin;
    private Configuration configuration;
    private File configFile;

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        configFile = new File(plugin.getDataFolder(), "bungee-config.yml");

        if (!configFile.exists()) {
            plugin.getDataFolder().mkdirs();
            try (InputStream inputStream = plugin.getResourceAsStream("bungee-config.yml")) {
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