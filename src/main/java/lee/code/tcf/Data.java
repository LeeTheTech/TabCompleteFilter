package lee.code.tcf;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Data {

    private final HashMap<String, List<String>> groupList = new HashMap<>();
    @Getter private final List<String> groups = new ArrayList<>();

    //get group list
    public List<String> getGroupList(String group) {
        return groupList.get(group);
    }

    //add to group list
    private void addGroupList(String group, List<String> commands) {
        groupList.put(group, commands);
    }

    private void addGroup(String group) {
        groups.add(group);
    }

    //load whitelist data
    public void loadData() {

        groups.clear();
        groupList.clear();

        TabCompleteFilter plugin = TabCompleteFilter.getPlugin();

        FileConfiguration file = plugin.getFile("config").getData();

        if (file.contains("Groups")) {
            for (String key : file.getConfigurationSection("Groups").getKeys(false)) {
                List<String> commands = new ArrayList<>(file.getStringList("Groups." + key + ".Commands"));
                addGroupList(key, commands);
                addGroup(key);
            }
        }
    }
}
