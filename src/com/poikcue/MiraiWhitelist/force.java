package com.poikcue.MiraiWhitelist;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static org.bukkit.Bukkit.*;

public class force implements Listener {
    private Main plugin;

    public force(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onForce(MiraiGroupMessageEvent e){
        String sender = String.valueOf(e.getSenderID());
        if (plugin.getConfig().get("Data." + sender) == null){
            if (e.getMessage().contains(plugin.getConfig().getString("Message.ReplaceForceGroupMessageTAG"))) {
                String name = e.getMessage().replace(plugin.getConfig().getString("Message.ReplaceForceGroupMessageTAG"), "");
                MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("Message.ForceMessage").replaceAll("%ID%", name));
            }
        } else if(plugin.getConfig().get("Data." + sender) == "EMPTY") {
            //把这个data.sender改成data.sender.uuid
            if (e.getMessage().contains(plugin.getConfig().getString("Message.ReplaceForceGroupMessageTAG"))) {
                String name = e.getMessage().replace(plugin.getConfig().getString("Message.ReplaceForceGroupMessageTAG"), "");
                MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("Message.ForceMessage").replaceAll("%ID%", name));
        }
    }
        else {
            if (e.getMessage().contains(plugin.getConfig().getString("Message.ReplaceForceGroupMessageTAG"))) {
                MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("Message.AlreadySignedUp"));
            }
        }
    }



    public void onConfirmForce(MiraiGroupMessageEvent e){
        String sender = String.valueOf(e.getSenderID());
        if (plugin.getConfig().get("Data." + sender) == null){
            if (e.getMessage().contains(plugin.getConfig().getString("Message.ReplaceForceConfirmMessageTAG"))) {
                String name2 = e.getMessage().replace(plugin.getConfig().getString("Message.ReplaceForceConfirmMessageTAG"), "");
                MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("Message.ForceConfirmMessage").replaceAll("%ID%", name2));
                getScheduler().runTask(Main.getInstance(), () -> dispatchCommand(getConsoleSender(), "whitelist add " + name2));
            }
        } else if(plugin.getConfig().get("Data." + sender) == "EMPTY") {
            if (e.getMessage().contains(plugin.getConfig().getString("Message.ReplaceForceConfirmMessageTAG"))) {
                String name2 = e.getMessage().replace(plugin.getConfig().getString("Message.ReplaceForceConfirmMessageTAG"), "");
                MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("Message.ForceConfirmMessage").replaceAll("%ID%", name2));
                getScheduler().runTask(Main.getInstance(), () -> dispatchCommand(getConsoleSender(), "whitelist add " + name2));
            }
        } else {
            if (e.getMessage().contains(plugin.getConfig().getString("Message.ReplaceForceConfirmMessageTAG"))) {
                MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("Message.AlreadySignedUp"));
            }
        }
    }
}
