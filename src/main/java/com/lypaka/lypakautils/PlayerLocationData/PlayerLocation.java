package com.lypaka.lypakautils.PlayerLocationData;

public class PlayerLocation {

    private int currentX;
    private int currentZ;
    private int lastX;
    private int lastZ;
    private int[] lastLandLocation = new int[3];
    private String lastKnownDimension;
    private String currentDimension;
    private String lastKnownWorld;
    private String currentWorld;

    public PlayerLocation (int currentX, int currentZ, int lastX, int lastZ, String lastKnownDimension, String currentDimension, String lastKnownWorld, String currentWorld) {

        this.currentX = currentX;
        this.currentZ = currentZ;
        this.lastX = lastX;
        this.lastZ = lastZ;
        this.lastKnownDimension = lastKnownDimension;
        this.currentDimension = currentDimension;
        this.lastKnownWorld = lastKnownWorld;
        this.currentWorld = currentWorld;

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

    public String getLastKnownDimension() {

        return this.lastKnownDimension;

    }

    public void setLastKnownDimension (String dim) {

        this.lastKnownDimension = dim;

    }

    public String getCurrentDimension() {

        return this.currentDimension;

    }

    public void setCurrentDimension (String dim) {

        this.currentDimension = dim;

    }

    public String getLastKnownWorld() {

        return this.lastKnownWorld;

    }

    public void setLastKnownWorld (String world) {

        this.lastKnownWorld = world;

    }

    public String getCurrentWorld() {

        return this.currentWorld;

    }

    public void setCurrentWorld (String world) {

        this.currentWorld = world;

    }

    public int[] getLastLandLocation() {

        return this.lastLandLocation;

    }

    public void setLastLandLocation (int[] coords) {

        this.lastLandLocation = coords;

    }

}
