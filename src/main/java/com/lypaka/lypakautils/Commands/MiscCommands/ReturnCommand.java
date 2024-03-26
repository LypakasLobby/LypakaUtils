package com.lypaka.lypakautils.Commands.MiscCommands;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.lypaka.lypakautils.MiscHandlers.WorldHelpers;
import com.lypaka.lypakautils.PlayerLocationData.PlayerDataHandler;
import com.lypaka.lypakautils.PlayerLocationData.PlayerLocation;
import com.lypaka.lypakautils.WorldStuff.WorldMap;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;

public class ReturnCommand {

    public ReturnCommand (CommandDispatcher<CommandSource> dispatcher) {

        dispatcher.register(
                Commands.literal("return")
                        .executes(c -> {

                            if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                if (!PermissionHandler.hasPermission(player, "lypakautils.command.return")) {

                                    player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                    return 0;

                                }

                                PlayerLocation playerLocation = PlayerDataHandler.playerLocationMap.get(player.getUniqueID());
                                String lastDeathLocation = playerLocation.getLastDeathLocation();
                                if (lastDeathLocation != null) {

                                    String dimension = lastDeathLocation.split(",")[0];
                                    int x = Integer.parseInt(lastDeathLocation.split(",")[1]);
                                    int y = Integer.parseInt(lastDeathLocation.split(",")[2]);
                                    int z = Integer.parseInt(lastDeathLocation.split(",")[3]);
                                    String currentDimension = playerLocation.getCurrentDimension();
                                    if (dimension.equalsIgnoreCase(currentDimension)) {

                                        WorldHelpers.teleportPlayer(player, WorldMap.getWorldName(player), x, y, z, player.rotationYaw, player.rotationPitch);

                                    } else {

                                        WorldHelpers.teleportPlayerToDimension(player, dimension, x, y, z, player.rotationYaw, player.rotationPitch);

                                    }
                                    player.sendMessage(FancyText.getFormattedText("&aSuccessfully returned you to your last known death location!"), player.getUniqueID());

                                } else {

                                    player.sendMessage(FancyText.getFormattedText("&cNo death location recorded!"), player.getUniqueID());

                                }

                            }

                            return 1;

                        })
        );

    }

}
