package com.lypaka.lypakautils.MiscHandlers;

import com.lypaka.lypakautils.WorldStuff.WorldMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class WorldHelpers {

    public static boolean isPlayerInUltraSpace (ServerPlayer player) {

        return player.serverLevel().dimension().registry().toString().equalsIgnoreCase("pixelmon:ultra_space");

    }

    public static boolean isPlayerInDrownedWorldForSomeUngodlyReason (ServerPlayer player) {

        return player.serverLevel().dimension().registry().toString().equalsIgnoreCase("pixelmon:drowned_world");

    }

    public static boolean isPlayerInOverworld (ServerPlayer player) {

        return player.serverLevel().dimension().registry().toString().equalsIgnoreCase("minecraft:overworld");

    }

    public static boolean isPlayerInNether (ServerPlayer player) {

        return player.serverLevel().dimension().registry().toString().equalsIgnoreCase("minecraft:nether");

    }

    public static boolean isPlayerInEnd (ServerPlayer player) {

        return player.serverLevel().dimension().registry().toString().equalsIgnoreCase("minecraft:the_end") ||
                player.serverLevel().dimension().registry().toString().equalsIgnoreCase("minecraft:end"); // not sure what Minecraft calls that, lol

    }

    public static String getEntityDimensionID (Entity entity) {

        return entity.level().dimension().location().toString();

    }

    public static void teleportPlayer (ServerPlayer player, String world, int x, int y, int z, float yaw, float pitch) {

        player.teleportTo(WorldMap.worldMap.get(world), x, y, z, yaw, pitch);

    }

}
