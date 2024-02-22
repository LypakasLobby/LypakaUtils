package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.LPPlayer;
import com.lypaka.lypakautils.LypakaUtils;
import com.lypaka.lypakautils.PlayerLocationData.PlayerDataHandler;
import com.lypaka.lypakautils.PlayerLocationData.PlayerLocation;
import io.leangen.geantyref.TypeToken;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.spongepowered.configurate.serialize.SerializationException;

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
    public static Map<UUID, ServerPlayer> playerMap = new HashMap<>();

    @SubscribeEvent
    public static void onJoin (PlayerEvent.PlayerLoggedInEvent event) throws SerializationException {

        ServerPlayer player = (ServerPlayer) event.getEntity();
        playerMap.put(player.getUUID(), player);
        if (!PlayerDataHandler.playerLocationMap.containsKey(player.getUUID())) {

            int x = player.getBlockX();
            int z = player.getBlockZ();
            PlayerLocation location = new PlayerLocation(x, z, x, z);
            PlayerDataHandler.playerLocationMap.put(player.getUUID(), location);

        }
        LypakaUtils.playerConfigManager.loadPlayer(player.getUUID());
        List<String> groups = LypakaUtils.playerConfigManager.getPlayerConfigNode(player.getUUID(), "Groups").getList(TypeToken.get(String.class));
        List<String> permissions = LypakaUtils.playerConfigManager.getPlayerConfigNode(player.getUUID(), "Permissions").getList(TypeToken.get(String.class));

        LPPlayer lpPlayer = new LPPlayer(player.getUUID(), groups, permissions);
        LypakaUtils.playerMap.put(player.getUUID(), lpPlayer);

    }

    @SubscribeEvent
    public static void onLeave (PlayerEvent.PlayerLoggedOutEvent event) {

        try {

            ServerPlayer player = (ServerPlayer) event.getEntity();
            LPPlayer lpPlayer = LypakaUtils.playerMap.get(player.getUUID());
            lpPlayer.save(true);

        } catch (Exception e) {

            // do nothing because I can't be bothered to fix this properly when literally no one (not even me) uses this (yet)

        }

        playerMap.entrySet().removeIf(entry -> entry.getKey().toString().equalsIgnoreCase(event.getEntity().getUUID().toString()));

    }

}
