package lee.code.tcf.spigot.data;

import lee.code.tcf.spigot.TabCompleteFilter;
import lee.code.tcf.spigot.files.CustomYML;
import lee.code.tcf.spigot.files.FileManager;
import lee.code.tcf.spigot.files.files.File;
import lee.code.tcf.spigot.files.files.FileArgs;
import lee.code.tcf.spigot.files.files.FileLang;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Data {
    @Getter private final ConcurrentHashMap<String, List<String>> groupData = new ConcurrentHashMap<>();
    @Getter private final HashMap<String, CustomArgData> customArgData = new HashMap<>();

    public List<String> getGroupCommands(String group) {
        return getGroupData().get(group);
    }

    public List<String> getAllGroups() {
        return List.copyOf(getGroupData().keySet());
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
        getGroupData().clear();
        getCustomArgData().clear();

        //load files
        loadFiles();
    }

    private void loadFiles() {
        loadFile(File.LANG.name().toLowerCase(), File.LANG);
        loadFile(File.CONFIG.name().toLowerCase(), File.CONFIG);
        loadFile(File.ARGS.name().toLowerCase(), File.ARGS);
    }

    private void loadFile(String config, File file) {
        final FileManager fileManager = TabCompleteFilter.getPlugin().getFileManager();
        fileManager.createYML(config);
        final CustomYML customYML = fileManager.getYML(config);
        final YamlConfiguration yaml = customYML.getFile();

        switch (file) {
            case CONFIG -> {
                final ConfigurationSection groups = yaml.getConfigurationSection("groups");
                if (groups == null) return;
                groups.getKeys(false).forEach(group -> getGroupData().put(group, new ArrayList<>(groups.getStringList(group))));
            }
            case LANG -> {
                for (FileLang value : FileLang.values()) {
                    if (!yaml.contains(value.getPath())) yaml.set(value.getPath(), value.getString());
                }
            }
            case ARGS -> {
                for (FileArgs value : FileArgs.values()) {
                    if (!yaml.contains(value.getPath())) yaml.set(value.getPath(), value.getString());
                }

                final ConfigurationSection commands = yaml.getConfigurationSection("custom-args");
                if (commands == null) return;
                commands.getKeys(false).forEach(commandID -> {
                    System.out.println("command ID:" + commandID);
                    final ConfigurationSection id = commands.getConfigurationSection(commandID);
                    if (id == null) return;
                    final String command = id.getString("command");
                    if (command == null) return;
                    final boolean permissionCheck = id.getBoolean("permission-check");
                    final String permission = id.getString("permission");

                    final ConfigurationSection args = id.getConfigurationSection("args");
                    if (args == null) return;
                    final HashMap<Integer, List<String>> commandArgs = new HashMap<>();
                    args.getKeys(false).forEach(argNumber -> {
                        final List<String> argList = args.getStringList(argNumber);
                        commandArgs.put(Integer.parseInt(argNumber), argList);
                    });
                    getCustomArgData().put(command, new CustomArgData(permissionCheck, permission, commandArgs));
                });
            }
        }
        customYML.saveFile();
    }
}
