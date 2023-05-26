package lee.code.tcf.bungee;

import lee.code.tcf.bungee.listeners.FilterListener;
import net.md_5.bungee.api.plugin.Plugin;

public class TabCompleteFilter extends Plugin {

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, new FilterListener());
    }

}
