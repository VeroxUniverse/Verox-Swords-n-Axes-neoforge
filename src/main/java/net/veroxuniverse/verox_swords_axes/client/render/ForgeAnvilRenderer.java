package net.veroxuniverse.verox_swords_axes.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.veroxuniverse.verox_swords_axes.block.ForgeAnvilBlock;
import net.veroxuniverse.verox_swords_axes.block.entity.ForgeAnvilBlockEntity;

/** Draws the base item flat on the anvil face and the addition next to it. */
public class ForgeAnvilRenderer implements BlockEntityRenderer<ForgeAnvilBlockEntity> {

    private final ItemRenderer itemRenderer;

    public ForgeAnvilRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(ForgeAnvilBlockEntity anvil, float partialTick, PoseStack poseStack, MultiBufferSource buffer,
                       int packedLight, int packedOverlay) {
        Level level = anvil.getLevel();
        if (level == null || anvil.isEmpty()) return;

        Direction facing = anvil.getBlockState().getValue(ForgeAnvilBlock.FACING);
        int light = LevelRenderer.getLightColor(level, anvil.getBlockPos().above());
        int seed = (int) anvil.getBlockPos().asLong();

        float yaw = facing.getAxis() == Direction.Axis.X ? 90.0F : 0.0F;

        this.renderFlat(anvil.getBase(), poseStack, buffer, light, packedOverlay, level, seed, yaw, 0.0F, 0.5F);
        this.renderFlat(anvil.getAddition(), poseStack, buffer, light, packedOverlay, level, seed + 1, yaw, 0.32F, 0.3F);
    }

    private void renderFlat(ItemStack stack, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay,
                            Level level, int seed, float yaw, float offset, float scale) {
        if (stack.isEmpty()) return;
        poseStack.pushPose();
        poseStack.translate(0.5, 1.0 + 1.0 / 64.0, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(yaw));
        poseStack.translate(0.0, offset > 0 ? 0.01 : 0.0, offset);
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        poseStack.scale(scale, scale, scale);
        this.itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, poseStack, buffer, level, seed);
        poseStack.popPose();
    }
}
