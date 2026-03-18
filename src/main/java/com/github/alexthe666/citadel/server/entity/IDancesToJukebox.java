package com.github.alexthe666.citadel.server.entity;

import net.minecraft.core.BlockPos;

public interface IDancesToJukebox {

    void setDancing(boolean dancing);

    void setJukeboxPos(BlockPos pos);

    default void onClientPlayMusicDisc(int entityId, BlockPos pos, boolean dancing) {
        // TODO ender
//        PacketDistributor.sendToServer(new DanceJukeboxMessage(entityId, dancing, pos));
        this.setDancing(dancing);
        if (dancing) {
            this.setJukeboxPos(pos);
        }
        else {
            this.setJukeboxPos(null);
        }
    }
}
