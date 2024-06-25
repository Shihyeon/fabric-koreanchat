package com.hyfata.najoan.koreanpatch.util;

import org.lwjgl.glfw.GLFW;

public class AnimationUtil {
    float savedIndicatorX = 0;
    float savedAnimatedIndicatorX = 0;
    float animatedIndicatorX = 0;
    float animationTickTime = 0;

    public AnimationUtil(float init) {
        savedIndicatorX = init;
        savedAnimatedIndicatorX = init;
        animatedIndicatorX = init;
    }
    public AnimationUtil() {}

    public float getAnimatedX(float targetX, float animationDuration) {
        if (targetX != savedIndicatorX) {
            savedIndicatorX = targetX;
            animationTickTime = (float) GLFW.glfwGetTime();
            savedAnimatedIndicatorX = animatedIndicatorX;
        }
        if (GLFW.glfwGetTime() - animationTickTime > animationDuration) {
            savedAnimatedIndicatorX = targetX;
        } else {
            animatedIndicatorX = savedAnimatedIndicatorX + (targetX - savedAnimatedIndicatorX) * EasingFunctions.easeOutQuint((float)(GLFW.glfwGetTime() - animationTickTime) / animationDuration);
            targetX = animatedIndicatorX;
        }
        return targetX;
    }
}
