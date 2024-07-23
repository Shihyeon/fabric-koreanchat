package com.hyfata.najoan.koreanpatch.client;

import com.sun.jna.Platform;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBinds {
    private static KeyBinding langBinding, imeBinding;

    public static void register() {
        int keycode;
        if (Platform.isWindows()) {
            keycode = GLFW.GLFW_KEY_RIGHT_ALT;
        } else {
            keycode = GLFW.GLFW_KEY_LEFT_CONTROL;
        }

        langBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.koreanpatch.toggle_langtype",
                InputUtil.Type.KEYSYM,
                keycode,
                "key.categories.koreanpatch"
        ));

        imeBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.koreanpatch.toggle_ime",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                "key.categories.koreanpatch"
        ));
    }

    public static KeyBinding getLangBinding() {
        return langBinding;
    }

    public static KeyBinding getImeBinding() {
        return imeBinding;
    }
}
