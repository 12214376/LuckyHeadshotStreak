/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Connection
 *  net.minecraft.network.protocol.Packet
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 */
package com.xybaka.autoaim.mixin;

import com.xybaka.autoaim.util.rotation.RotationManager;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value={Connection.class})
public class ConnectionMixin {
    @ModifyVariable(method={"send(Lnet/minecraft/network/protocol/Packet;)V"}, at=@At(value="HEAD"), argsOnly=true)
    private Packet<?> onSend(Packet<?> packet) {
        return RotationManager.applySilentRotation(packet);
    }
}
