package dev.lee.tcf.commands.subcommands;

import dev.lee.tcf.TabCompleteFilter;
import dev.lee.tcf.commands.SubCommand;
import dev.lee.tcf.files.files.Args;
import dev.lee.tcf.files.files.Lang;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCMD extends SubCommand {

  @Override
  public String getName() {
    return "reload";
  }

  @Override
  public String getDescription() {
    return Lang.CMD_RELOAD_DESCRIPTION.getString();
  }

  @Override
  public String getUsage() {
    return "/tcf reload";
  }

  @Override
  public String getPermission() {
    return "tcf.command.reload";
  }

  @Override
  public void perform(Player player, String[] args) {
    reload(player);
  }

  @Override
  public void performConsole(CommandSender console, String[] args) {
    reload(console);
  }

  private void reload(CommandSender sender) {
    TabCompleteFilter.getInstance().getFileData().loadFiles();
    if (Args.ENABLED.getBoolean()) TabCompleteFilter.getInstance().registerCustomTabCompletes();
    sender.sendMessage(Lang.PREFIX.getString() + Lang.CMD_RELOAD_SUCCESSFUL.getString());
  }
}
