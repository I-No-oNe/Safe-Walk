package net.i_no_am.sw.mixin;

import net.i_no_am.sw.SafeWalkConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(KeyBinding.class)
public abstract class MixinKeyBinding {
	@Shadow public abstract boolean equals(KeyBinding other);

	@Shadow public abstract boolean isPressed();
	@Unique
	MinecraftClient mc = MinecraftClient.getInstance();
	@Inject(method = "isPressed",at = @At("HEAD"),cancellable = true)
	private void pressHook(CallbackInfoReturnable<Boolean> cir){
		if(SafeWalkConfig.isSafeWalkEnabled() && this.equals(mc.options.sneakKey) && mc.player != null && mc.world != null && mc.player.isOnGround() && mc.world.getBlockState(new BlockPos((int) Math.floor(mc.player.getPos().getX()), (int) Math.floor(mc.player.getPos().getY()) - 1, (int) Math.floor(mc.player.getPos().getZ()))).isAir()){
			cir.setReturnValue(true);
		}
	}
}