package com.lypaka.lypakautils;

import net.minecraft.network.chat.TextColor;

public class FancyText {

    /**
     * Takes a String with formatting codes (like &e for yellow) and replaces the & with the UTF thing to make the text yellow.
     * Use this for ItemStack display names.
     * @param unformattedString
     * @return String object with formatted text
     */
    public static String getFormattedString (String unformattedString) {

        return unformattedString.replace("&", "\u00a7");

    }

    /**
     * Takes a String with formatting codes, replaces those codes with the UTF shit, and returns a TextComponentString with formatting.
     * Use this for sending messages to players, console, ItemStack lore, stuff like that.
     * @param unformattedText
     * @return
     */
    public static TextColor getFormattedText (String unformattedText) {

        //TextColor
        //TextComponentHelper
        //return ChatFormatting.valueOf(unformattedText);
        return TextColor.parseColor(unformattedText);

    }

}
