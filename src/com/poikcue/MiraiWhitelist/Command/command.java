package com.poikcue.MiraiWhitelist.Command;

import com.poikcue.MiraiWhitelist.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.md_5.bungee.api.ChatColor.*;
import static org.bukkit.Bukkit.*;

public class command implements CommandExecutor {
    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (commandSender.hasPermission("MiraiWhitelist.reload")) {
                commandSender.sendMessage(AQUA + "MiraiWhitelist" + DARK_GRAY + " by poikcue");
                commandSender.sendMessage(WHITE + " - /miraiwhitelist reload 重新加载插件。");
                commandSender.sendMessage(WHITE + " - /miraiwhitelist force <ID> 强制添加白名单。");
                commandSender.sendMessage(GRAY + "  - 此选项在 Mirai 指定群中依然生效。用法：!#Force <ID>。");
                return true;
            }else{
                commandSender.sendMessage(AQUA + "MiraiWhitelist" + WHITE + " by poikcue");
            }
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (commandSender.hasPermission("MiraiWhitelist.reload")) {
                Main.getInstance().reloadConfig();
                commandSender.sendMessage(translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("Message.Reload.Success")));
            } else {
                commandSender.sendMessage(translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("Message.Reload.Without-permission")));
            }
            return true;
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("force")) {
                if (Main.getInstance().getConfig().get("Data." + args[1]) == null) {
                    getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            Main.getInstance().getConfig().set("Data." + args[1] + ".UUID", "[Force]-" + args[2]);
                            Main.getInstance().saveConfig();
                        }
                    });
                    dispatchCommand(getConsoleSender(), "whitelist add " + args[2]);
                    commandSender.sendMessage(translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("Message.ForceInGameMessage").replaceAll("%id%", args[2]).replaceAll("%qq%", args[1])));
                    return true;
                }
                else{
                    commandSender.sendMessage(translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("Message.ForceInGameUnsuccessfulMessage").replaceAll("%id%", args[2]).replaceAll("%qq%", args[1])));
                }
            }
        }
        return true;
    }
}
