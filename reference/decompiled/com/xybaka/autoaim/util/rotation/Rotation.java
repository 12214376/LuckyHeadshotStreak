/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.phys.Vec3
 */
package com.xybaka.autoaim.util.rotation;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public final class Rotation {
    private static final Minecraft mc = Minecraft.m_91087_();

    private Rotation() {
    }

    public static float[] getRotationsToEntity(Entity entity) {
        return Rotation.getRotationsToPosition(entity.m_20182_().m_82520_(0.0, (double)entity.m_20192_(), 0.0));
    }

    public static float[] getRotationsToPosition(Vec3 targetPos) {
        Vec3 eyesPos = Rotation.mc.f_91074_.m_20299_(1.0f);
        double diffX = targetPos.f_82479_ - eyesPos.f_82479_;
        double diffZ = targetPos.f_82481_ - eyesPos.f_82481_;
        double diffY = targetPos.f_82480_ - eyesPos.f_82480_;
        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, dist)));
        return new float[]{Rotation.mc.f_91074_.m_146908_() + Mth.m_14177_((float)(yaw - Rotation.mc.f_91074_.m_146908_())), Rotation.mc.f_91074_.m_146909_() + Mth.m_14177_((float)(pitch - Rotation.mc.f_91074_.m_146909_()))};
    }
}
