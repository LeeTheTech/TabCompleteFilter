package lee.code.tcf.commands.subcommands;

import lee.code.tcf.TabCompleteFilter;
import lee.code.tcf.commands.SubCommand;
import lee.code.tcf.files.defaults.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reload extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return Lang.MESSAGE_HELP_SUB_COMMAND_RELOAD.getConfigValue(null);
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
        TabCompleteFilter plugin = TabCompleteFilter.getPlugin();

        plugin.reloadFiles();
        plugin.loadDefaults();
        plugin.getData().loadData();
        player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_RELOAD.getConfigValue(null));
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        TabCompleteFilter plugin = TabCompleteFilter.getPlugin();

        plugin.reloadFiles();
        plugin.loadDefaults();
        plugin.getData().loadData();
        console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.MESSAGE_RELOAD.getConfigValue(null));
    }
}
