package com.lypaka.lypakautils.MiscHandlers;

import com.lypaka.lypakautils.WorldStuff.WorldMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;

public class WorldHelpers {

    public static boolean isEntityInUltraSpace (Entity entity) {

        return entity.world.getDimensionKey().getLocation().toString().equalsIgnoreCase("pixelmon:ultra_space");

    }

    public static boolean isEntityInDrownedWorldForSomeUngodlyReason (Entity entity) {

        return entity.world.getDimensionKey().getLocation().toString().equalsIgnoreCase("pixelmon:drowned_world");

    }

    public static boolean isEntityInOverworld (Entity entity) {

        return entity.world.getDimensionKey().getLocation().toString().equalsIgnoreCase("minecraft:overworld");

    }

    public static boolean isEntityInNether (Entity entity) {

        return entity.world.getDimensionKey().getLocation().toString().equalsIgnoreCase("minecraft:nether");

    }

    public static boolean isEntityInEnd (Entity entity) {

        return entity.world.getDimensionKey().getLocation().toString().equalsIgnoreCase("minecraft:the_end") ||
                entity.world.getDimensionKey().getLocation().toString().equalsIgnoreCase("minecraft:end"); // not sure what Minecraft calls that, lol

    }

    public static String getEntityDimensionID (Entity entity) {

        return entity.world.getDimensionKey().getLocation().toString();

    }

    public static void teleportPlayer (ServerPlayerEntity player, String world, int x, int y, int z, float yaw, float pitch) {

        player.teleport(WorldMap.worldMap.get(world), x, y, z, yaw, pitch);

    }

    public static String getEntityLocation (Entity entity) {

        String worldName = WorldMap.getWorldName(entity.world);
        int x = entity.getPosition().getX();
        int y = entity.getPosition().getY();
        int z = entity.getPosition().getZ();

        return worldName + "," + x + "," + y + "," + z;

    }

}
