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
        MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(Main.getInstance().getConfig().getString("Message.WelcomeRejoinFriend"));
    }
}
