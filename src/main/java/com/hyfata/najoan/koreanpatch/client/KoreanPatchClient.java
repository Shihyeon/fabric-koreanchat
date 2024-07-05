package com.hyfata.najoan.koreanpatch.client;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.win32.StdCallLibrary;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.glfw.GLFW;

@Environment(value=EnvType.CLIENT)
public class KoreanPatchClient
implements ClientModInitializer {
    public static boolean gameTab = false;
    public static int KEYCODE = 346;
    public static int SCANCODE = 498;

    public void onInitializeClient() {
        if (Platform.isWindows()) {
            Imm32.INSTANCE.ImmDisableIME(-1);
            KEYCODE = GLFW.GLFW_KEY_RIGHT_ALT;
        }
        else {
            KEYCODE = GLFW.GLFW_KEY_LEFT_CONTROL;
        }


    }

    public interface Imm32
    extends StdCallLibrary {
        Imm32 INSTANCE = Native.load("Imm32", Imm32.class);

        void ImmDisableIME(int var1);
    }
}

