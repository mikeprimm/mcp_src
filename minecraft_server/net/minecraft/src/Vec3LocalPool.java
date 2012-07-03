package net.minecraft.src;

final class Vec3LocalPool extends ThreadLocal
{
    Vec3LocalPool()
    {
    }

    protected Vec3Pool func_58086_a()
    {
        return new Vec3Pool(300, 2000);
    }

    protected Object initialValue()
    {
        return func_58086_a();
    }
}
