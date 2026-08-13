/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.blaze3d.vertex.PoseStack$Pose
 *  com.mojang.blaze3d.vertex.VertexConsumer
 *  com.tacz.guns.entity.EntityKineticBullet
 *  net.minecraft.client.Camera
 *  net.minecraft.client.renderer.MultiBufferSource$BufferSource
 *  net.minecraft.client.renderer.RenderType
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.phys.Vec3
 *  net.minecraftforge.client.event.RenderLevelStageEvent
 *  net.minecraftforge.client.event.RenderLevelStageEvent$Stage
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.event.TickEvent$PlayerTickEvent
 *  net.minecraftforge.event.entity.EntityJoinLevelEvent
 *  net.minecraftforge.event.entity.EntityLeaveLevelEvent
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  org.joml.Matrix4f
 */
package com.xybaka.autoaim.modules.combat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.tacz.guns.entity.EntityKineticBullet;
import com.xybaka.autoaim.modules.Category;
import com.xybaka.autoaim.modules.Module;
import com.xybaka.autoaim.modules.settings.BooleanSetting;
import com.xybaka.autoaim.modules.settings.ModeSetting;
import com.xybaka.autoaim.modules.settings.NumberSetting;
import com.xybaka.autoaim.tacz.HomingBulletHandler;
import com.xybaka.autoaim.tacz.TacZWeaponEnhancementHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.joml.Matrix4f;

public class AutoAim
extends Module {
    public final ModeSetting<String> triggerMode = this.mode("\u8ffd\u8e2a\u89e6\u53d1", "\u5f00\u706b", "\u5f00\u706b", "\u5f00\u955c", "\u5f00\u706b&\u5f00\u955c");
    public final NumberSetting trackingRangeChunks = new NumberSetting("\u8ffd\u8e2a\u8ddd\u79bb\uff08\u533a\u5757\uff09", 8.0, 1.0, 32.0, 1.0);
    public final ModeSetting<String> targetPriority = this.mode("\u76ee\u6807\u4f18\u5148\u7ea7", "\u8ddd\u79bb\u4f18\u5148", "\u8ddd\u79bb\u4f18\u5148", "\u89c6\u89d2\u4f18\u5148");
    public final ModeSetting<String> aimPart = this.mode("\u7784\u51c6\u90e8\u4f4d", "\u5934\u90e8", "\u5934\u90e8", "\u80f8\u90e8");
    public final BooleanSetting showAimLine = new BooleanSetting("\u663e\u793a\u7784\u51c6\u7ebf", true);
    public final BooleanSetting pierceWalls = new BooleanSetting("\u5b50\u5f39\u7a7f\u5899", false);
    public final BooleanSetting infiniteAmmo = new BooleanSetting("\u65e0\u9650\u5b50\u5f39", false);
    public final BooleanSetting instantReload = new BooleanSetting("\u79d2\u6362\u5f39", false);
    public final BooleanSetting autoFireSingleShot = new BooleanSetting("\u5355\u53d1/\u9749\u5f39\u8fde\u53d1", false);
    public final NumberSetting fireRateMultiplier = new NumberSetting("\u5c04\u901f\u500d\u901f", 1.0, 0.1, 10.0, 0.1);
    public final BooleanSetting instantHit = new BooleanSetting("\u5b50\u5f39\u77ac\u51fb", false);
    private static final double SCREEN_CENTER_START_DISTANCE = 0.25;

    public AutoAim() {
        super("\u81ea\u52a8\u7784\u51c6", Category.COMBAT, 71);
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent playerTickEvent) {
        if (playerTickEvent.phase == TickEvent.Phase.START && this.isEnabled() && this.infiniteAmmo.isEnabled()) {
            TacZWeaponEnhancementHandler.refillHeldGun((LivingEntity)playerTickEvent.player);
        }
    }

    @SubscribeEvent
    public void onBulletJoinLevel(EntityJoinLevelEvent entityJoinLevelEvent) {
        if (!this.isEnabled()) {
            return;
        }
        Entity entity = entityJoinLevelEvent.getEntity();
        if (entity instanceof EntityKineticBullet) {
            HomingBulletHandler.rememberLaunch((EntityKineticBullet)entity);
        }
    }

    @SubscribeEvent
    public void onBulletLeaveLevel(EntityLeaveLevelEvent entityLeaveLevelEvent) {
        Entity entity = entityLeaveLevelEvent.getEntity();
        if (entity instanceof EntityKineticBullet) {
            HomingBulletHandler.forgetLaunch((EntityKineticBullet)entity);
        }
    }

    @SubscribeEvent
    public void onRenderLevel(RenderLevelStageEvent renderLevelStageEvent) {
        if (!this.isEnabled() || !this.showAimLine.isEnabled() || renderLevelStageEvent.getStage() != RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            return;
        }
        HomingBulletHandler.AimLine aimLine = HomingBulletHandler.getPreviewLine();
        if (aimLine == null) {
            return;
        }
        Camera camera = renderLevelStageEvent.getCamera();
        Vec3 vec3 = camera.m_90583_();
        Vec3 vec32 = new Vec3(camera.m_253058_());
        Vec3 vec33 = vec3.m_82549_(vec32.m_82490_(0.25));
        PoseStack poseStack = renderLevelStageEvent.getPoseStack();
        poseStack.m_85836_();
        poseStack.m_85837_(-vec3.f_82479_, -vec3.f_82480_, -vec3.f_82481_);
        PoseStack.Pose pose = poseStack.m_85850_();
        Matrix4f matrix4f = pose.m_252922_();
        RenderType renderType = RenderType.m_269399_((double)2.0);
        MultiBufferSource.BufferSource bufferSource = mc.m_91269_().m_110104_();
        VertexConsumer vertexConsumer = bufferSource.m_6299_(renderType);
        this.addLineVertex(vertexConsumer, matrix4f, vec33.m_82546_(vec3));
        this.addLineVertex(vertexConsumer, matrix4f, aimLine.end.m_82546_(vec3));
        bufferSource.m_109912_(renderType);
        poseStack.m_85849_();
    }

    private void addLineVertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Vec3 vec3) {
        vertexConsumer.m_252986_(matrix4f, (float)vec3.f_82479_, (float)vec3.f_82480_, (float)vec3.f_82481_).m_6122_(80, 230, 255, 220).m_5752_();
    }

    @Override
    public void onDisable() {
        HomingBulletHandler.clearLaunches();
    }
}
