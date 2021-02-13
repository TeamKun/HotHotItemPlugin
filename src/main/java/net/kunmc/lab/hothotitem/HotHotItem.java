package net.kunmc.lab.hothotitem;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class HotHotItem extends JavaPlugin implements Listener {
    public static HotHotItem plugin;
    public Config config;
    public boolean running = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        config = new Config();

        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("hotitem").setExecutor(new CommandManager());
    }

    public void start(){
        if(running) return;
        running = true;
    }

    public void stop(){
        running = false;
    }
}
