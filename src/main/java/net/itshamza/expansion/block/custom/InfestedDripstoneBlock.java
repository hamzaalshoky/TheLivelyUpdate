package net.itshamza.expansion.block.custom;

import com.google.common.collect.Maps;
import net.itshamza.expansion.entity.ModEntityCreator;
import net.itshamza.expansion.entity.custom.SkubbleEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Map;
import java.util.function.Supplier;

public class InfestedDripstoneBlock extends PointedDripstoneBlock implements SimpleWaterloggedBlock {
    public InfestedDripstoneBlock(Block p_54178_, Properties p_154025_) {
        super(p_154025_.destroyTime(p_54178_.defaultDestroyTime() / 2.0F).explosionResistance(0.75F));
        this.hostBlock = p_54178_;
        BLOCK_BY_HOST_BLOCK.put(p_54178_, this);
    }

    private final Block hostBlock;
    private static final Map<Block, Block> BLOCK_BY_HOST_BLOCK = Maps.newIdentityHashMap();
    private static final Map<BlockState, BlockState> HOST_TO_INFESTED_STATES = Maps.newIdentityHashMap();
    private static final Map<BlockState, BlockState> INFESTED_TO_HOST_STATES = Maps.newIdentityHashMap();

    public Block getHostBlock() {
        return this.hostBlock;
    }

    public static boolean isCompatibleHostBlock(BlockState p_54196_) {
        return BLOCK_BY_HOST_BLOCK.containsKey(p_54196_.getBlock());
    }

    private void spawnInfestation(ServerLevel p_54181_, BlockPos p_54182_) {
        SkubbleEntity skubble = ModEntityCreator.SKUBBLE.get().create(p_54181_);
        skubble.moveTo((double)p_54182_.getX() + 0.5D, (double)p_54182_.getY(), (double)p_54182_.getZ() + 0.5D, 0.0F, 0.0F);
        p_54181_.addFreshEntity(skubble);
        skubble.spawnAnim();
    }

    public void spawnAfterBreak(BlockState p_54188_, ServerLevel p_54189_, BlockPos p_54190_, ItemStack p_54191_) {
        super.spawnAfterBreak(p_54188_, p_54189_, p_54190_, p_54191_);
        if (p_54189_.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS) && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, p_54191_) == 0) {
            this.spawnInfestation(p_54189_, p_54190_);
        }

    }

    public void wasExploded(Level p_54184_, BlockPos p_54185_, Explosion p_54186_) {
        if (p_54184_ instanceof ServerLevel) {
            this.spawnInfestation((ServerLevel)p_54184_, p_54185_);
        }

    }

    public static BlockState infestedStateByHost(BlockState p_153431_) {
        return getNewStateWithProperties(HOST_TO_INFESTED_STATES, p_153431_, () -> {
            return BLOCK_BY_HOST_BLOCK.get(p_153431_.getBlock()).defaultBlockState();
        });
    }

    public BlockState hostStateByInfested(BlockState p_153433_) {
        return getNewStateWithProperties(INFESTED_TO_HOST_STATES, p_153433_, () -> {
            return this.getHostBlock().defaultBlockState();
        });
    }

    private static BlockState getNewStateWithProperties(Map<BlockState, BlockState> p_153424_, BlockState p_153425_, Supplier<BlockState> p_153426_) {
        return p_153424_.computeIfAbsent(p_153425_, (p_153429_) -> {
            BlockState blockstate = p_153426_.get();

            for(Property property : p_153429_.getProperties()) {
                blockstate = blockstate.hasProperty(property) ? blockstate.setValue(property, p_153429_.getValue(property)) : blockstate;
            }

            return blockstate;
        });
    }
}
