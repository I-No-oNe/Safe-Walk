package net.i_no_am.sw;

public class SafeWalkConfig {
    private static boolean isSafeWalkEnabled = true;

    public static boolean isSafeWalkEnabled() {
        return isSafeWalkEnabled;
    }

    public static void toggleSafeWalk() {
        isSafeWalkEnabled = !isSafeWalkEnabled;
    }
}
