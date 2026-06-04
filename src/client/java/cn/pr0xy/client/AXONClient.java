package cn.pr0xy.client;

/**Author:pr0xy | 2026-06-04**/

import cn.pr0xy.AXON;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.sound.PositionedSoundInstance;

public class AXONClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // register HUD
        HudRenderCallback.EVENT.register(new BodyCamHUD());
        // play sound when joining world
        ClientPlayConnectionEvents.JOIN.register(this::onJoin);
    }

    private void onJoin(ClientPlayNetworkHandler handler, PacketSender sender, MinecraftClient client) {
        client.execute(() -> {
            client.getSoundManager().play(
                PositionedSoundInstance.master(AXON.STARTUP_SOUND, 1.0f, 1.0f)
            );
            AXON.LOGGER.info("AXON BodyCam startup sound played.");
        });
    }
}
