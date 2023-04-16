package com.lypaka.lypakautils;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.HashMap;
import java.util.Map;

public class WorldMap {

    public static Map<String, Level> worldMap = new HashMap<>();

    public static void load() {

        for (Level l : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {

            if (l == null | !(l.getLevelData() instanceof ServerLevel)) continue;
            ServerLevelData d = (ServerLevelData) l.getLevelData();
            worldMap.put(d.getLevelName(), l);

        }

    }

}
