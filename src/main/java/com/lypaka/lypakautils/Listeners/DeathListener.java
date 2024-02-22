package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.LypakaUtils;
import net.minecraft.server.level.ServerPlayer;
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
    public static void onRespawn (PlayerEvent.PlayerRespawnEvent event) {

        ServerPlayer player = (ServerPlayer) event.getEntity();
        putTheBitchBackIfMissing(player);

    }

    private static void putTheBitchBackIfMissing (ServerPlayer player) {

        UUID uuid = player.getUUID();
        JoinListener.playerMap.entrySet().removeIf(entry -> entry.getKey().toString().equalsIgnoreCase(uuid.toString()));
        JoinListener.playerMap.put(uuid, player);

    }

}
