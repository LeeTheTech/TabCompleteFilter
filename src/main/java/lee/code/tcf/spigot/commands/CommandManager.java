package lee.code.tcf.spigot.commands;

import lee.code.tcf.spigot.Core;
import lee.code.tcf.spigot.commands.subcommands.AddCMD;
import lee.code.tcf.spigot.commands.subcommands.ListCMD;
import lee.code.tcf.spigot.commands.subcommands.ReloadCMD;
import lee.code.tcf.spigot.commands.subcommands.RemoveCMD;
import lee.code.tcf.spigot.files.files.FileLang;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor {
    @Getter private final ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManager() {
        subCommands.add(new ReloadCMD());
        subCommands.add(new AddCMD());
        subCommands.add(new RemoveCMD());
        subCommands.add(new ListCMD());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length > 0) {
                for (SubCommand subCommand : getSubCommands()) {
                    if (args[0].equalsIgnoreCase(subCommand.getName())) {
                        if (player.hasPermission(subCommand.getPermission())) subCommand.perform(player, args);
                        else player.sendMessage(FileLang.PREFIX.getString(null) + FileLang.ERROR_NO_PERMISSION.getString(null));
                        return true;
                    }
                }
            }
            sendHelpMessage(player);
        } else if (args.length > 0) {
            for (SubCommand subCommand : getSubCommands()) {
                if (args[0].equalsIgnoreCase(subCommand.getName())) {
                    subCommand.performConsole(sender, args);
                    return true;
                }
            }
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    public void sendHelpMessage(CommandSender sender) {
        int number = 1;
        final List<TextComponent> lines = new ArrayList<>();
        lines.add(FileLang.COMMAND_HELP_DIVIDER.getTextComponent(null));
        lines.add(FileLang.COMMAND_HELP_TITLE.getTextComponent(null));
        lines.add(new TextComponent(""));

        for (SubCommand subCommand : getSubCommands()) {
            if (sender.hasPermission(subCommand.getPermission())) {
                final String suggestCommand = Core.getTextBeforeCharacter(subCommand.getSyntax(), '&');
                final TextComponent helpSubCommand = FileLang.COMMAND_HELP_SUB_COMMAND.getTextComponent(new String[] { String.valueOf(number), subCommand.getSyntax() });
                helpSubCommand.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, suggestCommand));
                helpSubCommand.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(FileLang.COMMAND_HELP_SUB_COMMAND_HOVER.getString(new String[] { subCommand.getDescription() })).create()));
                lines.add(helpSubCommand);
                number++;
            }
        }
        lines.add(new TextComponent(""));
        lines.add(FileLang.COMMAND_HELP_DIVIDER.getTextComponent(null));
        for (TextComponent line : lines) sender.spigot().sendMessage(line);
    }
}