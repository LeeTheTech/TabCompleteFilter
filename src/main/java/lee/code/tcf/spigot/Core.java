package lee.code.tcf.spigot;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Core {
    @Getter private final static int mcVersion = Integer.parseInt(getMajorVersion(Bukkit.getVersion()).substring(2));
    @Getter private final static Pattern hexPattern = Pattern.compile("\\&#[a-fA-F0-9]{6}");

    public static String parseColorString(String text) {
        if (text == null) return "";
        if (isSupported(16)) {
            Matcher matcher = getHexPattern().matcher(text);

            while (matcher.find()) {
                final String color = text.substring(matcher.start(), matcher.end()).replaceAll("&", "");
                text = text.replace("&" + color, net.md_5.bungee.api.ChatColor.of(color) + "");
                matcher = getHexPattern().matcher(text);
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
        return getMcVersion() >= version;
    }

    public static boolean containOnlyIntNumbers(String string) {
        return string.matches("[0-9]+");
    }

    public static String getTextBeforeCharacter(String input, char character) {
        final int index = input.indexOf(character);
        if (index == -1) return input;
        else return input.substring(0, index);
    }
}
