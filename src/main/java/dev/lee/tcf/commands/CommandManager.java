package dev.lee.tcf.commands;

import dev.lee.tcf.commands.subcommands.*;
import dev.lee.tcf.files.files.Lang;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class CommandManager implements CommandExecutor {
  private final HashMap<String, SubCommand> subCommands = new HashMap<>();

  public CommandManager() {
    storeSubCommand(new ReloadCMD());
    storeSubCommand(new AddCMD());
    storeSubCommand(new RemoveCMD());
    storeSubCommand(new ListCMD());
    storeSubCommand(new HelpCMD());
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
          SubCommand subCommand = getSubCommand(args[0].toLowerCase());
          if (!player.hasPermission(subCommand.getPermission())) {
            player.sendMessage(Lang.PREFIX.getString(null) + Lang.ERROR_NO_PERMISSION.getString(null));
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
    getSubCommand("help").performConsole(sender, args);
    return true;
  }
}
