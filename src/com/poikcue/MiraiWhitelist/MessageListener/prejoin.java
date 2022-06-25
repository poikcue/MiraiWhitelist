package com.poikcue.MiraiWhitelist.MessageListener;

import com.poikcue.MiraiWhitelist.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

import static net.md_5.bungee.api.ChatColor.translateAlternateColorCodes;

public class prejoin implements Listener {
    private Main plugin;

    public prejoin(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent e) {
        UUID uuid = e.getUniqueId();
        Bukkit.getLogger().info("[MiraiWhitelist] Player of " + e.getName() + " (UUID:" + uuid + ") try to log in.");
        if(plugin.getConfig().getString("General.Enable.BuildInWhitelist") == "true") {
            plugin.reloadConfig();
            if (plugin.getConfig().getString("Whitelist." + uuid) == null ) {
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, translateAlternateColorCodes('&', plugin.getConfig().getString("Message.HaveNotWhitelist")));
                Bukkit.getLogger().info("[MiraiWhitelist] Player of " + e.getName() + " (UUID:" + uuid + ") don't have permission. Cancelled.");
            }
            else{
                Bukkit.getLogger().info("[MiraiWhitelist] Player of " + e.getName() + " (UUID:" + uuid + ") Logged in.");
            }
        }
    }
}