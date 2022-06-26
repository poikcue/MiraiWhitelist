package com.poikcue.MiraiWhitelist;

import com.poikcue.MiraiWhitelist.Command.command;
import com.poikcue.MiraiWhitelist.MessageListener.adminForce;
import com.poikcue.MiraiWhitelist.MessageListener.prejoin;
import com.poikcue.MiraiWhitelist.MessageListener.whitelist;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static org.bukkit.Bukkit.getScheduler;

public class Main extends JavaPlugin {
    private static Main instance;

    public void onEnable() {
        instance = this;
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new whitelist(this), this);
        pm.registerEvents(new welcome(this), this);
        pm.registerEvents(new adminForce(this), this);
        pm.registerEvents(new prejoin(this), this);
        saveDefaultConfig();
        if (Bukkit.getPluginCommand("miraiwhitelist") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("miraiwhitelist")).setExecutor(new command());
        }
    }


    public static Main getInstance(){
        return instance;
    }
}