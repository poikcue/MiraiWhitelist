package com.poikcue.MiraiWhitelist;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.ParametersAreNonnullByDefault;

import static net.md_5.bungee.api.ChatColor.*;

public class reload implements CommandExecutor {
    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender.hasPermission("MiraiWhitelist.reload")){
            Main.getInstance().reloadConfig();
            commandSender.sendMessage(translateAlternateColorCodes('&',Main.getInstance().getConfig().getString("Message.Reload.Success")));
        }
        else{
            commandSender.sendMessage(translateAlternateColorCodes('&',Main.getInstance().getConfig().getString("Message.Reload.Without-permission")));
        }
        return true;
    }
}