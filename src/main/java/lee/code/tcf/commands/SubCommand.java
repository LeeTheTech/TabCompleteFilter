package lee.code.tcf.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SubCommand {

    public abstract String getName();
    public abstract String getDescription();
    public abstract String getSyntax();
    public abstract String getPermission();
    public abstract void perform(Player player, String args[]);
    public abstract void performConsole(CommandSender console, String args[]);

}