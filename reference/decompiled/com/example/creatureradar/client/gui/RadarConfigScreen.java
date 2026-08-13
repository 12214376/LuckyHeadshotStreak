/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.AbstractSliderButton
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.CycleButton
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.CommonComponents
 *  net.minecraft.network.chat.Component
 */
package com.example.creatureradar.client.gui;

import com.example.creatureradar.config.RadarConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class RadarConfigScreen
extends Screen {
    private final Screen parent;
    private EditBox lineColorBox;
    private EditBox boxColorBox;
    private EditBox outlineColorBox;
    private EditBox lineGradStartBox;
    private EditBox lineGradEndBox;
    private EditBox boxGradStartBox;
    private EditBox boxGradEndBox;
    private EditBox outlineGradStartBox;
    private EditBox outlineGradEndBox;

    public RadarConfigScreen(Screen parent) {
        super((Component)Component.m_237115_((String)"creature_radar.screen.title"));
        this.parent = parent;
    }

    protected void m_7856_() {
        RadarConfig cfg = RadarConfig.get();
        int cx = this.f_96543_ / 2;
        int left = cx - 155;
        int right = cx + 5;
        int y = 28;
        int w = 150;
        int h = 20;
        int step = 24;
        this.m_142416_((GuiEventListener)CycleButton.m_168916_((boolean)cfg.enabled).m_168936_(left, y, w, h, (Component)Component.m_237115_((String)"creature_radar.option.enabled"), (btn, value) -> {
            cfg.enabled = value;
        }));
        this.m_142416_((GuiEventListener)CycleButton.m_168916_((boolean)cfg.showHud).m_168936_(right, y, w, h, (Component)Component.m_237115_((String)"creature_radar.option.hud"), (btn, value) -> {
            cfg.showHud = value;
        }));
        this.m_142416_((GuiEventListener)CycleButton.m_168916_((boolean)cfg.throughBlocks).m_168936_(left, y += step, w, h, (Component)Component.m_237115_((String)"creature_radar.option.through_blocks"), (btn, value) -> {
            cfg.throughBlocks = value;
        }));
        this.m_142416_((GuiEventListener)Button.m_253074_((Component)Component.m_237110_((String)"creature_radar.option.draw_mode", (Object[])new Object[]{Component.m_237115_((String)cfg.drawMode.langKey())}), btn -> {
            cfg.drawMode = cfg.drawMode.next();
            btn.m_93666_((Component)Component.m_237110_((String)"creature_radar.option.draw_mode", (Object[])new Object[]{Component.m_237115_((String)cfg.drawMode.langKey())}));
        }).m_252987_(right, y, w, h).m_253136_());
        this.m_142416_((GuiEventListener)new ValueSlider(left, y += step, w, h, cfg.maxDistance, 8.0, 128.0, v -> {
            cfg.maxDistance = v;
            return Component.m_237110_((String)"creature_radar.option.max_distance", (Object[])new Object[]{v});
        }));
        this.m_142416_((GuiEventListener)new ValueSlider(right, y, w, h, cfg.lineWidth, 0.5, 6.0, v -> {
            cfg.lineWidth = (float)v;
            return Component.m_237110_((String)"creature_radar.option.line_width", (Object[])new Object[]{v});
        }));
        this.m_142416_((GuiEventListener)new ValueSlider(left, y += step, w, h, cfg.boxAlpha, 0.0, 1.0, v -> {
            cfg.boxAlpha = (float)v;
            return Component.m_237110_((String)"creature_radar.option.box_alpha", (Object[])new Object[]{v});
        }));
        this.m_142416_((GuiEventListener)new ValueSlider(right, y, w, h, cfg.outlineAlpha, 0.0, 1.0, v -> {
            cfg.outlineAlpha = (float)v;
            return Component.m_237110_((String)"creature_radar.option.outline_alpha", (Object[])new Object[]{v});
        }));
        this.m_142416_((GuiEventListener)CycleButton.m_168916_((boolean)cfg.targetPlayers).m_168936_(left, y += step, w, h, (Component)Component.m_237115_((String)"creature_radar.option.players"), (btn, value) -> {
            cfg.targetPlayers = value;
        }));
        this.m_142416_((GuiEventListener)CycleButton.m_168916_((boolean)cfg.targetMonsters).m_168936_(right, y, w, h, (Component)Component.m_237115_((String)"creature_radar.option.monsters"), (btn, value) -> {
            cfg.targetMonsters = value;
        }));
        this.m_142416_((GuiEventListener)CycleButton.m_168916_((boolean)cfg.targetAnimals).m_168936_(left, y += step, w, h, (Component)Component.m_237115_((String)"creature_radar.option.animals"), (btn, value) -> {
            cfg.targetAnimals = value;
        }));
        this.m_142416_((GuiEventListener)CycleButton.m_168916_((boolean)cfg.targetVillagers).m_168936_(right, y, w, h, (Component)Component.m_237115_((String)"creature_radar.option.villagers"), (btn, value) -> {
            cfg.targetVillagers = value;
        }));
        this.m_142416_((GuiEventListener)CycleButton.m_168916_((boolean)cfg.targetGolems).m_168936_(left, y += step, w, h, (Component)Component.m_237115_((String)"creature_radar.option.golems"), (btn, value) -> {
            cfg.targetGolems = value;
        }));
        this.m_142416_((GuiEventListener)CycleButton.m_168916_((boolean)cfg.targetWater).m_168936_(right, y, w, h, (Component)Component.m_237115_((String)"creature_radar.option.water"), (btn, value) -> {
            cfg.targetWater = value;
        }));
        this.lineColorBox = this.colorBox(left, y += step, w, h, cfg.lineColor);
        this.boxColorBox = this.colorBox(right, y, w, h, cfg.boxColor);
        this.m_142416_((GuiEventListener)this.lineColorBox);
        this.m_142416_((GuiEventListener)this.boxColorBox);
        this.outlineColorBox = this.colorBox(left, y += step, w, h, cfg.outlineColor);
        this.m_142416_((GuiEventListener)this.outlineColorBox);
        this.m_142416_((GuiEventListener)CycleButton.m_168916_((boolean)cfg.lineGradient).m_168936_(right, y, w, h, (Component)Component.m_237115_((String)"creature_radar.option.line_gradient"), (btn, value) -> {
            cfg.lineGradient = value;
        }));
        this.m_142416_((GuiEventListener)CycleButton.m_168916_((boolean)cfg.boxGradient).m_168936_(left, y += step, w, h, (Component)Component.m_237115_((String)"creature_radar.option.box_gradient"), (btn, value) -> {
            cfg.boxGradient = value;
        }));
        this.m_142416_((GuiEventListener)CycleButton.m_168916_((boolean)cfg.outlineGradient).m_168936_(right, y, w, h, (Component)Component.m_237115_((String)"creature_radar.option.outline_gradient"), (btn, value) -> {
            cfg.outlineGradient = value;
        }));
        this.lineGradStartBox = this.colorBox(left, y += step, w, h, cfg.lineGradientStart);
        this.lineGradEndBox = this.colorBox(right, y, w, h, cfg.lineGradientEnd);
        this.m_142416_((GuiEventListener)this.lineGradStartBox);
        this.m_142416_((GuiEventListener)this.lineGradEndBox);
        this.boxGradStartBox = this.colorBox(left, y += step, w, h, cfg.boxGradientStart);
        this.boxGradEndBox = this.colorBox(right, y, w, h, cfg.boxGradientEnd);
        this.m_142416_((GuiEventListener)this.boxGradStartBox);
        this.m_142416_((GuiEventListener)this.boxGradEndBox);
        this.outlineGradStartBox = this.colorBox(left, y += step, w, h, cfg.outlineGradientStart);
        this.outlineGradEndBox = this.colorBox(right, y, w, h, cfg.outlineGradientEnd);
        this.m_142416_((GuiEventListener)this.outlineGradStartBox);
        this.m_142416_((GuiEventListener)this.outlineGradEndBox);
        this.m_142416_((GuiEventListener)Button.m_253074_((Component)CommonComponents.f_130655_, btn -> this.m_7379_()).m_252987_(cx - 50, Math.min(y += step + 4, this.f_96544_ - 28), 100, 20).m_253136_());
    }

    private EditBox colorBox(int x, int y, int w, int h, int color) {
        EditBox box = new EditBox(this.f_96547_, x, y, w, h, (Component)Component.m_237113_((String)"color"));
        box.m_94199_(10);
        box.m_94144_(String.format("#%08X", color));
        return box;
    }

    private void applyColors() {
        RadarConfig cfg = RadarConfig.get();
        cfg.lineColor = RadarConfigScreen.parseColor(this.lineColorBox.m_94155_(), cfg.lineColor);
        cfg.boxColor = RadarConfigScreen.parseColor(this.boxColorBox.m_94155_(), cfg.boxColor);
        cfg.outlineColor = RadarConfigScreen.parseColor(this.outlineColorBox.m_94155_(), cfg.outlineColor);
        cfg.lineGradientStart = RadarConfigScreen.parseColor(this.lineGradStartBox.m_94155_(), cfg.lineGradientStart);
        cfg.lineGradientEnd = RadarConfigScreen.parseColor(this.lineGradEndBox.m_94155_(), cfg.lineGradientEnd);
        cfg.boxGradientStart = RadarConfigScreen.parseColor(this.boxGradStartBox.m_94155_(), cfg.boxGradientStart);
        cfg.boxGradientEnd = RadarConfigScreen.parseColor(this.boxGradEndBox.m_94155_(), cfg.boxGradientEnd);
        cfg.outlineGradientStart = RadarConfigScreen.parseColor(this.outlineGradStartBox.m_94155_(), cfg.outlineGradientStart);
        cfg.outlineGradientEnd = RadarConfigScreen.parseColor(this.outlineGradEndBox.m_94155_(), cfg.outlineGradientEnd);
    }

    private static int parseColor(String text, int fallback) {
        if (text == null) {
            return fallback;
        }
        String value = text.trim();
        if (value.startsWith("#")) {
            value = value.substring(1);
        }
        try {
            if (value.length() == 6) {
                return (int)Long.parseLong(value, 16) | 0xFF000000;
            }
            if (value.length() == 8) {
                return (int)Long.parseLong(value, 16);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return fallback;
    }

    public void m_7379_() {
        this.applyColors();
        RadarConfig.save();
        if (this.f_96541_ != null) {
            this.f_96541_.m_91152_(this.parent);
        }
    }

    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.m_280273_(graphics);
        graphics.m_280653_(this.f_96547_, this.f_96539_, this.f_96543_ / 2, 10, 0xFFFFFF);
        graphics.m_280614_(this.f_96547_, (Component)Component.m_237115_((String)"creature_radar.option.line_color"), this.f_96543_ / 2 - 155, 162, 0xA0A0A0, false);
        graphics.m_280614_(this.f_96547_, (Component)Component.m_237115_((String)"creature_radar.option.box_color"), this.f_96543_ / 2 + 5, 162, 0xA0A0A0, false);
        graphics.m_280614_(this.f_96547_, (Component)Component.m_237115_((String)"creature_radar.option.outline_color"), this.f_96543_ / 2 - 155, 186, 0xA0A0A0, false);
        super.m_88315_(graphics, mouseX, mouseY, partialTick);
    }

    private static class ValueSlider
    extends AbstractSliderButton {
        private final double min;
        private final double max;
        private final ValueHandler handler;

        private ValueSlider(int x, int y, int width, int height, double current, double min, double max, ValueHandler handler) {
            super(x, y, width, height, (Component)Component.m_237119_(), 0.0);
            this.min = min;
            this.max = max;
            this.handler = handler;
            this.f_93577_ = (current - min) / (max - min);
            this.m_5695_();
        }

        protected void m_5695_() {
            double real = this.min + (this.max - this.min) * this.f_93577_;
            this.m_93666_(this.handler.message(real));
        }

        protected void m_5697_() {
            double real = this.min + (this.max - this.min) * this.f_93577_;
            this.m_93666_(this.handler.message(real));
        }

        @FunctionalInterface
        private static interface ValueHandler {
            public Component message(double var1);
        }
    }
}
