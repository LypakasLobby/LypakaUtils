package com.lypaka.lypakautils.MiscHandlers;

import com.lypaka.lypakautils.LPPlayer;
import com.lypaka.lypakautils.LypakaUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.permission.PermissionAPI;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.minecraftforge.server.permission.nodes.PermissionTypes;

import java.util.HashMap;
import java.util.Map;

public class PermissionHandler {

    public static Map<String, Map<String, PermissionNode<Boolean>>> permissions = new HashMap<>();

    public static boolean hasPermission (ServerPlayer player, String modID, String permission) {

        if (player.hasPermissions(4)) return true;
        if (permission.equalsIgnoreCase("")) return true;
        LPPlayer lpPlayer = LypakaUtils.playerMap.get(player.getUUID());
        if (lpPlayer.getPermissions().contains(permission)) return true;
        if (lpPlayer.getPermissions().contains("*")) return true;
        PermissionNode<Boolean> permissionNode = null;
        if (permissions.containsKey(modID)) {

            Map<String, PermissionNode<Boolean>> permissionsMap = permissions.get(modID);
            if (permissionsMap.containsKey(permission)) {

                permissionNode = permissionsMap.get(permission);

            }

        }
        if (permissionNode == null) {

            LypakaUtils.logger.error("Could not locate permission node: " + permission + " from " + modID + "!");
            return false;

        }
        return PermissionAPI.getPermission(player, permissionNode);

    }

    public static void createPermissionNode (String modID, String permission) {

        PermissionNode<Boolean> permissionNode = new PermissionNode<>(modID, permission, PermissionTypes.BOOLEAN, (player, uuid, contexts) -> false);
        Map<String, PermissionNode<Boolean>> map = new HashMap<>();
        if (permissions.containsKey(modID)) {

            map = permissions.get(modID);

        }
        map.put(permission, permissionNode);

    }

}
