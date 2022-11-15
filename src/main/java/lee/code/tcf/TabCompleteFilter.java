package lee.code.tcf;

import lee.code.tcf.commands.CommandManager;
import lee.code.tcf.commands.TabCompletion;
import lee.code.tcf.files.FileManager;
import lee.code.tcf.listeners.FilterListener;
import org.bukkit.plugin.java.JavaPlugin;
import lombok.Getter;

public class TabCompleteFilter extends JavaPlugin {

    @Getter private FileManager fileManager;
    @Getter private CommandManager commandManager;
    @Getter private Data data;

    @Override
    public void onEnable() {
        this.fileManager = new FileManager(this);
        this.data = new Data();
        this.commandManager = new CommandManager();

        data.load();

        registerListeners();
        registerCommands();
    }

    private void registerCommands() {
        getCommand("tcf").setExecutor(commandManager);
        getCommand("tcf").setTabCompleter(new TabCompletion());
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new FilterListener(), this);
    }

    public static TabCompleteFilter getPlugin() {
        return TabCompleteFilter.getPlugin(TabCompleteFilter.class);
    }

}
