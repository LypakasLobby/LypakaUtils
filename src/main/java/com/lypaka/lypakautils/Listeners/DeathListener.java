package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.LypakaUtils;
import com.lypaka.lypakautils.MiscHandlers.WorldHelpers;
import com.lypaka.lypakautils.PlayerLocationData.PlayerDataHandler;
import com.lypaka.lypakautils.PlayerLocationData.PlayerLocation;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

/**
 * Fixes a desync issue with player location upon being killed
 */
@Mod.EventBusSubscriber(modid = LypakaUtils.MOD_ID)
public class DeathListener {

    @SubscribeEvent
    public static void onDeath (LivingDamageEvent event) {

        if (event.getEntityLiving() instanceof ServerPlayerEntity) {

            ServerPlayerEntity player = (ServerPlayerEntity) event.getEntityLiving();
            if (player.getHealth() <= 2) {

                int currentX = player.getPosition().getX();
                int currentY = player.getPosition().getY();
                int currentZ = player.getPosition().getZ();
                PlayerLocation playerLocation = PlayerDataHandler.playerLocationMap.get(player.getUniqueID());
                String location = WorldHelpers.getEntityDimensionID(player) + "," + currentX + "," + currentY + "," + currentZ;
                playerLocation.setLastDeathLocation(location);

            }

        }

    }

    @SubscribeEvent
    public static void onRespawn (PlayerEvent.PlayerRespawnEvent event) {

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        putTheBitchBackIfMissing(player);

    }

    private static void putTheBitchBackIfMissing (ServerPlayerEntity player) {

        UUID uuid = player.getUniqueID();
        JoinListener.playerMap.entrySet().removeIf(entry -> entry.getKey().toString().equalsIgnoreCase(uuid.toString()));
        JoinListener.playerMap.put(uuid, player);

        /*PlayerDataHandler.playerLocationMap.entrySet().removeIf(entry -> entry.getKey().toString().equalsIgnoreCase(uuid.toString()));
        int x = player.getPosition().getX();
        int y = player.getPosition().getY();
        int z = player.getPosition().getZ();
        String dimension = WorldHelpers.getEntityDimensionID(player);
        String world = WorldMap.getWorldName(player);
        PlayerLocation playerLocation = new PlayerLocation(x, y, z, x, y, z, dimension, dimension, world, world);
        PlayerDataHandler.playerLocationMap.put(player.getUniqueID(), playerLocation);*/

    }

}
