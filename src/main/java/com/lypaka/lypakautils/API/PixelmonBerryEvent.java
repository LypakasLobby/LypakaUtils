package com.lypaka.lypakautils.API;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public abstract class PixelmonBerryEvent extends Event {

    private final ServerPlayerEntity player;
    private final String berryID;

    public PixelmonBerryEvent (ServerPlayerEntity player, String blockID) {

        this.player = player;
        this.berryID = blockID;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public String getBerryID() {

        return this.berryID;

    }

    @Cancelable
    public static class Pre extends PixelmonBerryEvent {

        public Pre (ServerPlayerEntity player, String berryID) {

            super(player, berryID);

        }

    }

    public static class Post extends PixelmonBerryEvent {

        private ItemStack berry;
        private int count;

        public Post (ServerPlayerEntity player, String berryID, ItemStack berry) {

            super(player, berryID);
            this.berry = berry;
            this.count = 1;

        }

        public ItemStack getBerry() {

            return this.berry;

        }

        public int getCount() {

            return this.count;

        }

        public void setCount (int count) {

            this.count = count;

        }

    }

}
