package com.poikcue.MiraiWhitelist.MessageListener;

import com.poikcue.MiraiWhitelist.Main;
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
        if(plugin.getConfig().getString("General.Enable.BuildInWhitelist") == "true") {
            if (plugin.getConfig().getString("Whitelist." + uuid) == null || plugin.getConfig().getString("Whitelist." + uuid) != "true") {
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, translateAlternateColorCodes('&', plugin.getConfig().getString("Message.HaveNotWhitelist")));
            }
        }
    }
}