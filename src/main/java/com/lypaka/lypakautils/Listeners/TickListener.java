package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.API.PlayerChangeDimensionEvent;
import com.lypaka.lypakautils.API.PlayerChangeWorldEvent;
import com.lypaka.lypakautils.API.PlayerMovementEvent;
import com.lypaka.lypakautils.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.WorldHelpers;
import com.lypaka.lypakautils.PlayerLocationData.PlayerDataHandler;
import com.lypaka.lypakautils.PlayerLocationData.PlayerLocation;
import com.lypaka.lypakautils.WorldStuff.WorldMap;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TickListener {

    private static int count = -1;
    private static int spriteCount = -1;
    private static final List<UUID> warnedPlayers = new ArrayList<>();

    @SubscribeEvent
    public void onTick (TickEvent.ServerTickEvent event) {

        if (ConfigGetters.antiPixelmonSpriteWearingEnabled) {

            spriteCount++;
            if (spriteCount >= 200) {

                spriteCount = -1;
                for (Map.Entry<UUID, ServerPlayerEntity> entry : JoinListener.playerMap.entrySet()) {

                    ServerPlayerEntity player = entry.getValue();
                    for (int i = 0; i < player.inventory.armorInventory.size(); i++) {

                        String itemName = player.inventory.armorInventory.get(i).getItem().getRegistryName().toString();
                        if (itemName.equalsIgnoreCase("pixelmon:pixelmon_sprite")) {

                            if (!warnedPlayers.contains(entry.getKey())) {

                                warnedPlayers.add(entry.getKey());
                                player.sendMessage(FancyText.getFormattedText(ConfigGetters.messages.get("Sprite-Removal-Warning")), entry.getKey());

                            } else {

                                warnedPlayers.removeIf(e -> e.toString().equalsIgnoreCase(entry.getKey().toString()));
                                player.inventory.armorInventory.set(3, ItemStack.EMPTY);

                            }

                        }

                    }

                }

            }

        }

        if (!ConfigGetters.tickListenerEnabled) return;

        if (count >= 20) {

            count = -1;
            for (Map.Entry<UUID, ServerPlayerEntity> entry : JoinListener.playerMap.entrySet()) {

                ServerPlayerEntity player = entry.getValue();
                int currentX = player.getPosition().getX();
                int currentY = player.getPosition().getY();
                int currentZ = player.getPosition().getZ();
                World world = player.getEntityWorld();
                int steps = PlayerDataHandler.calculateStepsTaken(player.getUniqueID(), currentX, currentY, currentZ);
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
                    String dimension = WorldHelpers.getEntityDimensionID(player);
                    String worldName = WorldMap.getWorldName(player);

                    PlayerLocation location = PlayerDataHandler.playerLocationMap.get(player.getUniqueID());
                    if (!location.getCurrentDimension().equalsIgnoreCase(dimension)) {

                        PlayerChangeDimensionEvent dimEvent = new PlayerChangeDimensionEvent(player, location.getLastKnownDimension(), dimension);
                        MinecraftForge.EVENT_BUS.post(dimEvent);
                        location.setLastKnownDimension(location.getCurrentDimension());
                        location.setCurrentDimension(dimension);

                    }
                    if (!location.getCurrentWorld().equalsIgnoreCase(worldName)) {

                        PlayerChangeWorldEvent worldEvent = new PlayerChangeWorldEvent(player, location.getLastKnownWorld(), worldName);
                        MinecraftForge.EVENT_BUS.post(worldEvent);
                        location.setLastKnownWorld(location.getCurrentWorld());
                        location.setCurrentWorld(worldName);

                    }

                }

            }

        }

    }

}
