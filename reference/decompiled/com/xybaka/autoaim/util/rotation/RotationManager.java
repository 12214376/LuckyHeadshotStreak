/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.protocol.Packet
 *  net.minecraft.network.protocol.game.ServerboundMovePlayerPacket
 *  net.minecraft.network.protocol.game.ServerboundMovePlayerPacket$PosRot
 *  net.minecraft.network.protocol.game.ServerboundMovePlayerPacket$Rot
 */
package com.xybaka.autoaim.util.rotation;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

public final class RotationManager {
    private static final Minecraft mc = Minecraft.m_91087_();
    private static boolean silentActive;
    private static float silentYaw;
    private static float silentPitch;

    private RotationManager() {
    }

    public static void setSilentRotation(float yaw, float pitch) {
        silentActive = true;
        silentYaw = yaw;
        silentPitch = pitch;
    }

    public static void clearSilentRotation() {
        silentActive = false;
    }

    public static boolean isSilentActive() {
        return silentActive;
    }

    public static float getSilentYaw() {
        return silentYaw;
    }

    public static float getSilentPitch() {
        return silentPitch;
    }

    public static float getEffectiveYaw() {
        return silentActive ? silentYaw : (RotationManager.mc.f_91074_ != null ? RotationManager.mc.f_91074_.m_146908_() : 0.0f);
    }

    public static float getEffectivePitch() {
        return silentActive ? silentPitch : (RotationManager.mc.f_91074_ != null ? RotationManager.mc.f_91074_.m_146909_() : 0.0f);
    }

    public static Packet<?> applySilentRotation(Packet<?> packet) {
        ServerboundMovePlayerPacket movePacket;
        block5: {
            block4: {
                if (!(packet instanceof ServerboundMovePlayerPacket)) break block4;
                movePacket = (ServerboundMovePlayerPacket)packet;
                if (silentActive) break block5;
            }
            return packet;
        }
        boolean onGround = movePacket.m_134139_();
        if (movePacket.m_179683_()) {
            return new ServerboundMovePlayerPacket.PosRot(movePacket.m_134129_(0.0), movePacket.m_134140_(0.0), movePacket.m_134146_(0.0), silentYaw, silentPitch, onGround);
        }
        return new ServerboundMovePlayerPacket.Rot(silentYaw, silentPitch, onGround);
    }
}
