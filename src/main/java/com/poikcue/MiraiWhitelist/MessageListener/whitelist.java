package com.poikcue.MiraiWhitelist.MessageListener;
import com.poikcue.MiraiWhitelist.Main;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import static org.bukkit.Bukkit.*;

public class whitelist implements Listener {
    private Main plugin;
    public whitelist(Main plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onMessageGet(MiraiGroupMessageEvent e) throws Exception {

        if (e.getMessage().startsWith(plugin.getConfig().getString("Message.ReplaceGroupMessageTAG"))) {
             String name = e.getMessage().replace(plugin.getConfig().getString("Message.ReplaceGroupMessageTAG"), "").replaceAll(" ", "");
             String sender = String.valueOf(e.getSenderID());
             URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
             HttpURLConnection connection = (HttpURLConnection) url.openConnection();
             UUID uuid = Bukkit.getOfflinePlayer(name).getUniqueId();
             connection.setRequestMethod("GET");
             if (plugin.getConfig().get("Data." + sender) == null) {
                 if (plugin.getConfig().getString("Whitelist." + uuid) == null) {
                     if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                         MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("Message.PremiumMinecraftAccountMessage").replaceAll("%ID%", name).replaceAll("%name%", e.getSenderName()));
                         getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
                             @Override
                             public void run() {
                                 plugin.getConfig().set("Data." + sender + ".UUID", String.valueOf(uuid));
                                 plugin.getConfig().set("Whitelist." + uuid, "true");
                                 plugin.saveConfig();
                             }
                         });
                         if (plugin.getConfig().getString("General.WhitelistCommand") == "Vanilla" && plugin.getConfig().getString("General.Enable.BuildInWhitelist") == "false") {
                             getScheduler().runTask(Main.getInstance(), () -> dispatchCommand(getConsoleSender(), "whitelist add " + name));
                         } else if(plugin.getConfig().getString("General.Enable.BuildInWhitelist") == "false") {
                             String command = plugin.getConfig().getString("General.WhitelistCommand");
                             getScheduler().runTask(Main.getInstance(), () -> dispatchCommand(getConsoleSender(), command.replaceAll("%ID%", name)));
                         }
                         //正版玩家
                     } else if (connection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
                         MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("Message.CrackedMinecraftAccountAlert").replaceAll("%ID%", name).replaceAll("%name%", e.getSenderName()));
                         //正常访问，但是是离线玩家
                     } else {
                         MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("Message.UrlError").replaceAll("%ID%", name).replaceAll("%name%", e.getSenderName()));
                         //出现问题，访问异常
                     }
                 }else{
                     MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("Message.AlreadyHave").replaceAll("%ID%", name).replaceAll("%name%", e.getSenderName()));
                 }
             }else {
                 MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("Message.AlreadySignedUp").replaceAll("%ID%", name).replaceAll("%name%", e.getSenderName()));
             }
        }
    }
}



