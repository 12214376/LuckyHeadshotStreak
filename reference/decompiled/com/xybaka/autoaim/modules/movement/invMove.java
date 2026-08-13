/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  net.minecraft.client.KeyMapping
 */
package com.xybaka.autoaim.modules.movement;

import com.mojang.blaze3d.platform.InputConstants;
import com.xybaka.autoaim.modules.Category;
import com.xybaka.autoaim.modules.Module;
import com.xybaka.autoaim.modules.settings.BooleanSetting;
import net.minecraft.client.KeyMapping;

public class invMove
extends Module {
    public final BooleanSetting sneak = new BooleanSetting("\u6f5c\u884c", false);

    public invMove() {
        super("\u80cc\u5305\u79fb\u52a8", Category.MOVEMENT, -1);
    }

    public void tickKeys() {
        if (!this.isEnabled()) {
            return;
        }
        if (invMove.mc.f_91080_ == null) {
            return;
        }
        this.tickKey(invMove.mc.f_91066_.f_92085_);
        this.tickKey(invMove.mc.f_91066_.f_92087_);
        this.tickKey(invMove.mc.f_91066_.f_92086_);
        this.tickKey(invMove.mc.f_91066_.f_92088_);
        this.tickKey(invMove.mc.f_91066_.f_92089_);
        this.tickKey(invMove.mc.f_91066_.f_92091_);
        if (this.sneak.isEnabled()) {
            this.tickKey(invMove.mc.f_91066_.f_92090_);
        }
    }

    private void tickKey(KeyMapping key) {
        if (key.getKey().m_84868_() == InputConstants.Type.KEYSYM && key.getKey().m_84873_() != InputConstants.f_84822_.m_84873_()) {
            boolean raw = InputConstants.m_84830_((long)mc.m_91268_().m_85439_(), (int)key.getKey().m_84873_());
            key.m_7249_(raw);
        }
    }
}
