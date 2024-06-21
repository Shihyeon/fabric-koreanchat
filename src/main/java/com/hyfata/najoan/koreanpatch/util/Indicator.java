package com.hyfata.najoan.koreanpatch.util;

import com.hyfata.najoan.koreanpatch.client.KoreanPatchClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.text.Text;
import org.joml.Matrix4f;

public class Indicator {
    static MinecraftClient client = MinecraftClient.getInstance();
    static Text KOREAN = Text.translatable("koreanpatch.langtype.korean");
    static Text ENGLISH = Text.translatable("koreanpatch.langtype.english");

    public static void showIndicator(DrawContext context, float x, float y, boolean center) {
        int rgb = 0x000000;
        int textOpacity = 55 * 255/100; // N% * (0 to 255)/100, default 50%
        int backgroundColor = ((textOpacity & 0xFF) << 24) | rgb;

        int frameColor;
        float len;
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

        drawLabel(context, x, y, x+len+1 +1, y+1+10 +1, frameColor, backgroundColor);
        drawCenteredText(context, lang, x+len/2 +1, y+1 +1);
    }

    private static void drawCenteredText(DrawContext context, Text text, float x, float y) {
        TextRenderer textRenderer = client.textRenderer;
        float textWidth = (float) textRenderer.getWidth(text);
        float xPosition = x - textWidth / 2.0f;

        Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();
        VertexConsumerProvider vertexConsumers = context.getVertexConsumers();

        textRenderer.draw(text, xPosition, y, -1, false, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
    }

    private static void drawLabel(DrawContext context, float x1, float y1, float x2, float y2, int frameColor, int backgroundColor) {
        fill(context, x1, y1, x2, y1+1, frameColor); // frame with fixed axis-y1
        fill(context, x1, y2, x2, y2-1, frameColor); // frame with fixed axis-y2
        fill(context, x1, y1, x1+1, y2, frameColor); // frame with fixed axis-x1
        fill(context, x2, y1, x2-1, y2, frameColor); // frame with fixed axis-x2

        fill(context, x1+1, y1+1, x2-1, y2-1, backgroundColor); // Background
    }

    private static void fill(DrawContext context, float x1, float y1, float x2, float y2, int color) {
        Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();
        float i;
        if (x1 < x2) {
            i = x1;
            x1 = x2;
            x2 = i;
        }

        if (y1 < y2) {
            i = y1;
            y1 = y2;
            y2 = i;
        }

        VertexConsumer vertexConsumer = context.getVertexConsumers().getBuffer(RenderLayer.getGui());
        vertexConsumer.vertex(matrix, x1, y1, 0f).color(color);
        vertexConsumer.vertex(matrix, x1, y2, 0f).color(color);
        vertexConsumer.vertex(matrix, x2, y2, 0f).color(color);
        vertexConsumer.vertex(matrix, x2, y1, 0f).color(color);
        context.draw();
    }
}
