package net.i_no_am.sw;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.i_no_am.sw.client.Global;
import net.i_no_am.sw.command.ModCommand;
import net.i_no_am.sw.config.SafeWalkConfig;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class SafeWalk implements ClientModInitializer, Global {
		/*Toggle Mod Key*/
	KeyBinding BIND = new KeyBinding("key.safe_walk_toggle.mod", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_C, "key.categories.safewalk");
		/*BedWars Mode Key*/
	KeyBinding BW = new KeyBinding("key.safe_walk_toggle.bw", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, "key.categories.safewalk");
/*------------------------------------------------------------------------------------*/
	@Override
	public void onInitializeClient() {
		/*Registering Keys*/
		KeyBindingHelper.registerKeyBinding(BIND);
		KeyBindingHelper.registerKeyBinding(BW);
		/*Registering Commands*/
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ModCommand.registerCommands()));
		/*Registering Config*/
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			SafeWalkConfig.loadConfig();
			if (BIND.wasPressed()) {
				SafeWalkConfig.toggleMod();
			}
			if (BW.wasPressed()) {
				SafeWalkConfig.toggleBedWars();
			}
		});
	}
}
