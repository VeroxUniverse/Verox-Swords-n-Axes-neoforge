package net.veroxuniverse.verox_swords_axes.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;
import net.veroxuniverse.verox_swords_axes.client.render.ForgeAnvilRenderer;
import net.veroxuniverse.verox_swords_axes.registry.ModBlockEntities;

@EventBusSubscriber(modid = SwordsNAxes.MOD_ID, value = Dist.CLIENT)
public final class ClientModEvents {

    private ClientModEvents() {}

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.FORGE_ANVIL.get(), ForgeAnvilRenderer::new);
    }
}
