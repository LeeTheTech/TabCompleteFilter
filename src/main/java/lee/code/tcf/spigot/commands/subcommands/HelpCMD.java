package lee.code.tcf.spigot.commands.subcommands;

import lee.code.tcf.spigot.TabCompleteFilter;
import lee.code.tcf.spigot.commands.SubCommand;
import lee.code.tcf.spigot.files.files.FileLang;
import lee.code.tcf.spigot.util.CoreUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HelpCMD extends SubCommand {
  private final TabCompleteFilter tabCompleteFilter;

  public HelpCMD(TabCompleteFilter tabCompleteFilter) {
    this.tabCompleteFilter = tabCompleteFilter;
  }

  @Override
  public String getName() {
    return "help";
  }

  @Override
  public String getDescription() {
    return FileLang.COMMAND_HELP_DESCRIPTION.getString(null);
  }

  @Override
  public String getSyntax() {
    return "/tcf help";
  }

  @Override
  public String getPermission() {
    return "shop.command.help";
  }

  @Override
  public void perform(Player player, String[] args) {
    performSender(player, args);
  }

  @Override
  public void performConsole(CommandSender console, String[] args) {
    performSender(console, args);
  }

  @Override @SuppressWarnings("deprecation")
  public void performSender(CommandSender sender, String[] args) {
    int number = 1;
    final List<TextComponent> lines = new ArrayList<>();
    lines.add(FileLang.COMMAND_HELP_DIVIDER.getTextComponent(null));
    lines.add(FileLang.COMMAND_HELP_TITLE.getTextComponent(null));
    lines.add(new TextComponent(""));

    for (SubCommand subCommand : tabCompleteFilter.getCommandManager().getSubCommandList()) {
      if (sender.hasPermission(subCommand.getPermission())) {
        final String suggestCommand = CoreUtil.getTextBeforeCharacter(subCommand.getSyntax(), '&');
        final TextComponent helpSubCommand = FileLang.COMMAND_HELP_SUB_COMMAND.getTextComponent(new String[]{String.valueOf(number), subCommand.getSyntax()});
        helpSubCommand.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggestCommand));
        helpSubCommand.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(FileLang.COMMAND_HELP_SUB_COMMAND_HOVER.getString(new String[]{subCommand.getDescription()})).create()));
        lines.add(helpSubCommand);
        number++;
      }
    }
    lines.add(new TextComponent(""));
    lines.add(FileLang.COMMAND_HELP_DIVIDER.getTextComponent(null));
    for (TextComponent line : lines) sender.spigot().sendMessage(line);
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    return new ArrayList<>();
  }
}
