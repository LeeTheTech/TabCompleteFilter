package lee.code.tcf.spigot.commands.subcommands;

import lee.code.tcf.spigot.util.CoreUtil;
import lee.code.tcf.spigot.data.Data;
import lee.code.tcf.spigot.TabCompleteFilter;
import lee.code.tcf.spigot.commands.SubCommand;
import lee.code.tcf.spigot.files.files.FileLang;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class ListCMD extends SubCommand {
  private final TabCompleteFilter tabCompleteFilter;

  public ListCMD(TabCompleteFilter tabCompleteFilter) {
    this.tabCompleteFilter = tabCompleteFilter;
  }

  @Override
  public String getName() {
    return "list";
  }

  @Override
  public String getDescription() {
    return FileLang.COMMAND_LIST_DESCRIPTION.getString(null);
  }

  @Override
  public String getSyntax() {
    return "/tcf list &f<group>";
  }

  @Override
  public String getPermission() {
    return "tcf.command.list";
  }

  @Override
  @SuppressWarnings("deprecation")
  public void perform(Player player, String[] args) {
    final Data data = tabCompleteFilter.getData();
    if (args.length > 1) {
      if (!data.getAllGroups().contains(args[1])) {
        player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_GROUP_DOES_NOT_EXIST.getString(new String[]{args[1]}));
        return;
      }
      final String group = args[1];
      final int maxDisplayed = 10;
      int index;
      int page = 0;
      if (args.length > 2) {
        if (CoreUtil.isPositiveIntNumber(args[2])) page = Integer.parseInt(args[2]);
      }
      final List<TextComponent> lines = new ArrayList<>();
      final List<String> commands = data.getGroupCommands(group);
      if (commands != null && !commands.isEmpty()) {
        lines.add(FileLang.COMMAND_LIST_HEADER_SPLITTER.getTextComponent(null));
        lines.add(new TextComponent(""));
        lines.add(FileLang.COMMAND_LIST_HEADER_GROUP.getTextComponent(new String[]{group}));
        lines.add(new TextComponent(""));
        for (int i = 0; i < maxDisplayed; i++) {
          index = maxDisplayed * page + i;
          if (index >= commands.size()) break;
          if (commands.get(index) != null) {
            final int commandNumber = index + 1;
            final String command = commands.get(index);
            lines.add(FileLang.COMMAND_LIST_LINE.getTextComponent(new String[]{String.valueOf(commandNumber), command}));
          }
        }
      }

      if (lines.size() == 4) return;
      lines.add(new TextComponent(""));

      final TextComponent next = FileLang.COMMAND_LIST_NEXT_PAGE.getTextComponent(null);
      next.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(FileLang.COMMAND_LIST_NEXT_PAGE_HOVER.getString(null)).create()));
      next.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tcf list " + group + " " + (page + 1)));

      final TextComponent split = FileLang.COMMAND_LIST_PAGE_SPACER.getTextComponent(null);

      final TextComponent prev = FileLang.COMMAND_LIST_PREVIOUS_PAGE.getTextComponent(null);
      prev.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(FileLang.COMMAND_LIST_PREVIOUS_PAGE_HOVER.getString(null)).create()));
      prev.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tcf list " + group + " " + (page - 1)));

      prev.addExtra(split);
      prev.addExtra(next);
      lines.add(prev);

      for (TextComponent line : lines) player.spigot().sendMessage(line);
    } else player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_COMMAND_LIST_ARG_1.getString(null));
  }

  @Override
  public void performConsole(CommandSender console, String[] args) {
    console.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_NOT_A_CONSOLE_COMMAND.getString(null));
  }

  @Override
  public void performSender(CommandSender sender, String[] args) {
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, String[] args) {
    if (args.length == 2) return StringUtil.copyPartialMatches(args[1], tabCompleteFilter.getData().getAllGroups(), new ArrayList<>());
    return new ArrayList<>();
  }
}
