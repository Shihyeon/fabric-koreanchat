package com.hyfata.najoan.koreanpatch.arch.win;

import com.hyfata.najoan.koreanpatch.client.KoreanPatchClient;
import com.hyfata.najoan.koreanpatch.plugin.InputController;
import com.hyfata.najoan.koreanpatch.util.ModLogger;
import net.minecraft.client.MinecraftClient;

public class WinController implements InputController {
    private boolean focus = false;

    @Override
    public void setFocus(boolean focus) {
        if (this.focus == focus) {
            return;
        }
        this.focus = focus;
        KoreanPatchClient.IME = focus;
        WinHandle.INSTANCE.set_focus(focus ? 1 : 0);
    }

    @Override
    public void toggleFocus() {
        setFocus(!focus);
    }

    WinHandle.PreeditCallback pc = (str, cursor, length) -> {};
    WinHandle.DoneCallback dc = (str) -> {};
    WinHandle.RectCallback rc = ret -> 1;

    public WinController() {
        WinHandle.INSTANCE.initialize(org.lwjgl.glfw.GLFWNativeWin32.glfwGetWin32Window(MinecraftClient.getInstance().getWindow().getHandle()), pc, dc,rc, (log) -> ModLogger.log("[Native|C] " + log), (log) -> ModLogger.error("[Native|C] " + log), (log) -> ModLogger.debug("[Native|C] " + log));
    }
}
