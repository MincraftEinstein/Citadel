package com.github.alexthe666.citadel.server.event;

import com.github.alexthe666.citadel.refabrciated.event.Event;
import com.github.alexthe666.citadel.refabrciated.event.ICancellableEvent;
import net.minecraft.world.entity.Entity;

public class EventChangeEntityTickRate extends Event implements ICancellableEvent {
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
}
