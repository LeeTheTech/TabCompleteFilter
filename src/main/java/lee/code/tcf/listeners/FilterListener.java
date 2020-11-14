package lee.code.tcf.listeners;

import java.util.List;

import lee.code.tcf.TabCompleteFilter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class FilterListener implements Listener {

    @EventHandler
    public void onPlayerCommandSendEvent(PlayerCommandSendEvent e) {
        TabCompleteFilter plugin = TabCompleteFilter.getPlugin();

        Player player = e.getPlayer();
        if (!player.isOp()) {
            if (!player.hasPermission("tcf.bypass")) {
                e.getCommands().clear();
                for (String group : plugin.getData().getGroups()) {
                    if (player.hasPermission("tcf." + group)) {
                        List<String> whitelist = plugin.getData().getGroupList(group);
                        if (!whitelist.isEmpty()) for (String command : whitelist) e.getCommands().add(command.replaceFirst("/", ""));
                    }
                }
            }
        }
    }
}
