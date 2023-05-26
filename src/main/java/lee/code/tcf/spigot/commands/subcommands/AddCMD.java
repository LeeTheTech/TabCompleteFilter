package lee.code.tcf.spigot.commands.subcommands;

import lee.code.tcf.spigot.Data;
import lee.code.tcf.spigot.TabCompleteFilter;
import lee.code.tcf.spigot.commands.SubCommand;
import lee.code.tcf.spigot.files.FileManager;
import lee.code.tcf.spigot.files.files.FileLang;
import org.bukkit.command.CommandSender;

import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        performSender(player, args);
    }


    @Override
    public void performConsole(CommandSender console, String[] args) {
        performSender(console, args);
    }

    @Override
    public void performSender(CommandSender sender, String[] args) {
        final FileManager fileManager = TabCompleteFilter.getPlugin().getFileManager();
        final Data data = TabCompleteFilter.getPlugin().getData();
        if (args.length > 1) {
            if (args.length > 2) {
                if (!data.getAllGroups().contains(args[1])) {
                    sender.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_GROUP_DOES_NOT_EXIST.getString(new String[]{args[1]}));
                    return;
                }
                final String group = args[1];
                if (!args[2].contains("/")) {
                    sender.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_COMMAND_NOT_A_COMMAND.getString(new String[]{args[2]}));
                    return;
                }
                final String command = args[2].toLowerCase();
                if (data.getGroupCommands(group).contains(command)) {
                    sender.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_COMMAND_ADD_DUPE.getString(new String[]{command, group}));
                    return;
                }
                data.addGroupCommand(group, command);
                fileManager.setGroupCommands(group);
                sender.sendMessage(FileLang.PREFIX.getString(null) + FileLang.COMMAND_ADD_SUCCESSFUL.getString(new String[]{command, group}));
            } else sender.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_COMMAND_ADD_ARG_2.getString(null));
        } else sender.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_COMMAND_ADD_ARG_1.getString(null));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) return StringUtil.copyPartialMatches(args[1], TabCompleteFilter.getPlugin().getData().getAllGroups(), new ArrayList<>());
        else if (args.length == 3) return StringUtil.copyPartialMatches(args[2], Collections.singletonList("<command>"), new ArrayList<>());
        return new ArrayList<>();
    }
}
