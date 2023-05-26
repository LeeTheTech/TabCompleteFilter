package lee.code.tcf.bungee.files;

import lombok.Getter;
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
    @Getter private final Plugin plugin;
    @Getter private Configuration configuration;
    @Getter private File configFile;

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        configFile = new File(getPlugin().getDataFolder(), "bungee-config.yml");

        if (!getConfigFile().exists()) {
            getPlugin().getDataFolder().mkdirs();
            try (InputStream inputStream = getPlugin().getResourceAsStream("bungee-config.yml")) {
                Files.copy(inputStream, getConfigFile().toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(getConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(getConfiguration(), getConfigFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

     public List<String> getWhiteList() {
        return getConfiguration().getStringList("whitelist");
    }
}