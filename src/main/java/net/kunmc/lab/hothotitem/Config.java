package net.kunmc.lab.hothotitem;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private HotHotItem plugin;
    private double time = 3;
    private int period = 20;
    private double damageOffset = 0;

    Config() {
        plugin = HotHotItem.plugin;
        loadConfig(false);
    }

    public boolean loadConfig() {
        return loadConfig(true);
    }

    public boolean loadConfig(boolean isReload) {
        HotHotItem plugin = HotHotItem.plugin;

        plugin.saveDefaultConfig();

        if (isReload) {
            plugin.reloadConfig();
        }

        FileConfiguration config = plugin.getConfig();

        try {
            setTime(config.getDouble("time"));
            setPeriod(config.getInt("period"));
            setDamageOffset(config.getDouble("offset"));
        } catch (Exception ignore) {
            return false;
        }

        return true;
    }

    public double getTime() {
        return time;
    }

    public boolean setTime(double time) {
        if (time <= 0) return false;

        this.time = time;
        return true;
    }

    public int getPeriod() {
        return period;
    }

    public boolean setPeriod(int period) {
        if (period <= 0) return false;

        this.period = period;

        if(plugin.isRunning()){
            plugin.stop();
            plugin.start();
        }

        return true;
    }

    public double getDamageOffset() {
        return damageOffset;
    }

    public boolean setDamageOffset(double damageOffset) {
        if (damageOffset < 0) return false;

        this.damageOffset = damageOffset;
        return true;
    }
}
