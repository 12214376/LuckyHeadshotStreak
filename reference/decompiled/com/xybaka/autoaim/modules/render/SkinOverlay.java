/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  com.mojang.blaze3d.platform.NativeImage
 *  net.minecraft.client.renderer.texture.AbstractTexture
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.resources.ResourceLocation
 */
package com.xybaka.autoaim.modules.render;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.NativeImage;
import com.xybaka.autoaim.config.ConfigManager;
import com.xybaka.autoaim.modules.Category;
import com.xybaka.autoaim.modules.Module;
import com.xybaka.autoaim.modules.settings.BooleanSetting;
import com.xybaka.autoaim.modules.settings.StringSetting;
import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Base64;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;

public class SkinOverlay
extends Module {
    public final StringSetting playerName = new StringSetting("\u73a9\u5bb6\u540d\u79f0", "");
    public final BooleanSetting loadCape = new BooleanSetting("\u52a0\u8f7d\u62ab\u98ce", true);
    private ResourceLocation customSkinLocation = null;
    private boolean isSkinLoaded = false;
    private ResourceLocation customCloakLocation = null;
    private boolean isCloakLoaded = false;

    public SkinOverlay() {
        super("\u76ae\u80a4\u66ff\u6362", Category.CLIENT, -1);
    }

    @Override
    public void onEnable() {
        String name = this.playerName.getValue().trim();
        if (!name.isEmpty()) {
            this.fetchAndLoadSkin(name);
        }
    }

    @Override
    public void onDisable() {
        this.customSkinLocation = null;
        this.isSkinLoaded = false;
        this.customCloakLocation = null;
        this.isCloakLoaded = false;
    }

    public void fetchAndLoadSkin(String username) {
        Thread thread = new Thread(() -> {
            try {
                String capeUrl;
                String skinUrl;
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest uuidReq = HttpRequest.newBuilder().uri(URI.create("https://api.mojang.com/users/profiles/minecraft/" + username)).build();
                HttpResponse<String> uuidRes = client.send(uuidReq, HttpResponse.BodyHandlers.ofString());
                if (uuidRes.statusCode() != 200) {
                    return;
                }
                JsonObject uuidJson = JsonParser.parseString((String)uuidRes.body()).getAsJsonObject();
                String playerId = uuidJson.get("id").getAsString();
                File cachedSkinFile = this.getCachedTextureFile(playerId, "skin");
                File cachedCapeFile = this.getCachedTextureFile(playerId, "cape");
                boolean shouldLoadCape = this.loadCape.isEnabled();
                if (cachedSkinFile.isFile()) {
                    this.loadTextureFromFile(cachedSkinFile, "skin/" + playerId, location -> {
                        this.customSkinLocation = location;
                    }, () -> {
                        this.isSkinLoaded = true;
                    });
                }
                if (shouldLoadCape && cachedCapeFile.isFile()) {
                    this.loadTextureFromFile(cachedCapeFile, "cape/" + playerId, location -> {
                        this.customCloakLocation = location;
                    }, () -> {
                        this.isCloakLoaded = true;
                    });
                }
                if (cachedSkinFile.isFile() && (!shouldLoadCape || cachedCapeFile.isFile())) {
                    return;
                }
                String formattedUUID = UUID.fromString(playerId.replaceFirst("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5")).toString();
                HttpRequest profileReq = HttpRequest.newBuilder().uri(URI.create("https://sessionserver.mojang.com/session/minecraft/profile/" + formattedUUID)).build();
                HttpResponse<String> profileRes = client.send(profileReq, HttpResponse.BodyHandlers.ofString());
                if (profileRes.statusCode() != 200) {
                    return;
                }
                JsonObject profileJson = JsonParser.parseString((String)profileRes.body()).getAsJsonObject();
                String base64 = profileJson.getAsJsonArray("properties").get(0).getAsJsonObject().get("value").getAsString();
                String decoded = new String(Base64.getDecoder().decode(base64));
                JsonObject textureJson = JsonParser.parseString((String)decoded).getAsJsonObject();
                JsonObject textures = textureJson.getAsJsonObject("textures");
                if (!cachedSkinFile.isFile() && textures.has("SKIN") && this.downloadTexture(client, skinUrl = textures.getAsJsonObject("SKIN").get("url").getAsString(), cachedSkinFile)) {
                    this.loadTextureFromFile(cachedSkinFile, "skin/" + playerId, location -> {
                        this.customSkinLocation = location;
                    }, () -> {
                        this.isSkinLoaded = true;
                    });
                }
                if (shouldLoadCape && !cachedCapeFile.isFile() && textures.has("CAPE") && this.downloadTexture(client, capeUrl = textures.getAsJsonObject("CAPE").get("url").getAsString(), cachedCapeFile)) {
                    this.loadTextureFromFile(cachedCapeFile, "cape/" + playerId, location -> {
                        this.customCloakLocation = location;
                    }, () -> {
                        this.isCloakLoaded = true;
                    });
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }, "SkinFetcher");
        thread.setDaemon(true);
        thread.start();
    }

    private File getSkinCacheDir() {
        File cacheDir = new File(ConfigManager.instance.getConfigDir(), "Skin");
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
        return cacheDir;
    }

    private File getCachedTextureFile(String playerId, String textureType) {
        return new File(this.getSkinCacheDir(), playerId + "_" + textureType + ".png");
    }

    private boolean downloadTexture(HttpClient client, String textureUrl, File outputFile) {
        try {
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(textureUrl)).build();
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            if (response.statusCode() != 200) {
                return false;
            }
            Files.createDirectories(outputFile.toPath().getParent(), new FileAttribute[0]);
            try (InputStream stream = response.body();){
                Files.copy(stream, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            return true;
        }
        catch (Exception ignored) {
            return false;
        }
    }

    private void loadTextureFromFile(File textureFile, String resourcePath, Consumer<ResourceLocation> locationConsumer, Runnable loadedCallback) {
        mc.execute(() -> {
            try (InputStream stream = Files.newInputStream(textureFile.toPath(), new OpenOption[0]);){
                NativeImage image = NativeImage.m_85058_((InputStream)stream);
                DynamicTexture texture = new DynamicTexture(image);
                ResourceLocation location = new ResourceLocation("autoaim", resourcePath);
                mc.m_91097_().m_118495_(location, (AbstractTexture)texture);
                locationConsumer.accept(location);
                loadedCallback.run();
            }
            catch (Exception exception) {
                // empty catch block
            }
        });
    }

    public ResourceLocation getCustomSkinLocation() {
        return this.customSkinLocation;
    }

    public boolean isSkinLoaded() {
        return this.isSkinLoaded;
    }

    public ResourceLocation getCustomCloakLocation() {
        return this.customCloakLocation;
    }

    public boolean isCloakLoaded() {
        return this.isCloakLoaded;
    }
}
