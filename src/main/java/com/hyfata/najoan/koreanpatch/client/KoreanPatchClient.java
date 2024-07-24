package com.hyfata.najoan.koreanpatch.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;

@Environment(value = EnvType.CLIENT)
public class KoreanPatchClient
        implements ClientModInitializer {

    public static final boolean DEBUG = false;
    public static boolean IME = false;
    public static boolean bypassInjection = false;

    public void onInitializeClient() {
        registerEvents();
        KeyBinds.register();
    }

    public void registerEvents() {
        ClientLifecycleEvents.CLIENT_STARTED.register(EventListener::onClientStarted);
        ScreenEvents.AFTER_INIT.register(EventListener::afterScreenChange);
        ClientTickEvents.END_CLIENT_TICK.register(EventListener::onClientTick);
    }
}

