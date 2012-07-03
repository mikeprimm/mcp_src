package net.minecraft.src;

public class MovingObjectPosition
{
    /** What type of ray trace hit was this? 0 = block, 1 = entity */
    public EnumMovingObjectType typeOfHit;

    /** x coordinate of the block ray traced against */
    public int blockX;

    /** y coordinate of the block ray traced against */
    public int blockY;

    /** z coordinate of the block ray traced against */
    public int blockZ;

    /**
     * Which side was hit. If its -1 then it went the full length of the ray trace. Bottom = 0, Top = 1, East = 2, West
     * = 3, North = 4, South = 5.
     */
    public int sideHit;

    /** The vector position of the hit */
    public Vec3 hitVec;

    /** The hit entity */
    public Entity entityHit;

    public MovingObjectPosition(int par1, int par2, int par3, int par4, Vec3 par5Vec3)
    {
        typeOfHit = EnumMovingObjectType.TILE;
        blockX = par1;
        blockY = par2;
        blockZ = par3;
        sideHit = par4;
        hitVec = Vec3.func_58052_a().func_58076_a(par5Vec3.xCoord, par5Vec3.yCoord, par5Vec3.zCoord);
    }

    public MovingObjectPosition(Entity par1Entity)
    {
        typeOfHit = EnumMovingObjectType.ENTITY;
        entityHit = par1Entity;
        hitVec = Vec3.func_58052_a().func_58076_a(par1Entity.posX, par1Entity.posY, par1Entity.posZ);
    }
}
