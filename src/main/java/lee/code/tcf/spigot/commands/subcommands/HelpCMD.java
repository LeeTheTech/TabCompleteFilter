package lee.code.tcf.spigot.commands.subcommands;

import lee.code.tcf.spigot.TabCompleteFilter;
import lee.code.tcf.spigot.commands.SubCommand;
import lee.code.tcf.spigot.files.files.FileLang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HelpCMD extends SubCommand {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return FileLang.COMMAND_HELP_DESCRIPTION.getString(null);
    }

    @Override
    public String getSyntax() {
        return "/tcf help";
    }

    @Override
    public String getPermission() {
        return "shop.command.help";
    }

    @Override
    public void perform(Player player, String[] args) {
        TabCompleteFilter.getPlugin().getCommandManager().sendHelpMessage(player);
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        console.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_NOT_A_CONSOLE_COMMAND.getString(null));
    }
}
