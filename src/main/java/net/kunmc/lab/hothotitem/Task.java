package net.kunmc.lab.hothotitem;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Task extends BukkitRunnable {
    // item holding times (ticks)
    private Map<Player, Integer> holdingTimes = new HashMap<>();

    private HotHotItem plugin;
    private Config config;

    Task() {
        plugin = HotHotItem.plugin;
        config = plugin.config;
    }

    private long countFilledSlots(Player player) {
        return Arrays.stream(player.getInventory().getContents()).filter(v -> v != null).count();
    }

    private boolean validatePlayer(Player player) {
        if (!player.isOnline()) return false;
        if (player.getGameMode() == GameMode.CREATIVE) return false;
        if (player.getGameMode() == GameMode.SPECTATOR) return false;
        return true;
    }

    private boolean isInWater(Player player) {
        Material material = player.getLocation().getBlock().getType();
        return material == Material.WATER;
    }

    private void hurtPlayer(Player player, long count) {
        if (count == 0) return;
        double damage = config.getCoefficient() * (count - 1) + config.getDamage();
        player.damage(damage);
    }

    @Override
    public void run() {
        int period = config.getPeriod();
        double time = config.getTime();

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            if (!validatePlayer(player)) continue;

            if (!holdingTimes.containsKey(player)) holdingTimes.put(player, 0);

            long count = countFilledSlots(player);

            if (count == 0) {
                holdingTimes.put(player, 0);
                continue;
            }

            // get holding time (ticks) and update
            int holdingTime = holdingTimes.get(player) + period;
            holdingTime = Math.min(holdingTime, (int) (time * 20) + 1); // prevent overflow
            holdingTimes.put(player, holdingTime);

            // if player is in the water, damage is decreased.
            if (isInWater(player)) --count;

            // if the player has item(s) too long...
            if (holdingTime > time * 20) {
                player.setFireTicks(period + 1);
                hurtPlayer(player, count);
            }

        }
    }
}
