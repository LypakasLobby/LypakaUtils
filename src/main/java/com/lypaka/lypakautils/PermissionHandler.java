package com.lypaka.lypakautils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.permission.PermissionAPI;
import net.minecraftforge.server.permission.nodes.PermissionNode;

import java.util.HashMap;
import java.util.Map;

public class PermissionHandler {

    // Used to store all my permissions across all of my mods for ease of access
    public static Map<String, PermissionNode<Boolean>> permissions = new HashMap<>();

    public static boolean hasPermission (ServerPlayer player, String node) {

        if (!permissions.containsKey(node)) return false;
        return PermissionAPI.getPermission(player, permissions.get(node));

    }

}
