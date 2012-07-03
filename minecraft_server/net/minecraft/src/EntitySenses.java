package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class EntitySenses
{
    EntityLiving entityObj;
    List field_55308_b;
    List field_55309_c;

    public EntitySenses(EntityLiving par1EntityLiving)
    {
        field_55308_b = new ArrayList();
        field_55309_c = new ArrayList();
        entityObj = par1EntityLiving;
    }

    /**
     * Clears canSeeCachePositive and canSeeCacheNegative.
     */
    public void clearSensingCache()
    {
        field_55308_b.clear();
        field_55309_c.clear();
    }

    /**
     * Checks, whether 'our' entity can see the entity given as argument (true) or not (false), caching the result.
     */
    public boolean canSee(Entity par1Entity)
    {
        if (field_55308_b.contains(par1Entity))
        {
            return true;
        }

        if (field_55309_c.contains(par1Entity))
        {
            return false;
        }

        Profiler.startSection("canSee");
        boolean flag = entityObj.canEntityBeSeen(par1Entity);
        Profiler.endSection();

        if (flag)
        {
            field_55308_b.add(par1Entity);
        }
        else
        {
            field_55309_c.add(par1Entity);
        }

        return flag;
    }
}
