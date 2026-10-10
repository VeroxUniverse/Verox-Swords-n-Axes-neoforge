package net.veroxuniverse.verox_swords_axes.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;
import net.veroxuniverse.verox_swords_axes.block.entity.ForgeAnvilBlockEntity;

public final class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, SwordsNAxes.MOD_ID);

    @SuppressWarnings("DataFlowIssue")
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ForgeAnvilBlockEntity>> FORGE_ANVIL =
            BLOCK_ENTITIES.register("forge_anvil",
                    () -> BlockEntityType.Builder.of(ForgeAnvilBlockEntity::new, ModBlocks.FORGE_ANVIL.get()).build(null));

    private ModBlockEntities() {}
}
