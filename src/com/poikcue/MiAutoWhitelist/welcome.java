package com.poikcue.MiAutoWhitelist;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.*;

public class welcome implements Listener {
    @EventHandler
    public void welcome(MiraiGroupMemberJoinEvent e) throws IOException {
        Yaml yml = new Yaml();
        FileReader reader = new FileReader("plugins/MiraWhitelist/List.yml");
        BufferedReader buffer = new BufferedReader(reader);
        Map<String,Object> mapA = yml.load(buffer);
        buffer.close();
        reader.close();
        if(mapA.get(e.getNewMemberID()) != null){
            Map<String, Object> mapB = new HashMap<>();
            FileWriter writer = new FileWriter("plugins/MiraWhitelist/List.yml");
            BufferedWriter bufferB = new BufferedWriter(writer);
            getScheduler().runTask(Main.getInstance(), () -> dispatchCommand(getConsoleSender(), "whitelist add " + mapB.get(e.getNewMemberID())));
            MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(Main.getInstance().getConfig().getString("Message.WelcomeRejoinFriend"));
        }
        else{
            MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(Main.getInstance().getConfig().getString("Message.WelcomeFriend"));
        }
    }
}
