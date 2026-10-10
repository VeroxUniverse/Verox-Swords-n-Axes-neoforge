package net.veroxuniverse.verox_swords_axes.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.veroxuniverse.unbound_lib.upgrade.UpgradeHelper;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;
import net.veroxuniverse.verox_swords_axes.registry.ModItems;

/** Effects of "special" and "charged" upgrades. Stat upgrades are handled entirely by the lib. */
@EventBusSubscriber(modid = SwordsNAxes.MOD_ID)
public final class UpgradeEffectHandler {

    private static final float FIRE_INLAY_SECONDS = 4.0F;

    private UpgradeEffectHandler() {}

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent.Post event) {
        LivingEntity target = event.getEntity();
        if (target.level().isClientSide || event.getNewDamage() <= 0) return;

        DamageSource source = event.getSource();
        if (!(source.getEntity() instanceof LivingEntity attacker) || source.getDirectEntity() != attacker) return;

        ItemStack weapon = source.getWeaponItem() != null ? source.getWeaponItem() : attacker.getMainHandItem();
        if (weapon.isEmpty()) return;

        if (UpgradeHelper.has(weapon, ModItems.FIRE_INLAY.get())
                && UpgradeHelper.consumeCharge(attacker, weapon, ModItems.FIRE_INLAY.get()).isPresent()) {
            target.igniteForSeconds(FIRE_INLAY_SECONDS);
        }
    }
}
