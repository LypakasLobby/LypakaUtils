package com.lypaka.lypakautils.MiscHandlers;

import com.lypaka.lypakautils.LPPlayer;
import com.lypaka.lypakautils.LypakaUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.server.permission.PermissionAPI;

public class PermissionHandler {

    /**
     * This boolean check for permissions is platform-independent. Meaning it will work for pure Forge, SpongeForge, and Spigot/Forge hybrids.
     * (provided that the unstable Spigot/Forge hybrid of choice doesn't do some janky hacky shit with permission checks)
     * It applies to commands as well as basically any other permission check (like "if have this permission, can click this block" for example)
     * @param player
     * @param permission
     * @return
     */
    public static boolean hasPermission (ServerPlayerEntity player, String permission) {

        if (permission.equalsIgnoreCase("")) return true;
        LPPlayer lpPlayer = LypakaUtils.playerMap.get(player.getUniqueID());
        if (lpPlayer.getPermissions().contains(permission)) return true;
        if (lpPlayer.getPermissions().contains("*")) return true;
        return PermissionAPI.hasPermission(player, permission);

    }

}
