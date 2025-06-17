package dev.lee.tcf.manager;

import dev.lee.tcf.data.CustomArgData;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.*;

@Getter
public class TabManager {
  private final Map<String, List<String>> groupDataMap = new HashMap<>();
  private final Map<String, CustomArgData> customArgDataMap = new HashMap<>();

  public void addGroupData(String group, List<String> commands) {
    groupDataMap.put(group, commands);
  }

  public void addGroupCommand(String group, String command) {
    groupDataMap.get(group).add(command);
  }

  public void removeGroupCommand(String group, String command) {
    groupDataMap.get(group).remove(command);
  }

  public boolean hasGroupData(String group) {
    return groupDataMap.containsKey(group);
  }

  public boolean hasGroupCommand(String group, String command) {
    return groupDataMap.get(group).contains(command);
  }

  public List<String> getGroupCommands(String group) {
    if (!groupDataMap.containsKey(group)) return new ArrayList<>();
    return groupDataMap.get(group);
  }

  public Set<String> getGroups() {
    return groupDataMap.keySet();
  }

  public List<String> getPlayerGroups(Player player) {
    List<String> groups = new ArrayList<>();
    for (String group : getGroups()) {
      if (player.hasPermission("tcf." + group)) groups.add(group);
    }
    return groups;
  }

  public void addCustomArgData(String command, CustomArgData customArgData) {
    customArgDataMap.put(command, customArgData);
  }

  public CustomArgData getCustomArgData(String command) {
    return customArgDataMap.get(command);
  }
}
