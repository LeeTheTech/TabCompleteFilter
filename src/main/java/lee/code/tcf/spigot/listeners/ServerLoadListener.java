package lee.code.tcf.spigot.listeners;

import lee.code.tcf.spigot.TabCompleteFilter;
import lee.code.tcf.spigot.files.files.FileArgs;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class ServerLoadListener implements Listener {
  private final TabCompleteFilter tabCompleteFilter;

  public ServerLoadListener(TabCompleteFilter tabCompleteFilter) {
    this.tabCompleteFilter = tabCompleteFilter;
  }

  @EventHandler
  public void onServerLoad(ServerLoadEvent event) {
    if (FileArgs.ENABLED.getBoolean()) tabCompleteFilter.registerCustomTabCompletes();
  }
}
