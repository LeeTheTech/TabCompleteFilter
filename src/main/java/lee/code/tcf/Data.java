package lee.code.tcf;

import lee.code.tcf.files.CustomYML;
import lee.code.tcf.files.FileManager;
import lee.code.tcf.files.files.File;
import lee.code.tcf.files.files.FileLang;
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

    public String getPlayerGroup(Player player) {
        for (String group : getAllGroups()) {
            if (player.hasPermission("tcf." + group)) return group;
        }
        return null;
    }

    public void load() {
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
