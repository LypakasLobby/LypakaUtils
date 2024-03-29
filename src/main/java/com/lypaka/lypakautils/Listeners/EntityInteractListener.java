package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.LypakaUtils;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.lypaka.lypakautils.WorldStuff.WorldMap;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LypakaUtils.MOD_ID)
public class EntityInteractListener {

    @SubscribeEvent
    public static void onEntityInteract (PlayerInteractEvent.EntityInteract event) {

        if (event.getHand() == Hand.OFF_HAND) return;
        if (event.getSide() == LogicalSide.CLIENT) return;

        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        String id = player.getHeldItem(Hand.MAIN_HAND).getItem().getRegistryName().toString();
        if (id.equalsIgnoreCase("minecraft:golden_sword")) {

            if (PermissionHandler.hasPermission(player, "lypakautils.admin")) {

                event.setCanceled(true);
                String world = WorldMap.getWorldName(player);
                int x = event.getTarget().getPosition().getX();
                int y = event.getTarget().getPosition().getY();
                int z = event.getTarget().getPosition().getZ();
                String location = world + "," + x + "," + y + "," + z;
                player.sendMessage(FancyText.getFormattedText("&eLocation of entity: &b" + location), player.getUniqueID());

            }

        }

    }

}
