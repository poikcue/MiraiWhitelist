package com.poikcue.MiraiWhitelist.Command;

import com.poikcue.MiraiWhitelist.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

import static org.bukkit.Bukkit.*;
import static org.bukkit.ChatColor.*;

public class command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (commandSender.hasPermission("MiraiWhitelist.admin")) {
                commandSender.sendMessage(AQUA + "MiraiWhitelist" + DARK_GRAY + " 作者 会挽雕弓如满月 西北望 射天狼");
                commandSender.sendMessage(WHITE + " - /miraiwhitelist reload 重新加载插件。");
                commandSender.sendMessage(WHITE + " - /miraiwhitelist force <QQ号码> <ID> 强制添加白名单。");
                commandSender.sendMessage(GRAY + "  - 此选项在 Mirai 指定群中依然生效。用法：%#<QQ号码> <ID>。");
                return true;
            } else {
                commandSender.sendMessage(AQUA + "MiraiWhitelist" + DARK_GRAY + " 作者 会挽雕弓如满月 西北望 射天狼");
            }
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (commandSender.hasPermission("MiraiWhitelist.admin")) {
                Main.getInstance().reloadConfig();
                commandSender.sendMessage(translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("文本消息.重新加载.成功时向操作者发送的消息")));
                return true;
            } else {
                commandSender.sendMessage(translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("文本消息.重新加载.请求者没有重新加载的权限")));
            }
            return true;
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("force")) {
                if(commandSender.hasPermission("MiraiWhitelist.admin")){
                    if (Main.getInstance().getConfig().get("数据." + args[1]) == null) {
                        getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                Main.getInstance().getConfig().set("数据." + args[1] + ".通用单一标识符", "[强制白名单]-" + args[2]);
                                UUID uuid = Bukkit.getOfflinePlayer(args[2]).getUniqueId();
                                Main.getInstance().getConfig().set("内置白名单列表." + uuid, "已加入");
                                Main.getInstance().saveConfig();
                            }
                        });
                        if (Main.getInstance().getConfig().getString("通用.运行白名单命令") == "我的世界原版服务端" && Main.getInstance().getConfig().getString("通用.可启用.内置白名单系统") == "不启用") {
                            getScheduler().runTask(Main.getInstance(), new Runnable() {
                                @Override
                                public void run() {
                                    dispatchCommand(getConsoleSender(), "whitelist add " + args[2]);
                                }
                            });
                        } else if (Main.getInstance().getConfig().getString("通用.可启用.内置白名单系统") == "不启用") {
                            String NonVanillaCommand = Main.getInstance().getConfig().getString("通用.运行白名单命令");
                            getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
                                @Override
                                public void run() {
                                    dispatchCommand(getConsoleSender(), NonVanillaCommand.replaceAll("%ID%", args[2]));
                                }
                            });
                        }
                        commandSender.sendMessage(translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("文本消息.游戏内强制添加白名单成功信息").replaceAll("%id%", args[2]).replaceAll("%qq%", args[1])));
                        return true;
                    } else {
                        commandSender.sendMessage(translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("文本消息.游戏内强制添加白名单但是已经申请过有效白名单").replaceAll("%id%", args[2]).replaceAll("%qq%", args[1])));
                        return true;
                    }
                } else {
                    commandSender.sendMessage(translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("文本消息.请求者没有强制添加白名单的权限")));
                }
            }
        }
        return true;
    }
}
