package com.lypaka.lypakautils.Commands;

import com.lypaka.lypakautils.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.LypakaUtils;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

public class ReloadCommand {

    public ReloadCommand (CommandDispatcher<CommandSource> dispatcher) {

        for (String a : LypakaUtilsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("reload")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayerEntity) {

                                                    ServerPlayerEntity player = (ServerPlayerEntity) c.getSource().getEntity();
                                                    if (!PermissionHandler.hasPermission(player, "lypakautils.command.admin")) {

                                                        player.sendMessage(FancyText.getFormattedText("&cYou don't have permission to use this command!"), player.getUniqueID());
                                                        return 1;

                                                    }

                                                }

                                                try {

                                                    LypakaUtils.configManager.load();
                                                    ConfigGetters.load();

                                                } catch (ObjectMappingException e) {

                                                    throw new RuntimeException(e);

                                                }
                                                c.getSource().sendFeedback(FancyText.getFormattedText("&aSuccessfully reloaded LypakaUtils configuration!"), true);
                                                return 0;

                                            })
                            )
            );

        }

    }

}
