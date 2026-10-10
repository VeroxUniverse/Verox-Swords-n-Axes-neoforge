package net.veroxuniverse.verox_swords_axes.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.LevelReader;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;

import java.util.List;

public class SmithingHammerItem extends Item {

    public SmithingHammerItem(Properties properties) {
        super(properties);
    }

    /** Shift + right-click must still reach the anvil (removes the last upgrade). */
    @Override
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        return true;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("tooltip." + SwordsNAxes.MOD_ID + ".smithing_hammer").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable("tooltip." + SwordsNAxes.MOD_ID + ".smithing_hammer.remove").withStyle(ChatFormatting.DARK_GRAY));
    }
}
