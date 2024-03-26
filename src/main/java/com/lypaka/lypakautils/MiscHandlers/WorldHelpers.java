package com.lypaka.lypakautils.MiscHandlers;

import com.lypaka.lypakautils.WorldStuff.WorldMap;
import com.pixelmonmod.pixelmon.world.dimension.drowned.DrownedWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.Locale;
import java.util.Optional;

public class WorldHelpers {

    public static boolean isEntityInUltraSpace (Entity entity) {

        return entity.world.getDimensionKey().getLocation().toString().equalsIgnoreCase("pixelmon:ultra_space");

    }

    public static boolean isEntityInDrownedWorldForSomeUngodlyReason (Entity entity) {

        return entity.world.getDimensionKey().getLocation().toString().equalsIgnoreCase("pixelmon:drowned_world");

    }

    public static boolean isEntityInOverworld (Entity entity) {

        return entity.world.getDimensionKey().getLocation().toString().equalsIgnoreCase("minecraft:overworld");

    }

    public static boolean isEntityInNether (Entity entity) {

        return entity.world.getDimensionKey().getLocation().toString().equalsIgnoreCase("minecraft:nether");

    }

    public static boolean isEntityInEnd (Entity entity) {

        return entity.world.getDimensionKey().getLocation().toString().equalsIgnoreCase("minecraft:the_end") ||
                entity.world.getDimensionKey().getLocation().toString().equalsIgnoreCase("minecraft:end"); // not sure what Minecraft calls that, lol

    }

    public static String getEntityDimensionID (Entity entity) {

        return entity.world.getDimensionKey().getLocation().toString();

    }

    public static void teleportPlayer (ServerPlayerEntity player, String world, int x, int y, int z, float yaw, float pitch) {

        player.teleport(WorldMap.worldMap.get(world), x, y, z, yaw, pitch);

    }

    public static void teleportPlayerToDimension (ServerPlayerEntity player, String dimension, int x, int y, int z, float yaw, float pitch) {

        final RegistryKey<World> destination = getDimension(dimension);
        if (player.world.getDimensionKey() != destination && player.getServer() != null) {
            getWorld(destination).ifPresent(world -> {
                if (destination == DrownedWorld.WORLD) {
                    player.getPersistentData().putDouble("PortalX", player.getPosX());
                    player.getPersistentData().putDouble("PortalY", player.getPosY());
                    player.getPersistentData().putDouble("PortalZ", player.getPosZ());
                    player.getPersistentData().putString("PortalD", player.getServerWorld().getDimensionKey().getLocation().toString());
                }
                player.teleport(world, x, y, z, yaw, pitch);
            });
        }

    }

    public static String getEntityLocation (Entity entity) {

        String worldName = WorldMap.getWorldName(entity.world);
        int x = entity.getPosition().getX();
        int y = entity.getPosition().getY();
        int z = entity.getPosition().getZ();

        return worldName + "," + x + "," + y + "," + z;

    }

    public static RegistryKey<World> getDimension(final String dimension) {
        if (dimension.isEmpty()) {
            return null;
        }
        return getDimension(of(dimension));
    }

    public static ResourceLocation of(String resourceLocation) {
        try {
            return new ResourceLocation(resourceLocation.toLowerCase(Locale.ROOT));
        } catch (ResourceLocationException var2) {
            return null;
        }
    }

    public static RegistryKey<World> getDimension(final ResourceLocation key) {
        return RegistryKey.getOrCreateKey(Registry.WORLD_KEY, key);
    }

    public static Optional<ServerWorld> getWorld(final RegistryKey<World> key) {
        return Optional.ofNullable(ServerLifecycleHooks.getCurrentServer().getWorld((RegistryKey)key));
    }

    public static Optional<ServerWorld> getWorld(final String key) {
        return getWorld(getDimension(key));
    }

}
