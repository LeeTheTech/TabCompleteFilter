package dev.lee.tcf.listeners;

import dev.lee.tcf.TabCompleteFilter;
import dev.lee.tcf.files.files.Args;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

public class ServerLoadListener implements Listener {

  @EventHandler
  public void onServerLoad(ServerLoadEvent event) {
    if (Args.ENABLED.getBoolean()) TabCompleteFilter.getInstance().registerCustomTabCompletes();
  }
}
