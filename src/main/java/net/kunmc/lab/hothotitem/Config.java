package net.kunmc.lab.hothotitem;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private long time = 3;
    private long period = 10;
    private long damageOffset = 0;

    Config() {
        loadConfig(false);
    }

    public void loadConfig() {
        loadConfig(true);
    }

    public void loadConfig(boolean isReload) {
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
            // do nothing
        }
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        if (time > 0) this.time = time;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        if (period > 0) this.period = period;
    }

    public long getDamageOffset() {
        return damageOffset;
    }

    public void setDamageOffset(long damageOffset) {
        if (damageOffset >= 0) this.damageOffset = damageOffset;
    }
}
