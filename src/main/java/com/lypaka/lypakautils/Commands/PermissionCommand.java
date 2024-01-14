package com.lypaka.lypakautils.Commands;

import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.LPPlayer;
import com.lypaka.lypakautils.LypakaUtils;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;

import java.util.Arrays;

public class PermissionCommand {

    private static SuggestionProvider<CommandSource> OPTIONS = (context, builder) -> ISuggestionProvider.suggest(Arrays.asList("add", "remove"), builder);

    public PermissionCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : LypakaUtilsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("permission")
                                            .then(
                                                    Commands.argument("option", StringArgumentType.string())
                                                            .suggests(OPTIONS)
                                                            .then(
                                                                    Commands.argument("player", EntityArgument.player())
                                                                            .then(
                                                                                    Commands.argument("permission", StringArgumentType.string())
                                                                                            .executes(c -> {

                                                                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                                                                    if (!PermissionHandler.hasPermission(player, "lypakapermissions.command.admin")) {

                                                                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                                                                        return 0;

                                                                                                    }

                                                                                                }

                                                                                                ServerPlayerEntity target = EntityArgument.getPlayer(c, "player");
                                                                                                String permission = StringArgumentType.getString(c, "permission");
                                                                                                String option = StringArgumentType.getString(c, "option");
                                                                                                LPPlayer lpPlayer = LypakaUtils.playerMap.get(target.getUniqueID());

                                                                                                if (option.equalsIgnoreCase("add")) {

                                                                                                    lpPlayer.addPermission(permission);
                                                                                                    c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully added permission: " + permission + " to " + target.getName().getString()), true);

                                                                                                } else {

                                                                                                    lpPlayer.removePermission(permission);
                                                                                                    c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully removed permission: " + permission + " from " + target.getName().getString()), true);

                                                                                                }

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
