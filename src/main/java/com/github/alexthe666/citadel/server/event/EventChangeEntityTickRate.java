package com.github.alexthe666.citadel.server.event;

import com.github.alexthe666.citadel.refabrciated.event.ICancellableEvent;
import net.minecraft.world.entity.Entity;

public class EventChangeEntityTickRate implements ICancellableEvent {
    private Entity entity;
    private float targetTickRate;

    public EventChangeEntityTickRate(Entity entity, float targetTickRate) {
        this.entity = entity;
        this.targetTickRate = targetTickRate;
    }

    public Entity getEntity() {
        return entity;
    }

    public float getTargetTickRate() {
        return targetTickRate;
    }

    private boolean canceled = false;

    @Override
    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public boolean isCanceled() {
        return canceled;
    }
}
