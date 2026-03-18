package com.github.alexthe666.citadel.client.event;

import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.entity.Entity;

public class EventGetOutlineColor {
    private Entity entityIn;
    private int color;
    private TriState result = TriState.DEFAULT;

    public EventGetOutlineColor(Entity entityIn, int color) {
        this.entityIn = entityIn;
        this.color = color;
    }

    public Entity getEntityIn() {
        return entityIn;
    }

    public void setEntityIn(Entity entityIn) {
        this.entityIn = entityIn;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setResult(TriState result) {
        this.result = result;
    }

    public TriState getResult() {
        return result;
    }
}
