/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  net.minecraft.client.Minecraft
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.ambient.AmbientCreature
 *  net.minecraft.world.entity.animal.AbstractFish
 *  net.minecraft.world.entity.animal.Animal
 *  net.minecraft.world.entity.animal.IronGolem
 *  net.minecraft.world.entity.animal.SnowGolem
 *  net.minecraft.world.entity.animal.WaterAnimal
 *  net.minecraft.world.entity.monster.Monster
 *  net.minecraft.world.entity.npc.AbstractVillager
 *  net.minecraft.world.entity.player.Player
 *  net.minecraftforge.fml.loading.FMLPaths
 */
package com.example.creatureradar.config;

import com.example.creatureradar.CreatureRadarMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.loading.FMLPaths;

public class RadarConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FMLPaths.CONFIGDIR.get().resolve("creature_radar.json");
    private static RadarConfig INSTANCE = new RadarConfig();
    public boolean enabled = true;
    public boolean showHud = true;
    public boolean throughBlocks = true;
    public DrawMode drawMode = DrawMode.ALL;
    public double maxDistance = 48.0;
    public float lineWidth = 2.0f;
    public float boxAlpha = 0.2f;
    public float outlineAlpha = 0.95f;
    public boolean targetPlayers = true;
    public boolean targetMonsters = true;
    public boolean targetAnimals = true;
    public boolean targetVillagers = true;
    public boolean targetGolems = true;
    public boolean targetWater = true;
    public int lineColor = -5317;
    public int boxColor = 857839347;
    public int outlineColor = -16718337;
    public boolean lineGradient = true;
    public int lineGradientStart = -5317;
    public int lineGradientEnd = -44462;
    public boolean boxGradient = false;
    public int boxGradientStart = 857839347;
    public int boxGradientEnd = 870915683;
    public boolean outlineGradient = true;
    public int outlineGradientStart = -16718337;
    public int outlineGradientEnd = -8978685;

    public static RadarConfig get() {
        return INSTANCE;
    }

    public static void load() {
        block10: {
            try {
                if (Files.exists(PATH, new LinkOption[0])) {
                    try (BufferedReader reader = Files.newBufferedReader(PATH);){
                        RadarConfig loaded = (RadarConfig)GSON.fromJson((Reader)reader, RadarConfig.class);
                        if (loaded != null) {
                            INSTANCE = loaded;
                        }
                        break block10;
                    }
                }
                RadarConfig.save();
            }
            catch (Exception e) {
                CreatureRadarMod.LOGGER.error("Failed to load creature_radar config", (Throwable)e);
                INSTANCE = new RadarConfig();
            }
        }
    }

    public static void save() {
        try {
            Files.createDirectories(PATH.getParent(), new FileAttribute[0]);
            try (BufferedWriter writer = Files.newBufferedWriter(PATH, new OpenOption[0]);){
                GSON.toJson((Object)INSTANCE, (Appendable)writer);
            }
        }
        catch (Exception e) {
            CreatureRadarMod.LOGGER.error("Failed to save creature_radar config", (Throwable)e);
        }
    }

    public static void toggleEnabled() {
        RadarConfig.get().enabled = !RadarConfig.get().enabled;
        RadarConfig.save();
    }

    public boolean shouldRender(LivingEntity entity) {
        if (entity == null || entity.m_213877_() || !entity.m_6084_()) {
            return false;
        }
        Minecraft mc = Minecraft.m_91087_();
        if (mc.f_91074_ == null || entity == mc.f_91074_) {
            return false;
        }
        if (entity instanceof Player) {
            return this.targetPlayers;
        }
        if (entity instanceof Monster) {
            return this.targetMonsters;
        }
        if (entity instanceof AbstractVillager) {
            return this.targetVillagers;
        }
        if (entity instanceof IronGolem || entity instanceof SnowGolem) {
            return this.targetGolems;
        }
        if (entity instanceof WaterAnimal || entity instanceof AbstractFish || entity instanceof AmbientCreature) {
            return this.targetWater;
        }
        if (entity instanceof Animal) {
            return this.targetAnimals;
        }
        return this.targetMonsters || this.targetAnimals;
    }

    public static float clamp01(float value) {
        return Math.max(0.0f, Math.min(1.0f, value));
    }

    public static int withAlpha(int argb, float alpha) {
        int a = Math.round(RadarConfig.clamp01(alpha) * 255.0f);
        return argb & 0xFFFFFF | a << 24;
    }

    public static int lerpColor(int from, int to, float t) {
        t = RadarConfig.clamp01(t);
        int a1 = from >>> 24 & 0xFF;
        int r1 = from >>> 16 & 0xFF;
        int g1 = from >>> 8 & 0xFF;
        int b1 = from & 0xFF;
        int a2 = to >>> 24 & 0xFF;
        int r2 = to >>> 16 & 0xFF;
        int g2 = to >>> 8 & 0xFF;
        int b2 = to & 0xFF;
        int a = Math.round((float)a1 + (float)(a2 - a1) * t);
        int r = Math.round((float)r1 + (float)(r2 - r1) * t);
        int g = Math.round((float)g1 + (float)(g2 - g1) * t);
        int b = Math.round((float)b1 + (float)(b2 - b1) * t);
        return a << 24 | r << 16 | g << 8 | b;
    }

    public int colorByDistance(int solid, int start, int end, boolean gradient, double distance) {
        if (!gradient) {
            return solid;
        }
        float t = RadarConfig.clamp01((float)(distance / Math.max(this.maxDistance, 1.0)));
        return RadarConfig.lerpColor(start, end, t);
    }

    public static enum DrawMode {
        LINE,
        BOX,
        OUTLINE,
        LINE_BOX,
        BOX_OUTLINE,
        LINE_OUTLINE,
        ALL;


        public boolean line() {
            return this == LINE || this == LINE_BOX || this == LINE_OUTLINE || this == ALL;
        }

        public boolean box() {
            return this == BOX || this == LINE_BOX || this == BOX_OUTLINE || this == ALL;
        }

        public boolean outline() {
            return this == OUTLINE || this == BOX_OUTLINE || this == LINE_OUTLINE || this == ALL;
        }

        public DrawMode next() {
            DrawMode[] values = DrawMode.values();
            return values[(this.ordinal() + 1) % values.length];
        }

        public String langKey() {
            return "creature_radar.mode." + this.name().toLowerCase();
        }
    }
}
