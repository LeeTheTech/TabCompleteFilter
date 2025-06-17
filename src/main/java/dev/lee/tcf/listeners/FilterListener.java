package dev.lee.tcf.listeners;

import dev.lee.tcf.TabCompleteFilter;
import dev.lee.tcf.files.files.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;
import java.util.List;

public class FilterListener implements Listener {

  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onCommandTabShow(PlayerCommandSendEvent e) {
    if (Config.OP_BYPASS.getBoolean() && e.getPlayer().isOp()) return;
    if (!e.getPlayer().isOp() && e.getPlayer().hasPermission("tcf.bypass")) return;
    e.getCommands().clear();
    List<String> groups = TabCompleteFilter.getInstance().getTabManager().getPlayerGroups(e.getPlayer());
    if (groups.isEmpty()) return;
    for (String group : groups) {
      for (String command : TabCompleteFilter.getInstance().getTabManager().getGroupCommands(group)) e.getCommands().add(command.replaceFirst("/", ""));
    }
  }
}
