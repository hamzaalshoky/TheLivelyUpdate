package net.itshamza.expansion.entity.custom;

import net.itshamza.expansion.block.custom.InfestedDripstoneBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Random;

public class SkubbleEntity extends Monster implements IAnimatable{

    private AnimationFactory factory = new AnimationFactory(this);

    public SkubbleEntity(EntityType<? extends Monster> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }


    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.15f).build();
    }

    @javax.annotation.Nullable
    private SkubbleEntity.SkubbleEntityWakeUpFriendsGoal friendsGoal;

    protected void registerGoals() {
        this.friendsGoal = new SkubbleEntity.SkubbleEntityWakeUpFriendsGoal(this);
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(1, new ClimbOnTopOfPowderSnowGoal(this, this.level));
        this.goalSelector.addGoal(3, this.friendsGoal);
        this.goalSelector.addGoal(5, new SkubbleEntity.SkubbleEntityMergeWithStoneGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public double getMyRidingOffset() {
        return 0.1D;
    }

    protected float getStandingEyeHeight(Pose p_33540_, EntityDimensions p_33541_) {
        return 0.13F;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.ATTACK_DAMAGE, 1.0D);
    }

    protected Entity.MovementEmission getMovementEmission() {
        return Entity.MovementEmission.EVENTS;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SILVERFISH_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_33549_) {
        return SoundEvents.SILVERFISH_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SILVERFISH_DEATH;
    }

    protected void playStepSound(BlockPos p_33543_, BlockState p_33544_) {
        this.playSound(SoundEvents.SILVERFISH_STEP, 0.15F, 1.0F);
    }

    public boolean hurt(DamageSource p_33527_, float p_33528_) {
        if (this.isInvulnerableTo(p_33527_)) {
            return false;
        } else {
            if ((p_33527_ instanceof EntityDamageSource || p_33527_ == DamageSource.MAGIC) && this.friendsGoal != null) {
                this.friendsGoal.notifyHurt();
            }

            return super.hurt(p_33527_, p_33528_);
        }
    }

    public void tick() {
        this.yBodyRot = this.getYRot();
        super.tick();
    }

    public void setYBodyRot(float p_33553_) {
        this.setYRot(p_33553_);
        super.setYBodyRot(p_33553_);
    }

    public float getWalkTargetValue(BlockPos p_33530_, LevelReader p_33531_) {
        return InfestedDripstoneBlock.isCompatibleHostBlock(p_33531_.getBlockState(p_33530_.below())) ? 10.0F : super.getWalkTargetValue(p_33530_, p_33531_);
    }

    public static boolean checkSkubbleEntitySpawnRules(EntityType<SkubbleEntity> p_186281_, LevelAccessor p_186282_, MobSpawnType p_186283_, BlockPos p_186284_, Random p_186285_) {
        if (checkAnyLightMonsterSpawnRules(p_186281_, p_186282_, p_186283_, p_186284_, p_186285_)) {
            Player player = p_186282_.getNearestPlayer((double)p_186284_.getX() + 0.5D, (double)p_186284_.getY() + 0.5D, (double)p_186284_.getZ() + 0.5D, 5.0D, true);
            return player == null;
        } else {
            return false;
        }
    }

    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    static class SkubbleEntityMergeWithStoneGoal extends RandomStrollGoal {
        @Nullable
        private Direction selectedDirection;
        private boolean doMerge;

        public SkubbleEntityMergeWithStoneGoal(SkubbleEntity p_33558_) {
            super(p_33558_, 1.0D, 10);
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            if (this.mob.getTarget() != null) {
                return false;
            } else if (!this.mob.getNavigation().isDone()) {
                return false;
            } else {
                Random random = this.mob.getRandom();
                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.mob.level, this.mob) && random.nextInt(10) == 0) {
                    this.selectedDirection = Direction.getRandom(random);
                    BlockPos blockpos = (new BlockPos(this.mob.getX(), this.mob.getY() + 0.5D, this.mob.getZ())).relative(this.selectedDirection);
                    BlockState blockstate = this.mob.level.getBlockState(blockpos);
                    if (InfestedDripstoneBlock.isCompatibleHostBlock(blockstate)) {
                        this.doMerge = true;
                        return true;
                    }
                }

                this.doMerge = false;
                return super.canUse();
            }
        }

        public boolean canContinueToUse() {
            return this.doMerge ? false : super.canContinueToUse();
        }

        public void start() {
            if (!this.doMerge) {
                super.start();
            } else {
                LevelAccessor levelaccessor = this.mob.level;
                BlockPos blockpos = (new BlockPos(this.mob.getX(), this.mob.getY() + 0.5D, this.mob.getZ())).relative(this.selectedDirection);
                BlockState blockstate = levelaccessor.getBlockState(blockpos);
                if (InfestedDripstoneBlock.isCompatibleHostBlock(blockstate)) {
                    levelaccessor.setBlock(blockpos, InfestedDripstoneBlock.infestedStateByHost(blockstate), 3);
                    this.mob.spawnAnim();
                    this.mob.discard();
                }

            }
        }
    }

    static class SkubbleEntityWakeUpFriendsGoal extends Goal {
        private final SkubbleEntity SkubbleEntity;
        private int lookForFriends;

        public SkubbleEntityWakeUpFriendsGoal(SkubbleEntity p_33565_) {
            this.SkubbleEntity = p_33565_;
        }

        public void notifyHurt() {
            if (this.lookForFriends == 0) {
                this.lookForFriends = this.adjustedTickDelay(20);
            }

        }

        public boolean canUse() {
            return this.lookForFriends > 0;
        }

        public void tick() {
            --this.lookForFriends;
            if (this.lookForFriends <= 0) {
                Level level = this.SkubbleEntity.level;
                Random random = this.SkubbleEntity.getRandom();
                BlockPos blockpos = this.SkubbleEntity.blockPosition();

                for(int i = 0; i <= 5 && i >= -5; i = (i <= 0 ? 1 : 0) - i) {
                    for(int j = 0; j <= 10 && j >= -10; j = (j <= 0 ? 1 : 0) - j) {
                        for(int k = 0; k <= 10 && k >= -10; k = (k <= 0 ? 1 : 0) - k) {
                            BlockPos blockpos1 = blockpos.offset(j, i, k);
                            BlockState blockstate = level.getBlockState(blockpos1);
                            Block block = blockstate.getBlock();
                            if (block instanceof InfestedDripstoneBlock) {
                                if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(level, this.SkubbleEntity)) {
                                    level.destroyBlock(blockpos1, true, this.SkubbleEntity);
                                } else {
                                    level.setBlock(blockpos1, ((InfestedDripstoneBlock)block).hostStateByInfested(level.getBlockState(blockpos1)), 3);
                                }

                                if (random.nextBoolean()) {
                                    return;
                                }
                            }
                        }
                    }
                }
            }

        }
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", true));
            return PlayState.CONTINUE;
        }

        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }
    


    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
