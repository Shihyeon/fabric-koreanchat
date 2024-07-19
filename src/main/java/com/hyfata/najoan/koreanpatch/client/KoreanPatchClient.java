package com.hyfata.najoan.koreanpatch.client;

import com.hyfata.najoan.koreanpatch.plugin.InputController;
import com.hyfata.najoan.koreanpatch.util.ModLogger;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;

@Environment(value = EnvType.CLIENT)
public class KoreanPatchClient
        implements ClientModInitializer {

    public static final boolean DEBUG = true;
    private static InputController controller;
    public static KeyBinding langBinding, imeBinding;
    public static int KEYCODE = 346;
    public static boolean IME = false;

    public static boolean gameTab = false;

    public void onInitializeClient() {
        registerEvents();

        if (Platform.isWindows()) {
            KEYCODE = GLFW.GLFW_KEY_RIGHT_ALT;
        } else {
            KEYCODE = GLFW.GLFW_KEY_LEFT_CONTROL;
        }

        langBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.koreanpatch.toggle_langtype",
                InputUtil.Type.KEYSYM,
                KEYCODE,
                "key.categories.koreanpatch"
        ));

        imeBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.koreanpatch.toggle_ime",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                "key.categories.koreanpatch"
        ));
    }

    public void registerEvents() {
        // register Input Controller
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> KoreanPatchClient.applyController(InputController.getController()));

        // on Update Screen
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            System.out.println("Screen opened: " + screen.getClass().getName());
            if (KoreanPatchClient.getController() != null) {
                KoreanPatchClient.getController().setFocus(true);
            }
            // TODO: add allow/disallow screens
        });

        // In-Game
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.currentScreen == null) {
                if (KoreanPatchClient.getController() != null)
                    KoreanPatchClient.getController().setFocus(false);
            }
        });
    }

    public static void applyController(InputController controller) {
        KoreanPatchClient.controller = controller;
    }

    public static InputController getController() {
        return controller;
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

