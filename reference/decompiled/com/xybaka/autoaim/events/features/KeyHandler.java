/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.client.event.InputEvent$Key
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package com.xybaka.autoaim.events.features;

import com.xybaka.autoaim.modules.Module;
import com.xybaka.autoaim.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class KeyHandler {
    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        if (event.getAction() == 1 && Minecraft.m_91087_().f_91080_ == null) {
            int pressedKey = event.getKey();
            if (pressedKey <= 0) {
                return;
            }
            for (Module m : ModuleManager.instance.getModules()) {
                if (m.getKey() != pressedKey) continue;
                m.toggle();
            }
        }
    }
}
