package tsuteto.tofu.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import tsuteto.tofu.init.TcItems;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * A tofu-themed villager NPC (Tofunian) that lives in Tofu biomes.
 * Tofunians trade tofu-related items with the player. They look like
 * small tofu-block-headed villagers and are peaceful creatures.
 */
public class EntityTofunian extends AbstractVillager {

    /** Tracks how many trades have been refreshed */
    private int tradeRefreshCount = 0;

    /** Maximum number of trade refreshes before requiring rest */
    private static final int MAX_TRADE_REFRESHES = 3;

    public EntityTofunian(EntityType<? extends EntityTofunian> type, Level level) {
        super(type, level);
    }

    /**
     * Creates the attribute supplier for tofunian entities.
     */
    public static AttributeSupplier.Builder createAttributes() {
        return AbstractVillager.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.FOLLOW_RANGE, 48.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 0.6D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Zombie.class, 8.0F, 0.5D, 0.6D));
        this.goalSelector.addGoal(3, new MoveTowardsRestrictionGoal(this, 0.35D));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 0.35D));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    protected void updateTrades() {
        // Build trade offers for the tofunian
        MerchantOffers offers = this.getOffers();

        // Tier 1 trades (always available)
        addTofuTrade(offers, new ItemStack(Items.EMERALD, 1),
                new ItemStack(TcItems.TOFU_KINU_FOOD.get(), 8));
        addTofuTrade(offers, new ItemStack(Items.EMERALD, 1),
                new ItemStack(TcItems.TOFU_MOMEN_FOOD.get(), 6));
        addTofuTrade(offers, new ItemStack(TcItems.SOYBEANS.get(), 16),
                new ItemStack(Items.EMERALD, 1));

        // Tier 2 trades
        addTofuTrade(offers, new ItemStack(Items.EMERALD, 2),
                new ItemStack(TcItems.TOFU_GRILLED_FOOD.get(), 4));
        addTofuTrade(offers, new ItemStack(Items.EMERALD, 2),
                new ItemStack(TcItems.TOFU_FRIED_FOOD.get(), 4));
        addTofuTrade(offers, new ItemStack(TcItems.SALT.get(), 8),
                new ItemStack(Items.EMERALD, 1));

        // Tier 3 trades (special items)
        addTofuTrade(offers, new ItemStack(Items.EMERALD, 3),
                new ItemStack(TcItems.TOFU_MISO_FOOD.get(), 2));
        addTofuTrade(offers, new ItemStack(Items.EMERALD, 4),
                new ItemStack(TcItems.ZUNDA.get(), 2));
        addTofuTrade(offers, new ItemStack(Items.EMERALD, 5),
                new ItemStack(TcItems.NIGARI.get(), 4));

        // Rare trades
        if (this.random.nextFloat() < 0.3F) {
            addTofuTrade(offers, new ItemStack(Items.EMERALD, 8),
                    new ItemStack(TcItems.TOFU_DIAMOND_NUGGET.get(), 1));
        }
        if (this.random.nextFloat() < 0.2F) {
            addTofuTrade(offers, new ItemStack(Items.EMERALD, 6),
                    new ItemStack(TcItems.TOFU_HELL_FOOD.get(), 1));
        }
    }

    /**
     * Helper to add a trade offer with standard parameters.
     */
    private void addTofuTrade(MerchantOffers offers, ItemStack cost, ItemStack result) {
        offers.add(new MerchantOffer(
                new ItemCost(cost.getItem(), cost.getCount()),
                result,
                12,    // max uses
                2,     // xp reward
                0.05F  // price multiplier
        ));
    }

    @Override
    protected void rewardTradeXp(MerchantOffer offer) {
        // Award a small amount of XP to the player on successful trades
        if (offer.shouldRewardExp()) {
            int xp = 3 + this.random.nextInt(4);
            this.level().addFreshEntity(
                    new net.minecraft.world.entity.ExperienceOrb(
                            this.level(), this.getX(), this.getY() + 0.5D, this.getZ(), xp));
        }
    }

    @Override
    public boolean showProgressBar() {
        return false; // Tofunians don't have villager-style leveling
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isTrading() ? SoundEvents.VILLAGER_TRADE : SoundEvents.VILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.VILLAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VILLAGER_DEATH;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return SoundEvents.VILLAGER_YES;
    }

    @Override
    protected SoundEvent getTradeUpdatedSound(boolean yesSound) {
        return yesSound ? SoundEvents.VILLAGER_YES : SoundEvents.VILLAGER_NO;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob other) {
        // Tofunians do not breed in the traditional sense
        return null;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("TradeRefreshCount", this.tradeRefreshCount);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.tradeRefreshCount = tag.getInt("TradeRefreshCount");
    }
}
