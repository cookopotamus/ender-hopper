package com.cookopotamus.enderhopper;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EnderHopperMod implements ModInitializer {
    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "enderhopper";
    public static final String MOD_NAME = "enderhopper";

    public static final EnderHopperBlock ENDER_HOPPER = new EnderHopperBlock(
            FabricBlockSettings.of(Material.METAL).hardness(8f).resistance(4).requiresTool().breakByHand(false));
    public static BlockEntityType<EnderHopperBlockEntity> ENDER_HOPPER_ENTITY;

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing...");

        Registry.register(Registry.BLOCK, new Identifier("enderhopper", "ender_hopper"), ENDER_HOPPER);
        Registry.register(Registry.ITEM, new Identifier("enderhopper", "ender_hopper"),
                new BlockItem(ENDER_HOPPER, new Item.Settings().group(ItemGroup.INVENTORY)));
        ENDER_HOPPER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier("enderhopper", "ender_hopper"),
                BlockEntityType.Builder.create(EnderHopperBlockEntity::new, ENDER_HOPPER).build(null));
    }

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }
}
