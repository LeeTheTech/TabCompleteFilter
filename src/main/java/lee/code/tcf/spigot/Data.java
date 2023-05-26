package lee.code.tcf.spigot;

import lee.code.tcf.spigot.files.CustomYML;
import lee.code.tcf.spigot.files.FileManager;
import lee.code.tcf.spigot.files.files.File;
import lee.code.tcf.spigot.files.files.FileLang;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Data {

    private final ConcurrentHashMap<String, ArrayList<String>> groupData = new ConcurrentHashMap<>();

    @Getter private final ArrayList<String> allGroups = new ArrayList<>();

    public ArrayList<String> getGroupCommands(String group) {
        return groupData.get(group);
    }

    public void addGroupCommand(String group, String command) {
        groupData.get(group).add(command);
    }

    public void removeGroupCommand(String group, String command) {
        groupData.get(group).remove(command);
    }

    public ArrayList<String> getPlayerGroup(Player player) {
        ArrayList<String> groups = new ArrayList<>();
        for (String group : getAllGroups()) {
            if (player.hasPermission("tcf." + group)) {
                groups.add(group);
            }
        }
        return groups;
    }

    public void load() {
        //clear maps for reloads
        allGroups.clear();
        groupData.clear();

        //load files
        loadFiles();
    }

    private void loadFiles() {
        loadFile(File.LANG.name().toLowerCase(), File.LANG);
        loadFile(File.CONFIG.name().toLowerCase(), File.CONFIG);
    }

    private void loadFile(String config, File file) {
        FileManager fileManager = TabCompleteFilter.getPlugin().getFileManager();
        fileManager.createYML(config);
        CustomYML customYML = fileManager.getYML(config);
        YamlConfiguration yaml = customYML.getFile();

        switch (file) {
            case CONFIG: {
                ConfigurationSection groups = yaml.getConfigurationSection("groups");
                if (groups == null) return;
                groups.getKeys(false).forEach(group -> {
                    groupData.put(group, new ArrayList<>(groups.getStringList(group)));
                    allGroups.add(group);
                });
                break;
            }

            case LANG: {
                for (FileLang value : FileLang.values()) {
                    if (!yaml.contains(value.getPath())) yaml.set(value.getPath(), value.getString());
                }
                break;
            }
        }
        customYML.saveFile();
    }
}
