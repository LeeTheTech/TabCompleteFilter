package lee.code.tcf.commands.subcommands;

import lee.code.tcf.TabCompleteFilter;
import lee.code.tcf.commands.SubCommand;
import lee.code.tcf.files.defaults.Lang;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListGroup extends SubCommand {

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getDescription() {
        return Lang.MESSAGE_HELP_SUB_COMMAND_LIST.getConfigValue(null);
    }

    @Override
    public String getSyntax() {
        return "/tcf list &f<group>";
    }

    @Override
    public String getPermission() {
        return "tcf.command.list";
    }

    @Override
    public void perform(Player player, String[] args) {
        TabCompleteFilter plugin = TabCompleteFilter.getPlugin();

        //tcf add
        if (args.length > 1) {
            if (plugin.getData().getGroups().contains(args[1])) {
                String group = args[1];

                List<String> commands = plugin.getData().getGroupList(group);

                int page = 0;

                //page check
                if (args.length > 2) {
                    Scanner sellScanner = new Scanner(args[2]);
                    if (sellScanner.hasNextInt()) {
                        page = Integer.parseInt(args[2]);
                    } else {
                        player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_LIST_PAGE.getConfigValue(new String[]{ args[2]} ));
                        return;
                    }
                }

                if (page < 0) return;

                int index;
                int maxDisplayed = 10;
                List<String> formattedList = new ArrayList<>();
                if (commands != null && !commands.isEmpty()) {
                    formattedList.add(Lang.MESSAGE_COMMAND_LIST_HEADER.getConfigValue(new String[] { group.toUpperCase() }));
                    for(int i = 0; i < maxDisplayed; i++) {
                        index = maxDisplayed * page + i;
                        if (index >= commands.size()) break;
                        if (commands.get(index) != null) {

                            int commandNumber = index + 1;
                            String command = commands.get(index);
                            formattedList.add(Lang.MESSAGE_COMMAND_LIST_COMMAND.getConfigValue(new String[] { String.valueOf(commandNumber), command }));
                        }
                    }
                }

                if (formattedList.size() < 2) return;

                for (String line : formattedList) player.sendMessage(plugin.getPU().format(line));

                //next page
                TextComponent next = new TextComponent(Lang.MESSAGE_COMMAND_LIST_NEXT_PAGE.getConfigValue(null));
                next.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tcf list " + group + " " + (page + 1)));
                if (plugin.getPU().supports(16)) next.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new Text(Lang.MESSAGE_COMMAND_LIST_NEXT_PAGE_HOVER.getConfigValue(null))));
                else next.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Lang.MESSAGE_COMMAND_LIST_NEXT_PAGE_HOVER.getConfigValue(null)).create()));
                //previous page
                TextComponent previous = new TextComponent(Lang.MESSAGE_COMMAND_LIST_PREVIOUS_PAGE.getConfigValue(null));
                previous.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tcf list " + group + " " + (page - 1)));
                if (plugin.getPU().supports(16)) previous.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new Text(Lang.MESSAGE_COMMAND_LIST_PREVIOUS_PAGE_HOVER.getConfigValue(null))));
                else previous.setHoverEvent(new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Lang.MESSAGE_COMMAND_LIST_PREVIOUS_PAGE_HOVER.getConfigValue(null)).create()));
                //spacer
                TextComponent spacer = new TextComponent(" | ");
                spacer.setBold(true);
                spacer.setColor(ChatColor.GRAY);

                player.spigot().sendMessage(previous, spacer, next);

            } else player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_GROUP_DOES_NOT_EXIST.getConfigValue(new String[]{ args[1] }));
        } else player.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_COMMAND_LIST_ARG_1.getConfigValue(null));
    }

    @Override
    public void performConsole(CommandSender console, String[] args) {
        console.sendMessage(Lang.PREFIX.getConfigValue(null) + Lang.ERROR_NOT_A_CONSOLE_COMMAND.getConfigValue(null));
    }
}
