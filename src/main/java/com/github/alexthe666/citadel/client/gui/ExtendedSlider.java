package com.github.alexthe666.citadel.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.text.DecimalFormat;

// TODO clean up this, cuz its compiled code
public class ExtendedSlider extends AbstractSliderButton {

    protected Component prefix;
    protected Component suffix;
    protected double minValue;
    protected double maxValue;
    protected double stepSize;
    protected boolean drawString;
    private final DecimalFormat format;

    public ExtendedSlider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double currentValue, double stepSize, int precision, boolean drawString) {
        super(x, y, width, height, Component.empty(), (double) 0.0F);
        prefix = prefix;
        suffix = suffix;
        minValue = minValue;
        maxValue = maxValue;
        stepSize = Math.abs(stepSize);
        value = snapToNearest((currentValue - minValue) / (maxValue - minValue));
        drawString = drawString;
        if (stepSize == (double) 0.0F) {
            precision = Math.min(precision, 4);
            StringBuilder builder = new StringBuilder("0");
            if (precision > 0) {
                builder.append('.');
            }

            while (precision-- > 0) {
                builder.append('0');
            }

            format = new DecimalFormat(builder.toString());
        }
        else if (Mth.equal(stepSize, Math.floor(stepSize))) {
            format = new DecimalFormat("0");
        }
        else {
            format = new DecimalFormat(Double.toString(stepSize).replaceAll("\\d", "0"));
        }

        updateMessage();
    }

    public ExtendedSlider(int x, int y, int width, int height, Component prefix, Component suffix, double minValue, double maxValue, double currentValue, boolean drawString) {
        this(x, y, width, height, prefix, suffix, minValue, maxValue, currentValue, (double) 1.0F, 0, drawString);
    }

    public double getValue() {
        return value * (maxValue - minValue) + minValue;
    }

    public long getValueLong() {
        return Math.round(getValue());
    }

    public int getValueInt() {
        return (int) getValueLong();
    }

    public void setValue(double value) {
        value = snapToNearest((value - minValue) / (maxValue - minValue));
        updateMessage();
    }

    public String getValueString() {
        return format.format(getValue());
    }

    public void onClick(double mouseX, double mouseY) {
        setValueFromMouse(mouseX);
    }

    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY) {
        super.onDrag(mouseX, mouseY, dragX, dragY);
        setValueFromMouse(mouseX);
    }

    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean flag = keyCode == 263;
        if (flag || keyCode == 262) {
            if (minValue > maxValue) {
                flag = !flag;
            }

            float f = flag ? -1.0F : 1.0F;
            if (stepSize <= (double) 0.0F) {
                setSliderValue(value + (double) (f / (float) (width - 8)));
            }
            else {
                setValue(getValue() + (double) f * stepSize);
            }
        }

        return false;
    }

    private void setValueFromMouse(double mouseX) {
        setSliderValue((mouseX - (double) (getX() + 4)) / (double) (width - 8));
    }

    private void setSliderValue(double value) {
        double oldValue = value;
        value = snapToNearest(value);
        if (!Mth.equal(oldValue, value)) {
            applyValue();
        }

        updateMessage();
    }

    private double snapToNearest(double value) {
        if (stepSize <= (double) 0.0F) {
            return Mth.clamp(value, (double) 0.0F, (double) 1.0F);
        }
        else {
            value = Mth.lerp(Mth.clamp(value, (double) 0.0F, (double) 1.0F), minValue, maxValue);
            value = stepSize * (double) Math.round(value / stepSize);
            if (minValue > maxValue) {
                value = Mth.clamp(value, maxValue, minValue);
            }
            else {
                value = Mth.clamp(value, minValue, maxValue);
            }

            return Mth.map(value, minValue, maxValue, (double) 0.0F, (double) 1.0F);
        }
    }

    protected void updateMessage() {
        if (drawString) {
            setMessage(Component.literal("").append(prefix).append(getValueString()).append(suffix));
        }
        else {
            setMessage(Component.empty());
        }

    }

    protected void applyValue() {
    }

    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        guiGraphics.blitSprite(getSprite(), getX(), getY(), getWidth(), getHeight());
        guiGraphics.blitSprite(getHandleSprite(), getX() + (int) (value * (double) (width - 8)), getY(), 8, getHeight());
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = active ? 16777215 : 10526880;
        renderScrollingString(guiGraphics, minecraft.font, 2, i | Mth.ceil(alpha * 255.0F) << 24);
    }
}
