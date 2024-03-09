package com.lypaka.lypakautils.API;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Event;

public class PlayerChangeWorldEvent extends Event {

    private final ServerPlayerEntity player;
    private final String worldFrom;
    private final String worldTo;

    public PlayerChangeWorldEvent (ServerPlayerEntity player, String worldFrom, String worldTo) {

        this.player = player;
        this.worldFrom = worldFrom;
        this.worldTo = worldTo;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public String getWorldFrom() {

        return this.worldFrom;

    }

    public String getWorldTo() {

        return this.worldTo;

    }

}