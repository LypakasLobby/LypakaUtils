package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.API.PlayerMovementEvent;
import com.lypaka.lypakautils.ConfigGetters;
import com.lypaka.lypakautils.PlayerLocationData.PlayerDataHandler;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;
import java.util.UUID;

public class TickListener {

    private static int count = -1;

    @SubscribeEvent
    public void onTick (TickEvent.ServerTickEvent event) {

        if (!ConfigGetters.tickListenerEnabled) return;

        count++;
        if (count >= 20) {

            count = -1;
            for (Map.Entry<UUID, ServerPlayer> entry : JoinListener.playerMap.entrySet()) {

                ServerPlayer player = entry.getValue();
                int currentX = player.getBlockX();
                int currentZ = player.getBlockZ();
                ServerLevel world = player.serverLevel();
                int steps = PlayerDataHandler.calculateStepsTaken(player.getUUID(), currentX, currentZ);
                if (steps > 0) {

                    String blockID = BuiltInRegistries.BLOCK.getKey(world.getBlockState(player.getOnPos()).getBlock()).toString();
                    if (blockID.contains("water") || blockID.contains("lava")) {

                        PlayerMovementEvent.Swim swimEvent = new PlayerMovementEvent.Swim(player, steps, blockID);
                        MinecraftForge.EVENT_BUS.post(swimEvent);

                    } else {

                        PlayerDataHandler.setLastKnownLandLocation(player.getUUID(), player.getBlockX(), player.getBlockY(), player.getBlockZ());
                        PlayerMovementEvent.Land landEvent = new PlayerMovementEvent.Land(player, steps, blockID);
                        MinecraftForge.EVENT_BUS.post(landEvent);

                    }

                }

            }

        }

    }

}
