package com.lypaka.lypakautils.MiscHandlers;

import com.google.common.reflect.TypeToken;
import com.lypaka.lypakautils.LPPlayer;
import com.lypaka.lypakautils.LypakaUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.server.permission.PermissionAPI;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;

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

        if (player.hasPermissionLevel(4)) return true;
        if (permission.equalsIgnoreCase("")) return true;
        try {

            LPPlayer lpPlayer = LypakaUtils.playerMap.get(player.getUniqueID());
            if (lpPlayer.getPermissions().contains(permission)) return true;
            if (lpPlayer.getPermissions().contains("*")) return true;

        } catch (NullPointerException e) {

            LypakaUtils.logger.warn("Caught a NullPointer trying to get a LPPlayer object from " + player.getName().getString() + " for permission checks. This is usually not a good thing, as it usually indicates that there is a ghost player somewhere.");
            LypakaUtils.logger.info("Attempting to patch...");
            LypakaUtils.playerConfigManager.loadPlayer(player.getUniqueID());
            try {

                List<String> groups = LypakaUtils.playerConfigManager.getPlayerConfigNode(player.getUniqueID(), "Groups").getList(TypeToken.of(String.class));
                List<String> permissions = LypakaUtils.playerConfigManager.getPlayerConfigNode(player.getUniqueID(), "Permissions").getList(TypeToken.of(String.class));
                LPPlayer lpPlayer = new LPPlayer(player.getUniqueID(), groups, permissions);
                LypakaUtils.playerMap.put(player.getUniqueID(), lpPlayer);
                try {

                    lpPlayer = LypakaUtils.playerMap.get(player.getUniqueID());
                    if (lpPlayer.getPermissions().contains(permission)) return true;
                    if (lpPlayer.getPermissions().contains("*")) return true;

                } catch (NullPointerException e2) {

                    LypakaUtils.logger.error("Couldn't patch, something is very wrong with player: " + player.getName().getString() + " game profile...");

                }

            } catch (ObjectMappingException ex) {

                throw new RuntimeException(ex);

            }

        }
        return PermissionAPI.hasPermission(player, permission);

    }

}
