package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageHouse4_Garden extends ComponentVillage
{
    private int averageGroundLevel;
    private final boolean isRoofAccessible;

    public ComponentVillageHouse4_Garden(ComponentVillageStartPiece par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
    {
        super(par1ComponentVillageStartPiece, par2);
        averageGroundLevel = -1;
        coordBaseMode = par5;
        boundingBox = par4StructureBoundingBox;
        isRoofAccessible = par3Random.nextBoolean();
    }

    public static ComponentVillageHouse4_Garden func_56311_a(ComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 5, 6, 5, par6);

        if (StructureComponent.findIntersecting(par1List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return new ComponentVillageHouse4_Garden(par0ComponentVillageStartPiece, par7, par2Random, structureboundingbox, par6);
        }
    }

    /**
     * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at
     * the end, it adds Fences...
     */
    public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
    {
        if (averageGroundLevel < 0)
        {
            averageGroundLevel = getAverageGroundLevel(par1World, par3StructureBoundingBox);

            if (averageGroundLevel < 0)
            {
                return true;
            }

            boundingBox.offset(0, ((averageGroundLevel - boundingBox.maxY) + 6) - 1, 0);
        }

        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 0, 4, Block.cobblestone.blockID, Block.cobblestone.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 4, 0, 4, 4, 4, Block.wood.blockID, Block.wood.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 4, 1, 3, 4, 3, Block.planks.blockID, Block.planks.blockID, false);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 0, 1, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 0, 2, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 0, 3, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 4, 1, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 4, 2, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 4, 3, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 0, 1, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 0, 2, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 0, 3, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 4, 1, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 4, 2, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cobblestone.blockID, 0, 4, 3, 4, par3StructureBoundingBox);
        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, Block.planks.blockID, Block.planks.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 3, Block.planks.blockID, Block.planks.blockID, false);
        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 4, 3, 3, 4, Block.planks.blockID, Block.planks.blockID, false);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 0, 2, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 2, 2, 4, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.thinGlass.blockID, 0, 4, 2, 2, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 1, 1, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 1, 2, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 1, 3, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 2, 3, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 3, 3, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 3, 2, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.planks.blockID, 0, 3, 1, 0, par3StructureBoundingBox);

        if (getBlockIdAtCurrentPosition(par1World, 2, 0, -1, par3StructureBoundingBox) == 0 && getBlockIdAtCurrentPosition(par1World, 2, -1, -1, par3StructureBoundingBox) != 0)
        {
            placeBlockAtCurrentPosition(par1World, Block.stairCompactCobblestone.blockID, getMetadataWithOffset(Block.stairCompactCobblestone.blockID, 3), 2, 0, -1, par3StructureBoundingBox);
        }

        fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 1, 3, 3, 3, 0, 0, false);

        if (isRoofAccessible)
        {
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 0, 5, 0, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 1, 5, 0, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 2, 5, 0, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 3, 5, 0, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 4, 5, 0, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 0, 5, 4, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 1, 5, 4, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 2, 5, 4, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 3, 5, 4, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 4, 5, 4, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 4, 5, 1, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 4, 5, 2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 4, 5, 3, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 0, 5, 1, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 0, 5, 2, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 0, 5, 3, par3StructureBoundingBox);
        }

        if (isRoofAccessible)
        {
            int i = getMetadataWithOffset(Block.ladder.blockID, 3);
            placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, i, 3, 1, 3, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, i, 3, 2, 3, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, i, 3, 3, 3, par3StructureBoundingBox);
            placeBlockAtCurrentPosition(par1World, Block.ladder.blockID, i, 3, 4, 3, par3StructureBoundingBox);
        }

        placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 0, 2, 3, 1, par3StructureBoundingBox);

        for (int j = 0; j < 5; j++)
        {
            for (int k = 0; k < 5; k++)
            {
                clearCurrentPositionBlocksUpwards(par1World, k, 6, j, par3StructureBoundingBox);
                fillCurrentPositionBlocksDownwards(par1World, Block.cobblestone.blockID, 0, k, -1, j, par3StructureBoundingBox);
            }
        }

        spawnVillagers(par1World, par3StructureBoundingBox, 1, 1, 2, 1);
        return true;
    }
}
