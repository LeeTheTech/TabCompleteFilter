package dev.lee.tcf;

import dev.lee.tcf.data.CustomArgData;
import dev.lee.tcf.files.FileData;
import dev.lee.tcf.files.files.Lang;
import dev.lee.tcf.commands.CommandManager;
import dev.lee.tcf.commands.CustomTabCompletion;
import dev.lee.tcf.commands.TabCompletion;
import dev.lee.tcf.files.FileManager;
import dev.lee.tcf.listeners.FilterListener;
import dev.lee.tcf.listeners.ServerLoadListener;
import dev.lee.tcf.manager.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import lombok.Getter;

import java.util.*;
import java.util.logging.Level;

public class TabCompleteFilter extends JavaPlugin {
  @Getter private static TabCompleteFilter instance;
  @Getter private TabManager tabManager;
  @Getter private FileManager fileManager;
  @Getter private FileData fileData;
  @Getter private CommandManager commandManager;

  @Override
  public void onEnable() {
    instance = this;
    this.tabManager = new TabManager();
    this.fileManager = new FileManager();
    this.fileData = new FileData();
    this.commandManager = new CommandManager();

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
    for (Map.Entry<String, CustomArgData> argData : tabManager.getCustomArgDataMap().entrySet()) {
      PluginCommand pluginCommand = Bukkit.getServer().getPluginCommand(argData.getKey());
      if (pluginCommand == null) {
        getLogger().log(Level.WARNING, Lang.ERROR_CUSTOM_TAB_COMPLETE_NOT_REGISTERED.getString(new String[]{argData.getKey()}));
        continue;
      }
      pluginCommand.setTabCompleter(new CustomTabCompletion(argData.getValue()));
    }
  }
}
