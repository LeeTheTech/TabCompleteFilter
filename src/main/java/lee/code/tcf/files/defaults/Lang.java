package lee.code.tcf.files.defaults;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

@AllArgsConstructor
public enum Lang {
    //plugin prefix
    PREFIX("PREFIX", "&6&lTabCompleteFilter &e➔ &r"),
    //Error no permission
    ERROR_NO_PERMISSION("ERROR_NO_PERMISSION", "&cYou sadly do not have permission for this."),
    //Message Help info divider
    MESSAGE_HELP_DIVIDER("MESSAGE_HELP_DIVIDER", "&e▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    //Message Help info title
    MESSAGE_HELP_TITLE("MESSAGE_HELP_TITLE", "      &3-== &6&l&nTabCompleteFilter Help&r &3==-"),
    //Message Help info sub command
    MESSAGE_HELP_SUB_COMMAND("MESSAGE_HELP_SUB_COMMAND", "&3{0}&b. &e{1} &c| &7{2}"),
    //Message Help list sub command
    MESSAGE_HELP_SUB_COMMAND_LIST("MESSAGE_HELP_SUB_COMMAND_LIST", "Lists the commands of a specific group."),
    //Message Help add sub command
    MESSAGE_HELP_SUB_ADD_LIST("MESSAGE_HELP_SUB_ADD_LIST", "Add a command to a group."),
    //Message Help remove sub command
    MESSAGE_HELP_SUB_REMOVE_LIST("MESSAGE_HELP_SUB_REMOVE_LIST", "Remove a command from a group."),
    //Message Help reload sub command
    MESSAGE_HELP_SUB_COMMAND_RELOAD("MESSAGE_HELP_SUB_COMMAND_RELOAD", "Reloads the plugin."),
    //Message plugin has reloaded
    MESSAGE_RELOAD("MESSAGE_RELOAD", "&aThe plugin has been reloaded."),
    //Error command remove arg 1
    ERROR_COMMAND_REMOVE_ARG_1("ERROR_COMMAND_REMOVE_ARG_1", "&cYou need to select a group and input a command to remove."),
    //Error command remove arg 2
    ERROR_COMMAND_REMOVE_ARG_2("ERROR_COMMAND_REMOVE_ARG_2", "&cYou need to input a command to remove."),
    //Error command remove command not found
    ERROR_COMMAND_REMOVE_DOES_NOT_EXIST("ERROR_COMMAND_REMOVE_DOES_NOT_EXIST", "&cThe command &6{0} &ccould not be found in the group &6{1}&c."),
    //Error command add arg 1
    ERROR_COMMAND_ADD_ARG_1("ERROR_COMMAND_ADD_ARG_1", "&cYou need to select a group and input a command to add."),
    //Error command add arg 2
    ERROR_COMMAND_ADD_ARG_2("ERROR_COMMAND_ADD_ARG_2", "&cYou need to input a command to add."),
    //Error command list page number
    ERROR_COMMAND_LIST_PAGE("ERROR_COMMAND_LIST_PAGE", "&cThe input &6{0} &cis not a number. &a&lUse&7: &e(1)"),
    //Error command list group does not exist
    ERROR_GROUP_DOES_NOT_EXIST("ERROR_GROUP_DOES_NOT_EXIST", "&cThe group &6{0} &cdoes not exist."),
    //Error command list arg 1
    ERROR_COMMAND_LIST_ARG_1("ERROR_COMMAND_LIST_ARG_1", "&cYou need to select a group."),
    //Error not a console command
    ERROR_NOT_A_CONSOLE_COMMAND("ERROR_NOT_A_CONSOLE_COMMAND", "&cThis is not a console command."),
    //Error command add not a command
    ERROR_COMMAND_NOT_A_COMMAND("ERROR_COMMAND_NOT_A_COMMAND", "&cThe input &6{0} &cis not a command. You need to add a slash."),
    //Error command add command already added
    ERROR_COMMAND_ADD_DUPE("ERROR_COMMAND_ADD_DUPE", "&cThe command &6{0} &cis already set for the group &6{1}&c."),
    //Message command list header
    MESSAGE_COMMAND_LIST_HEADER("MESSAGE_COMMAND_LIST_HEADER", "&e&l---<< &9&l{0} &e&l>>---"),
    //Message command list command
    MESSAGE_COMMAND_LIST_COMMAND("MESSAGE_COMMAND_LIST_COMMAND", "&b{0}&7. &6{1}"),
    //Message command list next page
    MESSAGE_COMMAND_LIST_NEXT_PAGE("MESSAGE_COMMAND_LIST_NEXT_PAGE", "&e&lNext >>---"),
    //Message command list next page hover
    MESSAGE_COMMAND_LIST_NEXT_PAGE_HOVER("MESSAGE_COMMAND_LIST_NEXT_PAGE_HOVER", "&6&lNext Page"),
    //Message command list previous page
    MESSAGE_COMMAND_LIST_PREVIOUS_PAGE("MESSAGE_COMMAND_LIST_PREVIOUS_PAGE", "&e&l---<< Prev"),
    //Message command list previous page hover
    MESSAGE_COMMAND_LIST_PREVIOUS_PAGE_HOVER("MESSAGE_COMMAND_LIST_PREVIOUS_PAGE_HOVER", "&6&lPrevious Page"),
    //Message command add successful
    MESSAGE_COMMAND_ADD_SUCCESSFUL("MESSAGE_COMMAND_ADD_SUCCESSFUL", "&aYou successfully added the command &6{0} &ato the group &6{1}&a!"),
    //Message command remove successful
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