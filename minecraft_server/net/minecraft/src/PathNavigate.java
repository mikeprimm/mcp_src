package net.minecraft.src;

public class PathNavigate
{
    private EntityLiving theEntity;
    private World worldObj;

    /** The PathEntity being followed. */
    private PathEntity currentPath;
    private float speed;

    /**
     * The number of blocks (extra) +/- in each axis that get pulled out as cache for the pathfinder's search space
     */
    private float pathSearchRange;
    private boolean noSunPathfind;

    /** Time, in number of ticks, following the current path */
    private int totalTicks;

    /**
     * The time when the last position check was done (to detect successful movement)
     */
    private int ticksAtLastPos;

    /**
     * Coordinates of the entity's position last time a check was done (part of monitoring getting 'stuck')
     */
    private Vec3 lastPosCheck;

    /**
     * Specifically, if a wooden door block is even considered to be passable by the pathfinder
     */
    private boolean canPassOpenWoodenDoors;

    /** If door blocks are considered passable even when closed */
    private boolean canPassClosedWoodenDoors;

    /** If water blocks are avoided (at least by the pathfinder) */
    private boolean avoidsWater;

    /**
     * If the entity can swim. Swimming AI enables this and the pathfinder will also cause the entity to swim straight
     * upwards when underwater
     */
    private boolean canSwim;

    public PathNavigate(EntityLiving par1EntityLiving, World par2World, float par3)
    {
        noSunPathfind = false;
        lastPosCheck = Vec3.createVectorHelper(0.0D, 0.0D, 0.0D);
        canPassOpenWoodenDoors = true;
        canPassClosedWoodenDoors = false;
        avoidsWater = false;
        canSwim = false;
        theEntity = par1EntityLiving;
        worldObj = par2World;
        pathSearchRange = par3;
    }

    public void setAvoidsWater(boolean par1)
    {
        avoidsWater = par1;
    }

    public boolean getAvoidsWater()
    {
        return avoidsWater;
    }

    public void setBreakDoors(boolean par1)
    {
        canPassClosedWoodenDoors = par1;
    }

    /**
     * Sets if the entity can enter open doors
     */
    public void setEnterDoors(boolean par1)
    {
        canPassOpenWoodenDoors = par1;
    }

    /**
     * Returns true if the entity can break doors, false otherwise
     */
    public boolean getCanBreakDoors()
    {
        return canPassClosedWoodenDoors;
    }

    /**
     * Sets if the path should avoid sunlight
     */
    public void setAvoidSun(boolean par1)
    {
        noSunPathfind = par1;
    }

    /**
     * Sets the speed
     */
    public void setSpeed(float par1)
    {
        speed = par1;
    }

    /**
     * Sets if the entity can swim
     */
    public void setCanSwim(boolean par1)
    {
        canSwim = par1;
    }

    /**
     * Returns the path to the given coordinates
     */
    public PathEntity getPathToXYZ(double par1, double par3, double par5)
    {
        if (!canNavigate())
        {
            return null;
        }
        else
        {
            return worldObj.getEntityPathToXYZ(theEntity, MathHelper.floor_double(par1), (int)par3, MathHelper.floor_double(par5), pathSearchRange, canPassOpenWoodenDoors, canPassClosedWoodenDoors, avoidsWater, canSwim);
        }
    }

    /**
     * Try to find and set a path to XYZ. Returns true if successful.
     */
    public boolean tryMoveToXYZ(double par1, double par3, double par5, float par7)
    {
        PathEntity pathentity = getPathToXYZ(MathHelper.floor_double(par1), (int)par3, MathHelper.floor_double(par5));
        return setPath(pathentity, par7);
    }

    public PathEntity func_48661_a(EntityLiving par1EntityLiving)
    {
        if (!canNavigate())
        {
            return null;
        }
        else
        {
            return worldObj.getPathEntityToEntity(theEntity, par1EntityLiving, pathSearchRange, canPassOpenWoodenDoors, canPassClosedWoodenDoors, avoidsWater, canSwim);
        }
    }

    public boolean func_48652_a(EntityLiving par1EntityLiving, float par2)
    {
        PathEntity pathentity = func_48661_a(par1EntityLiving);

        if (pathentity != null)
        {
            return setPath(pathentity, par2);
        }
        else
        {
            return false;
        }
    }

    /**
     * sets the active path data if path is 100% unique compared to old path, checks to adjust path for sun avoiding
     * ents and stores end coords
     */
    public boolean setPath(PathEntity par1PathEntity, float par2)
    {
        if (par1PathEntity == null)
        {
            currentPath = null;
            return false;
        }

        if (!par1PathEntity.func_48427_a(currentPath))
        {
            currentPath = par1PathEntity;
        }

        if (noSunPathfind)
        {
            removeSunnyPath();
        }

        if (currentPath.getCurrentPathLength() == 0)
        {
            return false;
        }
        else
        {
            speed = par2;
            Vec3 vec3 = getEntityPosition();
            ticksAtLastPos = totalTicks;
            lastPosCheck.xCoord = vec3.xCoord;
            lastPosCheck.yCoord = vec3.yCoord;
            lastPosCheck.zCoord = vec3.zCoord;
            return true;
        }
    }

    /**
     * gets the actively used PathEntity
     */
    public PathEntity getPath()
    {
        return currentPath;
    }

    public void onUpdateNavigation()
    {
        totalTicks++;

        if (noPath())
        {
            return;
        }

        if (canNavigate())
        {
            pathFollow();
        }

        if (noPath())
        {
            return;
        }

        Vec3 vec3 = currentPath.getPosition(theEntity);

        if (vec3 == null)
        {
            return;
        }
        else
        {
            theEntity.getMoveHelper().setMoveTo(vec3.xCoord, vec3.yCoord, vec3.zCoord, speed);
            return;
        }
    }

    private void pathFollow()
    {
        Vec3 vec3 = getEntityPosition();
        int i = currentPath.getCurrentPathLength();
        int f = currentPath.getCurrentPathIndex();

        do
        {
            if (f >= currentPath.getCurrentPathLength())
            {
                break;
            }

            if (currentPath.getPathPointFromIndex(f).yCoord != (int)vec3.yCoord)
            {
                i = f;
                break;
            }

            f++;
        }
        while (true);

        float fa = theEntity.width * theEntity.width;

        for (int j = currentPath.getCurrentPathIndex(); j < i; j++)
        {
            if (vec3.squareDistanceTo(currentPath.getVectorFromIndex(theEntity, j)) < (double)fa)
            {
                currentPath.setCurrentPathIndex(j + 1);
            }
        }

        int k = (int)Math.ceil(theEntity.width);
        int l = (int)theEntity.height + 1;
        int i1 = k;
        int j1 = i - 1;

        do
        {
            if (j1 < currentPath.getCurrentPathIndex())
            {
                break;
            }

            if (isDirectPathBetweenPoints(vec3, currentPath.getVectorFromIndex(theEntity, j1), k, l, i1))
            {
                currentPath.setCurrentPathIndex(j1);
                break;
            }

            j1--;
        }
        while (true);

        if (totalTicks - ticksAtLastPos > 100)
        {
            if (vec3.squareDistanceTo(lastPosCheck) < 2.25D)
            {
                clearPathEntity();
            }

            ticksAtLastPos = totalTicks;
            lastPosCheck.xCoord = vec3.xCoord;
            lastPosCheck.yCoord = vec3.yCoord;
            lastPosCheck.zCoord = vec3.zCoord;
        }
    }

    /**
     * If null path or reached the end
     */
    public boolean noPath()
    {
        return currentPath == null || currentPath.isFinished();
    }

    /**
     * sets active PathEntity to null
     */
    public void clearPathEntity()
    {
        currentPath = null;
    }

    private Vec3 getEntityPosition()
    {
        return Vec3.func_58052_a().func_58076_a(theEntity.posX, getPathableYPos(), theEntity.posZ);
    }

    /**
     * Gets the safe pathing Y position for the entity depending on if it can path swim or not
     */
    private int getPathableYPos()
    {
        if (!theEntity.isInWater() || !canSwim)
        {
            return (int)(theEntity.boundingBox.minY + 0.5D);
        }

        int i = (int)theEntity.boundingBox.minY;
        int j = worldObj.getBlockId(MathHelper.floor_double(theEntity.posX), i, MathHelper.floor_double(theEntity.posZ));
        int k = 0;

        while (j == Block.waterMoving.blockID || j == Block.waterStill.blockID)
        {
            i++;
            j = worldObj.getBlockId(MathHelper.floor_double(theEntity.posX), i, MathHelper.floor_double(theEntity.posZ));

            if (++k > 16)
            {
                return (int)theEntity.boundingBox.minY;
            }
        }

        return i;
    }

    /**
     * If on ground or swimming and can swim
     */
    private boolean canNavigate()
    {
        return theEntity.onGround || canSwim && isInFluid();
    }

    /**
     * Returns true if the entity is in water or lava, false otherwise
     */
    private boolean isInFluid()
    {
        return theEntity.isInWater() || theEntity.handleLavaMovement();
    }

    /**
     * Trims path data from the end to the first sun covered block
     */
    private void removeSunnyPath()
    {
        if (worldObj.canBlockSeeTheSky(MathHelper.floor_double(theEntity.posX), (int)(theEntity.boundingBox.minY + 0.5D), MathHelper.floor_double(theEntity.posZ)))
        {
            return;
        }

        for (int i = 0; i < currentPath.getCurrentPathLength(); i++)
        {
            PathPoint pathpoint = currentPath.getPathPointFromIndex(i);

            if (worldObj.canBlockSeeTheSky(pathpoint.xCoord, pathpoint.yCoord, pathpoint.zCoord))
            {
                currentPath.setCurrentPathLength(i - 1);
                return;
            }
        }
    }

    /**
     * Returns true when an entity of specified size could safely walk in a straight line between the two points. Args:
     * pos1, pos2, entityXSize, entityYSize, entityZSize
     */
    private boolean isDirectPathBetweenPoints(Vec3 par1Vec3, Vec3 par2Vec3, int par3, int par4, int par5)
    {
        int i = MathHelper.floor_double(par1Vec3.xCoord);
        int j = MathHelper.floor_double(par1Vec3.zCoord);
        double d = par2Vec3.xCoord - par1Vec3.xCoord;
        double d1 = par2Vec3.zCoord - par1Vec3.zCoord;
        double d2 = d * d + d1 * d1;

        if (d2 < 1E-008D)
        {
            return false;
        }

        double d3 = 1.0D / Math.sqrt(d2);
        d *= d3;
        d1 *= d3;
        par3 += 2;
        par5 += 2;

        if (!isSafeToStandAt(i, (int)par1Vec3.yCoord, j, par3, par4, par5, par1Vec3, d, d1))
        {
            return false;
        }

        par3 -= 2;
        par5 -= 2;
        double d4 = 1.0D / Math.abs(d);
        double d5 = 1.0D / Math.abs(d1);
        double d6 = (double)(i * 1) - par1Vec3.xCoord;
        double d7 = (double)(j * 1) - par1Vec3.zCoord;

        if (d >= 0.0D)
        {
            d6++;
        }

        if (d1 >= 0.0D)
        {
            d7++;
        }

        d6 /= d;
        d7 /= d1;
        byte byte0 = ((byte)(d >= 0.0D ? 1 : -1));
        byte byte1 = ((byte)(d1 >= 0.0D ? 1 : -1));
        int k = MathHelper.floor_double(par2Vec3.xCoord);
        int l = MathHelper.floor_double(par2Vec3.zCoord);
        int i1 = k - i;

        for (int j1 = l - j; i1 * byte0 > 0 || j1 * byte1 > 0;)
        {
            if (d6 < d7)
            {
                d6 += d4;
                i += byte0;
                i1 = k - i;
            }
            else
            {
                d7 += d5;
                j += byte1;
                j1 = l - j;
            }

            if (!isSafeToStandAt(i, (int)par1Vec3.yCoord, j, par3, par4, par5, par1Vec3, d, d1))
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns true when an entity could stand at a position, including solid blocks under the entire entity. Args:
     * xOffset, yOffset, zOffset, entityXSize, entityYSize, entityZSize, originPosition, vecX, vecZ
     */
    private boolean isSafeToStandAt(int par1, int par2, int par3, int par4, int par5, int par6, Vec3 par7Vec3, double par8, double par10)
    {
        int i = par1 - par4 / 2;
        int j = par3 - par6 / 2;

        if (!isPositionClear(i, par2, j, par4, par5, par6, par7Vec3, par8, par10))
        {
            return false;
        }

        for (int k = i; k < i + par4; k++)
        {
            for (int l = j; l < j + par6; l++)
            {
                double d = ((double)k + 0.5D) - par7Vec3.xCoord;
                double d1 = ((double)l + 0.5D) - par7Vec3.zCoord;

                if (d * par8 + d1 * par10 < 0.0D)
                {
                    continue;
                }

                int i1 = worldObj.getBlockId(k, par2 - 1, l);

                if (i1 <= 0)
                {
                    return false;
                }

                Material material = Block.blocksList[i1].blockMaterial;

                if (material == Material.water && !theEntity.isInWater())
                {
                    return false;
                }

                if (material == Material.lava)
                {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Returns true if an entity does not collide with any solid blocks at the position. Args: xOffset, yOffset,
     * zOffset, entityXSize, entityYSize, entityZSize, originPosition, vecX, vecZ
     */
    private boolean isPositionClear(int par1, int par2, int par3, int par4, int par5, int par6, Vec3 par7Vec3, double par8, double par10)
    {
        for (int i = par1; i < par1 + par4; i++)
        {
            for (int j = par2; j < par2 + par5; j++)
            {
                for (int k = par3; k < par3 + par6; k++)
                {
                    double d = ((double)i + 0.5D) - par7Vec3.xCoord;
                    double d1 = ((double)k + 0.5D) - par7Vec3.zCoord;

                    if (d * par8 + d1 * par10 < 0.0D)
                    {
                        continue;
                    }

                    int l = worldObj.getBlockId(i, j, k);

                    if (l > 0 && !Block.blocksList[l].getBlocksMovement(worldObj, i, j, k))
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
