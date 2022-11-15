package lee.code.tcf.commands.subcommands;

import lee.code.tcf.TabCompleteFilter;
import lee.code.tcf.commands.SubCommand;
import lee.code.tcf.files.files.FileLang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCMD extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return FileLang.COMMAND_RELOAD_DESCRIPTION.getString(null);
    }

    @Override
    public String getSyntax() {
        return "/tcf reload";
    }

    @Override
    public String getPermission() {
        return "tcf.command.reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        TabCompleteFilter.getPlugin().getData().load();
        player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.COMMAND_RELOAD_SUCCESSFUL.getString(null));
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        TabCompleteFilter.getPlugin().getData().load();
        console.sendMessage(FileLang.PREFIX.getString(null) + FileLang.COMMAND_RELOAD_SUCCESSFUL.getString(null));
    }
}
