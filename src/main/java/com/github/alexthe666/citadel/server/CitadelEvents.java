package com.github.alexthe666.citadel.server;

import com.github.alexthe666.citadel.Citadel;
import com.github.alexthe666.citadel.CitadelConstants;
import com.github.alexthe666.citadel.server.block.CitadelLecternBlock;
import com.github.alexthe666.citadel.server.block.CitadelLecternBlockEntity;
import com.github.alexthe666.citadel.server.block.LecternBooks;
import com.github.alexthe666.citadel.server.entity.CitadelEntityData;
import com.github.alexthe666.citadel.server.tick.ServerTickRateTracker;
import com.github.alexthe666.citadel.server.world.CitadelServerData;
import com.github.alexthe666.citadel.server.world.ModifiableTickRateServer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

@SuppressWarnings("unused")
public class CitadelEvents {

    private int updateTimer;

    public static void init() {
        ServerTickEvents.END_SERVER_TICK.register(CitadelEvents::onServerTick);
        ServerPlayerEvents.COPY_FROM.register(CitadelEvents::onPlayerClone);
        UseBlockCallback.EVENT.register(CitadelEvents::onRightClickBlock);
    }

    //(ender) fix if need debug
    public void onEntityUpdateDebug(Entity event) {
        if (CitadelConstants.DEBUG) {
            if (event instanceof Player player) {
                CompoundTag tag = CitadelEntityData.getCitadelTag(player);
                tag.putInt("CitadelInt", tag.getInt("CitadelInt") + 1);
                Citadel.LOGGER.debug("Citadel Data Tag tracker example: {}", tag.getInt("CitadelInt"));
            }
        }
    }

    public static InteractionResult onRightClickBlock(Player entity, Level level, InteractionHand hand, BlockHitResult blockHitResult) {
        BlockPos pos = blockHitResult.getBlockPos();
        ItemStack itemStack = entity.getItemInHand(hand);
        if (level.getBlockState(pos).is(Blocks.LECTERN) && LecternBooks.isLecternBook(itemStack)) {
            entity.getCooldowns().addCooldown(itemStack.getItem(), 1);
            BlockState oldLectern = level.getBlockState(pos);
            if (level.getBlockEntity(pos) instanceof LecternBlockEntity oldBe && !oldBe.hasBook()) {
                BlockState newLectern = Citadel.LECTERN.get().defaultBlockState().setValue(CitadelLecternBlock.FACING, oldLectern.getValue(LecternBlock.FACING)).setValue(CitadelLecternBlock.POWERED, oldLectern.getValue(LecternBlock.POWERED)).setValue(CitadelLecternBlock.HAS_BOOK, true);
                level.setBlockAndUpdate(pos, newLectern);
                CitadelLecternBlockEntity newBe = new CitadelLecternBlockEntity(pos, newLectern);
                ItemStack bookCopy = itemStack.copy();
                bookCopy.setCount(1);
                newBe.setBook(bookCopy);
                if (!entity.isCreative()) {
                    itemStack.shrink(1);
                }
                level.setBlockEntity(newBe);
                entity.swing(hand, true);
                level.playSound(null, pos, SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    public static void onPlayerClone(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean alive) {
        if (CitadelEntityData.getCitadelTag(oldPlayer) != null) {
            CitadelEntityData.setCitadelTag(newPlayer, CitadelEntityData.getCitadelTag(oldPlayer));
        }
    }

    public static void onServerTick(MinecraftServer server) {
        if (server.isRunning()) {
            ServerTickRateTracker tickRateTracker = CitadelServerData.get(server).getOrCreateTickRateTracker();
            if (server instanceof ModifiableTickRateServer modifiableServer) {
                long l = tickRateTracker.getServerTickLengthMs();
                if (l == ServerTickRateTracker.MS_PER_TICK) {
                    modifiableServer.resetGlobalTickLengthMs();
                } else {
                    modifiableServer.setGlobalTickLengthMs(tickRateTracker.getServerTickLengthMs());
                }
                if (!server.isShutdown()) {
                    tickRateTracker.masterTick();
                }
            }
        }
    }
}
