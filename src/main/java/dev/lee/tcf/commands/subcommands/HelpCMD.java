package dev.lee.tcf.commands.subcommands;

import dev.lee.tcf.files.files.Lang;
import dev.lee.tcf.TabCompleteFilter;
import dev.lee.tcf.commands.SubCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class HelpCMD extends SubCommand {

  @Override
  public String getName() {
    return "help";
  }

  @Override
  public String getDescription() {
    return Lang.CMD_HELP_DESCRIPTION.getString();
  }

  @Override
  public String getUsage() {
    return "/tcf help";
  }

  @Override
  public String getPermission() {
    return "tcf.command.help";
  }

  @Override
  public void perform(Player player, String[] args) {
    help(player);
  }

  @Override
  public void performConsole(CommandSender console, String[] args) {
    help(console);
  }

  private void help(CommandSender sender) {
    int number = 1;
    List<TextComponent> lines = new LinkedList<>();
    lines.add(Lang.CMD_HELP_DIVIDER.getTextComponent());
    lines.add(new TextComponent(""));
    lines.add(Lang.CMD_HELP_TITLE.getTextComponent());
    lines.add(new TextComponent(""));

    List<SubCommand> subCommands = TabCompleteFilter.getInstance().getCommandManager().getSubCommandList();
    subCommands.sort(Comparator.comparing(SubCommand::getName, String.CASE_INSENSITIVE_ORDER));

    for (SubCommand subCommand : subCommands) {
      if (!sender.hasPermission(subCommand.getPermission())) continue;
      TextComponent helpSubCommand = Lang.CMD_HELP_SUB_COMMAND.getTextComponent(new String[]{String.valueOf(number), subCommand.getUsage()});
      helpSubCommand.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tcf " + subCommand.getName()));
      helpSubCommand.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Lang.CMD_HELP_SUB_COMMAND_HOVER.getString(new String[]{subCommand.getDescription()})).create()));
      lines.add(helpSubCommand);
      number++;
    }
    lines.add(new TextComponent(""));
    lines.add(Lang.CMD_HELP_DIVIDER.getTextComponent());
    for (TextComponent line : lines) sender.spigot().sendMessage(line);
  }
}
