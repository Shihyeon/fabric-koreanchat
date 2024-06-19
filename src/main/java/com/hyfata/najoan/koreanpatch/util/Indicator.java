package com.hyfata.najoan.koreanpatch.util;

import com.hyfata.najoan.koreanpatch.client.KoreanPatchClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class Indicator {
    static MinecraftClient client = MinecraftClient.getInstance();
    static Text KOREAN = Text.translatable("koreanpatch.langtype.korean");
    static Text ENGLISH = Text.translatable("koreanpatch.langtype.english");

    public static void showIndicator(DrawContext context, int x, int y, boolean center) {
        int rgb = 0x000000;
        int textOpacity = 50 * 255/100; // N% * (0 to 255)/100, default 50%
        int backgroundColor = ((textOpacity & 0xFF) << 24) | rgb;

        int frameColor;
        int len;
        Text lang;

        if (KoreanPatchClient.KOREAN) {
            lang = KOREAN;
            len = 2 + client.textRenderer.getWidth(lang);
            frameColor = -65536;
        } else {
            lang = ENGLISH;
            len = 2 + client.textRenderer.getWidth(lang);
            frameColor = -16711936;
        }

        if (center) {
            x -= len / 2;
            y -= 2;
        }

        drawLabel(context, x+1 -1, y+1 -1, x+len+1 +1, y+1+10 +1, frameColor, backgroundColor);
        drawCenteredText(context, lang, x+len/2 +1, y+1 +1);
    }

    private static void drawCenteredText(DrawContext context, Text text, int x, int y) {
        context.drawText(client.textRenderer, text, x - client.textRenderer.getWidth(text) / 2, y, -1, false);
    }

    private static void drawLabel(DrawContext context, int x1, int y1, int x2, int y2, int frameColor, int backgroundColor) {
        context.fill(x1, y1, x2, y1+1, frameColor); // frame with fixed axis-y1
        context.fill(x1, y2, x2, y2-1, frameColor); // frame with fixed axis-y2
        context.fill(x1, y1, x1+1, y2, frameColor); // frame with fixed axis-x1
        context.fill(x2, y1, x2-1, y2, frameColor); // frame with fixed axis-x2

        context.fill(x1+1, y1+1, x2, y2, backgroundColor); // Background
    }
}
