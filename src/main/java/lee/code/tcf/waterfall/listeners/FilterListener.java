package lee.code.tcf.waterfall.listeners;

import io.github.waterfallmc.waterfall.event.ProxyDefineCommandsEvent;
import lee.code.tcf.waterfall.TabCompleteFilter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.*;

public class FilterListener implements Listener {
  private final TabCompleteFilter tabCompleteFilter;

  public FilterListener(TabCompleteFilter tabCompleteFilter) {
    this.tabCompleteFilter = tabCompleteFilter;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onTabCompleteResponse(ProxyDefineCommandsEvent e) {
    if (e.getReceiver() instanceof ProxiedPlayer proxiedPlayer) {
      if (proxiedPlayer.hasPermission("tcf.bypass")) return;
      final Set<String> whitelist = new HashSet<>(tabCompleteFilter.getConfigManager().getWhiteList());
      final Map<String, Command> filteredCommands = new HashMap<>();

      for (Command command : e.getCommands().values()) {
        if (whitelist.contains("/" + command.getName())) {
          filteredCommands.put(command.getName(), command);
        }
      }

      e.getCommands().clear();
      e.getCommands().putAll(filteredCommands);
    }
  }
}
