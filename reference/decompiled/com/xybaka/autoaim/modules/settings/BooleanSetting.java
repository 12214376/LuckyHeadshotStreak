/*
 * Decompiled with CFR 0.152.
 */
package com.xybaka.autoaim.modules.settings;

import com.xybaka.autoaim.modules.settings.Setting;

public class BooleanSetting
extends Setting {
    private boolean enabled;

    public BooleanSetting(String name, boolean defaultValue) {
        super(name);
        this.enabled = defaultValue;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void toggle() {
        this.enabled = !this.enabled;
    }
}
