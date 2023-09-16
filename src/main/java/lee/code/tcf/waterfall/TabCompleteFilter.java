package lee.code.tcf.waterfall;

import lee.code.tcf.waterfall.files.ConfigManager;
import lee.code.tcf.waterfall.listeners.FilterListener;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public class TabCompleteFilter extends Plugin {
  @Getter private ConfigManager configManager;

  @Override
  public void onEnable() {
    this.configManager = new ConfigManager(this);

    getConfigManager().loadConfig();
    getProxy().getPluginManager().registerListener(this, new FilterListener(this));
  }
}
