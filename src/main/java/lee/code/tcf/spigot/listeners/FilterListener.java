package lee.code.tcf.spigot.listeners;

import lee.code.tcf.spigot.data.Data;
import lee.code.tcf.spigot.TabCompleteFilter;
import lee.code.tcf.spigot.files.files.FileConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.ArrayList;

public class FilterListener implements Listener {
  private final TabCompleteFilter tabCompleteFilter;

  public FilterListener(TabCompleteFilter tabCompleteFilter) {
    this.tabCompleteFilter = tabCompleteFilter;
  }

  @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
  public void onCommandTabShow(PlayerCommandSendEvent e) {
    final Player player = e.getPlayer();
    if (FileConfig.OP_BYPASS.getBoolean() && player.isOp()) return;
    if (player.hasPermission("tcf.bypass")) return;
    e.getCommands().clear();
    final Data data = tabCompleteFilter.getData();
    final ArrayList<String> groups = data.getPlayerGroup(player);
    if (groups.isEmpty()) return;
    for (String group : groups) {
      for (String command : data.getGroupCommands(group)) e.getCommands().add(command.replaceFirst("/", ""));
    }
  }
}
