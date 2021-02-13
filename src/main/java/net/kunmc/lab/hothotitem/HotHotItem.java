package net.kunmc.lab.hothotitem;

import org.bukkit.plugin.java.JavaPlugin;

public final class HotHotItem extends JavaPlugin {
    public static HotHotItem plugin;
    public Config config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        config = new Config();

        System.out.printf("time: %d%n", config.getTime());
        System.out.printf("period: %d%n", config.getPeriod());
        System.out.printf("offset: %d%n", config.getDamageOffset());
    }
}
