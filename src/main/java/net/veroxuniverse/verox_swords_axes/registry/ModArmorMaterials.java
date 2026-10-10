package net.veroxuniverse.verox_swords_axes.registry;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;

import java.util.EnumMap;
import java.util.List;

public final class ModArmorMaterials {

    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS =
            DeferredRegister.create(Registries.ARMOR_MATERIAL, SwordsNAxes.MOD_ID);

    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> DUELLIST_BASE = register("duellist_base",
            2, 6, 5, 2, 9, SoundEvents.ARMOR_EQUIP_IRON, 0.0F, 0.0F, Items.IRON_INGOT);
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> DUELLIST_DIAMOND = register("duellist_diamond",
            3, 8, 6, 3, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2.0F, 0.0F, Items.DIAMOND);
    public static final DeferredHolder<ArmorMaterial, ArmorMaterial> DUELLIST_NETHERITE = register("duellist_netherite",
            3, 8, 6, 3, 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F, Items.NETHERITE_INGOT);

    private ModArmorMaterials() {}

    private static DeferredHolder<ArmorMaterial, ArmorMaterial> register(String name, int boots, int chest, int legs, int helmet,
                                                                          int enchantability, Holder<SoundEvent> sound,
                                                                          float toughness, float knockbackResistance, ItemLike repair) {
        return ARMOR_MATERIALS.register(name, () -> new ArmorMaterial(
                Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                    map.put(ArmorItem.Type.BOOTS, boots);
                    map.put(ArmorItem.Type.LEGGINGS, legs);
                    map.put(ArmorItem.Type.CHESTPLATE, chest);
                    map.put(ArmorItem.Type.HELMET, helmet);
                    map.put(ArmorItem.Type.BODY, chest);
                }),
                enchantability,
                sound,
                () -> Ingredient.of(repair),
                List.of(new ArmorMaterial.Layer(SwordsNAxes.id(name))),
                toughness,
                knockbackResistance));
    }
}
