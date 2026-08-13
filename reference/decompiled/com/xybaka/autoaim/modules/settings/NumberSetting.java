/*
 * Decompiled with CFR 0.152.
 */
package com.xybaka.autoaim.modules.settings;

import com.xybaka.autoaim.modules.settings.Setting;

public class NumberSetting
extends Setting {
    private double value;
    private double min;
    private double max;
    private double increment;

    public NumberSetting(String name, double defaultValue, double min, double max, double increment) {
        super(name);
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.increment = increment;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        double precision = 1.0 / this.increment;
        this.value = (double)Math.round(Math.max(this.min, Math.min(this.max, value)) * precision) / precision;
    }

    public float getValueFloat() {
        return (float)this.value;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }
}
