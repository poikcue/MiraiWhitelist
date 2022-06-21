package com.poikcue.MiraiWhitelist;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class Main extends JavaPlugin {
    private static Main instance;

    public void onEnable() {
        instance = this;
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new whitelist(this), this);
        pm.registerEvents(new welcome(), this);
        pm.registerEvents(new force(this), this);
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        if (Bukkit.getPluginCommand("miraiwhitelistreload") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("miraiwhitelistreload")).setExecutor(new reload());
        }
    }


    public static Main getInstance(){
        return instance;
    }
}