package com.lypaka.lypakautils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = LypakaUtils.MOD_ID)
public class JoinListener {

    /**
     * Used to store all actively online players
     * I know Forge has a way to do this, but I'm not particularly fond of how it works
     */
    public static Map<UUID, ServerPlayer> playerMap = new HashMap<>();

    @SubscribeEvent
    public static void onJoin (PlayerEvent.PlayerLoggedInEvent event) {

        playerMap.put(event.getEntity().getUUID(), (ServerPlayer) event.getEntity());

    }

    @SubscribeEvent
    public static void onLeave (PlayerEvent.PlayerLoggedOutEvent event) {

        playerMap.entrySet().removeIf(entry -> entry.getKey().toString().equalsIgnoreCase(event.getEntity().getUUID().toString()));

    }

}
