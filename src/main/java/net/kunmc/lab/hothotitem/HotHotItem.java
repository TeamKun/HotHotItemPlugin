package net.kunmc.lab.hothotitem;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;

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

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!isRunning()) return;

        EntityDamageEvent.DamageCause cause = event.getCause();
        if (cause != EntityDamageEvent.DamageCause.FIRE_TICK) return;

        Entity entity = event.getEntity();
        if (!(entity instanceof Player)) return;

        Player player = (Player) entity;
        if (player.getGameMode() == GameMode.CREATIVE) return;
        if (player.getGameMode() == GameMode.SPECTATOR) return;

        long count = Arrays.stream(player.getInventory().getContents()).filter(v -> v != null).count();
        double damage = config.getCoefficient() * (count - 1) + config.getDamage();
        event.setDamage(damage);
    }
}
