package net.veroxuniverse.verox_swords_axes.item;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.veroxuniverse.unbound_lib.cosmetic.HidesPlayerCape;
import net.veroxuniverse.verox_swords_axes.client.render.DuellistArmorRenderer;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class DuellistArmorItem extends ArmorItem implements GeoItem, HidesPlayerCape {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final ArmorTier tier;

    public DuellistArmorItem(Holder<ArmorMaterial> material, Type type, ArmorTier tier, Properties properties) {
        super(material, type, properties);
        this.tier = tier;
    }

    public ArmorTier getTier() {
        return this.tier;
    }

    @Override
    public boolean hidesPlayerCape(ItemStack stack) {
        return this.getType() == Type.CHESTPLATE;
    }

    @Override
    public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
        consumer.accept(new GeoRenderProvider() {
            private DuellistArmorRenderer renderer;

            @Override
            public <E extends LivingEntity> HumanoidModel<?> getGeoArmorRenderer(@Nullable E livingEntity, ItemStack itemStack,
                                                                              @Nullable EquipmentSlot equipmentSlot,
                                                                              @Nullable HumanoidModel<E> original) {
                if (this.renderer == null) {
                    this.renderer = new DuellistArmorRenderer(DuellistArmorItem.this.tier);
                }
                return this.renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
