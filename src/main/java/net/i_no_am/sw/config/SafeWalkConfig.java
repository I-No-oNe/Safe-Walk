package net.i_no_am.sw.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import net.fabricmc.loader.api.FabricLoader;
import net.i_no_am.sw.client.Global;
import net.minecraft.text.Text;

public class SafeWalkConfig implements Global {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_DIR = new File(FabricLoader.getInstance().getConfigDir().toFile(), "safe-walk");
    private static final File CONFIG_FILE = new File(CONFIG_DIR, "safewalk.json");

    private boolean isEnabled = true;

    private boolean bedWarsMode = true;

    public static SafeWalkConfig INSTANCE;

    public static void loadConfig() {
        if (!CONFIG_DIR.exists()) {
            CONFIG_DIR.mkdirs();
        }

        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                INSTANCE = GSON.fromJson(reader, SafeWalkConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            INSTANCE = new SafeWalkConfig();
            saveConfig();
        }
    }

    public static void saveConfig() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(INSTANCE, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isEnabled() {
        if (INSTANCE == null) {
            loadConfig();
        }
        return INSTANCE.isEnabled;
    }

    public static boolean isBedWars() {
        if (INSTANCE == null) {
            loadConfig();
        }
        return INSTANCE.bedWarsMode;
    }

    public static void setEnabled(boolean enabled) {
        if (INSTANCE == null) {
            loadConfig();
        }
        INSTANCE.isEnabled = enabled;
        saveConfig();
    }

    public static void setBedWarsEnabled(boolean enabled) {
        if (INSTANCE == null) {
            loadConfig();
        }
        INSTANCE.bedWarsMode = enabled;
        saveConfig();
    }

    public static void toggleMod() {
        if (INSTANCE == null) {
            loadConfig();
        }
        boolean enabled = !SafeWalkConfig.isEnabled();
        SafeWalkConfig.setEnabled(enabled);
        mc.player.sendMessage(Text.literal("§6SafeWalk§r is now " + (enabled ? "§2enabled" : "§4disabled")));
    }

    public static void toggleBedWars() {
        if (INSTANCE == null) {
            loadConfig();
        }
        boolean enabled = !SafeWalkConfig.isBedWars();
        SafeWalkConfig.setBedWarsEnabled(enabled);
        mc.player.sendMessage(Text.literal("§6Bed Wars Mode§r is now " + (enabled ? "§2enabled" : "§4disabled")));
    }
}
