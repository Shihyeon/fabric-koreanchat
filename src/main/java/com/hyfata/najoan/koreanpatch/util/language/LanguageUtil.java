package com.hyfata.najoan.koreanpatch.util.language;

import com.hyfata.najoan.koreanpatch.client.KoreanPatchClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;

public class LanguageUtil {
    public static final int EN = 0;
    public static final int KO = 1;
    private static int currentType = EN;

    static MinecraftClient client = MinecraftClient.getInstance();
    static Text KO_TEXT = Text.translatable("koreanpatch.langtype.korean");
    static Text EN_TEXT = Text.translatable("koreanpatch.langtype.english");

    public static int getCurrentType() {
        return currentType;
    }

    public static boolean isKorean() {
        return getCurrentType() == KO;
    }

    public static void setCurrentType(int currentType) {
        LanguageUtil.currentType = currentType;
    }

    public static void toggleCurrentType() {
        if (isKorean()) {
            setCurrentType(EN);
        } else {
            setCurrentType(KO);
        }
    }
    
    public static OrderedText getCurrentText() {
        if (KoreanPatchClient.IME) {
            return Text.literal("IME").asOrderedText();
        }
        return switch (currentType) {
            case EN -> EN_TEXT.asOrderedText();
            case KO -> KO_TEXT.asOrderedText();
            default -> throw new IllegalStateException("Unexpected value: " + currentType);
        };
    }

    public static int getCurrentTextWidth() {
        return client.textRenderer.getWidth(getCurrentText());
    }
}
