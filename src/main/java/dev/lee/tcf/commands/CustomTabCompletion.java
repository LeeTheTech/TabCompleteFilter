package dev.lee.tcf.commands;

import dev.lee.tcf.data.CustomArgData;
import dev.lee.tcf.data.Placeholders;
import dev.lee.tcf.util.CoreUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CustomTabCompletion implements TabCompleter {
  private final CustomArgData customArgData;

  public CustomTabCompletion(CustomArgData customArgData) {
    this.customArgData = customArgData;
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
    if (customArgData.isPermissionCheck()) {
      if (customArgData.getArgs().containsKey(args.length)) {
        List<String> filteredList = new ArrayList<>();
        for (String commandArg : customArgData.getArgs().get(args.length)) {
          if (sender.hasPermission(customArgData.getPermission() + commandArg)) filteredList.add(commandArg);
        }
        return filteredList;
      }
    } else if (customArgData.getArgs().containsKey(args.length)) {
      List<String> listArgs = customArgData.getArgs().get(args.length);
      if (listArgs.contains(Placeholders.ONLINE_PLAYERS.getPlaceholder())) return CoreUtil.getOnlinePlayers();
      else return listArgs;
    }
    return new ArrayList<>();
  }
}
