package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class ComponentVillageTorch extends ComponentVillage
{
    private int averageGroundLevel;

    public ComponentVillageTorch(ComponentVillageStartPiece par1ComponentVillageStartPiece, int par2, Random par3Random, StructureBoundingBox par4StructureBoundingBox, int par5)
    {
        super(par1ComponentVillageStartPiece, par2);
        averageGroundLevel = -1;
        coordBaseMode = par5;
        boundingBox = par4StructureBoundingBox;
    }

    public static StructureBoundingBox func_56316_a(ComponentVillageStartPiece par0ComponentVillageStartPiece, List par1List, Random par2Random, int par3, int par4, int par5, int par6)
    {
        StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par3, par4, par5, 0, 0, 0, 3, 4, 2, par6);

        if (StructureComponent.findIntersecting(par1List, structureboundingbox) != null)
        {
            return null;
        }
        else
        {
            return structureboundingbox;
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

            boundingBox.offset(0, ((averageGroundLevel - boundingBox.maxY) + 4) - 1, 0);
        }

        fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 2, 3, 1, 0, 0, false);
        placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 1, 0, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 1, 1, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.fence.blockID, 0, 1, 2, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.cloth.blockID, 15, 1, 3, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 15, 0, 3, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 15, 1, 3, 1, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 15, 2, 3, 0, par3StructureBoundingBox);
        placeBlockAtCurrentPosition(par1World, Block.torchWood.blockID, 15, 1, 3, -1, par3StructureBoundingBox);
        return true;
    }
}
