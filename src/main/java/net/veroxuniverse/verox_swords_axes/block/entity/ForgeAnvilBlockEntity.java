package net.veroxuniverse.verox_swords_axes.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.veroxuniverse.unbound_lib.upgrade.InstalledUpgrade;
import net.veroxuniverse.unbound_lib.upgrade.UpgradeHelper;
import net.veroxuniverse.verox_swords_axes.SwordsNAxes;
import net.veroxuniverse.verox_swords_axes.recipe.ForgingRecipe;
import net.veroxuniverse.verox_swords_axes.recipe.ForgingRecipeInput;
import net.veroxuniverse.verox_swords_axes.registry.ModBlockEntities;
import net.veroxuniverse.verox_swords_axes.registry.ModRecipes;
import net.veroxuniverse.verox_swords_axes.registry.ModTags;

import java.util.List;
import java.util.Optional;

/**
 * Forge anvil: lay an item on top (base), add material / upgrade (addition), strike with the hammer.
 * After {@link #STRIKES_NEEDED} strikes the anvil forges (recipe), refills a charged upgrade or installs an upgrade.
 * Shift + hammer pulls out the last installed upgrade.
 */
public class ForgeAnvilBlockEntity extends BlockEntity {

    public static final int STRIKES_NEEDED = 3;

    private ItemStack base = ItemStack.EMPTY;
    private ItemStack addition = ItemStack.EMPTY;
    private int strikes;

    public ForgeAnvilBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.FORGE_ANVIL.get(), pos, state);
    }

    public ItemStack getBase() {
        return this.base;
    }

    public ItemStack getAddition() {
        return this.addition;
    }

    public int getStrikes() {
        return this.strikes;
    }

    public boolean isEmpty() {
        return this.base.isEmpty() && this.addition.isEmpty();
    }

    public boolean canAccept(ItemStack stack) {
        if (this.base.isEmpty()) return this.isValidBase(stack);
        if (this.addition.isEmpty()) return true;
        return ItemStack.isSameItemSameComponents(this.addition, stack) && this.addition.getCount() < this.addition.getMaxStackSize();
    }

    private boolean isValidBase(ItemStack stack) {
        if (stack.is(ModTags.Items.FORGEABLE) || UpgradeHelper.slots(stack) > 0) return true;
        if (this.level == null) return false;
        return this.level.getRecipeManager().getAllRecipesFor(ModRecipes.FORGING_TYPE.get()).stream()
                .anyMatch(holder -> holder.value().base().test(stack));
    }

    public void insert(Player player, ItemStack held) {
        if (this.base.isEmpty()) {
            this.base = held.copyWithCount(1);
            if (!player.getAbilities().instabuild) held.shrink(1);
        } else if (this.addition.isEmpty()) {
            this.addition = held.copy();
            if (!player.getAbilities().instabuild) held.setCount(0);
        } else {
            int moved = Math.min(held.getCount(), this.addition.getMaxStackSize() - this.addition.getCount());
            this.addition.grow(moved);
            if (!player.getAbilities().instabuild) held.shrink(moved);
        }
        this.playSound(SoundEvents.ITEM_FRAME_ADD_ITEM, 1.0F);
        this.contentsChanged();
    }

    /** Empty hand: take the addition first, then the base. */
    public void takeTop(Player player) {
        ItemStack taken;
        if (!this.addition.isEmpty()) {
            taken = this.addition;
            this.addition = ItemStack.EMPTY;
        } else {
            taken = this.base;
            this.base = ItemStack.EMPTY;
        }
        this.give(player, taken);
        this.playSound(SoundEvents.ITEM_FRAME_REMOVE_ITEM, 1.0F);
        this.contentsChanged();
    }

    public void strike(Player player, ItemStack hammer, InteractionHand hand) {
        if (this.base.isEmpty() || this.addition.isEmpty()) {
            this.fail(player, "nothing");
            return;
        }

        Action action = this.findAction();
        if (action == Action.NONE) {
            this.fail(player, this.installFailureKey());
            return;
        }

        this.strikes++;
        this.sparks(4);
        if (this.strikes < STRIKES_NEEDED) {
            this.playSound(SoundEvents.ANVIL_PLACE, 0.6F + this.strikes * 0.15F);
            this.syncToClient();
            return;
        }

        this.strikes = 0;
        boolean done = switch (action) {
            case FORGE -> this.forge();
            case REFILL -> this.refill();
            case INSTALL -> this.install();
            case NONE -> false;
        };

        if (done) {
            this.playSound(SoundEvents.ANVIL_USE, 1.0F);
            this.sparks(12);
            hammer.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
        }
        this.contentsChanged();
    }

    /** Shift + hammer: pull out the last installed upgrade (charges are lost). */
    public void removeLastUpgrade(Player player) {
        int installed = UpgradeHelper.get(this.base).size();
        if (this.base.isEmpty() || installed == 0) {
            this.fail(player, "no_upgrades");
            return;
        }
        ItemStack removed = UpgradeHelper.remove(this.base, installed - 1);
        this.give(player, removed);
        this.playSound(SoundEvents.GRINDSTONE_USE, 1.0F);
        this.contentsChanged();
    }

    private enum Action { FORGE, REFILL, INSTALL, NONE }

    private Action findAction() {
        if (this.findRecipe().isPresent()) return Action.FORGE;
        if (this.refillTarget().isPresent()) return Action.REFILL;
        if (UpgradeHelper.canInstall(this.base, this.addition) == UpgradeHelper.InstallResult.OK) return Action.INSTALL;
        return Action.NONE;
    }

    private Optional<RecipeHolder<ForgingRecipe>> findRecipe() {
        if (this.level == null) return Optional.empty();
        return this.level.getRecipeManager().getRecipeFor(ModRecipes.FORGING_TYPE.get(),
                new ForgingRecipeInput(this.base, this.addition), this.level);
    }

    private Optional<Item> refillTarget() {
        for (InstalledUpgrade upgrade : UpgradeHelper.active(this.base)) {
            Item item = upgrade.item().value();
            if (UpgradeHelper.canRefill(this.base, item, this.addition)) return Optional.of(item);
        }
        return Optional.empty();
    }

    private boolean forge() {
        Optional<RecipeHolder<ForgingRecipe>> recipe = this.findRecipe();
        if (recipe.isEmpty() || this.level == null) return false;
        ForgingRecipe forging = recipe.get().value();
        this.base = forging.assemble(new ForgingRecipeInput(this.base, this.addition), this.level.registryAccess());
        this.addition.shrink(forging.additionCount());
        return true;
    }

    private boolean refill() {
        Optional<Item> target = this.refillTarget();
        if (target.isEmpty()) return false;
        ResourceLocation payload = BuiltInRegistries.ITEM.getKey(this.addition.getItem());
        if (!UpgradeHelper.refill(this.base, target.get(), this.addition, Optional.of(payload))) return false;
        this.addition.shrink(1);
        return true;
    }

    private boolean install() {
        if (UpgradeHelper.install(this.base, this.addition) != UpgradeHelper.InstallResult.OK) return false;
        this.addition.shrink(1);
        return true;
    }

    private String installFailureKey() {
        UpgradeHelper.InstallResult result = UpgradeHelper.canInstall(this.base, this.addition);
        return switch (result) {
            case NO_SLOTS -> "no_slots";
            case FULL -> "full";
            case NOT_APPLICABLE -> "not_applicable";
            case ALREADY_INSTALLED -> "already_installed";
            default -> "invalid";
        };
    }

    private void fail(Player player, String reason) {
        this.strikes = 0;
        player.displayClientMessage(Component.translatable("message." + SwordsNAxes.MOD_ID + ".forge_anvil." + reason), true);
        this.playSound(SoundEvents.VILLAGER_NO, 0.8F);
        this.syncToClient();
    }

    private void give(Player player, ItemStack stack) {
        if (stack.isEmpty()) return;
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }

    private void playSound(net.minecraft.sounds.SoundEvent sound, float pitch) {
        if (this.level != null) {
            this.level.playSound(null, this.worldPosition, sound, SoundSource.BLOCKS, 0.7F, pitch);
        }
    }

    private void sparks(int count) {
        if (this.level instanceof ServerLevel server) {
            server.sendParticles(ParticleTypes.LAVA,
                    this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 1.05, this.worldPosition.getZ() + 0.5,
                    Math.max(1, count / 4), 0.15, 0.02, 0.15, 0.0);
            server.sendParticles(ParticleTypes.CRIT,
                    this.worldPosition.getX() + 0.5, this.worldPosition.getY() + 1.05, this.worldPosition.getZ() + 0.5,
                    count, 0.2, 0.05, 0.2, 0.3);
        }
    }

    private void contentsChanged() {
        if (this.addition.isEmpty()) this.addition = ItemStack.EMPTY;
        this.strikes = 0;
        this.syncToClient();
    }

    private void syncToClient() {
        this.setChanged();
        Level level = this.level;
        if (level != null && !level.isClientSide) {
            BlockState state = this.getBlockState();
            level.sendBlockUpdated(this.worldPosition, state, state, Block.UPDATE_CLIENTS);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!this.base.isEmpty()) tag.put("base", this.base.save(registries));
        if (!this.addition.isEmpty()) tag.put("addition", this.addition.save(registries));
        tag.putInt("strikes", this.strikes);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.base = ItemStack.parseOptional(registries, tag.getCompound("base"));
        this.addition = ItemStack.parseOptional(registries, tag.getCompound("addition"));
        this.strikes = tag.getInt("strikes");
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    /** For JEI / tooltips later. */
    public List<ItemStack> contents() {
        return List.of(this.base, this.addition);
    }
}
