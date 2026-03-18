package com.github.alexthe666.citadel.server.generation;

// TODO ender
//public class SpawnProbabilityModifier implements BiomeModifier {
//
//    @Override
//    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
//        float probability = (float) (ServerConfig.chunkGenSpawnModifierVal) * builder.getMobSpawnSettings().getProbability();
//        if (phase == Phase.MODIFY) {
//            builder.getMobSpawnSettings().creatureGenerationProbability(Mth.clamp(probability, 0F, 1F));
//        }
//    }
//
//    @Override
//    public MapCodec<? extends BiomeModifier> codec() {
//        return makeCodec();
//    }
//
//    public static MapCodec<SpawnProbabilityModifier> makeCodec() {
//        return MapCodec.unit(SpawnProbabilityModifier::new);
//    }
//}
