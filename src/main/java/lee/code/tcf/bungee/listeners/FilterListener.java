package lee.code.tcf.bungee.listeners;

import io.github.waterfallmc.waterfall.event.ProxyDefineCommandsEvent;
import lee.code.tcf.bungee.TabCompleteFilter;
import lombok.Getter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

import java.util.*;

public class FilterListener implements Listener {
    @Getter private final TabCompleteFilter plugin;

    public FilterListener(TabCompleteFilter plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onTabCompleteResponse(ProxyDefineCommandsEvent e) {
        if (e.getReceiver() instanceof ProxiedPlayer proxiedPlayer) {
            if (proxiedPlayer.getGroups().contains("admin")) return;
            final Set<String> whitelist = new HashSet<>(getPlugin().getConfigManager().getWhiteList());
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
