package lee.code.tcf.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lee.code.tcf.Data;
import lee.code.tcf.TabCompleteFilter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class TabCompletion implements TabCompleter {

    private final List<String> subCommands = Arrays.asList("reload", "add", "remove", "list");
    private final List<String> blank = new ArrayList<>();

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Data data = TabCompleteFilter.getPlugin().getData();
        if (sender instanceof Player) {
            if (args.length == 1) {
                List<String> hasCommand = new ArrayList<>();
                for (String pluginCommand : subCommands) if (sender.hasPermission("tcf.command." + pluginCommand)) hasCommand.add(pluginCommand);
                return StringUtil.copyPartialMatches(args[0], hasCommand, new ArrayList<>());
            } else if (args[0].equals("add")) {
                if (args.length == 2) return StringUtil.copyPartialMatches(args[1], data.getAllGroups(), new ArrayList<>());
                else if (args.length == 3) return StringUtil.copyPartialMatches(args[2], Collections.singletonList("<command>"), new ArrayList<>());
            } else if (args[0].equals("list")) {
                if (args.length == 2) return StringUtil.copyPartialMatches(args[1], data.getAllGroups(), new ArrayList<>());
            } else if (args[0].equals("remove")) {
                if (args.length == 2) return StringUtil.copyPartialMatches(args[1], data.getAllGroups(), new ArrayList<>());
                else if (args.length == 3) return StringUtil.copyPartialMatches(args[2], Collections.singletonList("<command>"), new ArrayList<>());
            }
        }
        return blank;
    }
}