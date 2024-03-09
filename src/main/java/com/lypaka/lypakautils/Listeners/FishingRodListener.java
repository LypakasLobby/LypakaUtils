package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.LypakaUtils;
import com.lypaka.lypakautils.WorldStuff.WorldMap;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LypakaUtils.MOD_ID)
public class FishingRodListener {

    @SubscribeEvent
    public static void onFish (PlayerInteractEvent.RightClickItem event) {

        if (event.getSide() == LogicalSide.CLIENT) return;
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        String world = WorldMap.getWorldName(player);
        if (event.getHand() == Hand.MAIN_HAND) {

            String id = player.getHeldItem(Hand.MAIN_HAND).getItem().getRegistryName().toString();
            if (id.equals("minecraft:fishing_rod")) {

                if (ConfigGetters.fishingRodBlacklist.contains(world)) {

                    event.setCanceled(true);
                    player.sendMessage(FancyText.getFormattedText(ConfigGetters.messages.get("Fishing-Rod")), player.getUniqueID());

                }

            }

        } else if (event.getHand() == Hand.OFF_HAND) {

            String id = player.getHeldItem(Hand.OFF_HAND).getItem().getRegistryName().toString();
            if (id.equals("minecraft:fishing_rod")) {

                if (ConfigGetters.fishingRodBlacklist.contains(world)) {

                    event.setCanceled(true);
                    player.sendMessage(FancyText.getFormattedText(ConfigGetters.messages.get("Fishing-Rod")), player.getUniqueID());

                }

            }

        }

    }

}
