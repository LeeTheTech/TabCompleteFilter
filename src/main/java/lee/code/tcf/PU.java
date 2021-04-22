package lee.code.tcf;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class PU {

    private final int VERSION = Integer.parseInt(getMajorVersion(Bukkit.getVersion()).substring(2));

    public String format(String format) {
        return ChatColor.translateAlternateColorCodes('&', format);
    }

    private String getMajorVersion(String version) {
        Validate.notEmpty(version, "Cannot get major Minecraft version from null or empty string");

        // getVersion()
        int index = version.lastIndexOf("MC:");
        if (index != -1) {
            version = version.substring(index + 4, version.length() - 1);
        } else if (version.endsWith("SNAPSHOT")) {
            // getBukkitVersion()
            index = version.indexOf('-');
            version = version.substring(0, index);
        }

        // 1.13.2, 1.14.4, etc...
        int lastDot = version.lastIndexOf('.');
        if (version.indexOf('.') != lastDot) version = version.substring(0, lastDot);

        return version;
    }

    public boolean supports(int version) {
        return VERSION >= version;
    }
}
