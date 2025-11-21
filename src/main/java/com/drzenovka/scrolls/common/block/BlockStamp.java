package com.drzenovka.scrolls.common.block;

import com.drzenovka.scrolls.common.tileentity.TileEntityStamp;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.util.AxisAlignedBB;

public class BlockStamp extends Block {

    public BlockStamp() {
        super(Material.wood);
        this.setBlockName("stamp");
        this.setHardness(0.5F);
        this.setStepSound(soundTypeWood);

        // Tiny bounds: 2x2x5 pixels
        //this.setBlockBounds(0f, 0f, 0f, 2f/16f, 5f/16f, 2f/16f);
        this.setBlockBounds(7f/16f, 0f, 7f/16f, 9f/16f, 5f/16f, 9f/16f);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(
            x + 0f, y + 0f, z + 0f,
            x + 2f/16f, y + 5f/16f, z + 2f/16f
        );
    }

    @Override
    public boolean isOpaqueCube() {
        return false; // Tiny block, not full cube
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false; // Use custom renderer
    }

    @Override
    public int getRenderType() {
        return -1; // Custom TESR render type
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntityStamp();
    }

}
