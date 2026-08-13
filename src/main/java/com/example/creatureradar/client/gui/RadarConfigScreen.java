package com.example.creatureradar.client.gui;

import com.example.creatureradar.config.RadarConfig;
import com.xybaka.autoaim.modules.Module;
import com.xybaka.autoaim.modules.ModuleManager;
import com.xybaka.autoaim.modules.client.Target;
import com.xybaka.autoaim.modules.combat.AutoAim;
import com.xybaka.autoaim.modules.combat.NoRecoil;
import com.xybaka.autoaim.modules.movement.Sprint;
import com.xybaka.autoaim.modules.movement.invMove;
import com.xybaka.autoaim.modules.render.FullBright;
import com.xybaka.autoaim.modules.render.HUD;
import com.xybaka.autoaim.modules.render.TargetHud;
import com.xybaka.autoaim.modules.render.ThirdPersonSpin;
import com.xybaka.autoaim.modules.settings.BooleanSetting;
import com.xybaka.autoaim.modules.settings.ModeSetting;
import com.xybaka.autoaim.modules.settings.NumberSetting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class RadarConfigScreen extends Screen {
    private enum Tab { RADAR, COMBAT, MOVEMENT, RENDER }

    private final Screen parent;
    private Tab tab = Tab.COMBAT;

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
        super(Component.m_237115_("creature_radar.screen.title"));
        this.parent = parent;
    }

    protected void m_7856_() {
        this.m_169413_();
        int cx = this.f_96543_ / 2;
        this.addTabButton(cx - 154, 6, 75, Tab.RADAR, "creature_radar.tab.radar");
        this.addTabButton(cx - 75, 6, 75, Tab.COMBAT, "creature_radar.tab.combat");
        this.addTabButton(cx + 4, 6, 75, Tab.MOVEMENT, "creature_radar.tab.movement");
        this.addTabButton(cx + 83, 6, 75, Tab.RENDER, "creature_radar.tab.render");

        switch (this.tab) {
            case RADAR -> this.buildRadarPage();
            case COMBAT -> this.buildCombatPage();
            case MOVEMENT -> this.buildMovementPage();
            case RENDER -> this.buildRenderPage();
        }

        this.m_142416_(Button.m_253074_(CommonComponents.f_130655_, btn -> this.m_7379_())
                .m_252987_(cx - 50, this.f_96544_ - 26, 100, 20).m_253136_());
    }

    private void addTabButton(int x, int y, int w, Tab target, String langKey) {
        this.m_142416_(Button.m_253074_(Component.m_237115_(langKey), btn -> {
            this.tab = target;
            this.m_6575_(this.f_96541_, this.f_96543_, this.f_96544_);
        }).m_252987_(x, y, w, 20).m_253136_());
    }

    private void buildRadarPage() {
        RadarConfig cfg = RadarConfig.get();
        int cx = this.f_96543_ / 2;
        int left = cx - 155;
        int right = cx + 5;
        int y = 34;
        int w = 150;
        int h = 20;
        int step = 22;

        this.m_142416_(CycleButton.m_168916_(cfg.enabled).m_168936_(left, y, w, h,
                Component.m_237115_("creature_radar.option.enabled"), (btn, value) -> cfg.enabled = value));
        this.m_142416_(CycleButton.m_168916_(cfg.showHud).m_168936_(right, y, w, h,
                Component.m_237115_("creature_radar.option.hud"), (btn, value) -> cfg.showHud = value));
        this.m_142416_(CycleButton.m_168916_(cfg.throughBlocks).m_168936_(left, y += step, w, h,
                Component.m_237115_("creature_radar.option.through_blocks"), (btn, value) -> cfg.throughBlocks = value));
        this.m_142416_(Button.m_253074_(
                Component.m_237110_("creature_radar.option.draw_mode", Component.m_237115_(cfg.drawMode.langKey())),
                btn -> {
                    cfg.drawMode = cfg.drawMode.next();
                    btn.m_93666_(Component.m_237110_("creature_radar.option.draw_mode", Component.m_237115_(cfg.drawMode.langKey())));
                }
        ).m_252987_(right, y, w, h).m_253136_());
        this.m_142416_(new ValueSlider(left, y += step, w, h, cfg.maxDistance, 8.0, 128.0, v -> {
            cfg.maxDistance = v;
            return Component.m_237110_("creature_radar.option.max_distance", v);
        }));
        this.m_142416_(new ValueSlider(right, y, w, h, cfg.lineWidth, 0.5, 6.0, v -> {
            cfg.lineWidth = (float) v;
            return Component.m_237110_("creature_radar.option.line_width", v);
        }));
        this.m_142416_(new ValueSlider(left, y += step, w, h, cfg.boxAlpha, 0.0, 1.0, v -> {
            cfg.boxAlpha = (float) v;
            return Component.m_237110_("creature_radar.option.box_alpha", v);
        }));
        this.m_142416_(new ValueSlider(right, y, w, h, cfg.outlineAlpha, 0.0, 1.0, v -> {
            cfg.outlineAlpha = (float) v;
            return Component.m_237110_("creature_radar.option.outline_alpha", v);
        }));
        this.m_142416_(CycleButton.m_168916_(cfg.targetPlayers).m_168936_(left, y += step, w, h,
                Component.m_237115_("creature_radar.option.players"), (btn, value) -> cfg.targetPlayers = value));
        this.m_142416_(CycleButton.m_168916_(cfg.targetMonsters).m_168936_(right, y, w, h,
                Component.m_237115_("creature_radar.option.monsters"), (btn, value) -> cfg.targetMonsters = value));
        this.m_142416_(CycleButton.m_168916_(cfg.targetAnimals).m_168936_(left, y += step, w, h,
                Component.m_237115_("creature_radar.option.animals"), (btn, value) -> cfg.targetAnimals = value));
        this.m_142416_(CycleButton.m_168916_(cfg.targetVillagers).m_168936_(right, y, w, h,
                Component.m_237115_("creature_radar.option.villagers"), (btn, value) -> cfg.targetVillagers = value));
        this.m_142416_(CycleButton.m_168916_(cfg.targetGolems).m_168936_(left, y += step, w, h,
                Component.m_237115_("creature_radar.option.golems"), (btn, value) -> cfg.targetGolems = value));
        this.m_142416_(CycleButton.m_168916_(cfg.targetWater).m_168936_(right, y, w, h,
                Component.m_237115_("creature_radar.option.water"), (btn, value) -> cfg.targetWater = value));

        this.lineColorBox = this.colorBox(left, y += step, w, h, cfg.lineColor);
        this.boxColorBox = this.colorBox(right, y, w, h, cfg.boxColor);
        this.m_142416_(this.lineColorBox);
        this.m_142416_(this.boxColorBox);
        this.outlineColorBox = this.colorBox(left, y += step, w, h, cfg.outlineColor);
        this.m_142416_(this.outlineColorBox);
        this.m_142416_(CycleButton.m_168916_(cfg.lineGradient).m_168936_(right, y, w, h,
                Component.m_237115_("creature_radar.option.line_gradient"), (btn, value) -> cfg.lineGradient = value));
        this.m_142416_(CycleButton.m_168916_(cfg.boxGradient).m_168936_(left, y += step, w, h,
                Component.m_237115_("creature_radar.option.box_gradient"), (btn, value) -> cfg.boxGradient = value));
        this.m_142416_(CycleButton.m_168916_(cfg.outlineGradient).m_168936_(right, y, w, h,
                Component.m_237115_("creature_radar.option.outline_gradient"), (btn, value) -> cfg.outlineGradient = value));
        this.lineGradStartBox = this.colorBox(left, y += step, w, h, cfg.lineGradientStart);
        this.lineGradEndBox = this.colorBox(right, y, w, h, cfg.lineGradientEnd);
        this.m_142416_(this.lineGradStartBox);
        this.m_142416_(this.lineGradEndBox);
        this.boxGradStartBox = this.colorBox(left, y += step, w, h, cfg.boxGradientStart);
        this.boxGradEndBox = this.colorBox(right, y, w, h, cfg.boxGradientEnd);
        this.m_142416_(this.boxGradStartBox);
        this.m_142416_(this.boxGradEndBox);
        this.outlineGradStartBox = this.colorBox(left, y += step, w, h, cfg.outlineGradientStart);
        this.outlineGradEndBox = this.colorBox(right, y, w, h, cfg.outlineGradientEnd);
        this.m_142416_(this.outlineGradStartBox);
        this.m_142416_(this.outlineGradEndBox);
    }

    private void buildCombatPage() {
        AutoAim aim = ModuleManager.instance.get(AutoAim.class);
        NoRecoil noRecoil = ModuleManager.instance.get(NoRecoil.class);
        Target target = ModuleManager.instance.get(Target.class);
        int cx = this.f_96543_ / 2;
        int left = cx - 155;
        int right = cx + 5;
        int y = 34;
        int w = 150;
        int h = 20;
        int step = 22;

        if (aim != null) {
            this.m_142416_(this.moduleToggle(left, y, w, h, aim, "creature_radar.mod.autoaim"));
        }
        if (noRecoil != null) {
            this.m_142416_(this.moduleToggle(right, y, w, h, noRecoil, "creature_radar.mod.norecoil"));
        }
        if (aim != null) {
            this.m_142416_(this.boolButton(left, y += step, w, h, aim.showAimLine, "creature_radar.autoaim.show_aim_line"));
            this.m_142416_(this.modeButton(right, y, w, h, aim.triggerMode, "creature_radar.autoaim.trigger_mode"));
            this.m_142416_(this.modeButton(left, y += step, w, h, aim.targetPriority, "creature_radar.autoaim.target_priority"));
            this.m_142416_(this.modeButton(right, y, w, h, aim.aimPart, "creature_radar.autoaim.aim_part"));
            this.m_142416_(this.numberSlider(left, y += step, w, h, aim.trackingRangeChunks, "creature_radar.autoaim.tracking_range"));
            this.m_142416_(this.numberSlider(right, y, w, h, aim.fireRateMultiplier, "creature_radar.autoaim.fire_rate"));
            this.m_142416_(this.boolButton(left, y += step, w, h, aim.pierceWalls, "creature_radar.autoaim.pierce_walls"));
            this.m_142416_(this.boolButton(right, y, w, h, aim.instantHit, "creature_radar.autoaim.instant_hit"));
            this.m_142416_(this.boolButton(left, y += step, w, h, aim.infiniteAmmo, "creature_radar.autoaim.infinite_ammo"));
            this.m_142416_(this.boolButton(right, y, w, h, aim.instantReload, "creature_radar.autoaim.instant_reload"));
            this.m_142416_(this.boolButton(left, y += step, w, h, aim.autoFireSingleShot, "creature_radar.autoaim.auto_fire_single"));
        }
        if (target != null) {
            this.m_142416_(this.modeButton(right, y, w, h, target.mode, "creature_radar.target.mode"));
            this.m_142416_(this.modeButton(left, y += step, w, h, target.aimPart, "creature_radar.target.aim_part"));
            this.m_142416_(this.boolButton(right, y, w, h, target.players, "creature_radar.target.players"));
            this.m_142416_(this.boolButton(left, y += step, w, h, target.monsters, "creature_radar.target.monsters"));
            this.m_142416_(this.boolButton(right, y, w, h, target.animals, "creature_radar.target.animals"));
            this.m_142416_(this.boolButton(left, y += step, w, h, target.villagers, "creature_radar.target.villagers"));
            this.m_142416_(this.boolButton(right, y, w, h, target.golems, "creature_radar.target.golems"));
            this.m_142416_(this.boolButton(left, y += step, w, h, target.waterAnimals, "creature_radar.target.water_animals"));
            this.m_142416_(this.boolButton(right, y, w, h, target.waterCreatures, "creature_radar.target.water_creatures"));
            this.m_142416_(this.boolButton(left, y += step, w, h, target.ambient, "creature_radar.target.ambient"));
        }
    }

    private void buildMovementPage() {
        Sprint sprint = ModuleManager.instance.get(Sprint.class);
        invMove inv = ModuleManager.instance.get(invMove.class);
        int cx = this.f_96543_ / 2;
        int left = cx - 155;
        int right = cx + 5;
        int y = 34;
        int w = 150;
        int h = 20;
        int step = 22;
        if (sprint != null) {
            this.m_142416_(this.moduleToggle(left, y, w, h, sprint, "creature_radar.mod.sprint"));
        }
        if (inv != null) {
            this.m_142416_(this.moduleToggle(right, y, w, h, inv, "creature_radar.mod.invmove"));
            this.m_142416_(this.boolButton(left, y += step, w, h, inv.sneak, "creature_radar.invmove.sneak"));
        }
    }

    private void buildRenderPage() {
        HUD hud = ModuleManager.instance.get(HUD.class);
        TargetHud targetHud = ModuleManager.instance.get(TargetHud.class);
        FullBright fullBright = ModuleManager.instance.get(FullBright.class);
        ThirdPersonSpin spin = ModuleManager.instance.get(ThirdPersonSpin.class);
        int cx = this.f_96543_ / 2;
        int left = cx - 155;
        int right = cx + 5;
        int y = 34;
        int w = 150;
        int h = 20;
        int step = 22;

        if (hud != null) {
            this.m_142416_(this.moduleToggle(left, y, w, h, hud, "creature_radar.mod.hud"));
            this.m_142416_(this.boolButton(right, y, w, h, hud.showNotifications, "creature_radar.hud.notifications"));
            this.m_142416_(this.boolButton(left, y += step, w, h, hud.showInfo, "creature_radar.hud.info"));
        }
        if (targetHud != null) {
            this.m_142416_(this.moduleToggle(right, y, w, h, targetHud, "creature_radar.mod.targethud"));
        }
        if (fullBright != null) {
            this.m_142416_(this.moduleToggle(left, y += step, w, h, fullBright, "creature_radar.mod.fullbright"));
        }
        if (spin != null) {
            this.m_142416_(this.moduleToggle(right, y, w, h, spin, "creature_radar.mod.spin"));
            this.m_142416_(this.numberSlider(left, y += step, w, h, spin.spinSpeed, "creature_radar.spin.speed"));
            this.m_142416_(this.boolButton(right, y, w, h, spin.onlyThirdPerson, "creature_radar.spin.only_third"));
        }
    }

    private Button moduleToggle(int x, int y, int w, int h, Module module, String langKey) {
        return Button.m_253074_(
                Component.m_237110_(langKey, Component.m_237113_(module.isEnabled() ? "ON" : "OFF")),
                btn -> {
                    module.toggle();
                    btn.m_93666_(Component.m_237110_(langKey, Component.m_237113_(module.isEnabled() ? "ON" : "OFF")));
                }
        ).m_252987_(x, y, w, h).m_253136_();
    }

    private Button boolButton(int x, int y, int w, int h, BooleanSetting setting, String langKey) {
        return Button.m_253074_(
                Component.m_237110_(langKey, Component.m_237113_(setting.isEnabled() ? "ON" : "OFF")),
                btn -> {
                    setting.toggle();
                    btn.m_93666_(Component.m_237110_(langKey, Component.m_237113_(setting.isEnabled() ? "ON" : "OFF")));
                }
        ).m_252987_(x, y, w, h).m_253136_();
    }

    private Button modeButton(int x, int y, int w, int h, ModeSetting setting, String langKey) {
        return Button.m_253074_(
                Component.m_237110_(langKey, Component.m_237113_(String.valueOf(setting.getValue()))),
                btn -> {
                    setting.cycle();
                    btn.m_93666_(Component.m_237110_(langKey, Component.m_237113_(String.valueOf(setting.getValue()))));
                }
        ).m_252987_(x, y, w, h).m_253136_();
    }

    private ValueSlider numberSlider(int x, int y, int w, int h, NumberSetting setting, String langKey) {
        return new ValueSlider(x, y, w, h, setting.getValue(), setting.getMin(), setting.getMax(), v -> {
            setting.setValue(v);
            return Component.m_237110_(langKey, setting.getValue());
        });
    }

    private EditBox colorBox(int x, int y, int w, int h, int color) {
        EditBox box = new EditBox(this.f_96547_, x, y, w, h, Component.m_237113_("color"));
        box.m_94199_(10);
        box.m_94144_(String.format("#%08X", color));
        return box;
    }

    private void applyColors() {
        if (this.lineColorBox == null) {
            return;
        }
        RadarConfig cfg = RadarConfig.get();
        cfg.lineColor = parseColor(this.lineColorBox.m_94155_(), cfg.lineColor);
        cfg.boxColor = parseColor(this.boxColorBox.m_94155_(), cfg.boxColor);
        cfg.outlineColor = parseColor(this.outlineColorBox.m_94155_(), cfg.outlineColor);
        cfg.lineGradientStart = parseColor(this.lineGradStartBox.m_94155_(), cfg.lineGradientStart);
        cfg.lineGradientEnd = parseColor(this.lineGradEndBox.m_94155_(), cfg.lineGradientEnd);
        cfg.boxGradientStart = parseColor(this.boxGradStartBox.m_94155_(), cfg.boxGradientStart);
        cfg.boxGradientEnd = parseColor(this.boxGradEndBox.m_94155_(), cfg.boxGradientEnd);
        cfg.outlineGradientStart = parseColor(this.outlineGradStartBox.m_94155_(), cfg.outlineGradientStart);
        cfg.outlineGradientEnd = parseColor(this.outlineGradEndBox.m_94155_(), cfg.outlineGradientEnd);
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
                return (int) Long.parseLong(value, 16) | 0xFF000000;
            }
            if (value.length() == 8) {
                return (int) Long.parseLong(value, 16);
            }
        } catch (Exception ignored) {
        }
        return fallback;
    }

    public void m_7379_() {
        this.applyColors();
        RadarConfig.save();
        ModuleManager.instance.saveConfig();
        if (this.f_96541_ != null) {
            this.f_96541_.m_91152_(this.parent);
        }
    }

    public void m_88315_(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.m_280273_(graphics);
        String tabName = switch (this.tab) {
            case RADAR -> "creature_radar.tab.radar";
            case COMBAT -> "creature_radar.tab.combat";
            case MOVEMENT -> "creature_radar.tab.movement";
            case RENDER -> "creature_radar.tab.render";
        };
        graphics.m_280653_(this.f_96547_, Component.m_237115_(tabName), this.f_96543_ / 2, 28, 0xFFFFFF);
        super.m_88315_(graphics, mouseX, mouseY, partialTick);
    }

    private static class ValueSlider extends AbstractSliderButton {
        private final double min;
        private final double max;
        private final ValueHandler handler;

        private ValueSlider(int x, int y, int width, int height, double current, double min, double max, ValueHandler handler) {
            super(x, y, width, height, Component.m_237119_(), 0.0);
            this.min = min;
            this.max = max;
            this.handler = handler;
            this.f_93577_ = (max - min) == 0.0 ? 0.0 : (current - min) / (max - min);
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
        private interface ValueHandler {
            Component message(double value);
        }
    }
}