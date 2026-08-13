/*
 * Decompiled with CFR 0.152.
 */
package com.xybaka.autoaim.modules;

import com.xybaka.autoaim.config.ConfigManager;
import com.xybaka.autoaim.modules.Category;
import com.xybaka.autoaim.modules.Module;
import com.xybaka.autoaim.modules.client.Target;
import com.xybaka.autoaim.modules.client.Teams;
import com.xybaka.autoaim.modules.combat.AutoAim;
import com.xybaka.autoaim.modules.combat.NoRecoil;
import com.xybaka.autoaim.modules.combat.TriggerBot;
import com.xybaka.autoaim.modules.movement.Sprint;
import com.xybaka.autoaim.modules.movement.invMove;
import com.xybaka.autoaim.modules.render.Camera;
import com.xybaka.autoaim.modules.render.ClickGUI;
import com.xybaka.autoaim.modules.render.ESP;
import com.xybaka.autoaim.modules.render.FullBright;
import com.xybaka.autoaim.modules.render.HUD;
import com.xybaka.autoaim.modules.render.NoFov;
import com.xybaka.autoaim.modules.render.SkinOverlay;
import com.xybaka.autoaim.modules.render.TargetHud;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static final ModuleManager instance = new ModuleManager();
    private final List<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        this.modules.add(new Target());
        this.modules.add(new Teams());
        this.modules.add(new AutoAim());
        this.modules.add(new NoRecoil());
        this.modules.add(new TriggerBot());
        this.modules.add(new Sprint());
        this.modules.add(new invMove());
        this.modules.add(new HUD());
        this.modules.add(new TargetHud());
        this.modules.add(new ClickGUI());
        this.modules.add(new ESP());
        this.modules.add(new FullBright());
        this.modules.add(new Camera());
        this.modules.add(new NoFov());
        this.modules.add(new SkinOverlay());
        this.modules.forEach(Module::init);
        ConfigManager.instance.load(this.modules);
        this.get(Target.class).enable();
    }

    public void saveConfig() {
        ConfigManager.instance.save(this.modules);
    }

    public void loadConfig() {
        ConfigManager.instance.load(this.modules);
    }

    public List<Module> getModules() {
        return this.modules;
    }

    public Module getModuleByName(String name) {
        return this.modules.stream().filter(m -> m.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public List<Module> getModulesByCategory(Category category) {
        ArrayList<Module> categoryModules = new ArrayList<Module>();
        for (Module m : this.modules) {
            if (m.getCategory() != category) continue;
            categoryModules.add(m);
        }
        return categoryModules;
    }

    public <T extends Module> T get(Class<T> clazz) {
        return (T)((Module)this.modules.stream().filter(m -> m.getClass() == clazz).map(clazz::cast).findFirst().orElse(null));
    }
}
