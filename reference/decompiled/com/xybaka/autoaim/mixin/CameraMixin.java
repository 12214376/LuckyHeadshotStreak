/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Camera
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package com.xybaka.autoaim.mixin;

import com.xybaka.autoaim.modules.ModuleManager;
import com.xybaka.autoaim.modules.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={net.minecraft.client.Camera.class})
public class CameraMixin {
    @Inject(method={"getMaxZoom"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetMaxZoom(double startingDistance, CallbackInfoReturnable<Double> cir) {
        Camera mod = (Camera)ModuleManager.instance.getModuleByName("\u955c\u5934");
        if (mod != null && mod.isEnabled() && mod.noClip.isEnabled()) {
            cir.setReturnValue((Object)startingDistance);
        }
    }
}
