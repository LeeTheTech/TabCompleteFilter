package lee.code.tcf.spigot.commands;

import lee.code.tcf.spigot.TabCompleteFilter;
import lee.code.tcf.spigot.commands.subcommands.AddCMD;
import lee.code.tcf.spigot.commands.subcommands.ListCMD;
import lee.code.tcf.spigot.commands.subcommands.ReloadCMD;
import lee.code.tcf.spigot.commands.subcommands.RemoveCMD;
import lee.code.tcf.spigot.files.files.FileLang;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandManager implements CommandExecutor {
  @Getter private final HashMap<String, SubCommand> subCommands = new HashMap<>();

  public CommandManager(TabCompleteFilter tabCompleteFilter) {
    storeSubCommand(new ReloadCMD(tabCompleteFilter));
    storeSubCommand(new AddCMD(tabCompleteFilter));
    storeSubCommand(new RemoveCMD(tabCompleteFilter));
    storeSubCommand(new ListCMD(tabCompleteFilter));
  }

  private void storeSubCommand(SubCommand subCommand) {
    subCommands.put(subCommand.getName(), subCommand);
  }

  public SubCommand getSubCommand(String command) {
    return subCommands.get(command);
  }

  public List<SubCommand> getSubCommandList() {
    return new ArrayList<>(subCommands.values());
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (args.length > 0) {
      if (sender instanceof Player player) {
        if (subCommands.containsKey(args[0].toLowerCase())) {
          final SubCommand subCommand = getSubCommand(args[0].toLowerCase());
          if (!player.hasPermission(subCommand.getPermission())) {
            player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_NO_PERMISSION.getString(null));
            return true;
          }
          subCommand.perform(player, args);
          return true;
        }
      } else {
        if (subCommands.containsKey(args[0].toLowerCase())) {
          final SubCommand subCommand = getSubCommand(args[0].toLowerCase());
          subCommand.performConsole(sender, args);
          return true;
        }
      }
    }
    getSubCommand("help").performSender(sender, args);
    return true;
  }
}
