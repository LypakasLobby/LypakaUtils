package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.MiscHandlers.WorldHelpers;
import com.lypaka.lypakautils.PlayerLocationData.PlayerDataHandler;
import com.lypaka.lypakautils.PlayerLocationData.PlayerLocation;
import com.pixelmonmod.pixelmon.api.events.LostToTrainerEvent;
import com.pixelmonmod.pixelmon.api.events.LostToWildPixelmonEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BattleListeners {

    @SubscribeEvent
    public void onLoseToTrainer (LostToTrainerEvent event) {

        ServerPlayerEntity player = event.player;
        int currentX = player.getPosition().getX();
        int currentY = player.getPosition().getY();
        int currentZ = player.getPosition().getZ();
        PlayerLocation playerLocation = PlayerDataHandler.playerLocationMap.get(player.getUniqueID());
        String location = WorldHelpers.getEntityDimensionID(player) + "," + currentX + "," + currentY + "," + currentZ;
        playerLocation.setLastDeathLocation(location);

    }

    @SubscribeEvent
    public void onLoseToWild (LostToWildPixelmonEvent event) {

        ServerPlayerEntity player = event.player;
        int currentX = player.getPosition().getX();
        int currentY = player.getPosition().getY();
        int currentZ = player.getPosition().getZ();
        PlayerLocation playerLocation = PlayerDataHandler.playerLocationMap.get(player.getUniqueID());
        String location = WorldHelpers.getEntityDimensionID(player) + "," + currentX + "," + currentY + "," + currentZ;
        playerLocation.setLastDeathLocation(location);

    }

}
