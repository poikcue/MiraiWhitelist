package com.poikcue.MiAutoWhitelist;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    public void onEnable() {
        instance = this;
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new whitelist(), this);
        pm.registerEvents(new welcome(), this);
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
    }


    public static Main getInstance(){
        return instance;
    }
}