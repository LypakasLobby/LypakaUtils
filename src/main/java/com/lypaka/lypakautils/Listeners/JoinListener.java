package com.lypaka.lypakautils.Listeners;

import com.google.common.reflect.TypeToken;
import com.lypaka.lypakautils.LPPlayer;
import com.lypaka.lypakautils.LypakaUtils;
import com.lypaka.lypakautils.MiscHandlers.WorldHelpers;
import com.lypaka.lypakautils.PlayerLocationData.PlayerDataHandler;
import com.lypaka.lypakautils.PlayerLocationData.PlayerLocation;
import com.lypaka.lypakautils.WorldStuff.WorldMap;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = LypakaUtils.MOD_ID)
public class JoinListener {

    /**
     * Used to store all actively online players
     * I know Forge has a way to do this, but I'm not particularly fond of how it works
     */
    public static Map<UUID, ServerPlayerEntity> playerMap = new HashMap<>();

    @SubscribeEvent
    public static void onJoin (PlayerEvent.PlayerLoggedInEvent event) throws ObjectMappingException {

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        playerMap.put(player.getUniqueID(), player);
        if (!PlayerDataHandler.playerLocationMap.containsKey(player.getUniqueID())) {

            int x = player.getPosition().getX();
            int y = player.getPosition().getY();
            int z = player.getPosition().getZ();
            String dimension = WorldHelpers.getEntityDimensionID(player);
            String world = WorldMap.getWorldName(player);
            PlayerLocation location = new PlayerLocation(x, y, z, x, y, z, dimension, dimension, world, world);
            PlayerDataHandler.playerLocationMap.put(player.getUniqueID(), location);

        }
        LypakaUtils.playerConfigManager.loadPlayer(player.getUniqueID());
        List<String> groups = LypakaUtils.playerConfigManager.getPlayerConfigNode(player.getUniqueID(), "Groups").getList(TypeToken.of(String.class));
        List<String> permissions = LypakaUtils.playerConfigManager.getPlayerConfigNode(player.getUniqueID(), "Permissions").getList(TypeToken.of(String.class));

        LPPlayer lpPlayer = new LPPlayer(player.getUniqueID(), groups, permissions);
        LypakaUtils.playerMap.put(player.getUniqueID(), lpPlayer);

    }

    @SubscribeEvent
    public static void onLeave (PlayerEvent.PlayerLoggedOutEvent event) {

        try {

            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            LPPlayer lpPlayer = LypakaUtils.playerMap.get(player.getUniqueID());
            lpPlayer.save(true);

        } catch (Exception e) {

            // do nothing because I can't be bothered to fix this properly when literally no one (not even me) uses this (yet)

        }

        playerMap.entrySet().removeIf(entry -> entry.getKey().toString().equalsIgnoreCase(event.getPlayer().getUniqueID().toString()));

    }

}
