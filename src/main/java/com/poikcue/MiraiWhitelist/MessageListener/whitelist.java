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

        if (e.getMessage().startsWith(plugin.getConfig().getString("文本消息.群聊中标准白名单模式所寻找的关键字"))) {
             String PlayerName = e.getMessage().replace(plugin.getConfig().getString("文本消息.群聊中标准白名单模式所寻找的关键字"), "").replaceAll(" ", "");
             String SenderAccount = String.valueOf(e.getSenderID());
             URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + PlayerName);
             HttpURLConnection connection = (HttpURLConnection) url.openConnection();
             UUID uuid = Bukkit.getOfflinePlayer(PlayerName).getUniqueId();
             connection.setRequestMethod("GET");
             if (plugin.getConfig().get("数据." + SenderAccount) == null) {
                 if (plugin.getConfig().getString("内置白名单列表." + uuid) == null) {
                     if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                         MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("文本消息.正版玩家成功添加白名单祝贺").replaceAll("%ID%", PlayerName).replaceAll("%name%", e.getSenderName()));
                         getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
                             @Override
                             public void run() {
                                 plugin.getConfig().set("数据." + SenderAccount + ".通用单一标识符", String.valueOf(uuid));
                                 plugin.getConfig().set("内置白名单列表." + uuid, "已加入");
                                 plugin.saveConfig();
                             }
                         });
                         if (plugin.getConfig().getString("通用.运行白名单命令") == "我的世界原版服务端" && plugin.getConfig().getString("通用.可启用.内置白名单系统") == "不启用") {
                             getScheduler().runTask(Main.getInstance(), new Runnable() {
                                 @Override
                                 public void run() {
                                     dispatchCommand(getConsoleSender(), "whitelist add " + PlayerName);
                                 }
                             });
                         } else if(plugin.getConfig().getString("通用.可启用.内置白名单系统") == "不启用") {
                             String command = plugin.getConfig().getString("通用.运行白名单命令");
                             getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable() {
                                 @Override
                                 public void run() {
                                     dispatchCommand(getConsoleSender(), command.replaceAll("%ID%", PlayerName));
                                 }
                             });
                         }
                         //正版玩家
                     } else if (connection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
                         MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("文本消息.请求非正版白名单时群聊中通知信息").replaceAll("%ID%", PlayerName).replaceAll("%name%", e.getSenderName()));
                         //正常访问，但是是离线玩家
                     } else {
                         MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("文本消息.请求我的世界国际版本官方应用编程接口网络异常").replaceAll("%ID%", PlayerName).replaceAll("%name%", e.getSenderName()));
                         //出现问题，访问异常
                     }
                 }else{
                     MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("文本消息.账户未曾经申请过有效白名单但目标玩家已在白名单列表中").replaceAll("%ID%", PlayerName).replaceAll("%name%", e.getSenderName()));
                 }
             }else {
                 MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(plugin.getConfig().getString("文本消息.此账户已经申请过白名单时通知信息").replaceAll("%ID%", PlayerName).replaceAll("%name%", e.getSenderName()));
             }
        }
    }
}



