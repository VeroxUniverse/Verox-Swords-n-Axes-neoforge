package net.veroxuniverse.verox_swords_axes.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;
import net.veroxuniverse.verox_swords_axes.registry.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SwordsNAxes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModelFile anvil = models().withExistingParent("forge_anvil", mcLoc("block/template_anvil"))
                .texture("particle", mcLoc("block/anvil"))
                .texture("body", mcLoc("block/anvil"))
                .texture("top", mcLoc("block/anvil_top"));
        horizontalBlock(ModBlocks.FORGE_ANVIL.get(), anvil, 0);
    }
}
