package com.poikcue.MiraiWhitelist.MessageListener;

import com.poikcue.MiraiWhitelist.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

import static org.bukkit.ChatColor.*;

public class prejoin implements Listener {
    private Main plugin;

    public prejoin(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent e) {
        UUID uuid = e.getUniqueId();
        Bukkit.getLogger().info("[MiraiWhitelist] 玩家 " + e.getName() + " (通用单一标识符:" + uuid + ") 尝试登入");
        if(plugin.getConfig().getString("通用.可启用.内置白名单系统") == "启用") {
            plugin.reloadConfig();
            if (plugin.getConfig().getString("内置白名单列表." + uuid) == null ) {
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, translateAlternateColorCodes('&', plugin.getConfig().getString("文本消息.启用内置白名单系统后无白名单玩家踢出时屏幕显示的信息")));
                Bukkit.getLogger().info("[MiraiWhitelist] 玩家 " + e.getName() + " (通用单一标识符:" + uuid + ") 因为未在白名单内，已拒绝加入。");
            }
            else{
                Bukkit.getLogger().info("[MiraiWhitelist] 玩家 " + e.getName() + " (通用单一标识符:" + uuid + ") 已加入。");
            }
        }
    }
}