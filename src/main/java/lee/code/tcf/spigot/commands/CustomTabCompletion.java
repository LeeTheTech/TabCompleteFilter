package lee.code.tcf.spigot.commands;

import lee.code.tcf.spigot.data.PlaceHolders;
import lee.code.tcf.spigot.Core;
import lee.code.tcf.spigot.data.CustomArgData;
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
                final List<String> filteredList = new ArrayList<>();
                for (String commandArg : customArgData.getArgs().get(args.length)) {
                    if (sender.hasPermission(customArgData.getPermission() + commandArg)) filteredList.add(commandArg);
                }
                return filteredList;
            }
        } else if (customArgData.getArgs().containsKey(args.length)) {
            final List<String> listArgs = customArgData.getArgs().get(args.length);
            if (listArgs.contains(PlaceHolders.ONLINE_PLAYERS.getPlaceholder())) return Core.getOnlinePlayers();
            else return listArgs;
        }
        return new ArrayList<>();
    }
}
