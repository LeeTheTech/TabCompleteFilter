package lee.code.tcf.spigot.commands;

import java.util.ArrayList;
import java.util.List;

import lee.code.tcf.spigot.TabCompleteFilter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

public class TabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final CommandManager commandManager = TabCompleteFilter.getPlugin().getCommandManager();
        if (args.length == 1) {
            final ArrayList<String> hasCommand = new ArrayList<>();
            for (SubCommand subCommand : commandManager.getSubCommands()) if (sender.hasPermission("tcf.command." + subCommand.getName())) hasCommand.add(subCommand.getName());
            return StringUtil.copyPartialMatches(args[0], hasCommand, new ArrayList<>());
        } else {
            for (SubCommand subCommand : commandManager.getSubCommands()) {
                if (args[0].equalsIgnoreCase(subCommand.getName())) {
                    return subCommand.onTabComplete(sender, args);
                }
            }
        }
        return new ArrayList<>();
    }
}