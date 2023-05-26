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
import java.util.stream.Collectors;

public class Data {

    @Getter private final ConcurrentHashMap<String, ArrayList<String>> groupData = new ConcurrentHashMap<>();

    @Getter private final ArrayList<String> allGroups = new ArrayList<>();

    public ArrayList<String> getGroupCommands(String group) {
        return getGroupData().get(group);
    }

    public void addGroupCommand(String group, String command) {
        getGroupData().get(group).add(command);
    }

    public void removeGroupCommand(String group, String command) {
        getGroupData().get(group).remove(command);
    }

    public ArrayList<String> getPlayerGroup(Player player) {
        return getAllGroups().stream()
                .filter(group -> player.hasPermission("tcf." + group))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void load() {
        //clear maps for reloads
        getAllGroups().clear();
        getGroupData().clear();

        //load files
        loadFiles();
    }

    private void loadFiles() {
        loadFile(File.LANG.name().toLowerCase(), File.LANG);
        loadFile(File.CONFIG.name().toLowerCase(), File.CONFIG);
    }

    private void loadFile(String config, File file) {
        final FileManager fileManager = TabCompleteFilter.getPlugin().getFileManager();
        fileManager.createYML(config);
        final CustomYML customYML = fileManager.getYML(config);
        final YamlConfiguration yaml = customYML.getYamlFile();

        switch (file) {
            case CONFIG: {
                final ConfigurationSection groups = yaml.getConfigurationSection("groups");
                if (groups == null) return;
                groups.getKeys(false).forEach(group -> {
                    getGroupData().put(group, new ArrayList<>(groups.getStringList(group)));
                    getAllGroups().add(group);
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
        customYML.saveYamlFile();
    }
}
