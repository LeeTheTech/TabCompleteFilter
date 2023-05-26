package lee.code.tcf.bungee;

import lee.code.tcf.bungee.files.ConfigManager;
import lee.code.tcf.bungee.listeners.FilterListener;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public class TabCompleteFilter extends Plugin {

    @Getter private ConfigManager configManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);

        getConfigManager().loadConfig();
        getProxy().getPluginManager().registerListener(this, new FilterListener(this));
    }
}
