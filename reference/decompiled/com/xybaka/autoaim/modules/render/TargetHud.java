/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraftforge.client.event.RenderGuiEvent$Post
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package com.xybaka.autoaim.modules.render;

import com.xybaka.autoaim.gui.targethud.TargetHudManager;
import com.xybaka.autoaim.modules.Category;
import com.xybaka.autoaim.modules.Module;
import com.xybaka.autoaim.modules.ModuleManager;
import com.xybaka.autoaim.modules.combat.AutoAim;
import com.xybaka.autoaim.modules.settings.ModeSetting;
import com.xybaka.autoaim.util.TargetUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TargetHud
extends Module {
    public static final int HUD_WIDTH = 150;
    public static final int HUD_HEIGHT = 44;
    public final ModeSetting<StyleMode> styleMode = new ModeSetting<StyleMode>("Style", StyleMode.AUTOAIM, new StyleMode[0]);
    private int hudX = 12;
    private int hudY = 12;

    public TargetHud() {
        super("TargetHud", Category.RENDER, -1);
        this.enable();
    }

    @SubscribeEvent
    public void onRenderGui(RenderGuiEvent.Post post) {
        if (!this.isEnabled() || TargetHud.mc.f_91074_ == null || TargetHud.mc.f_91066_.f_92062_) {
            return;
        }
        GuiGraphics guiGraphics = post.getGuiGraphics();
        LivingEntity livingEntity = this.resolveTarget();
        boolean bl = TargetHudManager.isChatEditing();
        if (livingEntity != null || bl) {
            this.renderHud(guiGraphics, livingEntity, this.getHudX(), this.getHudY());
        }
    }

    public LivingEntity resolveTarget() {
        AutoAim autoAim = ModuleManager.instance.get(AutoAim.class);
        return TargetUtil.getBestTarget(autoAim.trackingRangeChunks.getValue() * 16.0);
    }

    public void renderHud(GuiGraphics guiGraphics, LivingEntity livingEntity, int n, int n2) {
        TargetHudManager.renderStyle(this.styleMode.getValue(), guiGraphics, this, livingEntity, n, n2);
    }

    public int getHudX() {
        int n = mc.m_91268_().m_85445_();
        return Mth.m_14045_((int)this.hudX, (int)0, (int)Math.max(0, n - 150));
    }

    public int getHudY() {
        int n = mc.m_91268_().m_85446_();
        return Mth.m_14045_((int)this.hudY, (int)0, (int)Math.max(0, n - 44));
    }

    public int getStoredX() {
        return this.hudX;
    }

    public int getStoredY() {
        return this.hudY;
    }

    public void setStoredPosition(int n, int n2) {
        this.hudX = n;
        this.hudY = n2;
    }

    public void setPosition(int n, int n2, int n3, int n4) {
        this.hudX = Mth.m_14045_((int)n, (int)0, (int)Math.max(0, n3 - 150));
        this.hudY = Mth.m_14045_((int)n2, (int)0, (int)Math.max(0, n4 - 44));
    }

    public static enum StyleMode {
        AUTOAIM;

    }
}
