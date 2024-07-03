package net.i_no_am.sw.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.i_no_am.sw.client.Global;
import net.i_no_am.sw.config.SafeWalkConfig;
import net.minecraft.text.Text;

import java.util.concurrent.CompletableFuture;

public class ModCommand implements Global {

    protected static final int COMMAND_FAIL = -1;
    protected static final int COMMAND_PASS = 0;
    protected static final int SINGLE_SUCCESS = 1;

    public static LiteralArgumentBuilder<FabricClientCommandSource> registerCommands() {
        return LiteralArgumentBuilder.<FabricClientCommandSource>literal("safe-walk")
                .then(ClientCommandManager.argument("enabled", BoolArgumentType.bool())
                        .suggests(ModCommand::suggestCommands)
                        .executes(ModCommand::executeToggleCommand)
                )
                .then(LiteralArgumentBuilder.<FabricClientCommandSource>literal("bedwars")
                        .then(ClientCommandManager.argument("enabled", BoolArgumentType.bool())
                                .suggests(ModCommand::suggestCommands)
                                .executes(ModCommand::executeToggleBedWarsCommand)
                        )
                );
    }

    private static CompletableFuture<Suggestions> suggestCommands(CommandContext<FabricClientCommandSource> context, SuggestionsBuilder builder) {
        builder.suggest("true").suggest("false");
        return builder.buildFuture();
    }

    private static int executeToggleCommand(CommandContext<FabricClientCommandSource> context) {
        boolean enabled = BoolArgumentType.getBool(context, "enabled");
        SafeWalkConfig.setEnabled(enabled);
        mc.player.sendMessage(Text.literal("§6SafeWalk§r is now " + (enabled ? "§2enabled" : "§4disabled")));
        return SINGLE_SUCCESS;
    }

    private static int executeToggleBedWarsCommand(CommandContext<FabricClientCommandSource> context) {
        boolean enabled = BoolArgumentType.getBool(context, "enabled");
        SafeWalkConfig.setBedWarsEnabled(enabled);
        mc.player.sendMessage(Text.literal("§6BedWars Mode§r is now " + (enabled ? "§2enabled" : "§4disabled")));
        return SINGLE_SUCCESS;
    }
}
