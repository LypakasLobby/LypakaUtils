package com.lypaka.lypakautils.Commands;

import com.lypaka.lypakautils.ConfigGetters;
import com.lypaka.lypakautils.FancyText;
import com.lypaka.lypakautils.LypakaUtils;
import com.lypaka.lypakautils.MiscHandlers.MessageHandler;
import com.lypaka.lypakautils.MiscHandlers.PermissionHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.configurate.serialize.SerializationException;

public class ReloadCommand {

    public ReloadCommand (CommandDispatcher<CommandSourceStack> dispatcher) {

        for (String a : LypakaUtilsCommand.ALIASES) {

            dispatcher.register(
                    Commands.literal(a)
                            .then(
                                    Commands.literal("reload")
                                            .executes(c -> {

                                                if (c.getSource().getEntity() instanceof ServerPlayer) {

                                                    ServerPlayer player = (ServerPlayer) c.getSource().getEntity();
                                                    if (!PermissionHandler.hasPermission(player, "lypakautils", "lypakautils.command.admin")) {

                                                        MessageHandler.sendMessage(player, "&cYou don't have permission to use this command!");
                                                        return 1;

                                                    }

                                                }

                                                try {

                                                    LypakaUtils.configManager.load();
                                                    ConfigGetters.load();

                                                } catch (SerializationException e) {

                                                    throw new RuntimeException(e);

                                                }
                                                MessageHandler.sendMessage(c.getSource(), FancyText.getFormattedString("&aSuccessfully reloaded LypakaUtils configuration!"));
                                                return 0;

                                            })
                            )
            );

        }

    }

}
