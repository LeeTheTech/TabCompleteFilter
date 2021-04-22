package lee.code.tcf.files;

import java.io.*;
import java.util.logging.Level;

import com.google.common.base.Charsets;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class CustomFile {

    File file;
    File path;
    YamlConfiguration config;
    Plugin plugin;
    InputStream defaults;

    //creates custom file
    public CustomFile(String name, String path, InputStream defaults, Plugin plugin) {
        this.plugin = plugin;
        this.defaults = defaults;
        file = new File(this.plugin.getDataFolder() + path, name);
        this.path = new File(this.plugin.getDataFolder() + path);
        config = new YamlConfiguration();
        createFile();
    }

    //saves file
    public void save() {
        try {
            String str = StringEscapeUtils.unescapeJava(config.saveToString());
            Writer fw = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);
            fw.write(str);
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //creates file
    private void createFile() {
        if (!path.exists()) path.mkdirs();
        if (defaults != null) {
            try {
                if (!file.exists()) {
                    InputStream in = defaults;
                    OutputStream out = new FileOutputStream(file);
                    byte[] buf = new byte[defaults.available()];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    out.close();
                    in.close();
                }
                config = loadConfiguration(file);
            } catch (IOException ex) {
                plugin.getLogger().severe("Plugin unable to write configuration file " + file.getName() + "!");
                plugin.getLogger().severe("Disabling...");
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                ex.printStackTrace();
            }
        }
    }

    //fixes unicode
    private YamlConfiguration loadConfiguration(File file) {
        Validate.notNull(file, "File cannot be null");
        YamlConfiguration config = new YamlConfiguration();
        try {
            FileInputStream stream = new FileInputStream(file);
            config.load(new InputStreamReader(stream, Charsets.UTF_8));
        } catch (FileNotFoundException ignored) {
        } catch (IOException | InvalidConfigurationException var4) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, var4);
        }
        return config;
    }

    //reloads file data
    public void reload() {
        config = loadConfiguration(file);
    }

    //loads file data
    public YamlConfiguration getData() {
        return config;
    }
}