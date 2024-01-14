package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.API.PlayerMovementEvent;
import com.lypaka.lypakautils.ConfigGetters;
import com.lypaka.lypakautils.PlayerLocationData.PlayerDataHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.World;
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
            for (Map.Entry<UUID, ServerPlayerEntity> entry : JoinListener.playerMap.entrySet()) {

                ServerPlayerEntity player = entry.getValue();
                int currentX = player.getPosition().getX();
                int currentZ = player.getPosition().getZ();
                World world = player.getEntityWorld();
                int steps = PlayerDataHandler.calculateStepsTaken(player.getUniqueID(), currentX, currentZ);
                if (steps > 0) {

                    String blockID = world.getBlockState(player.getPosition()).getBlock().getRegistryName().toString();
                    if (blockID.contains("water") || blockID.contains("lava")) {

                        PlayerMovementEvent.Swim swimEvent = new PlayerMovementEvent.Swim(player, steps, blockID);
                        MinecraftForge.EVENT_BUS.post(swimEvent);

                    } else {

                        PlayerDataHandler.setLastKnownLandLocation(player.getUniqueID(), player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
                        PlayerMovementEvent.Land landEvent = new PlayerMovementEvent.Land(player, steps, blockID);
                        MinecraftForge.EVENT_BUS.post(landEvent);

                    }

                }

            }

        }

    }

}
