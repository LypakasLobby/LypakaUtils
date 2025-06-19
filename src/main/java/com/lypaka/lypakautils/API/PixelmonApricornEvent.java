package com.lypaka.lypakautils.API;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

public class PixelmonApricornEvent extends Event {

    private final ServerPlayerEntity player;
    private final String apricornID;

    public PixelmonApricornEvent (ServerPlayerEntity player, String apricornID) {

        this.player = player;
        this.apricornID = apricornID;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public String getApricornID() {

        return this.apricornID;

    }

    @Cancelable
    public static class Pre extends PixelmonBerryEvent {

        public Pre (ServerPlayerEntity player, String apricornID) {

            super(player, apricornID);

        }

    }

    public static class Post extends PixelmonBerryEvent {

        private final ItemStack apricorn;
        private int count;

        public Post (ServerPlayerEntity player, String apricornID, ItemStack apricorn) {

            super(player, apricornID);
            this.apricorn = apricorn;
            this.count = 1;

        }

        public ItemStack getApricorn() {

            return this.apricorn;

        }

        public int getCount() {

            return this.count;

        }

        public void setCount (int count) {

            this.count = count;

        }

    }
}
