package com.lypaka.lypakautils.Commands;

import com.lypaka.lypakautils.LypakaUtils;
import com.mojang.brigadier.context.StringRange;
import com.mojang.brigadier.suggestion.Suggestion;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

import java.util.*;

@Mod.EventBusSubscriber(modid = LypakaUtils.MOD_ID)
public class LypakaUtilsCommand {

    public static List<String> ALIASES = Arrays.asList("lypakautils", "lyputils", "lutils");
    public static Map<String, Collection<Suggestion>> suggestions;

    @SubscribeEvent
    public static void onCommandRegistration (RegisterCommandsEvent event) {

        new PermissionCommand(event.getDispatcher());
        new ReloadCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());

    }

    public static void createCommandSuggestionProviders() {

        suggestions = new HashMap<>();
        List<String> commandNames = Arrays.asList("permission", "reload");
        Suggestion add = new Suggestion(new StringRange(0, 1), "add");
        Suggestion remove = new Suggestion(new StringRange(0, 1), "remove");
        Collection<Suggestion> collection = Arrays.asList(add, remove);
        suggestions.put("permission", collection);

    }

}
