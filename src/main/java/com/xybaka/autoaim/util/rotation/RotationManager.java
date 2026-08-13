package com.xybaka.autoaim.util.rotation;

import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

public final class RotationManager {
    private static final Minecraft mc = Minecraft.m_91087_();
    private static boolean silentActive;
    private static float silentYaw;
    private static float silentPitch;
    private static boolean visualSpinActive;
    private static float visualSpinYaw;

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

    public static void setVisualSpinYaw(float yaw) {
        visualSpinActive = true;
        visualSpinYaw = yaw;
    }

    public static void clearVisualSpin() {
        visualSpinActive = false;
    }

    public static boolean isVisualSpinActive() {
        return visualSpinActive;
    }

    public static float getVisualSpinYaw() {
        return visualSpinYaw;
    }

    public static boolean hasRenderOverride() {
        return silentActive || visualSpinActive;
    }

    public static float getRenderYaw() {
        if (silentActive) {
            return silentYaw;
        }
        if (visualSpinActive) {
            return visualSpinYaw;
        }
        return mc.f_91074_ != null ? mc.f_91074_.m_146908_() : 0.0f;
    }

    public static float getRenderPitch() {
        if (silentActive) {
            return silentPitch;
        }
        return mc.f_91074_ != null ? mc.f_91074_.m_146909_() : 0.0f;
    }

    public static float getEffectiveYaw() {
        return silentActive ? silentYaw : (mc.f_91074_ != null ? mc.f_91074_.m_146908_() : 0.0f);
    }

    public static float getEffectivePitch() {
        return silentActive ? silentPitch : (mc.f_91074_ != null ? mc.f_91074_.m_146909_() : 0.0f);
    }

    public static Packet<?> applySilentRotation(Packet<?> packet) {
        if (!(packet instanceof ServerboundMovePlayerPacket movePacket) || !silentActive) {
            return packet;
        }
        boolean onGround = movePacket.m_134139_();
        if (movePacket.m_179683_()) {
            return new ServerboundMovePlayerPacket.PosRot(
                    movePacket.m_134129_(0.0),
                    movePacket.m_134140_(0.0),
                    movePacket.m_134146_(0.0),
                    silentYaw,
                    silentPitch,
                    onGround
            );
        }
        return new ServerboundMovePlayerPacket.Rot(silentYaw, silentPitch, onGround);
    }
}