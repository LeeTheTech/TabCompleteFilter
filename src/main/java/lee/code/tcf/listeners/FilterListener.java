package lee.code.tcf.listeners;

import lee.code.tcf.Data;
import lee.code.tcf.TabCompleteFilter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class FilterListener implements Listener {

    @EventHandler (priority = EventPriority.LOWEST)
    public void onCommandTabShow(PlayerCommandSendEvent e) {
        Data data = TabCompleteFilter.getPlugin().getData();
        Player player = e.getPlayer();
        if (player.isOp()) return;
        if (player.hasPermission("tcf.bypass")) return;
        e.getCommands().clear();
        String group = data.getPlayerGroup(player);
        if (group == null) return;
        for (String command : data.getGroupCommands(group)) e.getCommands().add(command.replaceFirst("/", ""));
    }
}
