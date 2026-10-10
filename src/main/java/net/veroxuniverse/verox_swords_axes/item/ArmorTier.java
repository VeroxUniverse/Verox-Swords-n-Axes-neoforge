package net.veroxuniverse.verox_swords_axes.item;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum ArmorTier implements StringRepresentable {
    BASE,
    DIAMOND,
    NETHERITE;

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
