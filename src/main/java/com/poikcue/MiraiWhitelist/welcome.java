package com.poikcue.MiraiWhitelist;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class welcome implements Listener {
    private Main plugin;

    public welcome(Main plugin) {
        this.plugin = plugin;
    }
    @EventHandler
    public void welcome(MiraiMemberJoinEvent e){
        MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("文本消息.加入群聊时欢迎消息").replaceAll("%name%", e.getMemberNick()));
    }
}
