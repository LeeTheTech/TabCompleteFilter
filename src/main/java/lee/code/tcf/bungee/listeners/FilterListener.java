package lee.code.tcf.bungee.listeners;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteResponseEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FilterListener implements Listener {

    // List of blocked commands
    private List<String> blockedCommands = Arrays.asList("blockedcommand1", "blockedcommand2");

    @EventHandler
    public void onTabCompleteResponse(TabCompleteResponseEvent event) {
        final ProxiedPlayer player = (ProxiedPlayer) event.getSender(); // Retrieve the player

        final List<String> suggestions = event.getSuggestions();

        final Iterator<String> iterator = suggestions.iterator();
        while (iterator.hasNext()) {
            final String suggestion = iterator.next();
            final String command = suggestion.substring(1); // Remove the leading slash

            // Check if the command is blocked
            if (blockedCommands.contains(command)) {
                iterator.remove();
            }
        }
    }
}
