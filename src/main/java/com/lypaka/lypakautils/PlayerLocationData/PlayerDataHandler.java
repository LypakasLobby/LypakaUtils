package com.lypaka.lypakautils.PlayerLocationData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDataHandler {

    public static Map<UUID, PlayerLocation> playerLocationMap = new HashMap<>();

    public static void setLastKnownLandLocation (UUID uuid, int x, int y, int z) {

        PlayerLocation location = playerLocationMap.get(uuid);
        location.setLastLandLocation(new int[]{x, y, z});

    }

    public static int calculateStepsTaken (UUID uuid, int currentX, int currentY, int currentZ) {

        PlayerLocation location = playerLocationMap.get(uuid);
        int lastX = location.getLastX();
        int lastZ = location.getLastZ();
        int changeX = currentX - lastX;
        int changeZ = currentZ - lastZ;

        location.setCurrentX(currentX);
        location.setCurrentY(currentY);
        location.setCurrentZ(currentZ);
        location.setLastX(currentX);
        location.setLastY(currentY);
        location.setLastZ(currentZ);
        if (changeX == -currentX && changeZ == -currentZ) {

            return 0;

        }
        int stepsTaken = Math.abs(changeX) +  Math.abs(changeZ);
        if (stepsTaken > 20 || stepsTaken == 0) {

            return 0;

        }
        return stepsTaken;

    }

}
