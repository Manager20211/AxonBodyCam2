package cn.pr0xy;

/**Author:pr0xy | 2026-06-04**/

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AXON implements ModInitializer {
    public static final String MOD_ID = "axon";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // bodycam boot sound
    public static final Identifier STARTUP_SOUND_ID = new Identifier(MOD_ID, "startup");
    public static final SoundEvent STARTUP_SOUND = SoundEvent.of(STARTUP_SOUND_ID);

    @Override
    public void onInitialize() {
        Registry.register(Registries.SOUND_EVENT, STARTUP_SOUND_ID, STARTUP_SOUND);
        LOGGER.info("AXON BodyCam Mod initialized!");
    }
}
