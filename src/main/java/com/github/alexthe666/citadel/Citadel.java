package com.github.alexthe666.citadel;

import com.github.alexthe666.citadel.config.ConfigHolder;
import com.github.alexthe666.citadel.config.ServerConfig;
import com.github.alexthe666.citadel.item.ItemCitadelBook;
import com.github.alexthe666.citadel.item.ItemCitadelDebug;
import com.github.alexthe666.citadel.item.ItemCustomRender;
import com.github.alexthe666.citadel.item.component.CustomRenderDisplay;
import com.github.alexthe666.citadel.server.CitadelEvents;
import com.github.alexthe666.citadel.server.block.CitadelLecternBlock;
import com.github.alexthe666.citadel.server.block.CitadelLecternBlockEntity;
import com.github.alexthe666.citadel.server.block.LecternBooks;
import com.github.alexthe666.citadel.server.generation.VillageHouseManager;
import com.github.alexthe666.citadel.server.message.*;
import com.github.alexthe666.citadel.web.WebHelper;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeConfigRegistry;
import fuzs.forgeconfigapiport.fabric.api.neoforge.v4.NeoForgeModConfigEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.fml.config.ModConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class Citadel implements ModInitializer {

    public static final String MOD_ID = "citadel";
    public static final Logger LOGGER = LogManager.getLogger("citadel");

    public static ServerProxy PROXY = unsafeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    public static List<String> PATREONS = new ArrayList<>();

    public static final Supplier<Item> DEBUG_ITEM = register("debug", new ItemCitadelDebug(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> CITADEL_BOOK = register("citadel_book", new ItemCitadelBook(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> EFFECT_ITEM = register("effect_item", new ItemCustomRender(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> FANCY_ITEM = register("fancy_item", new ItemCustomRender(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ICON_ITEM = register("icon_item", new ItemCustomRender(new Item.Properties().stacksTo(1)));

    public static final Supplier<DataComponentType<CustomRenderDisplay>> CUSTOM_RENDER_DISPLAY = registerComponentType("custom_render_display", builder -> builder.persistent(CustomRenderDisplay.CODEC));

    public static final Supplier<DataComponentType<ResourceLocation>> ICON_LOCATION = registerComponentType("icon_location", builder -> builder.persistent(ResourceLocation.CODEC).networkSynchronized(ResourceLocation.STREAM_CODEC));
    public static final Supplier<DataComponentType<ResourceKey<MobEffect>>> DISPLAY_EFFECT = registerComponentType("display_effect", builder -> builder.persistent(ResourceKey.codec(Registries.MOB_EFFECT)).networkSynchronized(ResourceKey.streamCodec(Registries.MOB_EFFECT)));
    public static final Supplier<Block> LECTERN = registerBlock("lectern", new CitadelLecternBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.LECTERN)));

    public static final Supplier<BlockEntityType<CitadelLecternBlockEntity>> LECTERN_BE = registerBlockEntity("lectern", BlockEntityType.Builder.of(CitadelLecternBlockEntity::new, LECTERN.get()).build(null));

    // TODO ender
//    public Citadel(ModContainer modContainer, IEventBus bus) {
//        final DeferredRegister<MapCodec<? extends BiomeModifier>> serializers = DeferredRegister.create(NeoForgeRegistries.BIOME_MODIFIER_SERIALIZERS, "citadel");
//        serializers.register(bus);
//        serializers.register("mob_spawn_probability", SpawnProbabilityModifier::makeCodec);
//        // Only register ClientProxy to event bus - ServerProxy has no @SubscribeEvent methods
//        if (FMLEnvironment.dist.isClient()) {
//            NeoForge.EVENT_BUS.register(PROXY);
//        }
//        NeoForge.EVENT_BUS.register(new CitadelEvents());
//        // Register NeoForge bus events (non-mod lifecycle events)
//        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, Citadel::onServerAboutToStart);
//    }

    @Override
    public void onInitialize() {
        NeoForgeConfigRegistry.INSTANCE.register(MOD_ID, ModConfig.Type.COMMON, ConfigHolder.SERVER_SPEC);
        NeoForgeModConfigEvents.reloading(MOD_ID).register(Citadel::onModConfigEvent);
        CitadelEvents.init();
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            VillageHouseManager.addAllHouses(server.registryAccess());
        });
        registerPayloads();

        LecternBooks.init();
        BufferedReader urlContents = WebHelper.getURLContents("https://raw.githubusercontent.com/Alex-the-666/Citadel/master/src/main/resources/assets/citadel/patreon.txt", "assets/citadel/patreon.txt");
        if (urlContents != null) {
            try {
                String line;
                while ((line = urlContents.readLine()) != null) {
                    PATREONS.add(line);
                }
                return;
            } catch (IOException ignored) {
            }
        }

        LOGGER.warn("Failed to load patreon contributor perks");
    }

    public static void onModConfigEvent(ModConfig config) {
        // Rebake the configs when they change
        ServerConfig.skipWarnings = ConfigHolder.SERVER.skipDatapackWarnings.get();
        if (config.getSpec() == ConfigHolder.SERVER_SPEC) {
            ServerConfig.citadelEntityTrack = ConfigHolder.SERVER.citadelEntityTracker.get();
            ServerConfig.chunkGenSpawnModifierVal = ConfigHolder.SERVER.chunkGenSpawnModifier.get();
            ServerConfig.aprilFools = ConfigHolder.SERVER.aprilFoolsContent.get();
            //citadelTestBiomeData = SpawnBiomeConfig.create(ResourceLocation.parse("citadel:config_biome"), CitadelBiomeDefinitions.TERRALITH_TEST);
        }
    }

    public static void registerPayloads() {
        // PropertiesMessage is bidirectional - used by both client (GUI) and server (entity utils)
        PayloadTypeRegistry.playC2S().register(PropertiesMessage.TYPE, PropertiesMessage.CODEC);
        PayloadTypeRegistry.playS2C().register(PropertiesMessage.TYPE, PropertiesMessage.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(PropertiesMessage.TYPE, (message, ctx) -> PropertiesMessage.handle(message, ctx.player()));
        // AnimationMessage is sent from server to all clients via sendToAllPlayers
        PayloadTypeRegistry.playS2C().register(AnimationMessage.TYPE, AnimationMessage.CODEC);
        // DanceJukeboxMessage is sent from client to server via sendToServer
        PayloadTypeRegistry.playS2C().register(DanceJukeboxMessage.TYPE, DanceJukeboxMessage.CODEC);
        // SyncePathMessage is sent from server to specific player via sendToPlayer
        PayloadTypeRegistry.playS2C().register(SyncePathMessage.TYPE, SyncePathMessage.CODEC);
        // SyncPathReachedMessage is sent from server to specific player via sendToPlayer
        PayloadTypeRegistry.playS2C().register(SyncPathReachedMessage.TYPE, SyncPathReachedMessage.CODEC);
    }

    private static <T> T unsafeRunForDist(Supplier<Supplier<T>> clientTarget, Supplier<Supplier<T>> serverTarget) {
        return switch (FabricLoader.getInstance().getEnvironmentType()) {
            case CLIENT -> clientTarget.get().get();
            case SERVER -> serverTarget.get().get();
        };
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static Supplier<Item> register(String name, Item item) {
        Item registered = Registry.register(BuiltInRegistries.ITEM, id(name), item);
        return () -> registered;
    }

    public static <T> Supplier<DataComponentType<T>> registerComponentType(String name, Function<DataComponentType.Builder<T>, DataComponentType.Builder<T>> builder) {
        DataComponentType<T> registered = Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, id(name), builder.apply(DataComponentType.builder()).build());
        return () -> registered;
    }

    public static Supplier<Block> registerBlock(String name, Block block) {
        Block registered = Registry.register(BuiltInRegistries.BLOCK, id(name), block);
        return () -> registered;
    }

    public static <T extends BlockEntity> Supplier<BlockEntityType<T>> registerBlockEntity(String name, BlockEntityType<T> blockEntityType) {
        BlockEntityType<T> registered = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id(name), blockEntityType);
        return () -> registered;
    }
}