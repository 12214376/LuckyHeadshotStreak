/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraftforge.common.MinecraftForge
 */
package com.xybaka.autoaim.modules;

import com.xybaka.autoaim.modules.Category;
import com.xybaka.autoaim.modules.render.HUD;
import com.xybaka.autoaim.modules.settings.ModeSetting;
import com.xybaka.autoaim.modules.settings.Setting;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public abstract class Module {
    public static final Minecraft mc = Minecraft.m_91087_();
    private final List<Setting> settings = new ArrayList<Setting>();
    private final String name;
    private final Category category;
    private int key;
    private boolean enabled;

    public Module(String name, Category category, int key) {
        this.name = name;
        this.category = category;
        this.key = key;
        this.enabled = false;
    }

    public final void init() {
        this.setupSettings();
    }

    protected final ModeSetting<String> mode(String name, String defaultValue, String ... values) {
        return new ModeSetting<String>(name, defaultValue, values);
    }

    private void setupSettings() {
        try {
            for (Field field : this.getClass().getDeclaredFields()) {
                if (!Setting.class.isAssignableFrom(field.getType())) continue;
                field.setAccessible(true);
                Object obj = field.get(this);
                if (!(obj instanceof Setting)) continue;
                Setting s = (Setting)obj;
                s.setParent(this);
                this.settings.add(s);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toggle() {
        if (this.enabled) {
            this.disable();
        } else {
            this.enable();
        }
        HUD.push(this.getName(), this.enabled);
    }

    public void enable() {
        this.enabled = true;
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.onEnable();
    }

    public void disable() {
        this.enabled = false;
        MinecraftForge.EVENT_BUS.unregister((Object)this);
        this.onDisable();
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public String getName() {
        return this.name;
    }

    public Category getCategory() {
        return this.category;
    }

    public int getKey() {
        return this.key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public List<Setting> getSettings() {
        return this.settings;
    }
}
