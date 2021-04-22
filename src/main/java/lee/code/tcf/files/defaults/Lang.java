package lee.code.tcf.files.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

@AllArgsConstructor
public enum Lang {
    PREFIX("PREFIX", "&6&lTabCompleteFilter &e➔ &r"),
    ERROR_NO_PERMISSION("ERROR_NO_PERMISSION", "&cYou sadly do not have permission for this."),
    MESSAGE_HELP_DIVIDER("MESSAGE_HELP_DIVIDER", "&e▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    MESSAGE_HELP_TITLE("MESSAGE_HELP_TITLE", "      &3-== &6&l&nTabCompleteFilter Help&r &3==-"),
    MESSAGE_HELP_SUB_COMMAND("MESSAGE_HELP_SUB_COMMAND", "&3{0}&b. &e{1} &c| &7{2}"),
    MESSAGE_HELP_SUB_COMMAND_LIST("MESSAGE_HELP_SUB_COMMAND_LIST", "Lists the commands of a specific group."),
    MESSAGE_HELP_SUB_ADD_LIST("MESSAGE_HELP_SUB_ADD_LIST", "Add a command to a group."),
    MESSAGE_HELP_SUB_REMOVE_LIST("MESSAGE_HELP_SUB_REMOVE_LIST", "Remove a command from a group."),
    MESSAGE_HELP_SUB_COMMAND_RELOAD("MESSAGE_HELP_SUB_COMMAND_RELOAD", "Reloads the plugin."),
    MESSAGE_RELOAD("MESSAGE_RELOAD", "&aThe plugin has been reloaded."),
    ERROR_COMMAND_REMOVE_ARG_1("ERROR_COMMAND_REMOVE_ARG_1", "&cYou need to select a group and input a command to remove."),
    ERROR_COMMAND_REMOVE_ARG_2("ERROR_COMMAND_REMOVE_ARG_2", "&cYou need to input a command to remove."),
    ERROR_COMMAND_REMOVE_DOES_NOT_EXIST("ERROR_COMMAND_REMOVE_DOES_NOT_EXIST", "&cThe command &6{0} &ccould not be found in the group &6{1}&c."),
    ERROR_COMMAND_ADD_ARG_1("ERROR_COMMAND_ADD_ARG_1", "&cYou need to select a group and input a command to add."),
    ERROR_COMMAND_ADD_ARG_2("ERROR_COMMAND_ADD_ARG_2", "&cYou need to input a command to add."),
    ERROR_COMMAND_LIST_PAGE("ERROR_COMMAND_LIST_PAGE", "&cThe input &6{0} &cis not a number. &a&lUse&7: &e(1)"),
    ERROR_GROUP_DOES_NOT_EXIST("ERROR_GROUP_DOES_NOT_EXIST", "&cThe group &6{0} &cdoes not exist."),
    ERROR_COMMAND_LIST_ARG_1("ERROR_COMMAND_LIST_ARG_1", "&cYou need to select a group."),
    ERROR_NOT_A_CONSOLE_COMMAND("ERROR_NOT_A_CONSOLE_COMMAND", "&cThis is not a console command."),
    ERROR_COMMAND_NOT_A_COMMAND("ERROR_COMMAND_NOT_A_COMMAND", "&cThe input &6{0} &cis not a command. You need to add a slash."),
    ERROR_COMMAND_ADD_DUPE("ERROR_COMMAND_ADD_DUPE", "&cThe command &6{0} &cis already set for the group &6{1}&c."),
    MESSAGE_COMMAND_LIST_HEADER("MESSAGE_COMMAND_LIST_HEADER", "&e&l---<< &9&l{0} &e&l>>---"),
    MESSAGE_COMMAND_LIST_COMMAND("MESSAGE_COMMAND_LIST_COMMAND", "&b{0}&7. &6{1}"),
    MESSAGE_COMMAND_LIST_NEXT_PAGE("MESSAGE_COMMAND_LIST_NEXT_PAGE", "&e&lNext >>---"),
    MESSAGE_COMMAND_LIST_NEXT_PAGE_HOVER("MESSAGE_COMMAND_LIST_NEXT_PAGE_HOVER", "&6&lNext Page"),
    MESSAGE_COMMAND_LIST_PREVIOUS_PAGE("MESSAGE_COMMAND_LIST_PREVIOUS_PAGE", "&e&l---<< Prev"),
    MESSAGE_COMMAND_LIST_PREVIOUS_PAGE_HOVER("MESSAGE_COMMAND_LIST_PREVIOUS_PAGE_HOVER", "&6&lPrevious Page"),
    MESSAGE_COMMAND_ADD_SUCCESSFUL("MESSAGE_COMMAND_ADD_SUCCESSFUL", "&aYou successfully added the command &6{0} &ato the group &6{1}&a!"),
    MESSAGE_COMMAND_REMOVE_SUCCESSFUL("MESSAGE_COMMAND_REMOVE_SUCCESSFUL", "&aYou successfully removed the command &6{0} &afrom the group &6{1}&a!"),
    ;

    @Getter private final String path;
    @Getter private final String def;
    @Setter private static FileConfiguration file;

    public String getDefault() {
        return def;
    }

    public String getConfigValue(final String[] args) {
        String fileValue = file.getString(this.path, this.def);
        if (fileValue == null) fileValue = "";
        String value = ChatColor.translateAlternateColorCodes('&', fileValue);
        if (args == null) return value;
        else if (args.length == 0) return value;
        for (int i = 0; i < args.length; i++) value = value.replace("{" + i + "}", args[i]);
        return value;
    }
}