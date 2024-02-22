package com.lypaka.lypakautils.PlayerLocationData;

public class PlayerLocation {

    private int currentX;
    private int currentZ;
    private int lastX;
    private int lastZ;
    private int[] lastLandLocation = new int[3];

    public PlayerLocation (int currentX, int currentZ, int lastX, int lastZ) {

        this.currentX = currentX;
        this.currentZ = currentZ;
        this.lastX = lastX;
        this.lastZ = lastZ;

    }

    public int getCurrentX() {

        return this.currentX;

    }

    public int getCurrentZ() {

        return this.currentZ;

    }

    public int getLastX() {

        return this.lastX;

    }

    public int getLastZ() {

        return this.lastZ;

    }

    public void setCurrentX (int x) {

        this.currentX = x;

    }

    public void setCurrentZ (int z) {

        this.currentZ = z;

    }

    public void setLastX (int x) {

        this.lastX = x;

    }

    public void setLastZ (int z) {

        this.lastZ = z;

    }

    public int[] getLastLandLocation() {

        return this.lastLandLocation;

    }

    public void setLastLandLocation (int[] coords) {

        this.lastLandLocation = coords;

    }

}
