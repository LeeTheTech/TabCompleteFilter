package dev.lee.tcf.commands.subcommands;

import dev.lee.tcf.TabCompleteFilter;
import dev.lee.tcf.commands.SubCommand;
import dev.lee.tcf.files.files.Lang;
import dev.lee.tcf.manager.TabManager;
import dev.lee.tcf.util.CoreUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class ListCMD extends SubCommand {

  @Override
  public String getName() {
    return "list";
  }

  @Override
  public String getDescription() {
    return Lang.CMD_LIST_DESCRIPTION.getString();
  }

  @Override
  public String getUsage() {
    return "/tcf list &f<group> <page>";
  }

  @Override
  public String getPermission() {
    return "tcf.command.list";
  }

  @Override
  public void perform(Player player, String[] args) {
    if (args.length < 2) {
      player.sendMessage(Lang.USAGE.getString(new String[]{getUsage()}));
      return;
    }
    if (args.length > 2) listGroupCommands(player, args[1], args[2]);
    else listGroupCommands(player, args[1], null);
  }

  @Override
  public void performConsole(CommandSender console, String[] args) {
    if (args.length < 2) {
      console.sendMessage(Lang.USAGE.getString(new String[]{getUsage()}));
      return;
    }
    if (args.length > 2) listGroupCommands(console, args[1], args[2]);
    else listGroupCommands(console, args[1], null);
  }

  private void listGroupCommands(CommandSender sender, String group, String pageString) {
    TabManager tabManager = TabCompleteFilter.getInstance().getTabManager();
    if (!tabManager.hasGroupData(group)) {
      sender.sendMessage(Lang.PREFIX.getString() + Lang.ERROR_GROUP_DOES_NOT_EXIST.getString(new String[]{group}));
      return;
    }

    int index;
    int page = 0;
    int maxDisplayed = 10;
    if (CoreUtil.isPositiveInt(pageString)) page = Integer.parseInt(pageString);
    int position = page * maxDisplayed + 1;

    List<String> commandList = tabManager.getGroupCommands(group);
    commandList.sort(String::compareToIgnoreCase);

    List<TextComponent> lines = new LinkedList<>();
    lines.add(Lang.CMD_LIST_HEADER_SPLITTER.getTextComponent());
    lines.add(new TextComponent(""));
    lines.add(Lang.CMD_LIST_HEADER_GROUP.getTextComponent(new String[]{group}));
    lines.add(new TextComponent(""));

    for (int i = 0; i < maxDisplayed; i++) {
      index = maxDisplayed * page + i;
      if (index >= commandList.size()) break;
      String command = commandList.get(index);
      lines.add(Lang.CMD_LIST_LINE.getTextComponent(new String[]{String.valueOf(position), command}));
      position++;
    }

    if (lines.size() == 4) return;
    lines.add(new TextComponent(""));
    lines.add(CoreUtil.createPageSelectionMessage("/tcf list " + group, page));
    for (TextComponent line : lines) sender.spigot().sendMessage(line);
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    if (args.length == 2) return StringUtil.copyPartialMatches(args[1], TabCompleteFilter.getInstance().getTabManager().getGroups(), new ArrayList<>());
    return new ArrayList<>();
  }
}
