package com.lypaka.lypakautils.API;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.eventbus.api.Event;

public class PlayerChangeDimensionEvent extends Event {

    private final ServerPlayerEntity player;
    private final String dimensionFrom;
    private final String dimensionTo;

    public PlayerChangeDimensionEvent (ServerPlayerEntity player, String dimensionFrom, String dimensionTo) {

        this.player = player;
        this.dimensionFrom = dimensionFrom;
        this.dimensionTo = dimensionTo;

    }

    public ServerPlayerEntity getPlayer() {

        return this.player;

    }

    public String getDimensionFrom() {

        return this.dimensionFrom;

    }

    public String getDimensionTo() {

        return this.dimensionTo;

    }

}
