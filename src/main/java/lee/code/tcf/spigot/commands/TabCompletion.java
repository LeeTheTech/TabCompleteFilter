package lee.code.tcf.spigot.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabCompletion implements TabCompleter {
  private final CommandManager commandManager;

  public TabCompletion(CommandManager commandManager) {
    this.commandManager = commandManager;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    if (commandManager.getSubCommands().containsKey(args[0].toLowerCase())) {
      final SubCommand subCommand = commandManager.getSubCommand(args[0].toLowerCase());
      if (sender.hasPermission("tcf.command." + subCommand.getName())) return subCommand.onTabComplete(sender, args);
    }
    return new ArrayList<>();
  }
}
