package net.veroxuniverse.verox_swords_axes.client.render;

import net.veroxuniverse.unbound_lib.client.geo.LibArmorModel;
import net.veroxuniverse.unbound_lib.client.geo.LibDyeLayer;
import net.veroxuniverse.unbound_lib.client.geo.LibTrimLayer;
import net.veroxuniverse.unbound_lib.dye.DyeHelper;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;
import net.veroxuniverse.verox_swords_axes.item.ArmorTier;
import net.veroxuniverse.verox_swords_axes.item.DuellistArmorItem;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

/**
 * One renderer per tier. Assets:
 * geo/item/armor/duellist_{tier}.geo.json, textures/item/armor/duellist_{tier}.png,
 * animations/item/armor/duellist_{tier}.animation.json.
 * Overlays next to the texture: _trim_default (built-in details), _trim_{pattern} (greyscale, tinted),
 * _trim_{pattern}_{material} (hand-made), _dye_cloth / _dye_plume (greyscale masks).
 * Cape & plume bones are moved by the lib (ArmorPhysics).
 */
public class DuellistArmorRenderer extends GeoArmorRenderer<DuellistArmorItem> {

    public DuellistArmorRenderer(ArmorTier tier) {
        super(new LibArmorModel<>(SwordsNAxes.id("armor/duellist_" + tier.getSerializedName())));
        this.addRenderLayer(new LibTrimLayer<>(this));
        this.addRenderLayer(new LibDyeLayer<>(this, DyeHelper.CLOTH, DyeHelper.PLUME));
    }
}
