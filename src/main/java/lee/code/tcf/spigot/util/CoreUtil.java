package lee.code.tcf.spigot.util;

import net.md_5.bungee.api.ChatColor;
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
  private final static Pattern numberIntPattern = Pattern.compile("^[1-9]\\d*$");

  public static String parseColorString(String text) {
    if (text == null) return "";
    if (isSupported(16)) {
      Matcher matcher = hexPattern.matcher(text);
      while (matcher.find()) {
        final String color = text.substring(matcher.start(), matcher.end()).replaceAll("&", "");
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
    final int lastDot = version.lastIndexOf('.');
    if (version.indexOf('.') != lastDot) version = version.substring(0, lastDot);
    return version;
  }

  public static boolean isSupported(int version) {
    return mcVersion >= version;
  }

  public static boolean isPositiveIntNumber(String numbers) {
    final String intMax = String.valueOf(Integer.MAX_VALUE);
    if (numbers.length() > intMax.length() || (numbers.length() == intMax.length() && numbers.compareTo(intMax) > 0)) return false;
    return numberIntPattern.matcher(numbers).matches();
  }

  public static String getTextBeforeCharacter(String input, char character) {
    final int index = input.indexOf(character);
    if (index == -1) return input;
    else return input.substring(0, index);
  }

  public static List<String> getOnlinePlayers() {
    final List<String> players = new ArrayList<>();
    for (Player player : Bukkit.getOnlinePlayers()) {
      if (!player.getGameMode().equals(GameMode.SPECTATOR)) players.add(player.getName());
    }
    return players;
  }
}
