package com.lypaka.lypakautils.WorldStuff;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.Map;

public class WorldMap {

    public static Map<String, ServerLevel> worldMap = new HashMap<>();

    public static void load() {

        for (ServerLevel world : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {

            if (world == null) continue;
            worldMap.put(((ServerLevelData) world.getLevel().getLevelData()).getLevelName(), world);

        }

    }

    public static String getWorldName (ServerPlayer player) {

        /*try {

            return ((ServerLevelData) player.level().getLevelData()).getLevelName();

        } catch (ClassCastException er) {

            try {

                return ((DerivedWorldInfo) player.world.getWorldInfo()).getWorldName();

            } catch (ClassCastException er2) {

                LypakaUtils.logger.error("Couldn't get world name for player: " + player.getName().getString());
                LypakaUtils.logger.info("Report this message to Lypaka along with all the following data:");
                LypakaUtils.logger.info("Player world data: " + player.getServerWorld().getWorldInfo());

            }

        }*/
        return ((ServerLevelData) player.level().getLevelData()).getLevelName();

        //return "";

    }

    public static String getWorldName (Level world) {

        /*try {

            return ((ServerWorldInfo) world.getWorldInfo()).getWorldName();

        } catch (ClassCastException er) {

            return ((DerivedWorldInfo) world.getWorldInfo()).getWorldName();

        }*/
        return ((ServerLevelData) world.getLevelData()).getLevelName();

    }

}
