package net.i_no_am.sw.util;

import net.i_no_am.sw.client.Global;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class Utils implements Global {

/* Ported from https://github.com/ItziSpyder/ClickCrystals/blob/main/src/main/java/io/github/itzispyder/clickcrystals/util/minecraft/HotbarUtils.java */

    public static boolean nameContains(String contains) {
        return nameContains(contains, Hand.MAIN_HAND);
    }

    public static boolean nameContains(String contains, Hand hand) {
        if (mc.player == null) {
            return false;
        }

        ItemStack item = mc.player.getStackInHand(hand);
        return item != null && item.getTranslationKey().toLowerCase().contains(contains.toLowerCase());
    }
}

