package com.hyfata.najoan.koreanpatch.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = KoreanPatchClient.MODID, dist = Dist.CLIENT)
public class KoreanPatchClient {

    public static final String MODID = "koreanpatch";
    public static final boolean DEBUG = false;
    public static boolean IME = false;
    public static boolean bypassInjection = false;

    public KoreanPatchClient(IEventBus bus, ModContainer modContainer) {
        KeyBinds.register();
        bus.addListener(this::registerKeys);
        registerEvents();
    }

    public void registerEvents() {
        NeoForge.EVENT_BUS.register(EventListener.class);
    }

    @SubscribeEvent
    public void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(KeyBinds.getImeBinding());
        event.register(KeyBinds.getLangBinding());
	}

//    public void onInitializeClient() {
//        registerEvents();
//        KeyBinds.register();
//    }

//    public void registerEvents() {
//        ClientLifecycleEvents.CLIENT_STARTED.register(EventListener::onClientStarted);
//        ScreenEvents.AFTER_INIT.register(EventListener::afterScreenChange);
//        ClientTickEvents.END_CLIENT_TICK.register(EventListener::onClientTick);
//    }
}

