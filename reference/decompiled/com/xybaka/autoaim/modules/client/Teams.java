/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.scores.PlayerTeam
 */
package com.xybaka.autoaim.modules.client;

import com.xybaka.autoaim.modules.Category;
import com.xybaka.autoaim.modules.Module;
import com.xybaka.autoaim.modules.settings.BooleanSetting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.scores.PlayerTeam;

public class Teams
extends Module {
    public final BooleanSetting scoreboard = new BooleanSetting("\u8ba1\u5206\u677f", true);
    public final BooleanSetting colorCheck = new BooleanSetting("\u989c\u8272\u68c0\u6d4b", false);

    public Teams() {
        super("\u961f\u53cb\u8bc6\u522b", Category.CLIENT, -1);
    }

    public boolean isTeam(Player target) {
        if (Teams.mc.f_91074_ == null || target == null) {
            return false;
        }
        if (this.scoreboard.isEnabled()) {
            PlayerTeam myTeam = (PlayerTeam)Teams.mc.f_91074_.m_5647_();
            PlayerTeam targetTeam = (PlayerTeam)target.m_5647_();
            if (myTeam != null && targetTeam != null && myTeam == targetTeam) {
                return true;
            }
        }
        if (this.colorCheck.isEnabled() && Teams.mc.f_91074_.m_5446_().m_7383_().m_131135_() != null && target.m_5446_().m_7383_().m_131135_() != null) {
            return Teams.mc.f_91074_.m_5446_().m_7383_().m_131135_().equals((Object)target.m_5446_().m_7383_().m_131135_());
        }
        return false;
    }
}
