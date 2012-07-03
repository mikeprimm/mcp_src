package net.minecraft.src;

import java.util.*;

class StructureVillageStart extends StructureStart
{
    /** well ... thats what it does */
    private boolean hasMoreThanTwoComponents;

    public StructureVillageStart(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        hasMoreThanTwoComponents = false;
        ArrayList arraylist = StructureVillagePieces.getStructureVillageWeightedPieceList(par2Random, par5);
        ComponentVillageStartPiece componentvillagestartpiece = new ComponentVillageStartPiece(par1World.getWorldChunkManager(), 0, par2Random, (par3 << 4) + 2, (par4 << 4) + 2, arraylist, par5);
        components.add(componentvillagestartpiece);
        componentvillagestartpiece.buildComponent(componentvillagestartpiece, components, par2Random);
        ArrayList arraylist1 = componentvillagestartpiece.field_35387_f;

        for (ArrayList arraylist2 = componentvillagestartpiece.field_35389_e; !arraylist1.isEmpty() || !arraylist2.isEmpty();)
        {
            if (arraylist1.isEmpty())
            {
                int i = par2Random.nextInt(arraylist2.size());
                StructureComponent structurecomponent = (StructureComponent)arraylist2.remove(i);
                structurecomponent.buildComponent(componentvillagestartpiece, components, par2Random);
            }
            else
            {
                int j = par2Random.nextInt(arraylist1.size());
                StructureComponent structurecomponent1 = (StructureComponent)arraylist1.remove(j);
                structurecomponent1.buildComponent(componentvillagestartpiece, components, par2Random);
            }
        }

        updateBoundingBox();
        int k = 0;
        Iterator iterator = components.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            StructureComponent structurecomponent2 = (StructureComponent)iterator.next();

            if (!(structurecomponent2 instanceof ComponentVillageRoadPiece))
            {
                k++;
            }
        }
        while (true);

        hasMoreThanTwoComponents = k > 2;
    }

    /**
     * currently only defined for Villages, returns true if Village has more than 2 non-road components
     */
    public boolean isSizeableStructure()
    {
        return hasMoreThanTwoComponents;
    }
}
