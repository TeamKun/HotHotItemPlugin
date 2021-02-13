package net.kunmc.lab.hothotitem;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandManager implements CommandExecutor, TabCompleter {
    private final String prefixAccept = ChatColor.GREEN + "[HotHotItem]" + ChatColor.RESET + " ";
    private final String prefixReject = ChatColor.RED + "[HotHotItem]" + ChatColor.RESET + " ";

    private boolean same(String a, String b) {
        return a.equals(b);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length <= 0)
            return false;

        // help
        if (same(args[0], "help")) {
            final String[] HELP_MESSAGE = {
                    "----------------- [ " + ChatColor.GREEN + "HotHotItem Plugin" + ChatColor.RESET + " ] -----------------",
                    "/hotitem help : ヘルプ表示",
                    "/hotitem start : プラグインを有効化",
                    "/hotitem stop : プラグインを無効化",
                    "/hotitem time < double > : 耐えられる時間 (sec)",
                    "/hotitem period < int > : インベントリチェックのインターバル (ticks)",
                    "/hotitem offset < double > : 与ダメージのゲタはかせ",
                    "/hotitem loadconfig : コンフィグの読み出し（リロード）",
                    "-----------------------------------------------------",
            };
            Stream.of(HELP_MESSAGE).forEach(sender::sendMessage);
            return true;
        }

        // start
        if (same(args[0], "start")) {
            HotHotItem.plugin.start();
            sender.sendMessage(prefixAccept + "Plugin is started.");
            return true;
        }

        // stop
        if (same(args[0], "stop")) {
            HotHotItem.plugin.stop();
            sender.sendMessage(prefixAccept + "Plugin is stopped.");
            return true;
        }

        // time <double>
        if (args.length == 2 && same(args[0], "time")) {
            try {
                double value = Double.parseDouble(args[1]);

                if (HotHotItem.plugin.config.setTime(value)) {
                    sender.sendMessage(String.format("%s\"time\" が %s に設定されました。", prefixAccept, args[1]));
                    return true;
                }
            } catch (Exception ignore) {
                // do nothing
            }

            sender.sendMessage(prefixReject + " 不正な引数です。");
            return false;
        }

        // period <int>
        if (args.length == 2 && same(args[0], "period")) {
            try {
                int value = Integer.parseInt(args[1]);

                if (HotHotItem.plugin.config.setPeriod(value)) {
                    sender.sendMessage(String.format("%s\"period\" が %s に設定されました。", prefixAccept, args[1]));
                    return true;
                }
            } catch (Exception ignore) {
                // do nothing
            }

            sender.sendMessage(prefixReject + "不正な引数です。");
            return false;
        }

        // offset <double>
        if (args.length == 2 && same(args[0], "offset")) {
            try {
                double value = Double.parseDouble(args[1]);
                if (HotHotItem.plugin.config.setDamageOffset(value)) {
                    sender.sendMessage(String.format("%s\"offset\" が %s に設定されました。", prefixAccept, args[1]));
                    return true;
                }
            } catch (Exception ignore) {
                // do nothing
            }

            sender.sendMessage(prefixReject + "不正な引数です。");
            return false;
        }

        // loadconfig
        if (same(args[0], "loadconfig")) {
            if (HotHotItem.plugin.config.loadConfig()) {
                sender.sendMessage(prefixAccept + "Config is loaded.");
                return true;
            }
            sender.sendMessage(prefixReject + "Failed to load config.");
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Stream.of("help", "loadconfig", "start", "stop", "time", "period", "offset")
                    .filter(e -> e.startsWith(args[0]))
                    .collect(Collectors.toList());
        }

        if (args.length == 2 && same(args[0], "time")) {
            if (args[1].length() == 0) {
                String suggestion = String.valueOf(HotHotItem.plugin.config.getTime());
                return Collections.singletonList(suggestion);
            }
        }

        // period
        if (args.length == 2 && same(args[0], "period")) {
            if (args[1].length() == 0) {
                String suggestion = String.valueOf(HotHotItem.plugin.config.getPeriod());
                return Collections.singletonList(suggestion);
            }
        }

        // offset
        if (args.length == 2 && same(args[0], "offset")) {
            if (args[1].length() == 0) {
                String suggestion = String.valueOf(HotHotItem.plugin.config.getDamageOffset());
                return Collections.singletonList(suggestion);
            }
        }

        return null;
    }
}