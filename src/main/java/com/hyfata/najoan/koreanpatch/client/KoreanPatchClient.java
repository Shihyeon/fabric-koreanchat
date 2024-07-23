package com.hyfata.najoan.koreanpatch.client;

import com.hyfata.najoan.koreanpatch.util.ModLogger;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;

@Environment(value = EnvType.CLIENT)
public class KoreanPatchClient
        implements ClientModInitializer {

    public static final boolean DEBUG = false;
    public static boolean IME = false;

    public static boolean gameTab = false;

    public void onInitializeClient() {
        registerEvents();
        KeyBinds.register();
    }

    public void registerEvents() {
        ClientLifecycleEvents.CLIENT_STARTED.register(EventListener::onClientStarted);
        ScreenEvents.AFTER_INIT.register(EventListener::afterScreenChange);
        ClientTickEvents.END_CLIENT_TICK.register(EventListener::onClientTick);
    }

    public static String copyLibrary(final String name) {
        try {
            final URL url = KoreanPatchClient.class.getClassLoader().getResource("native/" + name);
            if (url == null) {
                throw new IOException("Native library (" + name + ") not found.");
            }

            final File lib = File.createTempFile("koreanpatch", Platform.isWindows() ? ".dll" : null, tempDir());
            try (
                    final InputStream is = url.openStream();
                    final FileOutputStream fos = new FileOutputStream(lib)
            ) {
                ModLogger.debug("Extracting library to {}", lib.getAbsolutePath());
                fos.write(is.readAllBytes());
                lib.deleteOnExit();
            }

            ModLogger.log("CocoaInput Driver has copied library to native directory.");

            return lib.getAbsolutePath();
        } catch (final Exception exception) {
            ModLogger.error("An error occurred while loading the library.");
            throw new RuntimeException(exception);
        }
    }

    private static File tempDir() throws IOException {
        try {
            final Method method = Native.class.getDeclaredMethod("getTempDir");
            method.setAccessible(true);
            return (File) method.invoke(null);
        } catch (final Exception exception) {
            return File.createTempFile("native", "temp");
        }
    }
}

