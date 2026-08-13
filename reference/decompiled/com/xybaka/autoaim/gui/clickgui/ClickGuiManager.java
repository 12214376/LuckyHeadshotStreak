/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.gui.screens.Screen
 *  org.lwjgl.glfw.GLFW
 */
package com.xybaka.autoaim.gui.clickgui;

import com.xybaka.autoaim.gui.clickgui.mode.AutoAimClickGuiScreen;
import com.xybaka.autoaim.modules.Category;
import com.xybaka.autoaim.modules.Module;
import com.xybaka.autoaim.modules.ModuleManager;
import com.xybaka.autoaim.modules.settings.ModeSetting;
import com.xybaka.autoaim.modules.settings.Setting;
import com.xybaka.autoaim.modules.settings.StringSetting;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public final class ClickGuiManager {
    private static final float SCROLL_STEP = 8.0f;
    private final String mode;
    private Category selectedCategory = Category.values()[0];
    private final Map<Module, Float> slideAnimations = new HashMap<Module, Float>();
    private final Map<Module, String> openEnums = new HashMap<Module, String>();
    private final Map<Module, Float> toggleAnimations = new HashMap<Module, Float>();
    private final Map<Category, Float> tabHoverAnimations = new HashMap<Category, Float>();
    private float scrollOffset;
    private float scrollTarget;

    public ClickGuiManager(String mode) {
        this.mode = mode;
    }

    public String getMode() {
        return this.mode;
    }

    public Screen createScreen() {
        return switch (this.mode) {
            case "\u81ea\u52a8\u7784\u51c6" -> new AutoAimClickGuiScreen(this);
            default -> new AutoAimClickGuiScreen(this);
        };
    }

    public String getKeyName(int key) {
        if (key <= 0) {
            return "\u672a\u7ed1\u5b9a";
        }
        try {
            int sc = GLFW.glfwGetKeyScancode((int)key);
            String n = GLFW.glfwGetKeyName((int)key, (int)sc);
            if (n != null) {
                return n.toUpperCase();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return switch (key) {
            case 32 -> "\u7a7a\u683c";
            case 258 -> "\u5236\u8868\u952e";
            case 257 -> "\u56de\u8f66";
            case 259 -> "\u9000\u683c";
            case 256 -> "\u9000\u51fa";
            case 340 -> "\u5de6 Shift";
            case 344 -> "\u53f3 Shift";
            case 341 -> "\u5de6 Ctrl";
            case 345 -> "\u53f3 Ctrl";
            case 342 -> "\u5de6 Alt";
            case 346 -> "\u53f3 Alt";
            case 343 -> "\u5de6 Win";
            case 347 -> "\u53f3 Win";
            case 348 -> "\u83dc\u5355";
            case 260 -> "Insert";
            case 261 -> "Delete";
            case 268 -> "Home";
            case 269 -> "End";
            case 266 -> "Page Up";
            case 267 -> "Page Down";
            case 280 -> "Caps Lock";
            default -> "K" + key;
        };
    }

    public Category getSelectedCategory() {
        return this.selectedCategory;
    }

    public void selectCategory(Category category) {
        if (category == this.selectedCategory) {
            return;
        }
        this.selectedCategory = category;
        this.slideAnimations.clear();
        this.openEnums.clear();
        this.scrollOffset = 0.0f;
        this.scrollTarget = 0.0f;
    }

    public List<Module> getVisibleModules() {
        return ModuleManager.instance.getModulesByCategory(this.selectedCategory);
    }

    public int getCategoryModuleCount(Category category) {
        return ModuleManager.instance.getModulesByCategory(category).size();
    }

    public boolean isExpanded(Module module) {
        return this.slideAnimations.containsKey(module);
    }

    public void expand(Module module) {
        this.slideAnimations.putIfAbsent(module, Float.valueOf(0.0f));
    }

    public void collapse(Module module) {
        this.slideAnimations.remove(module);
        this.openEnums.remove(module);
    }

    public void toggleExpanded(Module module) {
        if (module.getSettings().isEmpty()) {
            return;
        }
        if (this.isExpanded(module)) {
            this.collapse(module);
        } else {
            this.expand(module);
        }
    }

    public float getSlideAnimation(Module module) {
        return this.slideAnimations.getOrDefault(module, Float.valueOf(0.0f)).floatValue();
    }

    public void setSlideAnimation(Module module, float value) {
        this.slideAnimations.put(module, Float.valueOf(value));
    }

    public float getToggleAnimation(Module module, boolean enabledDefault) {
        return this.toggleAnimations.getOrDefault(module, Float.valueOf(enabledDefault ? 1.0f : 0.0f)).floatValue();
    }

    public void setToggleAnimation(Module module, float value) {
        this.toggleAnimations.put(module, Float.valueOf(value));
    }

    public float getTabHoverAnimation(Category category) {
        return this.tabHoverAnimations.getOrDefault((Object)category, Float.valueOf(0.0f)).floatValue();
    }

    public void setTabHoverAnimation(Category category, float value) {
        this.tabHoverAnimations.put(category, Float.valueOf(value));
    }

    public String getOpenEnum(Module module) {
        return this.openEnums.get(module);
    }

    public void toggleOpenEnum(Module module, Setting setting) {
        String current = this.openEnums.get(module);
        this.openEnums.put(module, setting.getName().equals(current) ? null : setting.getName());
    }

    public boolean isEnumOpen(Module module, Setting setting) {
        return setting.getName().equals(this.openEnums.get(module));
    }

    public int calcSettingsHeight(Module module) {
        if (module == null || module.getSettings().isEmpty()) {
            return 0;
        }
        String openEnum = this.openEnums.get(module);
        int h = 6;
        for (Setting setting : module.getSettings()) {
            if (setting instanceof ModeSetting) {
                ModeSetting enumSetting = (ModeSetting)setting;
                if (setting.getName().equals(openEnum)) {
                    h += 16 + enumSetting.getValues().length * 15 + 4;
                    continue;
                }
            }
            if (setting instanceof StringSetting) {
                h += 34;
                continue;
            }
            h += 26;
        }
        return h;
    }

    public int calcModuleListHeight() {
        int h = 0;
        for (Module module : this.getVisibleModules()) {
            h += 22;
            if (!this.isExpanded(module)) continue;
            h += Math.round((float)this.calcSettingsHeight(module) * this.getSlideAnimation(module));
        }
        return h;
    }

    public float getScrollOffset() {
        return this.scrollOffset;
    }

    public float getScrollTarget() {
        return this.scrollTarget;
    }

    public void animateScroll(float factor) {
        this.scrollOffset = ClickGuiManager.lerp(this.scrollOffset, this.scrollTarget, factor);
    }

    public void clampScroll(int viewportHeight) {
        float maxScroll = Math.max(0, this.calcModuleListHeight() - viewportHeight);
        this.scrollTarget = ClickGuiManager.clamp(this.scrollTarget, 0.0f, maxScroll);
        this.scrollOffset = ClickGuiManager.clamp(this.scrollOffset, 0.0f, maxScroll);
    }

    public void scrollBy(double delta, int viewportHeight) {
        this.clampScroll(viewportHeight);
        float normalized = (float)Math.max(-1.0, Math.min(1.0, delta));
        if (normalized == 0.0f) {
            return;
        }
        this.scrollTarget -= normalized * 8.0f;
        this.clampScroll(viewportHeight);
    }

    private static float lerp(float a, float b, float t) {
        float d = b - a;
        return Math.abs(d) < 0.4f ? b : a + d * t;
    }

    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(max, value));
    }
}
