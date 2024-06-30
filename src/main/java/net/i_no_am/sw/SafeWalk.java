package net.i_no_am.sw;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class SafeWalk implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(LiteralArgumentBuilder.<FabricClientCommandSource>literal("safe-walk")
                .then(ClientCommandManager.argument("enabled", BoolArgumentType.bool())
                        .executes(SafeWalk::executeToggleCommand))));
	}

	private static int executeToggleCommand(CommandContext<FabricClientCommandSource> context) {
		boolean enabled = BoolArgumentType.getBool(context, "enabled");
		if (enabled) {
			SafeWalkConfig.toggleSafeWalk();
			MinecraftClient.getInstance().player.sendMessage(Text.literal(("§6SafeWalk§r is now " + (SafeWalkConfig.isSafeWalkEnabled() ? "§2enabled" : "§4disabled"))));
		}
		return 1;
	}
}
