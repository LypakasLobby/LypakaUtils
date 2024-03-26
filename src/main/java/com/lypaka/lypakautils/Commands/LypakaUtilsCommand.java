package com.lypaka.lypakautils.Commands;

import com.lypaka.lypakautils.Commands.MiscCommands.ReturnCommand;
import com.lypaka.lypakautils.LypakaUtils;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = LypakaUtils.MOD_ID)
public class LypakaUtilsCommand {

    public static List<String> ALIASES = Arrays.asList("lypakautils", "lyputils", "lutils");

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new PermissionCommand(event.getDispatcher());
        new ReloadCommand(event.getDispatcher());

        // MiscCommands
        new ReturnCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

}
