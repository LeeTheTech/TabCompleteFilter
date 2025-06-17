package dev.lee.tcf.util;

import dev.lee.tcf.files.files.Lang;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoreUtil {
  private final static int mcVersion = Integer.parseInt(getMajorVersion(Bukkit.getVersion()).substring(2));
  private final static Pattern hexPattern = Pattern.compile("\\&#[a-fA-F0-9]{6}");

  public static String parseColor(String text) {
    if (text == null) return "";
    if (isSupported(16)) {
      Matcher matcher = hexPattern.matcher(text);
      while (matcher.find()) {
        String color = text.substring(matcher.start(), matcher.end()).replaceAll("&", "");
        text = text.replace("&" + color, net.md_5.bungee.api.ChatColor.of(color) + "");
        matcher = hexPattern.matcher(text);
      }
    }
    return ChatColor.translateAlternateColorCodes('&', text);
  }

  private static String getMajorVersion(String version) {
    if (version == null) return null;
    int index = version.lastIndexOf("MC:");
    if (index != -1) {
      version = version.substring(index + 4, version.length() - 1);
    } else if (version.endsWith("SNAPSHOT")) {
      index = version.indexOf('-');
      version = version.substring(0, index);
    }
    int lastDot = version.lastIndexOf('.');
    if (version.indexOf('.') != lastDot) version = version.substring(0, lastDot);
    return version;
  }

  public static boolean isSupported(int version) {
    return mcVersion >= version;
  }

  public static boolean isPositiveInt(String input) {
    if (input == null || input.isEmpty()) return false;
    try {
      int value = Integer.parseInt(input);
      return value > 0;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  public static List<String> getOnlinePlayers() {
    List<String> players = new ArrayList<>();
    for (Player player : Bukkit.getOnlinePlayers()) {
      if (!player.getGameMode().equals(GameMode.SPECTATOR)) players.add(player.getName());
    }
    return players;
  }

  public static TextComponent createHoverMessage(String message, String hoverText) {
    TextComponent messageComponent = new TextComponent(parseColor(message));
    HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(parseColor(hoverText)).create());
    messageComponent.setHoverEvent(hoverEvent);
    return messageComponent;
  }

  public static TextComponent createHoverClickMessage(String message, String hoverText, String command) {
    TextComponent messageComponent = createHoverMessage(message, hoverText);
    ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, command);
    messageComponent.setClickEvent(clickEvent);
    return messageComponent;
  }

  public static TextComponent createPageSelectionMessage(String command, int page) {
    TextComponent prev = createHoverClickMessage(Lang.PREVIOUS_PAGE_TEXT.getString(), Lang.PREVIOUS_PAGE_HOVER.getString(), command + " " + (page - 1));
    TextComponent split = createHoverMessage(Lang.PAGE_SPACER_TEXT.getString(), "");
    TextComponent next = createHoverClickMessage(Lang.NEXT_PAGE_TEXT.getString(), Lang.NEXT_PAGE_HOVER.getString(), command + " " + (page + 1));
    prev.addExtra(split);
    prev.addExtra(next);
    return prev;
  }
}
