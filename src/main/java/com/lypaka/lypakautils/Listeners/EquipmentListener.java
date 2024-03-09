package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.LypakaUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LypakaUtils.MOD_ID)
public class EquipmentListener {

    @SubscribeEvent
    public static void onEquip (LivingEquipmentChangeEvent event) {

        if (event.getSlot() == EquipmentSlotType.HEAD) {

            if (event.getTo().getItem().getRegistryName().toString().contains("pixelmon:pixelmon_sprite")) {

                if (event.getEntity() instanceof ServerPlayerEntity) {

                    ServerPlayerEntity player = (ServerPlayerEntity) event.getEntity();
                    player.sendMessage(FancyText.getFormattedText(ConfigGetters.messages.get("Equip-Pixelmon-Sprite")), player.getUniqueID());

                }

            }

        }

    }

}
