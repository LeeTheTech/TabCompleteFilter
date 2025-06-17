package dev.lee.tcf.commands.subcommands;

import dev.lee.tcf.TabCompleteFilter;
import dev.lee.tcf.commands.SubCommand;
import dev.lee.tcf.files.files.Lang;
import dev.lee.tcf.manager.TabManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class RemoveCMD extends SubCommand {

  @Override
  public String getName() {
    return "remove";
  }

  @Override
  public String getDescription() {
    return Lang.CMD_REMOVE_DESCRIPTION.getString();
  }

  @Override
  public String getUsage() {
    return "/tcf remove &f<group> <command>";
  }

  @Override
  public String getPermission() {
    return "tcf.command.remove";
  }

  @Override
  public void perform(Player player, String[] args) {
    if (args.length < 3) {
      player.sendMessage(Lang.USAGE.getString(new String[]{getUsage()}));
      return;
    }
    removeGroupCommand(player, args[1], args[2].toLowerCase());
  }

  @Override
  public void performConsole(CommandSender console, String[] args) {
    if (args.length < 3) {
      console.sendMessage(Lang.USAGE.getString(new String[]{getUsage()}));
      return;
    }
    removeGroupCommand(console, args[1], args[2].toLowerCase());
  }

  private void removeGroupCommand(CommandSender sender, String group, String command) {
    TabManager tabManager = TabCompleteFilter.getInstance().getTabManager();
    if (!tabManager.hasGroupData(group)) {
      sender.sendMessage(Lang.PREFIX.getString() + Lang.ERROR_GROUP_DOES_NOT_EXIST.getString(new String[]{group}));
      return;
    }
    if (!command.contains("/")) {
      sender.sendMessage(Lang.PREFIX.getString() + Lang.ERROR_COMMAND_NOT_A_COMMAND.getString(new String[]{command}));
      return;
    }
    if (!tabManager.hasGroupCommand(group, command)) {
      sender.sendMessage(Lang.PREFIX.getString() + Lang.ERROR_COMMAND_REMOVE_DOES_NOT_EXIST.getString(new String[]{command, group}));
      return;
    }
    tabManager.removeGroupCommand(group, command);
    TabCompleteFilter.getInstance().getFileManager().setGroupCommands(group, tabManager.getGroupCommands(group));
    sender.sendMessage(Lang.PREFIX.getString() + Lang.CMD_REMOVE_SUCCESSFUL.getString(new String[]{command, group}));
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    if (args.length == 2) return StringUtil.copyPartialMatches(args[1], TabCompleteFilter.getInstance().getTabManager().getGroups(), new ArrayList<>());
    else if (args.length == 3) return StringUtil.copyPartialMatches(args[2], TabCompleteFilter.getInstance().getTabManager().getGroupCommands(args[1]), new ArrayList<>());
    else return new ArrayList<>();
  }
}
