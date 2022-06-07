package net.orcinus.overweightfarming.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.ShapeContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;


/**
 * @author MrSterner
 */
//TODO: CREATE COMPAT
public class OFCropFullBlock extends CropBlock implements Fertilizable {
    private final Block stemBlock;

    protected OFCropFullBlock(Block stemBlock, Settings settings) {
        super(settings);
        this.stemBlock = stemBlock;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.fullCube();
    }

    public Block getStemBlock() {
        return this.stemBlock;
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return !world.getBlockState(pos.up()).isOf(this.stemBlock);
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        BlockPos above = pos.up();
        BlockPos below = pos.down();
        if (this.stemBlock != null && world.getBlockState(above).isAir()) {
            world.setBlockState(above, stemBlock.getDefaultState(), 2);
        }
        if (world.testBlockState(below, AbstractBlockState::isAir)) {
            world.setBlockState(below, Blocks.HANGING_ROOTS.getDefaultState(), 2);
        }
    }
}

