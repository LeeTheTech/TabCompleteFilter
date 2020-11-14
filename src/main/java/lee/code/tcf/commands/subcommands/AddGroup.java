package lee.code.tcf.commands.subcommands;

import lee.code.tcf.TabCompleteFilter;
import lee.code.tcf.commands.SubCommand;
import lee.code.tcf.files.defaults.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class AddGroup extends SubCommand {

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return Lang.MESSAGE_HELP_SUB_ADD_LIST.getConfigValue(null);
    }

    @Override
    public String getSyntax() {
        return "/tcf add &f<group> <command>";
    }

    @Override
    public String getPermission() {
        return "tcf.command.add";
    }

    @Override
    public void perform(Player player, String[] args) {
        TabCompleteFilter plugin = TabCompleteFilter.getPlugin();

        //tcf add
        if (args.length == 1) {
            player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_ADD_ARG_1.getConfigValue(null));
            return;
        }

        if (args.length > 1) {

            //tcf add <group>
            if (args.length == 2) {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_ADD_ARG_2.getConfigValue(null));
                return;
            }

            //group check
            String group;
            if (plugin.getData().getGroups().contains(args[1])) {
                group = args[1];
            } else {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_GROUP_DOES_NOT_EXIST.getConfigValue(new String[]{ args[1] }));
                return;
            }

            //command check
            String command;
            if (args[2].contains("/")) {
                command = args[2].toLowerCase();
            } else {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_NOT_A_COMMAND.getConfigValue(new String[] { args[2] }));
                return;
            }

            if (plugin.getData().getGroupList(group).contains(command)) {
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_ADD_DUPE.getConfigValue(new String[] { command, group }));
                return;
            }

            FileConfiguration file = plugin.getFile("config").getData();

            if (file.contains("Groups")) {

                List<String> savedCommands = file.getStringList("Groups." + group + ".Commands");
                savedCommands.add(command);

                file.set("Groups." + group + ".Commands", savedCommands);
                plugin.saveFile("config");
                plugin.getData().loadData();
                player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_COMMAND_ADD_SUCCESSFUL.getConfigValue(new String[] { command, group }));
            }

        }
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NOT_A_CONSOLE_COMMAND.getConfigValue(null));
    }
}
