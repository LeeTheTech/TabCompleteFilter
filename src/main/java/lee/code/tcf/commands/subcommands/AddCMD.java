package lee.code.tcf.commands.subcommands;

import lee.code.tcf.Data;
import lee.code.tcf.TabCompleteFilter;
import lee.code.tcf.commands.SubCommand;
import lee.code.tcf.files.FileManager;
import lee.code.tcf.files.files.FileLang;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;

public class AddCMD extends SubCommand {

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public String getDescription() {
        return FileLang.COMMAND_ADD_DESCRIPTION.getString(null);
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
        FileManager fileManager = TabCompleteFilter.getPlugin().getFileManager();
        Data data = TabCompleteFilter.getPlugin().getData();
        if (args.length > 1) {
            if (args.length > 2) {
                if (!data.getAllGroups().contains(args[1])) {
                    player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_GROUP_DOES_NOT_EXIST.getString(new String[]{args[1]}));
                    return;
                }
                String group = args[1];
                if (!args[2].contains("/")) {
                    player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_COMMAND_NOT_A_COMMAND.getString(new String[]{args[2]}));
                    return;
                }
                String command = args[2].toLowerCase();
                if (data.getGroupCommands(group).contains(command)) {
                    player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_COMMAND_ADD_DUPE.getString(new String[]{command, group}));
                    return;
                }
                data.addGroupCommand(group, command);
                fileManager.setGroupCommands(group);
                player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.COMMAND_ADD_SUCCESSFUL.getString(new String[]{command, group}));
            } else player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_COMMAND_ADD_ARG_2.getString(null));
        } else player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_COMMAND_ADD_ARG_1.getString(null));
    }


    @Override
    public void performConsole(CommandSender console, String[] args) {
        console.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_NOT_A_CONSOLE_COMMAND.getString(null));
    }
}
