package com.xybaka.autoaim.modules.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.xybaka.autoaim.modules.Category;
import com.xybaka.autoaim.modules.Module;
import com.xybaka.autoaim.modules.settings.BooleanSetting;
import com.xybaka.autoaim.modules.settings.NumberSetting;
import net.minecraft.client.CameraType;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

/**
 * Visual-only third-person spin.
 * Rotates the local player model via PoseStack in RenderPlayerEvent.Pre.
 * Does not change camera look, movement, or outbound rotation packets.
 */
public class ThirdPersonSpin extends Module {
    public final NumberSetting spinSpeed = new NumberSetting("转速", 180.0, 10.0, 720.0, 5.0);
    public final BooleanSetting onlyThirdPerson = new BooleanSetting("仅第三人称", true);

    private float spinYaw;

    public ThirdPersonSpin() {
        super("第三人称自转", Category.RENDER, -1);
    }

    @Override
    public void onEnable() {
        this.spinYaw = 0.0f;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END || !this.isEnabled()) {
            return;
        }
        if (!this.shouldSpinNow()) {
            return;
        }
        this.spinYaw += (float) (this.spinSpeed.getValue() / 20.0);
        if (this.spinYaw >= 360.0f) {
            this.spinYaw -= 360.0f;
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderPlayerPre(RenderPlayerEvent.Pre event) {
        if (!this.isEnabled()) {
            return;
        }
        Player player = event.getEntity();
        if (!(player instanceof LocalPlayer) || player != mc.f_91074_) {
            return;
        }
        if (!this.shouldSpinNow()) {
            return;
        }

        float partial = event.getPartialTick();
        float yaw = this.spinYaw + (float) (this.spinSpeed.getValue() * partial / 20.0);
        PoseStack pose = event.getPoseStack();
        // Axis.YP = f_252436_
        pose.m_252781_(Axis.f_252436_.m_252977_(yaw));
    }

    private boolean shouldSpinNow() {
        if (mc == null || mc.f_91066_ == null) {
            return true;
        }
        if (!this.onlyThirdPerson.isEnabled()) {
            return true;
        }
        return mc.f_91066_.m_92176_() != CameraType.FIRST_PERSON;
    }
}
