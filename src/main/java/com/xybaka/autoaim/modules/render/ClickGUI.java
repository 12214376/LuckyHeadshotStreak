package com.xybaka.autoaim.modules.render;

import com.example.creatureradar.client.gui.RadarConfigScreen;
import com.xybaka.autoaim.modules.Category;
import com.xybaka.autoaim.modules.Module;
import net.minecraft.client.Minecraft;

public class ClickGUI extends Module {
    public ClickGUI() {
        // no default hotkey (disable Right Shift menu open)
        super("ClickGUI", Category.RENDER, -1);
    }

    public void onEnable() {
        Minecraft mc = Minecraft.m_91087_();
        if (mc != null) {
            mc.m_91152_(new RadarConfigScreen(mc.f_91080_));
        }
        this.disable();
    }
}
