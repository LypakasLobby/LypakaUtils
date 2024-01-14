package com.lypaka.lypakautils.MiscHandlers;

import com.lypaka.lypakautils.ConfigGetters;
import net.minecraft.entity.player.ServerPlayerEntity;
import org.bukkit.Bukkit;

import java.util.List;

public class CommandHandler {

    public static void handleCommands (List<String> commands, ServerPlayerEntity player) {

        for (String c : commands) {

            if (ConfigGetters.spigotCommands.contains(c)) {

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c.replace("%player%", player.getName().getString()));

            } else {

                player.getServer().getCommandManager().handleCommand(player.getServer().getCommandSource(), c.replace("%player%", player.getName().getString()));

            }

        }

    }

}
