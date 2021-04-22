package lee.code.tcf;

import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Data {

    private final ConcurrentHashMap<String, List<String>> groupList = new ConcurrentHashMap<>();
    public List<String> getGroupList(String group) {
        return groupList.get(group);
    }
    @Getter private final List<String> groups = new ArrayList<>();

    private void addGroupList(String group, List<String> commands) {
        groupList.put(group, commands);
    }
    private void addGroup(String group) {
        groups.add(group);
    }

    //load whitelist data
    public void loadData() {
        TabCompleteFilter plugin = TabCompleteFilter.getPlugin();

        groups.clear();
        groupList.clear();

        FileConfiguration file = plugin.getFile("config").getData();
        if (file.contains("Groups")) {
            ConfigurationSection groups = file.getConfigurationSection("Groups");
            if (groups != null) {
                for (String key : groups.getKeys(false)) {
                    List<String> commands = new ArrayList<>(file.getStringList("Groups." + key + ".Commands"));
                    addGroupList(key, commands);
                    addGroup(key);
                }
            }
        }
    }
}
