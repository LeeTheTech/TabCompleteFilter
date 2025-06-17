package dev.lee.tcf.files.files;

import dev.lee.tcf.TabCompleteFilter;
import dev.lee.tcf.util.CoreUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.chat.TextComponent;

@Getter
@AllArgsConstructor
public enum Lang {
  PREFIX("&6&lTCF &e➔ &r"),
  USAGE("&6&lUsage&7: &e{0}"),
  NEXT_PAGE_TEXT("&2&lNext &a&l>>&a---------"),
  PREVIOUS_PAGE_TEXT("&a---------&a&l<< &2&lPrev"),
  PAGE_SPACER_TEXT(" &e| "),
  NEXT_PAGE_HOVER("&6&lNext Page"),
  PREVIOUS_PAGE_HOVER("&6&lPrevious Page"),

  CMD_HELP_DIVIDER("&a▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"),
  CMD_HELP_TITLE("             &2-== &6&l&nTabCompleteFilter Help&r &2==-"),
  CMD_HELP_SUB_COMMAND("&3{0}&b. &e{1}"),
  CMD_HELP_SUB_COMMAND_HOVER("&6{0}"),
  CMD_RELOAD_SUCCESSFUL("&aThe plugin has been reloaded."),
  CMD_LIST_HEADER_SPLITTER("&a&l------------------------------"),
  CMD_LIST_HEADER_GROUP("&6&lGroup&7: &9{0}"),
  CMD_LIST_LINE("&3{0}&b. &e{1}"),
  CMD_ADD_SUCCESSFUL("&aYou successfully added the command &3{0} &ato the group &3{1}&a!"),
  CMD_REMOVE_SUCCESSFUL("&aYou successfully removed the command &3{0} &afrom the group &3{1}&a!"),
  CMD_REMOVE_DESCRIPTION("Remove an existing group command."),
  CMD_RELOAD_DESCRIPTION("Reloads the plugin yml files."),
  CMD_HELP_DESCRIPTION("A list of all plugin commands."),
  CMD_LIST_DESCRIPTION("List of all group commands."),
  CMD_ADD_DESCRIPTION("Add a command to a group."),

  ERROR_NO_PERMISSION("&cYou sadly do not have permission for this."),
  ERROR_COMMAND_REMOVE_DOES_NOT_EXIST("&cThe command &3{0} &ccould not be found in the group &3{1}&c."),
  ERROR_GROUP_DOES_NOT_EXIST("&cThe group &3{0} &cdoes not exist."),
  ERROR_NOT_A_CONSOLE_COMMAND("&cThis is not a console command."),
  ERROR_COMMAND_NOT_A_COMMAND("&cThe input &3{0} &cis not a command. You need to add a slash."),
  ERROR_COMMAND_ADD_DUPE("&cThe command &3{0} &cis already set for the group &3{1}&c."),
  ERROR_CUSTOM_TAB_COMPLETE_NOT_REGISTERED("The command ({0}) is not registered so a custom tab-complete handler could not be registered for this command."),
  ;

  private final Object object;

  public String getPath() {
    return name();
  }

  public String getString() {
    return CoreUtil.parseColor(TabCompleteFilter.getInstance().getFileManager().getString("lang", getPath()));
  }

  public String getString(String[] variables) {
    String text = getString();
    if (variables == null || variables.length == 0) return text;
    for (int i = 0; i < variables.length; i++) text = text.replace("{" + i + "}", variables[i]);
    return CoreUtil.parseColor(text);
  }

  public TextComponent getTextComponent() {
    return new TextComponent(getString());
  }

  public TextComponent getTextComponent(String[] variables) {
    return new TextComponent(getString(variables));
  }
}
