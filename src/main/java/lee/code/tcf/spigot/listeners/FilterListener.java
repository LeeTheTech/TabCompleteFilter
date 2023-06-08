package lee.code.tcf.spigot.listeners;

import lee.code.tcf.spigot.data.Data;
import lee.code.tcf.spigot.TabCompleteFilter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.ArrayList;

public class FilterListener implements Listener {

    @EventHandler (priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onCommandTabShow(PlayerCommandSendEvent e) {
        final Data data = TabCompleteFilter.getPlugin().getData();
        final Player player = e.getPlayer();
        if (player.isOp()) return;
        if (player.hasPermission("tcf.bypass")) return;
        e.getCommands().clear();
        final ArrayList<String> groups = data.getPlayerGroup(player);
        if (groups.isEmpty()) return;
        for (String group : groups) {
            for (String command : data.getGroupCommands(group)) e.getCommands().add(command.replaceFirst("/", ""));
        }
    }
}
