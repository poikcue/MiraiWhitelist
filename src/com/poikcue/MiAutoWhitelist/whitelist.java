package com.poikcue.MiAutoWhitelist;

import me.dreamvoid.miraimc.bukkit.event.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bukkit.Bukkit.*;

public class whitelist implements Listener {
    @EventHandler
    public void onMessageGet(MiraiGroupMessageEvent e) throws IOException {
            if (e.getMessage().contains(Main.getInstance().getConfig().getString("Message.ReplaceFriendMessage"))) {
                String name = e.getMessage().replace(Main.getInstance().getConfig().getString("Message.ReplaceFriendMessage"), "");
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    e.sendMessage(Main.getInstance().getConfig().getString("Message.PremiumMinecraftAccountMessage"));
                    getScheduler().runTask(Main.getInstance(), () -> dispatchCommand(getConsoleSender(), "whitelist add " + name));
                    //正版玩家
                } else if (connection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
                    e.sendMessage(Main.getInstance().getConfig().getString("Message.CrackedMinecraftAccountAlert"));
                    //正常访问，但是离线玩家
                } else {
                    e.sendMessage(Main.getInstance().getConfig().getString("Message.UrlError"));
                    //出现问题，访问异常
                }
            }
        }
    }
