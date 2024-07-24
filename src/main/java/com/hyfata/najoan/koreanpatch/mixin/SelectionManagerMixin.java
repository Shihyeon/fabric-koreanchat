package com.hyfata.najoan.koreanpatch.mixin;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.hyfata.najoan.koreanpatch.client.KoreanPatchClient;
import com.hyfata.najoan.koreanpatch.util.mixin.selectionmanager.ISelectionManagerAccessor;
import com.hyfata.najoan.koreanpatch.util.mixin.selectionmanager.SelectionManagerHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.SelectionManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = {SelectionManager.class})
public abstract class SelectionManagerMixin implements ISelectionManagerAccessor {
    @Shadow
    private int selectionEnd;
    @Shadow
    @Final
    private Supplier<String> stringGetter;
    @Shadow
    @Final
    private Predicate<String> stringFilter;
    @Shadow
    @Final
    private Consumer<String> stringSetter;

    @Shadow
    protected abstract String getSelectedText(String string);

    @Shadow
    protected abstract void insert(String string, String insertion);

    @Unique
    private final SelectionManagerHandler handler = new SelectionManagerHandler(this);

    @Override
    public int fabric_koreanchat$getCursor() {
        return this.selectionEnd;
    }

    @Override
    public Supplier<String> fabric_koreanchat$getStringGetter() {
        return this.stringGetter;
    }

    @Override
    public Predicate<String> fabric_koreanchat$getStringFilter() {
        return this.stringFilter;
    }

    @Override
    public Consumer<String> fabric_koreanchat$getStringSetter() {
        return this.stringSetter;
    }

    @Override
    public String fabric_koreanchat$selectedText(String string) {
        return getSelectedText(string);
    }

    @Override
    public void fabric_koreanchat$runInsert(String string, String insertion) {
        insert(string, insertion);
    }

    @Inject(at = {@At(value = "HEAD")}, method = {"insert(C)Z"}, cancellable = true)
    public void insertChar(char chr, CallbackInfoReturnable<Boolean> cir) {
        handler.insertChar(chr, cir);
    }

    @Inject(at = {@At(value = "HEAD")}, method = {"insert(Ljava/lang/String;)V"}, cancellable = true)
    public void insertString(String string, CallbackInfo ci) {
        handler.insertString(string, ci);
    }

    @Inject(at = {@At(value = "HEAD")}, method = {"delete(I)V"}, cancellable = true)
    public void delete(int offset, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.currentScreen != null && !KoreanPatchClient.bypassInjection) {
            if (handler.onBackspaceKeyPressed()) {
                ci.cancel();
            }
        }
    }
}

