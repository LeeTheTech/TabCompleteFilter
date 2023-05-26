package lee.code.tcf.spigot.files.files;

import lee.code.tcf.spigot.TabCompleteFilter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.chat.TextComponent;

@AllArgsConstructor
public enum FileLang {
    PREFIX("prefix", "&6&lTCF &e➔ &r"),
    COMMAND_HELP_DIVIDER("command_help_divider", "&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
    COMMAND_HELP_TITLE("command_help_title", "           &2-== &6&l&nTabCompleteFilter Help&r &2==-"),
    COMMAND_HELP_SUB_COMMAND("command_help_sub_command", "&3{0}&b. &e{1}"),
    COMMAND_HELP_SUB_COMMAND_HOVER("command_help_sub_command_hover", "&6{0}"),
    COMMAND_RELOAD_SUCCESSFUL("command_reload_successful", "&aThe plugin has been reloaded."),
    COMMAND_LIST_HEADER("command_list_header", "&a&l----------<< &9&l{0} &a&l>>----------"),
    COMMAND_LIST_LINE("command_list_line", "&3{0}&b. &6{1}"),
    COMMAND_LIST_NEXT_PAGE("command_list_next_page", "&2&lNext &a&l>>--------"),
    COMMAND_LIST_NEXT_PAGE_HOVER("command_list_next_page_hover", "&6&lNext Page"),
    COMMAND_LIST_PAGE_SPACER("command_list_page_spacer", " &e| "),
    COMMAND_LIST_PREVIOUS_PAGE("command_list_previous_page", "&a&l--------<< &2&lPrev"),
    COMMAND_LIST_PREVIOUS_PAGE_HOVER("command_list_previous_page_hover", "&6&lPrevious Page"),
    COMMAND_ADD_SUCCESSFUL("command_add_successful", "&aYou successfully added the command &6{0} &ato the group &6{1}&a!"),
    COMMAND_REMOVE_SUCCESSFUL("command_remove_successful", "&aYou successfully removed the command &6{0} &afrom the group &6{1}&a!"),
    COMMAND_REMOVE_DESCRIPTION("command_remove_description", "Remove an existing group."),
    COMMAND_RELOAD_DESCRIPTION("command_reload_description", "Reloads the TabCompleteFilter plugin, this will read all yml files again and apply changes if any."),
    COMMAND_HELP_DESCRIPTION("command_help_description", "A list of commands you can use with this plugin."),
    COMMAND_LIST_DESCRIPTION("command_list_description", "List of all groups and permissions."),
    COMMAND_ADD_DESCRIPTION("command_add_description", "Add commands to groups."),
    ERROR_NO_PERMISSION("error_no_permission", "&cYou sadly do not have permission for this."),
    ERROR_COMMAND_REMOVE_ARG_1("error_command_remove_arg_1", "&cYou need to select a group and input a command to remove."),
    ERROR_COMMAND_REMOVE_ARG_2("error_command_remove_arg_2", "&cYou need to input a command to remove."),
    ERROR_COMMAND_REMOVE_DOES_NOT_EXIST("error_command_remove_does_not_exist", "&cThe command &6{0} &ccould not be found in the group &6{1}&c."),
    ERROR_COMMAND_ADD_ARG_1("error_command_add_arg_1", "&cYou need to select a group and input a command to add."),
    ERROR_COMMAND_ADD_ARG_2("error_command_add_arg_2", "&cYou need to input a command to add."),
    ERROR_COMMAND_LIST_PAGE("error_command_list_page", "&cThe input &6{0} &cis not a number."),
    ERROR_GROUP_DOES_NOT_EXIST("error_group_does_not_exist", "&cThe group &6{0} &cdoes not exist."),
    ERROR_COMMAND_LIST_ARG_1("error_command_list_arg_1", "&cYou need to select a group."),
    ERROR_NOT_A_CONSOLE_COMMAND("error_not_a_console_command", "&cThis is not a console command."),
    ERROR_COMMAND_NOT_A_COMMAND("error_command_not_a_command", "&cThe input &6{0} &cis not a command. You need to add a slash."),
    ERROR_COMMAND_ADD_DUPE("error_command_add_dupe", "&cThe command &6{0} &cis already set for the group &6{1}&c."),
    ;

    @Getter private final String path;
    @Getter private final String string;

    public String getString(String[] variables) {
        return TabCompleteFilter.getPlugin().getFileManager().getStringFromFile(File.LANG.name().toLowerCase(), getPath(), variables);
    }

    public TextComponent getTextComponent(String[] variables) {
        return new TextComponent(TabCompleteFilter.getPlugin().getFileManager().getStringFromFile(File.LANG.name().toLowerCase(), getPath(), variables));
    }
}