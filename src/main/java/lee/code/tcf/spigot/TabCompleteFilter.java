package lee.code.tcf.spigot;

import lee.code.tcf.spigot.data.CustomArgData;
import lee.code.tcf.spigot.data.Data;
import lee.code.tcf.spigot.commands.CommandManager;
import lee.code.tcf.spigot.commands.CustomTabCompletion;
import lee.code.tcf.spigot.commands.TabCompletion;
import lee.code.tcf.spigot.files.FileManager;
import lee.code.tcf.spigot.files.files.FileLang;
import lee.code.tcf.spigot.listeners.FilterListener;
import lee.code.tcf.spigot.listeners.ServerLoadListener;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import lombok.Getter;

import java.util.*;
import java.util.logging.Level;

public class TabCompleteFilter extends JavaPlugin {

    @Getter private FileManager fileManager;
    @Getter private CommandManager commandManager;
    @Getter private Data data;

    @Override
    public void onEnable() {
        this.fileManager = new FileManager(this);
        this.data = new Data();
        this.commandManager = new CommandManager();

        getData().load();
        registerListeners();
        registerCommands();
    }

    private void registerCommands() {
        getCommand("tcf").setExecutor(commandManager);
        getCommand("tcf").setTabCompleter(new TabCompletion());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new FilterListener(), this);
        getServer().getPluginManager().registerEvents(new ServerLoadListener(), this);
    }

    public void registerCustomTabCompletes() {
        final HashMap<String, CustomArgData> customArgData = getData().getCustomArgData();
        if (customArgData.isEmpty()) return;

        for (String command : customArgData.keySet()) {
            final PluginCommand pluginCommand =  Bukkit.getServer().getPluginCommand(command);
            if (pluginCommand == null) {
                Bukkit.getLogger().log(Level.WARNING, FileLang.ERROR_CUSTOM_TAB_COMPLETE_NOT_REGISTERED.getString(new String[] { command }));
                continue;
            }
            pluginCommand.setTabCompleter(new CustomTabCompletion(customArgData.get(command)));
        }
    }

    public static TabCompleteFilter getPlugin() {
        return TabCompleteFilter.getPlugin(TabCompleteFilter.class);
    }

}
