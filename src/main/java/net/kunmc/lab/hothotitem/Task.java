package net.kunmc.lab.hothotitem;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Task extends BukkitRunnable {
    private Map<Player, Integer> count = new HashMap<>();

    private boolean hasItem(Player player) {
        PlayerInventory inventory = player.getInventory();
        return Arrays.stream(inventory.getContents()).anyMatch(v -> v != null);
    }

    @Override
    public void run() {
        HotHotItem plugin = HotHotItem.plugin;
        int period = plugin.config.getPeriod();
        int time = plugin.config.getTime();

        for (Player player : plugin.getServer().getOnlinePlayers()) {
            // player validations
            if (!player.isOnline()) continue;
            if (player.getGameMode() == GameMode.CREATIVE) continue;
            if (player.getGameMode() == GameMode.SPECTATOR) continue;

            if (!count.containsKey(player)) count.put(player, 0);

            // get holding time
            int holdingTime = count.get(player);

            holdingTime = hasItem(player) ? holdingTime + period : 0;

            // if player has item(s) too long...
            if (holdingTime > time * 20) {
                player.setFireTicks(period + 1);
                holdingTime = time * 20; // prevent overflow
            }

            count.put(player, holdingTime);
        }
    }
}
