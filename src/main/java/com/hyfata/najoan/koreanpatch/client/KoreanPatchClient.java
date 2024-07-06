package com.hyfata.najoan.koreanpatch.client;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.win32.StdCallLibrary;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(value=EnvType.CLIENT)
public class KoreanPatchClient
implements ClientModInitializer {

    public static KeyBinding koreanpatchKeyBinding;
    public static boolean gameTab = false;
    public static int KEYCODE = 346;

    public void onInitializeClient() {

        if (Platform.isWindows()) {
            Imm32.INSTANCE.ImmDisableIME(-1);
            KEYCODE = GLFW.GLFW_KEY_RIGHT_ALT;
        }
        else {
            KEYCODE = GLFW.GLFW_KEY_LEFT_CONTROL;
        }

        koreanpatchKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "koreanpatch.key.toggle_langtype",
                InputUtil.Type.KEYSYM,
                KEYCODE,
                "koreanpatch.key.categories"
        ));
    }

    public interface Imm32
    extends StdCallLibrary {
        Imm32 INSTANCE = Native.load("Imm32", Imm32.class);

        void ImmDisableIME(int var1);
    }
}

