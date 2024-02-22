package com.lypaka.lypakautils.API;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class PermissionQueueEvent extends Event {

    private final ServerPlayer player;
    private final String permission;
    private boolean bypass;

    public PermissionQueueEvent (ServerPlayer player, String permission, boolean bypass) {

        this.player = player;
        this.permission = permission;
        this.bypass = bypass;

    }

    public ServerPlayer getPlayer() {

        return this.player;

    }

    public String getPermission() {

        return this.permission;

    }

    public boolean isBypassed() {

        return this.bypass;

    }

    public void setBypass (boolean bypass) {

        this.bypass = bypass;

    }

}
