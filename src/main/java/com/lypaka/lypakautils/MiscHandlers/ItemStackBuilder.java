package com.lypaka.lypakautils.MiscHandlers;

import com.lypaka.lypakautils.LypakaUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemStackBuilder {

    // "Forge 1.16 is better" they say. Is this really better? Fuck no it is not.
    public static ItemStack buildFromStringID (String id) {

        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id));
        if (item != null) {

            return new ItemStack(item);

        }

        LypakaUtils.logger.warn("Could not get ItemStack from item ID: " + id);
        return null;

    }

}
