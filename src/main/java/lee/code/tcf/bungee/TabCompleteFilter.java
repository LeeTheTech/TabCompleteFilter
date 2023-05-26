package lee.code.tcf.bungee;

import lee.code.tcf.bungee.listeners.FilterListener;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

public class TabCompleteFilter extends Plugin {

    @Getter private Data data;

    @Override
    public void onEnable() {
        this.data = new Data();

        registerListeners();
    }

    private void registerListeners() {
        getProxy().getPluginManager().registerListener(this, new FilterListener());
    }

}
