package com.github.alexthe666.citadel.server;

import com.github.alexthe666.citadel.server.tick.ServerTickRateTracker;
import com.github.alexthe666.citadel.server.world.CitadelServerData;
import com.github.alexthe666.citadel.server.world.ModifiableTickRateServer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;

public class CitadelEvents {

    private int updateTimer;

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(CitadelEvents::onServerTick);
    }

    // TODO ender
//    @SubscribeEvent
//    public void onEntityUpdateDebug(EntityTickEvent.Post event) {
//        if (CitadelConstants.DEBUG) {
//            if (event.getEntity() instanceof Player player) {
//                CompoundTag tag = CitadelEntityData.getCitadelTag(player);
//                tag.putInt("CitadelInt", tag.getInt("CitadelInt") + 1);
//                Citadel.LOGGER.debug("Citadel Data Tag tracker example: {}", tag.getInt("CitadelInt"));
//            }
//        }
//    }

    // TODO ender
//    @SubscribeEvent
//    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
//        if (event.getLevel().getBlockState(event.getPos()).is(Blocks.LECTERN) && LecternBooks.isLecternBook(event.getItemStack())) {
//            event.getEntity().getCooldowns().addCooldown(event.getItemStack().getItem(), 1);
//            BlockState oldLectern = event.getLevel().getBlockState(event.getPos());
//            if (event.getLevel().getBlockEntity(event.getPos()) instanceof LecternBlockEntity oldBe && !oldBe.hasBook()) {
//                BlockState newLectern = Citadel.LECTERN.get().defaultBlockState().setValue(CitadelLecternBlock.FACING, oldLectern.getValue(LecternBlock.FACING)).setValue(CitadelLecternBlock.POWERED, oldLectern.getValue(LecternBlock.POWERED)).setValue(CitadelLecternBlock.HAS_BOOK, true);
//                event.getLevel().setBlockAndUpdate(event.getPos(), newLectern);
//                CitadelLecternBlockEntity newBe = new CitadelLecternBlockEntity(event.getPos(), newLectern);
//                ItemStack bookCopy = event.getItemStack().copy();
//                bookCopy.setCount(1);
//                newBe.setBook(bookCopy);
//                if (!event.getEntity().isCreative()) {
//                    event.getItemStack().shrink(1);
//                }
//                event.getLevel().setBlockEntity(newBe);
//                event.getEntity().swing(event.getHand(), true);
//                event.getLevel().playSound(null, event.getPos(), SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1.0F, 1.0F);
//            }
//        }
//    }

    // TODO ender
//    @SubscribeEvent
//    public void onPlayerClone(PlayerEvent.Clone event) {
//        if (CitadelEntityData.getCitadelTag(event.getOriginal()) != null) {
//            CitadelEntityData.setCitadelTag(event.getEntity(), CitadelEntityData.getCitadelTag(event.getOriginal()));
//        }
//    }

    public static void onServerTick(MinecraftServer server) {
        if (server.isRunning()) {
            ServerTickRateTracker tickRateTracker = CitadelServerData.get(server).getOrCreateTickRateTracker();
            if (server instanceof ModifiableTickRateServer modifiableServer) {
                long l = tickRateTracker.getServerTickLengthMs();
                if (l == ServerTickRateTracker.MS_PER_TICK) {
                    modifiableServer.resetGlobalTickLengthMs();
                }
                else {
                    modifiableServer.setGlobalTickLengthMs(tickRateTracker.getServerTickLengthMs());
                }
                if (!server.isShutdown()) {
                    tickRateTracker.masterTick();
                }
            }
        }
    }
}
