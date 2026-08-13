/*
 * Decompiled with CFR 0.152.
 */
package com.xybaka.autoaim.modules.client;

import com.xybaka.autoaim.modules.Category;
import com.xybaka.autoaim.modules.Module;
import com.xybaka.autoaim.modules.settings.BooleanSetting;
import com.xybaka.autoaim.modules.settings.ModeSetting;

public class Target
extends Module {
    public final ModeSetting<String> mode = this.mode("\u6a21\u5f0f", "\u8ddd\u79bb", "\u8ddd\u79bb", "\u751f\u547d\u503c", "\u89c6\u91ce\u89d2");
    public final ModeSetting<String> aimPart = this.mode("\u7784\u51c6\u90e8\u4f4d", "\u5934\u90e8", "\u5934\u90e8", "\u8eab\u4f53", "\u811a\u90e8");
    public final BooleanSetting players = new BooleanSetting("\u73a9\u5bb6", true);
    public final BooleanSetting monsters = new BooleanSetting("\u602a\u7269", true);
    public final BooleanSetting animals = new BooleanSetting("\u52a8\u7269", false);
    public final BooleanSetting villagers = new BooleanSetting("\u6751\u6c11", false);
    public final BooleanSetting golems = new BooleanSetting("\u5080\u5121", false);
    public final BooleanSetting waterAnimals = new BooleanSetting("\u6c34\u751f\u52a8\u7269", false);
    public final BooleanSetting waterCreatures = new BooleanSetting("\u6c34\u751f\u751f\u7269", false);
    public final BooleanSetting ambient = new BooleanSetting("\u73af\u5883\u751f\u7269", false);

    public Target() {
        super("\u76ee\u6807\u8bbe\u7f6e", Category.CLIENT, -1);
        this.enable();
    }

    @Override
    public void toggle() {
        if (!this.isEnabled()) {
            this.enable();
        }
    }
}
