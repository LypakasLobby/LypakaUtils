package com.lypaka.lypakautils.MiscHandlers;

import com.lypaka.lypakautils.FancyText;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.level.ServerPlayer;

public class MessageHandler {

    public static void sendMessage (ServerPlayer target, String message) {

        target.sendChatMessage(
                OutgoingChatMessage.create(
                        PlayerChatMessage.unsigned(target.getUUID(), FancyText.getFormattedString(message))
                ),
                true,
                ChatType.bind(ChatType.CHAT, target)
        );

    }

    public static void sendMessage (CommandSourceStack sender, String message) {

        sender.sendSystemMessage(FancyText.getFormattedText(message));

    }

}
