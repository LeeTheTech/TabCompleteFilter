package lee.code.tcf.commands;

import lee.code.tcf.TabCompleteFilter;
import lee.code.tcf.commands.subcommands.AddGroup;
import lee.code.tcf.commands.subcommands.ListGroup;
import lee.code.tcf.commands.subcommands.Reload;
import lee.code.tcf.commands.subcommands.RemoveGroup;
import lee.code.tcf.files.defaults.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor {
    @Getter public final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManager() {
        subCommands.add(new Reload());
        subCommands.add(new AddGroup());
        subCommands.add(new RemoveGroup());
        subCommands.add(new ListGroup());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        TabCompleteFilter plugin = TabCompleteFilter.getPlugin();

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length > 0) {
                for (int i = 0; i < getSubCommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                        //perm check for sub command
                        if (p.hasPermission(getSubCommands().get(i).getPermission())) getSubCommands().get(i).perform(p, args);
                        else p.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NO_PERMISSION.getConfigValue(null));
                        return true;
                    }
                }
            }

            //plugin info
            int number = 1;
            List<String> lines = new ArrayList<>();
            lines.add(Lang.MESSAGE_HELP_DIVIDER.getConfigValue(null));
            lines.add(Lang.MESSAGE_HELP_TITLE.getConfigValue(null));
            lines.add("&r");

            for (int i = 0; i < getSubCommands().size(); i++) {
                //perm check
                if (p.hasPermission(getSubCommands().get(i).getPermission())) {
                    //add command to list
                    lines.add(Lang.MESSAGE_HELP_SUB_COMMAND.getConfigValue(new String [] { String.valueOf(number), getSubCommands().get(i).getSyntax(), getSubCommands().get(i).getDescription() }));
                    number++;
                }
            }
            lines.add("&r");
            lines.add(Lang.MESSAGE_HELP_DIVIDER.getConfigValue(null));

            for (String line : lines) p.sendMessage(plugin.getUtility().format(line));
            return true;

        }
        //console command
        if (args.length > 0) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    getSubCommands().get(i).performConsole(sender, args);
                    return true;
                }
            }

        }
        return true;
    }
}