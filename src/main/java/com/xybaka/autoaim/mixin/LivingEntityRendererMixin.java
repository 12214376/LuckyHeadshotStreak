package com.xybaka.autoaim.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.xybaka.autoaim.util.rotation.RotationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = {LivingEntityRenderer.class})
public class LivingEntityRendererMixin {
    @Redirect(method = {"render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;rotLerp(FFF)F", ordinal = 0))
    private float useSilentBodyYaw(float partialTicks, float start, float end, LivingEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity == Minecraft.m_91087_().f_91074_ && RotationManager.hasRenderOverride()) {
            float yaw = RotationManager.getRenderYaw();
            return Mth.m_14189_(partialTicks, yaw, yaw);
        }
        return Mth.m_14189_(partialTicks, start, end);
    }

    @Redirect(method = {"render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;rotLerp(FFF)F", ordinal = 1))
    private float useSilentHeadYaw(float partialTicks, float start, float end, LivingEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity == Minecraft.m_91087_().f_91074_ && RotationManager.hasRenderOverride()) {
            float yaw = RotationManager.getRenderYaw();
            return Mth.m_14189_(partialTicks, yaw, yaw);
        }
        return Mth.m_14189_(partialTicks, start, end);
    }

    @Redirect(method = {"render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(FFF)F", ordinal = 0))
    private float useSilentHeadPitch(float partialTicks, float start, float end, LivingEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity == Minecraft.m_91087_().f_91074_ && RotationManager.isSilentActive()) {
            float pitch = RotationManager.getSilentPitch();
            return Mth.m_14179_(partialTicks, pitch, pitch);
        }
        return Mth.m_14179_(partialTicks, start, end);
    }
}
