package com.poikcue.MiAutoWhitelist;

import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberLeaveEvent;
import org.bukkit.event.Listener;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.*;

public class Leave implements Listener{
    public void onLeave(MiraiGroupMemberLeaveEvent event) throws IOException {
        Yaml yml = new Yaml();
        FileReader reader = new FileReader("plugins/MiraWhitelist/List.yml");
        BufferedReader buffer = new BufferedReader(reader);
        Map<String,Object> mapA = yml.load(buffer);
        buffer.close();
        reader.close();
        if(mapA.get(event.getTargetID()) == null){
        }
        else{
            Map<String, Object> mapB = new HashMap<>();
            FileWriter writer = new FileWriter("plugins/MiraWhitelist/List.yml");
            BufferedWriter bufferB = new BufferedWriter(writer);
            getScheduler().runTask(Main.getInstance(), () -> dispatchCommand(getConsoleSender(), "whitelist remove " + mapB.get(event.getTargetID())));
        }
    }
}
