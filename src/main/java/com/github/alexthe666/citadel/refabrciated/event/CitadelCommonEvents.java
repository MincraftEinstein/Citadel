package com.github.alexthe666.citadel.refabrciated.event;

import com.github.alexthe666.citadel.server.event.EventChangeEntityTickRate;
import com.github.alexthe666.citadel.server.event.EventMergeStructureSpawns;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;

public interface CitadelCommonEvents {
    Event<MergeStructureSpawns> MERGE_STRUCTURE_SPAWNS = EventFactory.createArrayBacked(MergeStructureSpawns.class, callbacks -> event -> {
        for (var callback : callbacks) {
            callback.event(event);
            if (event.getResult() == TriState.TRUE) return;
        }
    });

    @FunctionalInterface
    interface MergeStructureSpawns {
        void event(EventMergeStructureSpawns event);
    }

    Event<ChangeEntityTickRate> CHANGE_ENTITY_TICK_RATE = EventFactory.createArrayBacked(ChangeEntityTickRate.class, callbacks -> event -> {
        for (var callback : callbacks) {
            callback.event(event);
            if (event.isCanceled()) return;
        }
    });

    @FunctionalInterface
    interface ChangeEntityTickRate {
        void event(EventChangeEntityTickRate event);
    }

}
