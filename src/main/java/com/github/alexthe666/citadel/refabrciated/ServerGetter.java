package com.github.alexthe666.citadel.refabrciated;

import net.minecraft.server.MinecraftServer;

public abstract class ServerGetter {
    static MinecraftServer MINECRAFT_SERVER = null;

    public static MinecraftServer getServer() {
        return MINECRAFT_SERVER;
    }

    public static void setServer(MinecraftServer server) {
        MINECRAFT_SERVER = server;
    }
}
