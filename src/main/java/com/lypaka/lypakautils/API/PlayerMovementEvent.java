package com.lypaka.lypakautils.API;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.eventbus.api.Event;

public abstract class PlayerMovementEvent extends Event {

    private final ServerPlayer player;
    private final int stepsTaken;

    public PlayerMovementEvent (ServerPlayer player, int stepsTaken) {

        this.player = player;
        this.stepsTaken = stepsTaken;

    }

    public static class Land extends PlayerMovementEvent {

        private final String blockID;

        public Land (ServerPlayer player, int stepsTaken, String blockID) {

            super(player, stepsTaken);
            this.blockID = blockID;

        }

        public String getBlockID() {

            return this.blockID;

        }

    }

    public static class Swim extends PlayerMovementEvent {

        private final String blockID;

        public Swim (ServerPlayer player, int stepsTaken, String blockID) {

            super(player, stepsTaken);
            this.blockID = blockID;

        }

        public String getBlockID() {

            return this.blockID;

        }

    }

    public ServerPlayer getPlayer() {

        return this.player;

    }

    public int getStepsTaken() {

        return this.stepsTaken;

    }

}
