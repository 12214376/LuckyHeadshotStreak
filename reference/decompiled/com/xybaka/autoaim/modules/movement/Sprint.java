/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.event.TickEvent$ClientTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package com.xybaka.autoaim.modules.movement;

import com.xybaka.autoaim.modules.Category;
import com.xybaka.autoaim.modules.Module;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Sprint
extends Module {
    public Sprint() {
        super("\u81ea\u52a8\u75be\u8dd1", Category.MOVEMENT, -1);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }
        if (Sprint.mc.f_91074_ != null && Sprint.mc.f_91074_.f_108618_.f_108568_) {
            Sprint.mc.f_91074_.m_6858_(true);
        }
    }
}
