package net.veroxuniverse.verox_swords_axes.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.veroxuniverse.unbound_lib.registry.LibCreativeTabs;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;

import java.util.Set;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = SwordsNAxes.MOD_ID)
public final class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, SwordsNAxes.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN = TABS.register("main",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + SwordsNAxes.MOD_ID))
                    .icon(() -> ModItems.SHORT_SWORD.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        Set<Item> upgrades = upgradeItems();
                        ModItems.ITEMS.getEntries().stream()
                                .map(holder -> (Item) holder.get())
                                .filter(item -> !upgrades.contains(item))
                                .forEach(output::accept);
                    })
                    .build());

    private ModCreativeTabs() {}

    @SubscribeEvent
    public static void onBuildTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != LibCreativeTabs.UPGRADES.getKey()) return;
        ModUpgradeItems.entries().forEach(entry -> event.accept(entry.item().get()));
    }

    private static Set<Item> upgradeItems() {
        return ModUpgradeItems.entries().stream()
                .map(entry -> (Item) entry.item().get())
                .collect(Collectors.toSet());
    }
}