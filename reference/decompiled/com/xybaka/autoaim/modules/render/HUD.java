/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraftforge.client.event.RenderGuiEvent$Post
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package com.xybaka.autoaim.modules.render;

import com.xybaka.autoaim.modules.Category;
import com.xybaka.autoaim.modules.Module;
import com.xybaka.autoaim.modules.ModuleManager;
import com.xybaka.autoaim.modules.settings.BooleanSetting;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HUD
extends Module {
    public final BooleanSetting showNotifications = new BooleanSetting("\u901a\u77e5", true);
    public final BooleanSetting showInfo = new BooleanSetting("\u4fe1\u606f", true);
    private static final List<Notification> NOTIFICATIONS = new ArrayList<Notification>();

    public HUD() {
        super("\u4fe1\u606f\u9762\u677f", Category.RENDER, -1);
        this.enable();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void push(String moduleName, boolean nowEnabled) {
        List<Notification> list = NOTIFICATIONS;
        synchronized (list) {
            NOTIFICATIONS.add(new Notification(moduleName, nowEnabled));
        }
    }

    @SubscribeEvent
    public void onRenderGui(RenderGuiEvent.Post event) {
        if (!this.isEnabled() || HUD.mc.f_91066_.f_92062_ || HUD.mc.f_91074_ == null) {
            return;
        }
        GuiGraphics graphics = event.getGuiGraphics();
        int screenW = mc.m_91268_().m_85445_();
        int screenH = mc.m_91268_().m_85446_();
        this.drawModuleList(graphics, screenW);
        if (this.showInfo.isEnabled()) {
            this.drawInfo(graphics, screenH);
        }
        if (this.showNotifications.isEnabled()) {
            this.drawNotifications(graphics, screenW, screenH);
        }
    }

    private void drawModuleList(GuiGraphics graphics, int screenW) {
        List<Module> active = ModuleManager.instance.getModules().stream().filter(Module::isEnabled).sorted(Comparator.comparingInt(m -> -HUD.mc.f_91062_.m_92895_(m.getName()))).toList();
        int yOffset = 5;
        for (Module m2 : active) {
            String text = m2.getName();
            int textWidth = HUD.mc.f_91062_.m_92895_(text);
            int x = screenW - textWidth - 5;
            int color = this.getRainbowColor(yOffset * 20);
            graphics.m_280056_(HUD.mc.f_91062_, text, x, yOffset, color, true);
            Objects.requireNonNull(HUD.mc.f_91062_);
            yOffset += 9 + 2;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void drawNotifications(GuiGraphics graphics, int screenW, int screenH) {
        int padding = 5;
        int barWidth = 140;
        Objects.requireNonNull(HUD.mc.f_91062_);
        int barHeight = 9 + 10;
        int gap = 4;
        List<Notification> list = NOTIFICATIONS;
        synchronized (list) {
            NOTIFICATIONS.removeIf(Notification::isExpired);
            if (NOTIFICATIONS.isEmpty()) {
                return;
            }
            int baseY = screenH - barHeight - 10;
            for (int i = 0; i < NOTIFICATIONS.size(); ++i) {
                Notification notification = NOTIFICATIONS.get(i);
                int alpha = (int)(notification.getAlpha() * 255.0f);
                int x = screenW - 140 - 10;
                int y = baseY - i * (barHeight + 4);
                int bgColor = HUD.withAlpha(notification.enabled ? 2776106 : 6040106, alpha);
                int accentColor = HUD.withAlpha(notification.enabled ? 0x55FF55 : 0xFF5555, alpha);
                graphics.m_280509_(x, y, x + 140, y + barHeight, bgColor);
                graphics.m_280509_(x, y, x + 3, y + barHeight, accentColor);
                String label = (notification.enabled ? "\u5f00 " : "\u5173 ") + notification.moduleName;
                graphics.m_280056_(HUD.mc.f_91062_, label, x + 8, y + 5, HUD.withAlpha(0xFFFFFF, alpha), true);
            }
        }
    }

    private void drawInfo(GuiGraphics graphics, int screenH) {
        int padding = 5;
        int textColor = 0xFFFFFF;
        String xyz = String.format("\u5750\u6807\uff1a%.1f\uff0c%.1f\uff0c%.1f", HUD.mc.f_91074_.m_20185_(), HUD.mc.f_91074_.m_20186_(), HUD.mc.f_91074_.m_20189_());
        Objects.requireNonNull(HUD.mc.f_91062_);
        int yXyz = screenH - 9 - 5;
        graphics.m_280056_(HUD.mc.f_91062_, xyz, 5, yXyz, 0xFFFFFF, true);
        double deltaX = HUD.mc.f_91074_.m_20185_() - HUD.mc.f_91074_.f_19854_;
        double deltaZ = HUD.mc.f_91074_.m_20189_() - HUD.mc.f_91074_.f_19856_;
        double speed = Math.sqrt(deltaX * deltaX + deltaZ * deltaZ) * 20.0;
        String speedText = String.format("\u901f\u5ea6\uff1a%.2f \u683c/\u79d2", speed);
        Objects.requireNonNull(HUD.mc.f_91062_);
        int ySpeed = yXyz - 9 - 5;
        graphics.m_280056_(HUD.mc.f_91062_, speedText, 5, ySpeed, 0xFFFFFF, true);
        String fpsText = String.format("\u5e27\u7387\uff1a%d", mc.m_260875_());
        Objects.requireNonNull(HUD.mc.f_91062_);
        int yFps = ySpeed - 9 - 5;
        graphics.m_280056_(HUD.mc.f_91062_, fpsText, 5, yFps, 0xFFFFFF, true);
    }

    private int getRainbowColor(int offset) {
        float speed = 3000.0f;
        float hue = (float)((System.currentTimeMillis() + (long)offset) % (long)((int)speed)) / speed;
        return Color.HSBtoRGB(hue, 0.6f, 1.0f);
    }

    private static int withAlpha(int rgb, int alpha) {
        return alpha << 24 | rgb & 0xFFFFFF;
    }

    private static class Notification {
        final String moduleName;
        final boolean enabled;
        final long createTime = System.currentTimeMillis();
        final long duration = 2500L;
        final float fadeInTime = 300.0f;
        final float fadeOutTime = 500.0f;

        Notification(String moduleName, boolean enabled) {
            this.moduleName = moduleName;
            this.enabled = enabled;
        }

        float getAlpha() {
            long elapsed = System.currentTimeMillis() - this.createTime;
            if ((float)elapsed < 300.0f) {
                return (float)elapsed / 300.0f;
            }
            long fadeOutStart = 2000L;
            if (elapsed > fadeOutStart) {
                return Math.max(0.0f, 1.0f - (float)(elapsed - fadeOutStart) / 500.0f);
            }
            return 1.0f;
        }

        boolean isExpired() {
            return System.currentTimeMillis() - this.createTime > 2500L;
        }
    }
}
