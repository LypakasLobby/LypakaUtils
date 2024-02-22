package com.lypaka.lypakautils.Commands;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.LPPlayer;
import com.lypaka.lypakautils.LypakaUtils;
import com.lypaka.lypakautils.MiscHandlers.MessageHandler;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;

public class PermissionCommand {

    public PermissionCommand (CommandDispatcher<CommandSourceStack> dispatcher) {

        for (String a : LypakaUtilsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("permission")
                                            .then(
                                                    Commands.literal("add")
                                                            .then(
                                                                    Commands.argument("player", EntityArgument.player())
                                                                            .then(
                                                                                    Commands.argument("permission", StringArgumentType.string())
                                                                                            .executes(c -> {

                                                                                                if (c.getSource().getEntity() instanceof ServerPlayer) {

                                                                                                    ServerPlayer player = (ServerPlayer) c.getSource().getEntity();
                                                                                                    if (!PermissionHandler.hasPermission(player, "lypakautils", "lypakapermissions.command.admin")) {

                                                                                                        MessageHandler.sendMessage(player, "&cYou don't have permission to use this command!");
                                                                                                        return 0;

                                                                                                    }

                                                                                                }

                                                                                                ServerPlayer target = EntityArgument.getPlayer(c, "player");
                                                                                                String permission = StringArgumentType.getString(c, "permission");
                                                                                                LPPlayer lpPlayer = LypakaUtils.playerMap.get(target.getUUID());
                                                                                                lpPlayer.addPermission(permission);
                                                                                                MessageHandler.sendMessage(c.getSource(), FancyText.getFormattedString("&aSuccessfully added permission: " + permission + " to " + target.getName().getString()));

                                                                                                return 1;

                                                                                            })
                                                                            )
                                                            )

                                            )
                                            .then(
                                                    Commands.literal("remove")
                                                            .then(
                                                                    Commands.argument("player", EntityArgument.player())
                                                                            .then(
                                                                                    Commands.argument("permission", StringArgumentType.string())
                                                                                            .executes(c -> {

                                                                                                if (c.getSource().getEntity() instanceof ServerPlayer) {

                                                                                                    ServerPlayer player = (ServerPlayer) c.getSource().getEntity();
                                                                                                    if (!PermissionHandler.hasPermission(player, "lypakautils", "lypakapermissions.command.admin")) {

                                                                                                        MessageHandler.sendMessage(player, "&cYou don't have permission to use this command!");
                                                                                                        return 0;

                                                                                                    }

                                                                                                }

                                                                                                ServerPlayer target = EntityArgument.getPlayer(c, "player");
                                                                                                String permission = StringArgumentType.getString(c, "permission");
                                                                                                LPPlayer lpPlayer = LypakaUtils.playerMap.get(target.getUUID());

                                                                                                lpPlayer.removePermission(permission);
                                                                                                MessageHandler.sendMessage(c.getSource(), FancyText.getFormattedString("&aSuccessfully removed permission: " + permission + " from " + target.getName().getString()));

                                                                                                return 1;

                                                                                            })
                                                                            )
                                                            )

                                            )
                            )
            );

        }

    }

}
