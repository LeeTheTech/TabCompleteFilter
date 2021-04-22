package lee.code.tcf;

import lee.code.tcf.commands.CommandManager;
import lee.code.tcf.commands.TabCompletion;
import lee.code.tcf.files.CustomFile;
import lee.code.tcf.files.FileManager;
import lee.code.tcf.files.defaults.Lang;
import lee.code.tcf.listeners.FilterListener;
import org.bukkit.plugin.java.JavaPlugin;
import lombok.Getter;

public class TabCompleteFilter extends JavaPlugin {

    @Getter private FileManager fileManager;
    @Getter private Data data;
    @Getter private PU pU;

    @Override
    public void onEnable() {

        this.fileManager = new FileManager();
        this.data = new Data();
        this.pU = new PU();

        //File Manager for configs
        fileManager.addConfig("config");
        fileManager.addConfig("lang");

        registerListeners();
        registerCommands();
        loadDefaults();

        //load command whitelist
        data.loadData();

        //add config
        fileManager.addConfig("config");
    }

    private void registerCommands() {
        getCommand("tcf").setExecutor(new CommandManager());
        getCommand("tcf").setTabCompleter(new TabCompletion());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new FilterListener(), this);
    }

    public void loadDefaults() {

        //lang
        Lang.setFile(fileManager.getConfig("lang").getData());

        for (Lang value : Lang.values()) fileManager.getConfig("lang").getData().addDefault(value.getPath(), value.getDefault());
        fileManager.getConfig("lang").getData().options().copyDefaults(true);
        fileManager.getConfig("lang").save();
    }

    public static TabCompleteFilter getPlugin() {
        return TabCompleteFilter.getPlugin(TabCompleteFilter.class);
    }

    //gets a file
    public CustomFile getFile(String file) {
        return fileManager.getConfig(file);
    }

    //saves a file
    public void saveFile(String file) {
        fileManager.getConfig(file).save();
    }

    //reloads all files
    public void reloadFiles() {
        fileManager.reloadAll();
    }
}
