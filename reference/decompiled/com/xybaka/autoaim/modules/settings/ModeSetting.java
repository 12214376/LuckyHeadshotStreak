/*
 * Decompiled with CFR 0.152.
 */
package com.xybaka.autoaim.modules.settings;

import com.xybaka.autoaim.modules.settings.Setting;
import java.util.Objects;

public class ModeSetting<T>
extends Setting {
    private T value;
    private final T[] values;

    @SafeVarargs
    public ModeSetting(String name, T defaultValue, T ... values) {
        super(name);
        this.value = defaultValue;
        this.values = values;
    }

    public T getValue() {
        return this.value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T[] getValues() {
        return this.values;
    }

    public void cycle() {
        for (int i = 0; i < this.values.length; ++i) {
            if (!Objects.equals(this.values[i], this.value)) continue;
            this.value = this.values[(i + 1) % this.values.length];
            return;
        }
        if (this.values.length > 0) {
            this.value = this.values[0];
        }
    }

    public void setValueByName(String name) {
        for (T option : this.values) {
            if (!Objects.equals(String.valueOf(option), name)) continue;
            this.value = option;
            return;
        }
    }

    public void setValueByIndex(int index) {
        if (index >= 0 && index < this.values.length) {
            this.value = this.values[index];
        }
    }

    public String getDisplayName() {
        return String.valueOf(this.value);
    }
}
