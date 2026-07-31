package com.mrpgc_skill_tree.mixin;

import com.mrpgc_skill_tree.effect.MrpgSkillEffects;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/// Camouflaged only grants the follow-range evasion of stealth
@Mixin(TrackTargetGoal.class)
public class CamouflagedFollowRangeMixin {
    @Shadow
    @Final
    protected MobEntity mob;

    @Inject(method = "getFollowRange", at = @At("HEAD"), cancellable = true)
    private void getFollowRange_HEAD_Camouflaged(CallbackInfoReturnable<Double> cir) {
        var target = mob.getTarget();
        if (target != null && target.hasStatusEffect(MrpgSkillEffects.CAMOUFLAGED.entry)) {
            cir.setReturnValue(1.0);
            cir.cancel();
        }
    }
}
