package dev.lee.tcf.commands;

import java.util.ArrayList;
import java.util.List;

import dev.lee.tcf.TabCompleteFilter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class TabCompletion implements TabCompleter {

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    if (args.length == 1) {
      ArrayList<String> hasCommand = new ArrayList<>();
      for (SubCommand subCommand : TabCompleteFilter.getInstance().getCommandManager().getSubCommandList()) if (sender.hasPermission(subCommand.getPermission())) hasCommand.add(subCommand.getName());
      return StringUtil.copyPartialMatches(args[0], hasCommand, new ArrayList<>());
    } else {
      SubCommand subCommand = TabCompleteFilter.getInstance().getCommandManager().getSubCommand(args[0].toLowerCase());
      if (sender.hasPermission(subCommand.getPermission())) return subCommand.onTabComplete(sender, args);
    }
    return new ArrayList<>();
  }
}
