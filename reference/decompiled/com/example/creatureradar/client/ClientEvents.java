/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraftforge.api.distmarker.Dist
 *  net.minecraftforge.client.event.InputEvent$Key
 *  net.minecraftforge.client.event.RegisterKeyMappingsEvent
 *  net.minecraftforge.client.event.RenderGuiOverlayEvent$Post
 *  net.minecraftforge.client.event.RenderLevelStageEvent
 *  net.minecraftforge.client.event.RenderLevelStageEvent$Stage
 *  net.minecraftforge.client.gui.overlay.VanillaGuiOverlay
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber
 *  net.minecraftforge.fml.common.Mod$EventBusSubscriber$Bus
 */
package com.example.creatureradar.client;

import com.example.creatureradar.client.gui.RadarConfigScreen;
import com.example.creatureradar.client.render.RadarHudOverlay;
import com.example.creatureradar.client.render.RadarWorldRenderer;
import com.example.creatureradar.config.RadarConfig;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public final class ClientEvents {
    public static final KeyMapping TOGGLE_KEY = new KeyMapping("key.creature_radar.toggle", InputConstants.Type.KEYSYM, 82, "key.categories.creature_radar");
    public static final KeyMapping CONFIG_KEY = new KeyMapping("key.creature_radar.config", InputConstants.Type.KEYSYM, 80, "key.categories.creature_radar");

    private ClientEvents() {
    }

    @Mod.EventBusSubscriber(modid="creature_radar", value={Dist.CLIENT}, bus=Mod.EventBusSubscriber.Bus.FORGE)
    public static class ForgeBus {
        @SubscribeEvent
        public static void onKey(InputEvent.Key event) {
            Minecraft mc = Minecraft.m_91087_();
            if (mc.f_91074_ == null) {
                return;
            }
            while (TOGGLE_KEY.m_90859_()) {
                RadarConfig.toggleEnabled();
                boolean on = RadarConfig.get().enabled;
                mc.f_91074_.m_5661_((Component)Component.m_237113_((String)("Creature Radar: " + (on ? "ON" : "OFF"))), true);
            }
            while (CONFIG_KEY.m_90859_()) {
                mc.m_91152_((Screen)new RadarConfigScreen(mc.f_91080_));
            }
        }

        @SubscribeEvent
        public static void onRenderLevel(RenderLevelStageEvent event) {
            if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) {
                return;
            }
            RadarHudOverlay.captureRenderMatrices(event);
            RadarWorldRenderer.render(event);
        }

        @SubscribeEvent
        public static void onHud(RenderGuiOverlayEvent.Post event) {
            if (event.getOverlay() != VanillaGuiOverlay.HOTBAR.type()) {
                return;
            }
            RadarHudOverlay.render(event.getGuiGraphics());
        }
    }

    @Mod.EventBusSubscriber(modid="creature_radar", value={Dist.CLIENT}, bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBus {
        @SubscribeEvent
        public static void onRegisterKeys(RegisterKeyMappingsEvent event) {
            event.register(TOGGLE_KEY);
            event.register(CONFIG_KEY);
        }
    }
}
