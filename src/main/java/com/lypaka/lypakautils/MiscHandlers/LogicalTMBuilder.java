package com.lypaka.lypakautils.MiscHandlers;

import com.lypaka.lypakautils.LypakaUtils;
import com.pixelmonmod.pixelmon.enums.TMType;
import com.pixelmonmod.pixelmon.enums.technicalmoves.ITechnicalMove;
import net.minecraft.item.ItemStack;

public class LogicalTMBuilder {

    public static ItemStack buildDisc (String discType, String move, int generation) {

        discType = discType.toLowerCase();
        ItemStack tm = ItemStackBuilder.buildFromStringID("pixelmon:" + discType + "_gen" + generation);
        int moveID = -1;
        TMType tmType = TMType.TM1;
        String value = discType.toUpperCase() + generation;
        if (value.equalsIgnoreCase("TM9")) tmType = TMType.TM9;
        if (value.equalsIgnoreCase("TR8")) tmType = TMType.TR8;
        if (value.equalsIgnoreCase("TM8")) tmType = TMType.TM8;
        if (value.equalsIgnoreCase("TM7")) tmType = TMType.TM7;
        if (value.equalsIgnoreCase("TM6")) tmType = TMType.TM6;
        if (value.equalsIgnoreCase("TM5")) tmType = TMType.TM5;
        if (value.equalsIgnoreCase("TM4")) tmType = TMType.TM4;
        if (value.equalsIgnoreCase("TM3")) tmType = TMType.TM3;
        if (value.equalsIgnoreCase("TM2")) tmType = TMType.TM2;
        ITechnicalMove[] allMoves = ITechnicalMove.getAllFor(tmType);
        for (ITechnicalMove attack : allMoves) {

            if (attack.getAttackName().equalsIgnoreCase(move)) {

                moveID = attack.getId();
                break;

            }

        }

        if (moveID != -1) {

            tm.getOrCreateTag().putInt("tm", moveID);
            return tm;

        }

        LypakaUtils.logger.error("Couldn't get " + discType + " " + generation + " move: " + move + " from Pixelmon's registry!");
        return null;

    }

}
