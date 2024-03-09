package com.lypaka.lypakautils;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigGetters {

    public static boolean antiPixelmonSpriteWearingEnabled;
    public static boolean tickListenerEnabled;
    public static List<String> fishingRodBlacklist;
    public static boolean loadPokemonTypeMap;
    public static Map<String, String> messages;
    public static Map<String, Map<String, String>> permissionGroups;

    public static void load() throws ObjectMappingException {

        boolean save = false;
        if (!LypakaUtils.configManager.getConfigNode(0, "Disable-Pixelmon-Sprite-Wearing").isVirtual()) {

            antiPixelmonSpriteWearingEnabled = LypakaUtils.configManager.getConfigNode(0, "Disable-Pixelmon-Sprite-Wearing").getBoolean();

        } else {

            if (!save) save = true;
            antiPixelmonSpriteWearingEnabled = false;
            LypakaUtils.configManager.getConfigNode(0, "Disable-Pixelmon-Sprite-Wearing").setComment("If true, it will enable the \"anti-sprite-wearing\" system that will warn players about wearing Pixelmon sprites on their heads and enable the check that runs every 5 seconds to remove them");
            LypakaUtils.configManager.getConfigNode(0, "Disable-Pixelmon-Sprite-Wearing").setValue(false);

        }
        tickListenerEnabled = LypakaUtils.configManager.getConfigNode(0, "Enable-Tick-Listener").getBoolean();
        fishingRodBlacklist = new ArrayList<>();
        if (!LypakaUtils.configManager.getConfigNode(0, "Fishing-Rod-Blacklist").isVirtual()) {

            fishingRodBlacklist = LypakaUtils.configManager.getConfigNode(0, "Fishing-Rod-Blacklist").getList(TypeToken.of(String.class));

        } else {

            if (!save) save = true;
            LypakaUtils.configManager.getConfigNode(0, "Fishing-Rod-Blacklist").setComment("Sets a list of worlds where Minecraft's fishing rods are NOT ALLOWED to be used at all, this is for preventing your players from moving your NPCs with fishing rods");
            LypakaUtils.configManager.getConfigNode(0, "Fishing-Rod-Blacklist").setValue(fishingRodBlacklist);

        }
        loadPokemonTypeMap = false;
        if (LypakaUtils.configManager.getConfigNode(0, "Load-Pokemon-Type-Map").isVirtual()) {

            if (!save) save = true;
            LypakaUtils.configManager.getConfigNode(0, "Load-Pokemon-Type-Map").setValue(false);

        } else {

            loadPokemonTypeMap = LypakaUtils.configManager.getConfigNode(0, "Load-Pokemon-Type-Map").getBoolean();

        }
        messages = new HashMap<>();
        if (!LypakaUtils.configManager.getConfigNode(0, "Messages").isVirtual()) {

            messages = LypakaUtils.configManager.getConfigNode(0, "Messages").getValue(new TypeToken<Map<String, String>>() {});

        } else {

            if (!save) save = true;
            messages.put("Equip-Pixelmon-Sprite", "&eWearing this item on your head is not allowed! Please remove it or it could be deleted from you!");
            messages.put("Fishing-Rod", "&eFishing rods are not enabled in this world!");
            messages.put("Sprite-Removal-Warning", "&eYou have 10 seconds to remove the Pixelmon sprite you are wearing or it will be deleted.");
            LypakaUtils.configManager.getConfigNode(0, "Messages").setValue(messages);

        }
        permissionGroups = LypakaUtils.configManager.getConfigNode(1, "Groups").getValue(new TypeToken<Map<String, Map<String, String>>>() {});

        if (save) {

            LypakaUtils.configManager.save();

        }

    }

}
