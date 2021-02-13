package net.kunmc.lab.hothotitem;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class HotHotItem extends JavaPlugin implements Listener {
    public static HotHotItem plugin;
    public Config config;
    private BukkitTask task;
    private boolean running = false;

    @Override
    public void onEnable() {
        plugin = this;
        config = new Config();

        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("hotitem").setExecutor(new CommandManager());
    }

    @Override
    public void onDisable() {
        stop();
    }

    public void start() {
        if (running) return;

        running = true;
        task = new Task().runTaskTimer(this, 0, config.getPeriod());
    }

    public void stop() {
        running = false;

        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    public boolean isRunning() {
        return running;
    }
}
