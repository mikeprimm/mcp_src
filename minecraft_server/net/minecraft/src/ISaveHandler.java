package net.minecraft.src;

import java.io.File;

public interface ISaveHandler
{
    /**
     * Loads and returns the world info
     */
    public abstract WorldInfo loadWorldInfo();

    /**
     * Checks the session lock to prevent save collisions
     */
    public abstract void checkSessionLock() throws MinecraftException;

    /**
     * initializes and returns the chunk loader for the specified world provider
     */
    public abstract IChunkLoader getChunkLoader(WorldProvider worldprovider);

    public abstract void func_56407_a(WorldInfo worldinfo, NBTTagCompound nbttagcompound);

    /**
     * used to update level.dat from old format to MCRegion format
     */
    public abstract void saveWorldInfo(WorldInfo worldinfo);

    public abstract IPlayerFileData getPlayerNBTManager();

    public abstract void func_22093_e();

    /**
     * Gets the file location of the given map
     */
    public abstract File getMapFileFromName(String s);

    public abstract String func_56406_g();
}
