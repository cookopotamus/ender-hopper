package com.cookopotamus.enderhopper;

import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class EnderHopperBlock extends HopperBlock {

  public EnderHopperBlock(Settings settings) {
    super(settings);
  }

  @Override
  public BlockEntity createBlockEntity(BlockView blockView) {
    return new EnderHopperBlockEntity();
  }

  @Override
  public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
    super.onPlaced(world, pos, state, placer, itemStack);
    EnderHopperBlockEntity blockEntity = (EnderHopperBlockEntity) world.getBlockEntity(pos);

    if (itemStack.hasCustomName()) {
      if (blockEntity instanceof EnderHopperBlockEntity) {
        blockEntity.setCustomName(itemStack.getName());
      }
    }

    if (placer instanceof PlayerEntity) {
      blockEntity.setOwner(placer.getUuid());
    }
  }

  @Override
  public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
      BlockHitResult hit) {
    if (world.isClient()) {
      return ActionResult.SUCCESS;
    } else {
      BlockEntity blockEntity = world.getBlockEntity(pos);

      if (blockEntity instanceof EnderHopperBlockEntity) {
        player.openHandledScreen((EnderHopperBlockEntity) blockEntity);
        player.incrementStat(Stats.INSPECT_HOPPER);
      }

      return ActionResult.CONSUME;
    }
  }

  @Override
  public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
    if (!state.isOf(newState.getBlock())) {
      BlockEntity blockEntity = world.getBlockEntity(pos);
      if (blockEntity instanceof EnderHopperBlockEntity) {
        ItemScatterer.spawn(world, (BlockPos) pos, (Inventory) ((EnderHopperBlockEntity) blockEntity));
        world.updateComparators(pos, this);
      }

      super.onStateReplaced(state, world, pos, newState, moved);
    }
  }

  @Override
  public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
    BlockEntity blockEntity = world.getBlockEntity(pos);
    if (blockEntity instanceof EnderHopperBlockEntity) {
      ((EnderHopperBlockEntity) blockEntity).onEntityCollided(entity);
    }
  }
}
