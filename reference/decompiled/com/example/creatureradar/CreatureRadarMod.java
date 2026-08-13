/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.logging.LogUtils
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.eventbus.api.IEventBus
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
 *  net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
 *  org.slf4j.Logger
 */
package com.example.creatureradar;

import com.example.creatureradar.config.RadarConfig;
import com.mojang.logging.LogUtils;
import com.xybaka.autoaim.events.EventManager;
import com.xybaka.autoaim.modules.ModuleManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(value="creature_radar")
public class CreatureRadarMod {
    public static final String MOD_ID = "creature_radar";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreatureRadarMod() {
        EventManager.register();
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    /*
     * WARNING - void declaration
     */
    private void commonSetup(FMLCommonSetupEvent fMLCommonSetupEvent) {
        void event;
        ModuleManager.instance.loadConfig();
        event.enqueueWork(RadarConfig::load);
        LOGGER.info("Creature Radar loaded");
    }
}
