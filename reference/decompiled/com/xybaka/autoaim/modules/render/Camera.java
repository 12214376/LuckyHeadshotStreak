/*
 * Decompiled with CFR 0.152.
 */
package com.xybaka.autoaim.modules.render;

import com.xybaka.autoaim.modules.Category;
import com.xybaka.autoaim.modules.Module;
import com.xybaka.autoaim.modules.settings.BooleanSetting;

public class Camera
extends Module {
    public final BooleanSetting noClip = new BooleanSetting("\u955c\u5934\u7a7f\u5899", true);
    public final BooleanSetting noHurtCam = new BooleanSetting("\u5173\u95ed\u53d7\u4f24\u6643\u52a8", false);

    public Camera() {
        super("\u955c\u5934", Category.RENDER, -1);
    }
}
