package com.lypaka.lypakautils.PlayerLocationData;

public class PlayerLocation {

    private int currentX;
    private int currentY;
    private int currentZ;
    private int lastX;
    private int lastY;
    private int lastZ;
    private int[] lastLandLocation = new int[3];
    private String lastKnownDimension;
    private String currentDimension;
    private String lastKnownWorld;
    private String currentWorld;
    private String lastDeathLocation = null;

    public PlayerLocation (int currentX, int currentY, int currentZ, int lastX, int lastY, int lastZ, String lastKnownDimension, String currentDimension, String lastKnownWorld, String currentWorld) {

        this.currentX = currentX;
        this.currentY = currentY;
        this.currentZ = currentZ;
        this.lastX = lastX;
        this.lastY = lastY;
        this.lastZ = lastZ;
        this.lastKnownDimension = lastKnownDimension;
        this.currentDimension = currentDimension;
        this.lastKnownWorld = lastKnownWorld;
        this.currentWorld = currentWorld;

    }

    public String getCurrentLocation() {

        return this.currentWorld + "," + this.currentX + "," + this.currentY + "," + this.currentZ;

    }

    public int getCurrentX() {

        return this.currentX;

    }

    public int getCurrentY() {

        return this.currentY;

    }

    public int getCurrentZ() {

        return this.currentZ;

    }

    public int getLastX() {

        return this.lastX;

    }

    public int getLastY() {

        return this.lastY;

    }

    public int getLastZ() {

        return this.lastZ;

    }

    public void setCurrentX (int x) {

        this.currentX = x;

    }

    public void setCurrentY (int y) {

        this.currentY = y;

    }

    public void setCurrentZ (int z) {

        this.currentZ = z;

    }

    public void setLastX (int x) {

        this.lastX = x;

    }

    public void setLastY (int y) {

        this.lastY = y;

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

    public String getLastDeathLocation() {

        return this.lastDeathLocation;

    }

    public void setLastDeathLocation (String location) {

        this.lastDeathLocation = location;

    }

}
