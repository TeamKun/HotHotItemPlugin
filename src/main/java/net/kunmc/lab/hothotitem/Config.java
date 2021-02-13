package net.kunmc.lab.hothotitem;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private long time = 3;
    private long period = 10;
    private long damageOffset = 0;

    Config() {
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
            setTime(config.getLong("time"));
            setPeriod(config.getLong("period"));
            setDamageOffset(config.getLong("offset"));
        } catch (Exception ignore) {
            return false;
        }

        return true;
    }

    public long getTime() {
        return time;
    }

    public boolean setTime(long time) {
        if (time <= 0) return false;

        this.time = time;
        return true;
    }

    public long getPeriod() {
        return period;
    }

    public boolean setPeriod(long period) {
        if (period <= 0) return false;

        this.period = period;
        return true;
    }

    public long getDamageOffset() {
        return damageOffset;
    }

    public boolean setDamageOffset(long damageOffset) {
        if (damageOffset < 0) return false;

        this.damageOffset = damageOffset;
        return true;
    }
}
