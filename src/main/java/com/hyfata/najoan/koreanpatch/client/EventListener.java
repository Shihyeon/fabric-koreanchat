package com.hyfata.najoan.koreanpatch.client;

import com.hyfata.najoan.koreanpatch.plugin.InputController;
import com.hyfata.najoan.koreanpatch.plugin.InputManager;
import com.hyfata.najoan.koreanpatch.util.ModLogger;
import com.hyfata.najoan.koreanpatch.util.ReflectionFieldChecker;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.JigsawBlockScreen;
import net.minecraft.client.gui.screen.ingame.StructureBlockScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.SelectionManager;

import java.util.Arrays;

public class EventListener {
    protected static void onClientStarted(MinecraftClient client) {
        InputManager.applyController(InputController.newController());
    }

    protected static void afterScreenChange(MinecraftClient client, Screen screen, int scaledWidth, int scaledHeight) {
        if (client.currentScreen != null) {
            ModLogger.debug("Current screen: " + client.currentScreen);

            // injection bypass screens
            Class<?>[] bypassScreens = {JigsawBlockScreen.class, StructureBlockScreen.class};
            KoreanPatchClient.bypassInjection = Arrays.stream(bypassScreens)
                    .anyMatch(cls -> cls.isInstance(client.currentScreen));

            // IME set focus
            if (InputManager.getController() != null) {
                boolean hasTextFieldWidget = ReflectionFieldChecker.hasFieldOfType(client.currentScreen, TextFieldWidget.class);
                boolean hasSelectionManager = ReflectionFieldChecker.hasFieldOfType(client.currentScreen, SelectionManager.class);
                InputManager.getController().setFocus(!hasTextFieldWidget && !hasSelectionManager);
            }
        }
    }

    protected static void onClientTick(MinecraftClient client) {
        if (InputManager.getController() == null) return;

        if (client.currentScreen == null) {
            InputManager.getController().setFocus(false);
        }
    }
}
