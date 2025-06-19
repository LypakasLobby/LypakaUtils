package com.lypaka.lypakautils.Listeners;

import com.lypaka.lypakautils.API.PixelmonApricornEvent;
import com.lypaka.lypakautils.API.PixelmonBerryEvent;
import com.lypaka.lypakautils.ConfigGetters;
import com.lypaka.lypakautils.MiscHandlers.ItemStackBuilder;
import com.pixelmonmod.pixelmon.blocks.ApricornLeavesBlock;
import com.pixelmonmod.pixelmon.blocks.BerryLeavesBlock;
import com.pixelmonmod.pixelmon.init.registry.BlockRegistration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.RegistryObject;

public class BlockInteractListener {

    @SubscribeEvent
    public void onBlockClick (PlayerInteractEvent.RightClickBlock event) {

        if (event.getSide() == LogicalSide.CLIENT) return;
        if (event.getHand() == Hand.OFF_HAND) return;

        if (!ConfigGetters.useBetterBerryPicker) return;
        ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
        Block block = player.world.getBlockState(event.getPos()).getBlock();
        String blockID = block.getRegistryName().toString();

        if (block instanceof BerryLeavesBlock) {

            for (RegistryObject<Block> object : BlockRegistration.BERRY_LEAVES) {

                String objectID = object.getId().toString();
                if (blockID.equalsIgnoreCase(objectID)) {

                    String berryID = getBerryIDFromLeaves(objectID);
                    PixelmonBerryEvent.Pre preEvent = new PixelmonBerryEvent.Pre(player, berryID);
                    MinecraftForge.EVENT_BUS.post(preEvent);
                    if (!preEvent.isCanceled()) {

                        BlockState state = player.world.getBlockState(event.getPos());
                        int growthStage = state.get(BerryLeavesBlock.AGE);
                        if (growthStage >= 2) {

                            event.setCanceled(true);
                            ItemStack berry = ItemStackBuilder.buildFromStringID(berryID);
                            PixelmonBerryEvent.Post postEvent = new PixelmonBerryEvent.Post(player, berryID, berry);
                            MinecraftForge.EVENT_BUS.post(postEvent);
                            berry.setCount(postEvent.getCount());
                            player.addItemStackToInventory(berry);
                            player.world.setBlockState(event.getPos(), state.with(BerryLeavesBlock.AGE, 0));

                        }

                    }
                    break;

                }

            }

        } else if (block instanceof ApricornLeavesBlock) {

            for (RegistryObject<Block> object : BlockRegistration.BERRY_LEAVES) {

                String objectID = object.getId().toString();
                if (blockID.equalsIgnoreCase(objectID)) {

                    String apricornID = getApricornIDFromLeaves(objectID);
                    PixelmonApricornEvent.Pre preEvent = new PixelmonApricornEvent.Pre(player, apricornID);
                    MinecraftForge.EVENT_BUS.post(preEvent);
                    if (!preEvent.isCanceled()) {

                        BlockState state = player.world.getBlockState(event.getPos());
                        int growthStage = state.get(BerryLeavesBlock.AGE);
                        if (growthStage >= 2) {

                            event.setCanceled(true);
                            ItemStack berry = ItemStackBuilder.buildFromStringID(apricornID);
                            PixelmonApricornEvent.Post postEvent = new PixelmonApricornEvent.Post(player, apricornID, berry);
                            MinecraftForge.EVENT_BUS.post(postEvent);
                            berry.setCount(postEvent.getCount());
                            player.addItemStackToInventory(berry);
                            player.world.setBlockState(event.getPos(), state.with(BerryLeavesBlock.AGE, 0));

                        }

                    }
                    break;

                }

            }

        }

    }

    private static String getBerryIDFromLeaves (String id) {

        String[] split = id.split("_");
        String berryName = split[2];
        return "pixelmon:" + berryName.toLowerCase() + "_berry";

    }

    private static String getApricornIDFromLeaves (String id) {

        String[] split = id.split("_");
        String apricornName = split[2];
        return "pixelmon:" + apricornName.toLowerCase() + "_apricorn";

    }

}
