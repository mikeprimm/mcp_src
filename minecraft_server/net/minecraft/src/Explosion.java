package net.minecraft.src;

import java.util.*;

public class Explosion
{
    /** whether or not the explosion sets fire to blocks around it */
    public boolean isFlaming;
    private int field_56556_h;
    private Random explosionRNG;
    private World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public Entity exploder;
    public float explosionSize;
    public List field_58065_g;
    private Map field_56555_k;

    public Explosion(World par1World, Entity par2Entity, double par3, double par5, double par7, float par9)
    {
        isFlaming = false;
        field_56556_h = 16;
        explosionRNG = new Random();
        field_58065_g = new ArrayList();
        field_56555_k = new HashMap();
        worldObj = par1World;
        exploder = par2Entity;
        explosionSize = par9;
        explosionX = par3;
        explosionY = par5;
        explosionZ = par7;
    }

    /**
     * Does the first part of the explosion (destroy blocks)
     */
    public void doExplosionA()
    {
        float f = explosionSize;
        HashSet hashset = new HashSet();

        for (int i = 0; i < field_56556_h; i++)
        {
            for (int k = 0; k < field_56556_h; k++)
            {
                label0:

                for (int i1 = 0; i1 < field_56556_h; i1++)
                {
                    if (i != 0 && i != field_56556_h - 1 && k != 0 && k != field_56556_h - 1 && i1 != 0 && i1 != field_56556_h - 1)
                    {
                        continue;
                    }

                    double d = ((float)i / ((float)field_56556_h - 1.0F)) * 2.0F - 1.0F;
                    double d1 = ((float)k / ((float)field_56556_h - 1.0F)) * 2.0F - 1.0F;
                    double d2 = ((float)i1 / ((float)field_56556_h - 1.0F)) * 2.0F - 1.0F;
                    double d3 = Math.sqrt(d * d + d1 * d1 + d2 * d2);
                    d /= d3;
                    d1 /= d3;
                    d2 /= d3;
                    float f1 = explosionSize * (0.7F + worldObj.rand.nextFloat() * 0.6F);
                    double d5 = explosionX;
                    double d7 = explosionY;
                    double d9 = explosionZ;
                    float f2 = 0.3F;

                    do
                    {
                        if (f1 <= 0.0F)
                        {
                            continue label0;
                        }

                        int k2 = MathHelper.floor_double(d5);
                        int l2 = MathHelper.floor_double(d7);
                        int i3 = MathHelper.floor_double(d9);
                        int j3 = worldObj.getBlockId(k2, l2, i3);

                        if (j3 > 0)
                        {
                            f1 -= (Block.blocksList[j3].getExplosionResistance(exploder) + 0.3F) * f2;
                        }

                        if (f1 > 0.0F)
                        {
                            hashset.add(new ChunkPosition(k2, l2, i3));
                        }

                        d5 += d * (double)f2;
                        d7 += d1 * (double)f2;
                        d9 += d2 * (double)f2;
                        f1 -= f2 * 0.75F;
                    }
                    while (true);
                }
            }
        }

        field_58065_g.addAll(hashset);
        explosionSize *= 2.0F;
        int j = MathHelper.floor_double(explosionX - (double)explosionSize - 1.0D);
        int l = MathHelper.floor_double(explosionX + (double)explosionSize + 1.0D);
        int j1 = MathHelper.floor_double(explosionY - (double)explosionSize - 1.0D);
        int k1 = MathHelper.floor_double(explosionY + (double)explosionSize + 1.0D);
        int l1 = MathHelper.floor_double(explosionZ - (double)explosionSize - 1.0D);
        int i2 = MathHelper.floor_double(explosionZ + (double)explosionSize + 1.0D);
        List list = worldObj.getEntitiesWithinAABBExcludingEntity(exploder, AxisAlignedBB.func_58089_a().func_58067_a(j, j1, l1, l, k1, i2));
        Vec3 vec3 = Vec3.func_58052_a().func_58076_a(explosionX, explosionY, explosionZ);

        for (int j2 = 0; j2 < list.size(); j2++)
        {
            Entity entity = (Entity)list.get(j2);
            double d4 = entity.getDistance(explosionX, explosionY, explosionZ) / (double)explosionSize;

            if (d4 > 1.0D)
            {
                continue;
            }

            double d6 = entity.posX - explosionX;
            double d8 = (entity.posY + (double)entity.getEyeHeight()) - explosionY;
            double d10 = entity.posZ - explosionZ;
            double d11 = MathHelper.sqrt_double(d6 * d6 + d8 * d8 + d10 * d10);

            if (d11 == 0.0D)
            {
                continue;
            }

            d6 /= d11;
            d8 /= d11;
            d10 /= d11;
            double d12 = worldObj.getBlockDensity(vec3, entity.boundingBox);
            double d13 = (1.0D - d4) * d12;
            entity.attackEntityFrom(DamageSource.explosion, (int)(((d13 * d13 + d13) / 2D) * 8D * (double)explosionSize + 1.0D));
            entity.motionX += d6 * d13;
            entity.motionY += d8 * d13;
            entity.motionZ += d10 * d13;

            if (entity instanceof EntityPlayer)
            {
                field_56555_k.put((EntityPlayer)entity, Vec3.func_58052_a().func_58076_a(d6 * d13, d8 * d13, d10 * d13));
            }
        }

        explosionSize = f;
    }

    /**
     * Does the second part of the explosion (sound, particles, drop spawn)
     */
    public void doExplosionB(boolean par1)
    {
        worldObj.playSoundEffect(explosionX, explosionY, explosionZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
        worldObj.spawnParticle("hugeexplosion", explosionX, explosionY, explosionZ, 0.0D, 0.0D, 0.0D);
        Iterator iterator = field_58065_g.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ChunkPosition chunkposition = (ChunkPosition)iterator.next();
            int i = chunkposition.x;
            int k = chunkposition.y;
            int i1 = chunkposition.z;
            int k1 = worldObj.getBlockId(i, k, i1);

            if (par1)
            {
                double d = (float)i + worldObj.rand.nextFloat();
                double d1 = (float)k + worldObj.rand.nextFloat();
                double d2 = (float)i1 + worldObj.rand.nextFloat();
                double d3 = d - explosionX;
                double d4 = d1 - explosionY;
                double d5 = d2 - explosionZ;
                double d6 = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
                d3 /= d6;
                d4 /= d6;
                d5 /= d6;
                double d7 = 0.5D / (d6 / (double)explosionSize + 0.10000000000000001D);
                d7 *= worldObj.rand.nextFloat() * worldObj.rand.nextFloat() + 0.3F;
                d3 *= d7;
                d4 *= d7;
                d5 *= d7;
                worldObj.spawnParticle("explode", (d + explosionX * 1.0D) / 2D, (d1 + explosionY * 1.0D) / 2D, (d2 + explosionZ * 1.0D) / 2D, d3, d4, d5);
                worldObj.spawnParticle("smoke", d, d1, d2, d3, d4, d5);
            }

            if (k1 > 0)
            {
                Block.blocksList[k1].dropBlockAsItemWithChance(worldObj, i, k, i1, worldObj.getBlockMetadata(i, k, i1), 0.3F, 0);
                worldObj.setBlockWithNotify(i, k, i1, 0);
                Block.blocksList[k1].onBlockDestroyedByExplosion(worldObj, i, k, i1);
            }
        }
        while (true);

        if (isFlaming)
        {
            Iterator iterator1 = field_58065_g.iterator();

            do
            {
                if (!iterator1.hasNext())
                {
                    break;
                }

                ChunkPosition chunkposition1 = (ChunkPosition)iterator1.next();
                int j = chunkposition1.x;
                int l = chunkposition1.y;
                int j1 = chunkposition1.z;
                int l1 = worldObj.getBlockId(j, l, j1);
                int i2 = worldObj.getBlockId(j, l - 1, j1);

                if (l1 == 0 && Block.opaqueCubeLookup[i2] && explosionRNG.nextInt(3) == 0)
                {
                    worldObj.setBlockWithNotify(j, l, j1, Block.fire.blockID);
                }
            }
            while (true);
        }
    }

    public Map func_56554_b()
    {
        return field_56555_k;
    }
}
