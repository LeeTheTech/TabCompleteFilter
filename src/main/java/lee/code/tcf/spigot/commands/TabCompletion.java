package lee.code.tcf.spigot.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class TabCompletion implements TabCompleter {
  private final CommandManager commandManager;

  public TabCompletion(CommandManager commandManager) {
    this.commandManager = commandManager;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    if (args.length == 1) {
      final ArrayList<String> hasCommand = new ArrayList<>();
      for (SubCommand subCommand : commandManager.getSubCommandList()) if (sender.hasPermission(subCommand.getPermission())) hasCommand.add(subCommand.getName());
      return StringUtil.copyPartialMatches(args[0], hasCommand, new ArrayList<>());
    } else {
      final SubCommand subCommand = commandManager.getSubCommand(args[0].toLowerCase());
      if (sender.hasPermission(subCommand.getPermission())) return subCommand.onTabComplete(sender, args);
    }
    return new ArrayList<>();
  }
}
