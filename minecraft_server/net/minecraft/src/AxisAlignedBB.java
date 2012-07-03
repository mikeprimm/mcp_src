package net.minecraft.src;

public class AxisAlignedBB
{
    private static final ThreadLocal field_56779_g = new AABBLocalPool();
    public double minX;
    public double minY;
    public double minZ;
    public double maxX;
    public double maxY;
    public double maxZ;

    /**
     * Returns a bounding box with the specified bounds. Args: minX, minY, minZ, maxX, maxY, maxZ
     */
    public static AxisAlignedBB getBoundingBox(double par0, double par2, double par4, double par6, double par8, double par10)
    {
        return new AxisAlignedBB(par0, par2, par4, par6, par8, par10);
    }

    public static AABBPool func_58089_a()
    {
        return (AABBPool)field_56779_g.get();
    }

    protected AxisAlignedBB(double par1, double par3, double par5, double par7, double par9, double par11)
    {
        minX = par1;
        minY = par3;
        minZ = par5;
        maxX = par7;
        maxY = par9;
        maxZ = par11;
    }

    /**
     * Sets the bounds of the bounding box. Args: minX, minY, minZ, maxX, maxY, maxZ
     */
    public AxisAlignedBB setBounds(double par1, double par3, double par5, double par7, double par9, double par11)
    {
        minX = par1;
        minY = par3;
        minZ = par5;
        maxX = par7;
        maxY = par9;
        maxZ = par11;
        return this;
    }

    /**
     * Adds the coordinates to the bounding box extending it if the point lies outside the current ranges. Args: x, y, z
     */
    public AxisAlignedBB addCoord(double par1, double par3, double par5)
    {
        double d = minX;
        double d1 = minY;
        double d2 = minZ;
        double d3 = maxX;
        double d4 = maxY;
        double d5 = maxZ;

        if (par1 < 0.0D)
        {
            d += par1;
        }

        if (par1 > 0.0D)
        {
            d3 += par1;
        }

        if (par3 < 0.0D)
        {
            d1 += par3;
        }

        if (par3 > 0.0D)
        {
            d4 += par3;
        }

        if (par5 < 0.0D)
        {
            d2 += par5;
        }

        if (par5 > 0.0D)
        {
            d5 += par5;
        }

        return func_58089_a().func_58067_a(d, d1, d2, d3, d4, d5);
    }

    /**
     * Returns a bounding box expanded by the specified vector (if negative numbers are given it will shrink). Args: x,
     * y, z
     */
    public AxisAlignedBB expand(double par1, double par3, double par5)
    {
        double d = minX - par1;
        double d1 = minY - par3;
        double d2 = minZ - par5;
        double d3 = maxX + par1;
        double d4 = maxY + par3;
        double d5 = maxZ + par5;
        return func_58089_a().func_58067_a(d, d1, d2, d3, d4, d5);
    }

    /**
     * Returns a bounding box offseted by the specified vector (if negative numbers are given it will shrink). Args: x,
     * y, z
     */
    public AxisAlignedBB getOffsetBoundingBox(double par1, double par3, double par5)
    {
        return func_58089_a().func_58067_a(minX + par1, minY + par3, minZ + par5, maxX + par1, maxY + par3, maxZ + par5);
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and Z dimensions, calculate the offset between them
     * in the X dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateXOffset(AxisAlignedBB par1AxisAlignedBB, double par2)
    {
        if (par1AxisAlignedBB.maxY <= minY || par1AxisAlignedBB.minY >= maxY)
        {
            return par2;
        }

        if (par1AxisAlignedBB.maxZ <= minZ || par1AxisAlignedBB.minZ >= maxZ)
        {
            return par2;
        }

        if (par2 > 0.0D && par1AxisAlignedBB.maxX <= minX)
        {
            double d = minX - par1AxisAlignedBB.maxX;

            if (d < par2)
            {
                par2 = d;
            }
        }

        if (par2 < 0.0D && par1AxisAlignedBB.minX >= maxX)
        {
            double d1 = maxX - par1AxisAlignedBB.minX;

            if (d1 > par2)
            {
                par2 = d1;
            }
        }

        return par2;
    }

    /**
     * if instance and the argument bounding boxes overlap in the X and Z dimensions, calculate the offset between them
     * in the Y dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateYOffset(AxisAlignedBB par1AxisAlignedBB, double par2)
    {
        if (par1AxisAlignedBB.maxX <= minX || par1AxisAlignedBB.minX >= maxX)
        {
            return par2;
        }

        if (par1AxisAlignedBB.maxZ <= minZ || par1AxisAlignedBB.minZ >= maxZ)
        {
            return par2;
        }

        if (par2 > 0.0D && par1AxisAlignedBB.maxY <= minY)
        {
            double d = minY - par1AxisAlignedBB.maxY;

            if (d < par2)
            {
                par2 = d;
            }
        }

        if (par2 < 0.0D && par1AxisAlignedBB.minY >= maxY)
        {
            double d1 = maxY - par1AxisAlignedBB.minY;

            if (d1 > par2)
            {
                par2 = d1;
            }
        }

        return par2;
    }

    /**
     * if instance and the argument bounding boxes overlap in the Y and X dimensions, calculate the offset between them
     * in the Z dimension.  return var2 if the bounding boxes do not overlap or if var2 is closer to 0 then the
     * calculated offset.  Otherwise return the calculated offset.
     */
    public double calculateZOffset(AxisAlignedBB par1AxisAlignedBB, double par2)
    {
        if (par1AxisAlignedBB.maxX <= minX || par1AxisAlignedBB.minX >= maxX)
        {
            return par2;
        }

        if (par1AxisAlignedBB.maxY <= minY || par1AxisAlignedBB.minY >= maxY)
        {
            return par2;
        }

        if (par2 > 0.0D && par1AxisAlignedBB.maxZ <= minZ)
        {
            double d = minZ - par1AxisAlignedBB.maxZ;

            if (d < par2)
            {
                par2 = d;
            }
        }

        if (par2 < 0.0D && par1AxisAlignedBB.minZ >= maxZ)
        {
            double d1 = maxZ - par1AxisAlignedBB.minZ;

            if (d1 > par2)
            {
                par2 = d1;
            }
        }

        return par2;
    }

    /**
     * Returns whether the given bounding box intersects with this one. Args: axisAlignedBB
     */
    public boolean intersectsWith(AxisAlignedBB par1AxisAlignedBB)
    {
        if (par1AxisAlignedBB.maxX <= minX || par1AxisAlignedBB.minX >= maxX)
        {
            return false;
        }

        if (par1AxisAlignedBB.maxY <= minY || par1AxisAlignedBB.minY >= maxY)
        {
            return false;
        }

        return par1AxisAlignedBB.maxZ > minZ && par1AxisAlignedBB.minZ < maxZ;
    }

    /**
     * Offsets the current bounding box by the specified coordinates. Args: x, y, z
     */
    public AxisAlignedBB offset(double par1, double par3, double par5)
    {
        minX += par1;
        minY += par3;
        minZ += par5;
        maxX += par1;
        maxY += par3;
        maxZ += par5;
        return this;
    }

    /**
     * Returns if the supplied Vec3D is completely inside the bounding box
     */
    public boolean isVecInside(Vec3 par1Vec3)
    {
        if (par1Vec3.xCoord <= minX || par1Vec3.xCoord >= maxX)
        {
            return false;
        }

        if (par1Vec3.yCoord <= minY || par1Vec3.yCoord >= maxY)
        {
            return false;
        }

        return par1Vec3.zCoord > minZ && par1Vec3.zCoord < maxZ;
    }

    /**
     * Returns a bounding box that is inset by the specified amounts
     */
    public AxisAlignedBB contract(double par1, double par3, double par5)
    {
        double d = minX + par1;
        double d1 = minY + par3;
        double d2 = minZ + par5;
        double d3 = maxX - par1;
        double d4 = maxY - par3;
        double d5 = maxZ - par5;
        return func_58089_a().func_58067_a(d, d1, d2, d3, d4, d5);
    }

    /**
     * Returns a copy of the bounding box.
     */
    public AxisAlignedBB copy()
    {
        return func_58089_a().func_58067_a(minX, minY, minZ, maxX, maxY, maxZ);
    }

    public MovingObjectPosition calculateIntercept(Vec3 par1Vec3, Vec3 par2Vec3)
    {
        Vec3 vec3 = par1Vec3.getIntermediateWithXValue(par2Vec3, minX);
        Vec3 vec3_1 = par1Vec3.getIntermediateWithXValue(par2Vec3, maxX);
        Vec3 vec3_2 = par1Vec3.getIntermediateWithYValue(par2Vec3, minY);
        Vec3 vec3_3 = par1Vec3.getIntermediateWithYValue(par2Vec3, maxY);
        Vec3 vec3_4 = par1Vec3.getIntermediateWithZValue(par2Vec3, minZ);
        Vec3 vec3_5 = par1Vec3.getIntermediateWithZValue(par2Vec3, maxZ);

        if (!isVecInYZ(vec3))
        {
            vec3 = null;
        }

        if (!isVecInYZ(vec3_1))
        {
            vec3_1 = null;
        }

        if (!isVecInXZ(vec3_2))
        {
            vec3_2 = null;
        }

        if (!isVecInXZ(vec3_3))
        {
            vec3_3 = null;
        }

        if (!isVecInXY(vec3_4))
        {
            vec3_4 = null;
        }

        if (!isVecInXY(vec3_5))
        {
            vec3_5 = null;
        }

        Vec3 vec3_6 = null;

        if (vec3 != null && (vec3_6 == null || par1Vec3.squareDistanceTo(vec3) < par1Vec3.squareDistanceTo(vec3_6)))
        {
            vec3_6 = vec3;
        }

        if (vec3_1 != null && (vec3_6 == null || par1Vec3.squareDistanceTo(vec3_1) < par1Vec3.squareDistanceTo(vec3_6)))
        {
            vec3_6 = vec3_1;
        }

        if (vec3_2 != null && (vec3_6 == null || par1Vec3.squareDistanceTo(vec3_2) < par1Vec3.squareDistanceTo(vec3_6)))
        {
            vec3_6 = vec3_2;
        }

        if (vec3_3 != null && (vec3_6 == null || par1Vec3.squareDistanceTo(vec3_3) < par1Vec3.squareDistanceTo(vec3_6)))
        {
            vec3_6 = vec3_3;
        }

        if (vec3_4 != null && (vec3_6 == null || par1Vec3.squareDistanceTo(vec3_4) < par1Vec3.squareDistanceTo(vec3_6)))
        {
            vec3_6 = vec3_4;
        }

        if (vec3_5 != null && (vec3_6 == null || par1Vec3.squareDistanceTo(vec3_5) < par1Vec3.squareDistanceTo(vec3_6)))
        {
            vec3_6 = vec3_5;
        }

        if (vec3_6 == null)
        {
            return null;
        }

        byte byte0 = -1;

        if (vec3_6 == vec3)
        {
            byte0 = 4;
        }

        if (vec3_6 == vec3_1)
        {
            byte0 = 5;
        }

        if (vec3_6 == vec3_2)
        {
            byte0 = 0;
        }

        if (vec3_6 == vec3_3)
        {
            byte0 = 1;
        }

        if (vec3_6 == vec3_4)
        {
            byte0 = 2;
        }

        if (vec3_6 == vec3_5)
        {
            byte0 = 3;
        }

        return new MovingObjectPosition(0, 0, 0, byte0, vec3_6);
    }

    /**
     * Checks if the specified vector is within the YZ dimensions of the bounding box. Args: Vec3D
     */
    private boolean isVecInYZ(Vec3 par1Vec3)
    {
        if (par1Vec3 == null)
        {
            return false;
        }
        else
        {
            return par1Vec3.yCoord >= minY && par1Vec3.yCoord <= maxY && par1Vec3.zCoord >= minZ && par1Vec3.zCoord <= maxZ;
        }
    }

    /**
     * Checks if the specified vector is within the XZ dimensions of the bounding box. Args: Vec3D
     */
    private boolean isVecInXZ(Vec3 par1Vec3)
    {
        if (par1Vec3 == null)
        {
            return false;
        }
        else
        {
            return par1Vec3.xCoord >= minX && par1Vec3.xCoord <= maxX && par1Vec3.zCoord >= minZ && par1Vec3.zCoord <= maxZ;
        }
    }

    /**
     * Checks if the specified vector is within the XY dimensions of the bounding box. Args: Vec3D
     */
    private boolean isVecInXY(Vec3 par1Vec3)
    {
        if (par1Vec3 == null)
        {
            return false;
        }
        else
        {
            return par1Vec3.xCoord >= minX && par1Vec3.xCoord <= maxX && par1Vec3.yCoord >= minY && par1Vec3.yCoord <= maxY;
        }
    }

    /**
     * Sets the bounding box to the same bounds as the bounding box passed in. Args: axisAlignedBB
     */
    public void setBB(AxisAlignedBB par1AxisAlignedBB)
    {
        minX = par1AxisAlignedBB.minX;
        minY = par1AxisAlignedBB.minY;
        minZ = par1AxisAlignedBB.minZ;
        maxX = par1AxisAlignedBB.maxX;
        maxY = par1AxisAlignedBB.maxY;
        maxZ = par1AxisAlignedBB.maxZ;
    }

    public String toString()
    {
        return (new StringBuilder()).append("box[").append(minX).append(", ").append(minY).append(", ").append(minZ).append(" -> ").append(maxX).append(", ").append(maxY).append(", ").append(maxZ).append("]").toString();
    }
}
