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
            if(e.getMessage().startsWith(plugin.getConfig().getString("Message.GroupCommandTAG"))){
                String string = e.getMessage().replace(plugin.getConfig().getString("Message.GroupCommandTAG"), "").replaceAll(" ", "");
                getScheduler().runTask(Main.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        dispatchCommand(getConsoleSender(), "miraiwhitelist force " + string);
                    }
                });
                MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("Message.ForceAdd").replaceAll("%arg%", string));
            }
        }
    }
}
