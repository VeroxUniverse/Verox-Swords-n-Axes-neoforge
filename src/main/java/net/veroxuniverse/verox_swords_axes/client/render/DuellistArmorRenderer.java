package net.veroxuniverse.verox_swords_axes.client.render;

import net.veroxuniverse.unbound_lib.client.geo.LibArmorModel;
import net.veroxuniverse.unbound_lib.client.geo.LibDyeLayer;
import net.veroxuniverse.unbound_lib.client.geo.LibTrimLayer;
import net.veroxuniverse.unbound_lib.dye.DyeHelper;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;
import net.veroxuniverse.verox_swords_axes.item.ArmorTier;
import net.veroxuniverse.verox_swords_axes.item.DuellistArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class DuellistArmorRenderer extends GeoArmorRenderer<DuellistArmorItem> {

    public DuellistArmorRenderer(ArmorTier tier) {
        super(new LibArmorModel<>(SwordsNAxes.id("armor/duellist_" + tier.getSerializedName())));
        this.addRenderLayer(new LibTrimLayer<>(this));
        this.addRenderLayer(new LibDyeLayer<>(this, DyeHelper.CLOTH, DyeHelper.PLUME));
    }
}