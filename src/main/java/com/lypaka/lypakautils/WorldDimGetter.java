package com.lypaka.lypakautils;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelData;

public class WorldDimGetter {

    public static int getDimID (Level world) {

        LevelData info = world.getLevelData();
        String worldDimension = world.dimension().location().toString();
        if (worldDimension.equalsIgnoreCase("minecraft:nether") || worldDimension.equalsIgnoreCase("minecraft:the_nether")) {

            return -1;

        } else {

            return 0;

        }

    }

}
