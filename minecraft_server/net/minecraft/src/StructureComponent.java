package net.minecraft.src;

import java.util.*;

public abstract class StructureComponent
{
    protected StructureBoundingBox boundingBox;

    /** switches the Coordinate System base off the Bounding Box */
    protected int coordBaseMode;

    /** The type ID of this component. */
    protected int componentType;

    protected StructureComponent(int par1)
    {
        componentType = par1;
        coordBaseMode = -1;
    }

    /**
     * Initiates construction of the Structure Component picked, at the current Location of StructGen
     */
    public void buildComponent(StructureComponent structurecomponent, List list, Random random)
    {
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public abstract boolean addComponentParts(World world, Random random, StructureBoundingBox structureboundingbox);

    public StructureBoundingBox getBoundingBox()
    {
        return boundingBox;
    }

    /**
     * Returns the component type ID of this component.
     */
    public int getComponentType()
    {
        return componentType;
    }

    /**
     * Discover if bounding box can fit within the current bounding box object.
     */
    public static StructureComponent findIntersecting(List par0List, StructureBoundingBox par1StructureBoundingBox)
    {
        for (Iterator iterator = par0List.iterator(); iterator.hasNext();)
        {
            StructureComponent structurecomponent = (StructureComponent)iterator.next();

            if (structurecomponent.getBoundingBox() != null && structurecomponent.getBoundingBox().intersectsWith(par1StructureBoundingBox))
            {
                return structurecomponent;
            }
        }

        return null;
    }

    public ChunkPosition getCenter()
    {
        return new ChunkPosition(boundingBox.getCenterX(), boundingBox.getCenterY(), boundingBox.getCenterZ());
    }

    /**
     * checks the entire StructureBoundingBox for Liquids
     */
    protected boolean isLiquidInStructureBoundingBox(World par1World, StructureBoundingBox par2StructureBoundingBox)
    {
        int i = Math.max(boundingBox.minX - 1, par2StructureBoundingBox.minX);
        int j = Math.max(boundingBox.minY - 1, par2StructureBoundingBox.minY);
        int k = Math.max(boundingBox.minZ - 1, par2StructureBoundingBox.minZ);
        int l = Math.min(boundingBox.maxX + 1, par2StructureBoundingBox.maxX);
        int i1 = Math.min(boundingBox.maxY + 1, par2StructureBoundingBox.maxY);
        int j1 = Math.min(boundingBox.maxZ + 1, par2StructureBoundingBox.maxZ);

        for (int k1 = i; k1 <= l; k1++)
        {
            for (int j2 = k; j2 <= j1; j2++)
            {
                int i3 = par1World.getBlockId(k1, j, j2);

                if (i3 > 0 && Block.blocksList[i3].blockMaterial.isLiquid())
                {
                    return true;
                }

                i3 = par1World.getBlockId(k1, i1, j2);

                if (i3 > 0 && Block.blocksList[i3].blockMaterial.isLiquid())
                {
                    return true;
                }
            }
        }

        for (int l1 = i; l1 <= l; l1++)
        {
            for (int k2 = j; k2 <= i1; k2++)
            {
                int j3 = par1World.getBlockId(l1, k2, k);

                if (j3 > 0 && Block.blocksList[j3].blockMaterial.isLiquid())
                {
                    return true;
                }

                j3 = par1World.getBlockId(l1, k2, j1);

                if (j3 > 0 && Block.blocksList[j3].blockMaterial.isLiquid())
                {
                    return true;
                }
            }
        }

        for (int i2 = k; i2 <= j1; i2++)
        {
            for (int l2 = j; l2 <= i1; l2++)
            {
                int k3 = par1World.getBlockId(i, l2, i2);

                if (k3 > 0 && Block.blocksList[k3].blockMaterial.isLiquid())
                {
                    return true;
                }

                k3 = par1World.getBlockId(l, l2, i2);

                if (k3 > 0 && Block.blocksList[k3].blockMaterial.isLiquid())
                {
                    return true;
                }
            }
        }

        return false;
    }

    protected int getXWithOffset(int par1, int par2)
    {
        switch (coordBaseMode)
        {
            case 0:
            case 2:
                return boundingBox.minX + par1;

            case 1:
                return boundingBox.maxX - par2;

            case 3:
                return boundingBox.minX + par2;
        }

        return par1;
    }

    protected int getYWithOffset(int par1)
    {
        if (coordBaseMode == -1)
        {
            return par1;
        }
        else
        {
            return par1 + boundingBox.minY;
        }
    }

    protected int getZWithOffset(int par1, int par2)
    {
        switch (coordBaseMode)
        {
            case 2:
                return boundingBox.maxZ - par2;

            case 0:
                return boundingBox.minZ + par2;

            case 1:
            case 3:
                return boundingBox.minZ + par1;
        }

        return par2;
    }

    /**
     * Returns the direction-shifted metadata for blocks that require orientation, e.g. doors, stairs, ladders.
     * Parameters: block ID, original metadata
     */
    protected int getMetadataWithOffset(int par1, int par2)
    {
        if (par1 == Block.rail.blockID)
        {
            if (coordBaseMode == 1 || coordBaseMode == 3)
            {
                return par2 != 1 ? 1 : 0;
            }
        }
        else if (par1 == Block.doorWood.blockID || par1 == Block.doorSteel.blockID)
        {
            if (coordBaseMode == 0)
            {
                if (par2 == 0)
                {
                    return 2;
                }

                if (par2 == 2)
                {
                    return 0;
                }
            }
            else
            {
                if (coordBaseMode == 1)
                {
                    return par2 + 1 & 3;
                }

                if (coordBaseMode == 3)
                {
                    return par2 + 3 & 3;
                }
            }
        }
        else if (par1 == Block.stairCompactCobblestone.blockID || par1 == Block.stairCompactPlanks.blockID || par1 == Block.stairsNetherBrick.blockID || par1 == Block.stairsStoneBrickSmooth.blockID || par1 == Block.field_56339_bQ.blockID)
        {
            if (coordBaseMode == 0)
            {
                if (par2 == 2)
                {
                    return 3;
                }

                if (par2 == 3)
                {
                    return 2;
                }
            }
            else if (coordBaseMode == 1)
            {
                if (par2 == 0)
                {
                    return 2;
                }

                if (par2 == 1)
                {
                    return 3;
                }

                if (par2 == 2)
                {
                    return 0;
                }

                if (par2 == 3)
                {
                    return 1;
                }
            }
            else if (coordBaseMode == 3)
            {
                if (par2 == 0)
                {
                    return 2;
                }

                if (par2 == 1)
                {
                    return 3;
                }

                if (par2 == 2)
                {
                    return 1;
                }

                if (par2 == 3)
                {
                    return 0;
                }
            }
        }
        else if (par1 == Block.ladder.blockID)
        {
            if (coordBaseMode == 0)
            {
                if (par2 == 2)
                {
                    return 3;
                }

                if (par2 == 3)
                {
                    return 2;
                }
            }
            else if (coordBaseMode == 1)
            {
                if (par2 == 2)
                {
                    return 4;
                }

                if (par2 == 3)
                {
                    return 5;
                }

                if (par2 == 4)
                {
                    return 2;
                }

                if (par2 == 5)
                {
                    return 3;
                }
            }
            else if (coordBaseMode == 3)
            {
                if (par2 == 2)
                {
                    return 5;
                }

                if (par2 == 3)
                {
                    return 4;
                }

                if (par2 == 4)
                {
                    return 2;
                }

                if (par2 == 5)
                {
                    return 3;
                }
            }
        }
        else if (par1 == Block.button.blockID)
        {
            if (coordBaseMode == 0)
            {
                if (par2 == 3)
                {
                    return 4;
                }

                if (par2 == 4)
                {
                    return 3;
                }
            }
            else if (coordBaseMode == 1)
            {
                if (par2 == 3)
                {
                    return 1;
                }

                if (par2 == 4)
                {
                    return 2;
                }

                if (par2 == 2)
                {
                    return 3;
                }

                if (par2 == 1)
                {
                    return 4;
                }
            }
            else if (coordBaseMode == 3)
            {
                if (par2 == 3)
                {
                    return 2;
                }

                if (par2 == 4)
                {
                    return 1;
                }

                if (par2 == 2)
                {
                    return 3;
                }

                if (par2 == 1)
                {
                    return 4;
                }
            }
        }
        else if (par1 == Block.field_56334_bT.blockID || Block.blocksList[par1] != null && (Block.blocksList[par1] instanceof BlockDirectional))
        {
            if (coordBaseMode == 0)
            {
                if (par2 == 0 || par2 == 2)
                {
                    return Direction.footInvisibleFaceRemap[par2];
                }
            }
            else if (coordBaseMode == 1)
            {
                if (par2 == 2)
                {
                    return 1;
                }

                if (par2 == 0)
                {
                    return 3;
                }

                if (par2 == 1)
                {
                    return 2;
                }

                if (par2 == 3)
                {
                    return 0;
                }
            }
            else if (coordBaseMode == 3)
            {
                if (par2 == 2)
                {
                    return 3;
                }

                if (par2 == 0)
                {
                    return 1;
                }

                if (par2 == 1)
                {
                    return 2;
                }

                if (par2 == 3)
                {
                    return 0;
                }
            }
        }
        else if (par1 == Block.pistonBase.blockID || par1 == Block.pistonStickyBase.blockID || par1 == Block.lever.blockID || par1 == Block.dispenser.blockID)
        {
            if (coordBaseMode == 0)
            {
                if (par2 == 2 || par2 == 3)
                {
                    return Facing.faceToSide[par2];
                }
            }
            else if (coordBaseMode == 1)
            {
                if (par2 == 2)
                {
                    return 4;
                }

                if (par2 == 3)
                {
                    return 5;
                }

                if (par2 == 4)
                {
                    return 2;
                }

                if (par2 == 5)
                {
                    return 3;
                }
            }
            else if (coordBaseMode == 3)
            {
                if (par2 == 2)
                {
                    return 5;
                }

                if (par2 == 3)
                {
                    return 4;
                }

                if (par2 == 4)
                {
                    return 2;
                }

                if (par2 == 5)
                {
                    return 3;
                }
            }
        }

        return par2;
    }

    /**
     * current Position depends on currently set Coordinates mode, is computed here
     */
    protected void placeBlockAtCurrentPosition(World par1World, int par2, int par3, int par4, int par5, int par6, StructureBoundingBox par7StructureBoundingBox)
    {
        int i = getXWithOffset(par4, par6);
        int j = getYWithOffset(par5);
        int k = getZWithOffset(par4, par6);

        if (!par7StructureBoundingBox.isVecInside(i, j, k))
        {
            return;
        }

        if (par1World.setBlockAndMetadata(i, j, k, par2, par3))
        {
            par1World.markBlockNeedsUpdate(i, j, k);
        }
    }

    protected int getBlockIdAtCurrentPosition(World par1World, int par2, int par3, int par4, StructureBoundingBox par5StructureBoundingBox)
    {
        int i = getXWithOffset(par2, par4);
        int j = getYWithOffset(par3);
        int k = getZWithOffset(par2, par4);

        if (!par5StructureBoundingBox.isVecInside(i, j, k))
        {
            return 0;
        }
        else
        {
            return par1World.getBlockId(i, j, k);
        }
    }

    protected void func_56292_a(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6, int par7, int par8)
    {
        for (int i = par4; i <= par7; i++)
        {
            for (int j = par3; j <= par6; j++)
            {
                for (int k = par5; k <= par8; k++)
                {
                    placeBlockAtCurrentPosition(par1World, 0, 0, j, i, k, par2StructureBoundingBox);
                }
            }
        }
    }

    /**
     * arguments: (World worldObj, StructureBoundingBox structBB, int minX, int minY, int minZ, int maxX, int maxY, int
     * maxZ, int placeBlockId, int replaceBlockId, boolean alwaysreplace)
     */
    protected void fillWithBlocks(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6, int par7, int par8, int par9, int par10, boolean par11)
    {
        for (int i = par4; i <= par7; i++)
        {
            for (int j = par3; j <= par6; j++)
            {
                for (int k = par5; k <= par8; k++)
                {
                    if (par11 && getBlockIdAtCurrentPosition(par1World, j, i, k, par2StructureBoundingBox) == 0)
                    {
                        continue;
                    }

                    if (i == par4 || i == par7 || j == par3 || j == par6 || k == par5 || k == par8)
                    {
                        placeBlockAtCurrentPosition(par1World, par9, 0, j, i, k, par2StructureBoundingBox);
                    }
                    else
                    {
                        placeBlockAtCurrentPosition(par1World, par10, 0, j, i, k, par2StructureBoundingBox);
                    }
                }
            }
        }
    }

    protected void func_56290_a(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6, int par7, int par8, int par9, int par10, int par11, int par12, boolean par13)
    {
        for (int i = par4; i <= par7; i++)
        {
            for (int j = par3; j <= par6; j++)
            {
                for (int k = par5; k <= par8; k++)
                {
                    if (par13 && getBlockIdAtCurrentPosition(par1World, j, i, k, par2StructureBoundingBox) == 0)
                    {
                        continue;
                    }

                    if (i == par4 || i == par7 || j == par3 || j == par6 || k == par5 || k == par8)
                    {
                        placeBlockAtCurrentPosition(par1World, par9, par10, j, i, k, par2StructureBoundingBox);
                    }
                    else
                    {
                        placeBlockAtCurrentPosition(par1World, par11, par12, j, i, k, par2StructureBoundingBox);
                    }
                }
            }
        }
    }

    /**
     * arguments: World worldObj, StructureBoundingBox structBB, int minX, int minY, int minZ, int maxX, int maxY, int
     * maxZ, boolean alwaysreplace, Random rand, StructurePieceBlockSelector blockselector
     */
    protected void fillWithRandomizedBlocks(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6, int par7, int par8, boolean par9, Random par10Random, StructurePieceBlockSelector par11StructurePieceBlockSelector)
    {
        for (int i = par4; i <= par7; i++)
        {
            for (int j = par3; j <= par6; j++)
            {
                for (int k = par5; k <= par8; k++)
                {
                    if (!par9 || getBlockIdAtCurrentPosition(par1World, j, i, k, par2StructureBoundingBox) != 0)
                    {
                        par11StructurePieceBlockSelector.selectBlocks(par10Random, j, i, k, i == par4 || i == par7 || j == par3 || j == par6 || k == par5 || k == par8);
                        placeBlockAtCurrentPosition(par1World, par11StructurePieceBlockSelector.getSelectedBlockId(), par11StructurePieceBlockSelector.getSelectedBlockMetaData(), j, i, k, par2StructureBoundingBox);
                    }
                }
            }
        }
    }

    /**
     * arguments: World worldObj, StructureBoundingBox structBB, Random rand, float randLimit, int minX, int minY, int
     * minZ, int maxX, int maxY, int maxZ, int olaceBlockId, int replaceBlockId, boolean alwaysreplace
     */
    protected void randomlyFillWithBlocks(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, float par4, int par5, int par6, int par7, int par8, int par9, int par10, int par11, int par12, boolean par13)
    {
        for (int i = par6; i <= par9; i++)
        {
            for (int j = par5; j <= par8; j++)
            {
                for (int k = par7; k <= par10; k++)
                {
                    if (par3Random.nextFloat() > par4 || par13 && getBlockIdAtCurrentPosition(par1World, j, i, k, par2StructureBoundingBox) == 0)
                    {
                        continue;
                    }

                    if (i == par6 || i == par9 || j == par5 || j == par8 || k == par7 || k == par10)
                    {
                        placeBlockAtCurrentPosition(par1World, par11, 0, j, i, k, par2StructureBoundingBox);
                    }
                    else
                    {
                        placeBlockAtCurrentPosition(par1World, par12, 0, j, i, k, par2StructureBoundingBox);
                    }
                }
            }
        }
    }

    /**
     * Randomly decides if placing or not. Used for Decoration such as Torches and Spiderwebs
     */
    protected void randomlyPlaceBlock(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, float par4, int par5, int par6, int par7, int par8, int par9)
    {
        if (par3Random.nextFloat() < par4)
        {
            placeBlockAtCurrentPosition(par1World, par8, par9, par5, par6, par7, par2StructureBoundingBox);
        }
    }

    /**
     * arguments: World worldObj, StructureBoundingBox structBB, int minX, int minY, int minZ, int maxX, int maxY, int
     * maxZ, int placeBlockId, boolean alwaysreplace
     */
    protected void randomlyRareFillWithBlocks(World par1World, StructureBoundingBox par2StructureBoundingBox, int par3, int par4, int par5, int par6, int par7, int par8, int par9, boolean par10)
    {
        float f = (par6 - par3) + 1;
        float f1 = (par7 - par4) + 1;
        float f2 = (par8 - par5) + 1;
        float f3 = (float)par3 + f / 2.0F;
        float f4 = (float)par5 + f2 / 2.0F;

        for (int i = par4; i <= par7; i++)
        {
            float f5 = (float)(i - par4) / f1;

            for (int j = par3; j <= par6; j++)
            {
                float f6 = ((float)j - f3) / (f * 0.5F);

                for (int k = par5; k <= par8; k++)
                {
                    float f7 = ((float)k - f4) / (f2 * 0.5F);

                    if (par10 && getBlockIdAtCurrentPosition(par1World, j, i, k, par2StructureBoundingBox) == 0)
                    {
                        continue;
                    }

                    float f8 = f6 * f6 + f5 * f5 + f7 * f7;

                    if (f8 <= 1.05F)
                    {
                        placeBlockAtCurrentPosition(par1World, par9, 0, j, i, k, par2StructureBoundingBox);
                    }
                }
            }
        }
    }

    /**
     * Deletes all continuous blocks from selected position upwards. Stops at hitting air.
     */
    protected void clearCurrentPositionBlocksUpwards(World par1World, int par2, int par3, int par4, StructureBoundingBox par5StructureBoundingBox)
    {
        int i = getXWithOffset(par2, par4);
        int j = getYWithOffset(par3);
        int k = getZWithOffset(par2, par4);

        if (!par5StructureBoundingBox.isVecInside(i, j, k))
        {
            return;
        }

        for (; !par1World.isAirBlock(i, j, k) && j < 255; j++)
        {
            par1World.setBlockAndMetadata(i, j, k, 0, 0);
        }
    }

    /**
     * Overwrites air and liquids from selected position downwards, stops at hitting anything else.
     */
    protected void fillCurrentPositionBlocksDownwards(World par1World, int par2, int par3, int par4, int par5, int par6, StructureBoundingBox par7StructureBoundingBox)
    {
        int i = getXWithOffset(par4, par6);
        int j = getYWithOffset(par5);
        int k = getZWithOffset(par4, par6);

        if (!par7StructureBoundingBox.isVecInside(i, j, k))
        {
            return;
        }

        for (; (par1World.isAirBlock(i, j, k) || par1World.getBlockMaterial(i, j, k).isLiquid()) && j > 1; j--)
        {
            par1World.setBlockAndMetadata(i, j, k, par2, par3);
        }
    }

    protected boolean func_56289_a(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, WeightedRandomChestContent par7ArrayOfWeightedRandomChestContent[], int par8)
    {
        int i = getXWithOffset(par4, par6);
        int j = getYWithOffset(par5);
        int k = getZWithOffset(par4, par6);

        if (par2StructureBoundingBox.isVecInside(i, j, k) && par1World.getBlockId(i, j, k) != Block.chest.blockID)
        {
            par1World.setBlockWithNotify(i, j, k, Block.chest.blockID);
            TileEntityChest tileentitychest = (TileEntityChest)par1World.getBlockTileEntity(i, j, k);

            if (tileentitychest != null)
            {
                WeightedRandomChestContent.func_55216_a(par3Random, par7ArrayOfWeightedRandomChestContent, tileentitychest, par8);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    protected boolean func_56291_a(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, int par7, WeightedRandomChestContent par8ArrayOfWeightedRandomChestContent[], int par9)
    {
        int i = getXWithOffset(par4, par6);
        int j = getYWithOffset(par5);
        int k = getZWithOffset(par4, par6);

        if (par2StructureBoundingBox.isVecInside(i, j, k) && par1World.getBlockId(i, j, k) != Block.dispenser.blockID)
        {
            par1World.setBlockAndMetadataWithNotify(i, j, k, Block.dispenser.blockID, getMetadataWithOffset(Block.dispenser.blockID, par7));
            TileEntityDispenser tileentitydispenser = (TileEntityDispenser)par1World.getBlockTileEntity(i, j, k);

            if (tileentitydispenser != null)
            {
                WeightedRandomChestContent.func_56542_a(par3Random, par8ArrayOfWeightedRandomChestContent, tileentitydispenser, par9);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    protected void placeDoorAtCurrentPosition(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, int par7)
    {
        int i = getXWithOffset(par4, par6);
        int j = getYWithOffset(par5);
        int k = getZWithOffset(par4, par6);

        if (par2StructureBoundingBox.isVecInside(i, j, k))
        {
            ItemDoor.placeDoorBlock(par1World, i, j, k, par7, Block.doorWood);
        }
    }
}
