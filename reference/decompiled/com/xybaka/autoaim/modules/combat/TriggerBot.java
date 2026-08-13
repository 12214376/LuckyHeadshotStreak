/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.phys.EntityHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraftforge.event.TickEvent$ClientTickEvent
 *  net.minecraftforge.event.TickEvent$Phase
 *  net.minecraftforge.eventbus.api.SubscribeEvent
 */
package com.xybaka.autoaim.modules.combat;

import com.xybaka.autoaim.modules.Category;
import com.xybaka.autoaim.modules.Module;
import com.xybaka.autoaim.modules.settings.BooleanSetting;
import com.xybaka.autoaim.modules.settings.NumberSetting;
import com.xybaka.autoaim.util.TargetUtil;
import com.xybaka.autoaim.util.tacz.TaczUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TriggerBot
extends Module {
    public final BooleanSetting checkAiming = new BooleanSetting("\u68c0\u67e5\u5f00\u955c", true);
    public final NumberSetting range = new NumberSetting("\u8303\u56f4", 128.0, 1.0, 128.0, 0.1);
    public final NumberSetting delay = new NumberSetting("\u5ef6\u8fdf", 100.0, 0.0, 500.0, 5.0);
    private long lastShotTime;

    public TriggerBot() {
        super("\u81ea\u52a8\u6273\u673a", Category.COMBAT, -1);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        EntityHitResult entityHitResult;
        block11: {
            block10: {
                if (!this.isEnabled() || event.phase != TickEvent.Phase.START || TriggerBot.mc.f_91074_ == null || TriggerBot.mc.f_91077_ == null) {
                    return;
                }
                HitResult hitResult = TriggerBot.mc.f_91077_;
                if (!(hitResult instanceof EntityHitResult)) break block10;
                entityHitResult = (EntityHitResult)hitResult;
                if (TriggerBot.mc.f_91077_.m_6662_() == HitResult.Type.ENTITY) break block11;
            }
            return;
        }
        Entity entity = entityHitResult.m_82443_();
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        LivingEntity target = (LivingEntity)entity;
        if (!TargetUtil.isValid(target) || (double)TriggerBot.mc.f_91074_.m_20270_((Entity)target) > this.range.getValue()) {
            return;
        }
        if (this.checkAiming.isEnabled() && TaczUtil.getAimProgress(TriggerBot.mc.f_91074_) < 1.0f) {
            return;
        }
        if (!TaczUtil.hasShootableAmmo(TriggerBot.mc.f_91074_)) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - this.lastShotTime < (long)this.delay.getValue()) {
            return;
        }
        if (TaczUtil.shoot(TriggerBot.mc.f_91074_)) {
            this.lastShotTime = now;
        }
    }
}
