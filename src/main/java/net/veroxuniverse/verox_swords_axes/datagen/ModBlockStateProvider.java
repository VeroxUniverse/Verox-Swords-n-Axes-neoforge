package net.veroxuniverse.verox_swords_axes.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, SwordsNAxes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
    }
}