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
        if(Main.getInstance().getConfig().getString("Group.WorkInGroup").equals(e.getGroup())){
            if (e.getMessage().contains(Main.getInstance().getConfig().getString("Message.ReplaceFriendMessage"))) {
                String name = e.getMessage().replace(Main.getInstance().getConfig().getString("Message.ReplaceFriendMessage"), "");
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    Yaml yml = new Yaml();
                    FileReader reader = new FileReader("plugins/MiraWhitelist/List.yml");
                    BufferedReader buffer = new BufferedReader(reader);
                    Map<String,Object> mapA = yml.load(buffer);
                    buffer.close();
                    reader.close();
                    if (mapA.get(e.getSenderID()) == null) {
                        e.sendMessage(Main.getInstance().getConfig().getString("Message.PremiumMinecraftAccountMessage"));
                        Map<String, Object> mapB = new HashMap<>();
                        mapB.put(e.getSenderName(), name);
                        FileWriter writer = new FileWriter("plugins/MiraWhitelist/List.yml", true);
                        BufferedWriter bufferW = new BufferedWriter(writer);
                        bufferW.newLine();
                        yml.dump(mapB, bufferW);
                        buffer.close();
                        writer.close();
                        getScheduler().runTask(Main.getInstance(), () -> dispatchCommand(getConsoleSender(), "whitelist add " + name));
                    }
                    else{
                        e.sendMessage(Main.getInstance().getConfig().getString("Message.AlreadySignedUp"));
                    }
                } else if (connection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
                    e.sendMessage(Main.getInstance().getConfig().getString("Message.CrackedMinecraftAccountAlert"));
                } else {
                    e.sendMessage(Main.getInstance().getConfig().getString("Message.UrlError"));
                }
            }
        }
    }
}

/*
History data:
File config = new File("plugins/MiraiWhitelist/config.yml");
                    YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(config);
                    List<String> list = yamlConfiguration.getStringList("List");
                    String whitelist = Main.getInstance().getConfig().getString("List");
*/
//Test commit