package com.poikcue.MiraiWhitelist.MessageListener;

import com.poikcue.MiraiWhitelist.Main;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.*;

public class adminForce implements Listener {
    private Main plugin;
    public adminForce(Main plugin){
        this.plugin = plugin;
    }
    @EventHandler
    public void onForce(MiraiGroupMessageEvent e){
        if(e.getSenderPermission() != 0){
            if(e.getMessage().startsWith(plugin.getConfig().getString("文本消息.群聊中管理员强制添加白名单所寻找的关键字"))){
                String ForceArg = e.getMessage().replace(plugin.getConfig().getString("文本消息.群聊中管理员强制添加白名单所寻找的关键字"), "").replaceAll(" ", "");
                getScheduler().runTask(Main.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        dispatchCommand(getConsoleSender(), "miraiwhitelist force " + ForceArg);
                    }
                });
                MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("文本消息.群聊中强制添加白名单机器人统一回复消息").replaceAll("%arg%", ForceArg));
            }
        }
    }
}
