/*
 * Decompiled with CFR 0.152.
 */
package com.xybaka.autoaim.modules.render;

import com.xybaka.autoaim.gui.clickgui.ClickGuiManager;
import com.xybaka.autoaim.modules.Category;
import com.xybaka.autoaim.modules.Module;
import com.xybaka.autoaim.modules.settings.ModeSetting;

public class ClickGUI
extends Module {
    public final ModeSetting<String> mode = this.mode("\u6a21\u5f0f", "\u81ea\u52a8\u7784\u51c6", "\u81ea\u52a8\u7784\u51c6");

    public ClickGUI() {
        super("\u70b9\u51fb\u83dc\u5355", Category.RENDER, 344);
    }

    @Override
    public void onEnable() {
        ClickGuiManager manager = new ClickGuiManager(this.mode.getValue());
        mc.m_91152_(manager.createScreen());
        this.disable();
    }
}
