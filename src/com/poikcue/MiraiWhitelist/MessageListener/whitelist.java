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
                String name = e.getMessage().replace(plugin.getConfig().getString("Message.ReplaceGroupMessageTAG"), "");
                String sender = String.valueOf(e.getSenderID());
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                UUID uuid = Bukkit.getOfflinePlayer(name).getUniqueId();
                connection.setRequestMethod("GET");
                if (plugin.getConfig().get("Data." + sender) == null) {
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("Message.PremiumMinecraftAccountMessage"));
                        getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
                            @Override
                            public void run() {
                                plugin.getConfig().set("Data." + sender + ".UUID", String.valueOf(uuid));
                                plugin.saveConfig();
                            }
                        });
                        getScheduler().runTask(Main.getInstance(), () -> dispatchCommand(getConsoleSender(), "whitelist add " + name));
                        //正版玩家
                    } else if (connection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
                        MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("Message.CrackedMinecraftAccountAlert"));
                        //正常访问，但是是离线玩家
                    } else {
                        MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("Message.UrlError"));
                        //出现问题，访问异常
                    }
                }
                else {
                    MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("Message.AlreadySignedUp"));
                }
            }
    }
}



