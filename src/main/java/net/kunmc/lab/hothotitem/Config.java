package net.kunmc.lab.hothotitem;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private HotHotItem plugin;
    private double time = 3;
    private int period = 20;
    private double damage = 1.0;
    private double coefficient = 0;

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
            setDamage(config.getDouble("damage"));
            setCoefficient(config.getDouble("coefficient"));
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

        if (plugin.isRunning()) {
            plugin.stop();
            plugin.start();
        }

        return true;
    }

    public double getDamage() {
        return damage;
    }

    public boolean setDamage(double damage) {
        if (damage < 0) return false;

        this.damage = damage;
        return true;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public boolean setCoefficient(double coefficient) {
        if (coefficient < 0) return false;

        this.coefficient = coefficient;
        return true;
    }
}
