package net.minecraft.src;

public class DerivedWorldInfo extends WorldInfo
{
    private final WorldInfo field_56518_a;

    public DerivedWorldInfo(WorldInfo par1WorldInfo)
    {
        field_56518_a = par1WorldInfo;
    }

    /**
     * Gets the NBTTagCompound for the worldInfo
     */
    public NBTTagCompound getNBTTagCompound()
    {
        return field_56518_a.getNBTTagCompound();
    }

    public NBTTagCompound func_56514_a(NBTTagCompound par1NBTTagCompound)
    {
        return field_56518_a.func_56514_a(par1NBTTagCompound);
    }

    /**
     * Returns the seed of current world.
     */
    public long getSeed()
    {
        return field_56518_a.getSeed();
    }

    /**
     * Returns the x spawn position
     */
    public int getSpawnX()
    {
        return field_56518_a.getSpawnX();
    }

    /**
     * Return the Y axis spawning point of the player.
     */
    public int getSpawnY()
    {
        return field_56518_a.getSpawnY();
    }

    /**
     * Returns the z spawn position
     */
    public int getSpawnZ()
    {
        return field_56518_a.getSpawnZ();
    }

    /**
     * Get current world time
     */
    public long getWorldTime()
    {
        return field_56518_a.getWorldTime();
    }

    public NBTTagCompound func_56509_g()
    {
        return field_56518_a.func_56509_g();
    }

    public int getDimension()
    {
        return field_56518_a.getDimension();
    }

    public String func_55310_h()
    {
        return field_56518_a.func_55310_h();
    }

    /**
     * Returns the save version of this world
     */
    public int getSaveVersion()
    {
        return field_56518_a.getSaveVersion();
    }

    /**
     * Returns true if it is thundering, false otherwise.
     */
    public boolean isThundering()
    {
        return field_56518_a.isThundering();
    }

    /**
     * Returns the number of ticks until next thunderbolt.
     */
    public int getThunderTime()
    {
        return field_56518_a.getThunderTime();
    }

    /**
     * Returns true if it is raining, false otherwise.
     */
    public boolean isRaining()
    {
        return field_56518_a.isRaining();
    }

    /**
     * Return the number of ticks until rain.
     */
    public int getRainTime()
    {
        return field_56518_a.getRainTime();
    }

    public EnumGameType func_56510_o()
    {
        return field_56518_a.func_56510_o();
    }

    /**
     * Set current world time
     */
    public void setWorldTime(long l)
    {
    }

    /**
     * Sets the spawn zone position. Args: x, y, z
     */
    public void setSpawnPosition(int i, int j, int k)
    {
    }

    public void setWorldName(String s)
    {
    }

    /**
     * Sets the save version of the world
     */
    public void setSaveVersion(int i)
    {
    }

    /**
     * Sets whether it is thundering or not.
     */
    public void setThundering(boolean flag)
    {
    }

    /**
     * Defines the number of ticks until next thunderbolt.
     */
    public void setThunderTime(int i)
    {
    }

    /**
     * Sets whether it is raining or not.
     */
    public void setRaining(boolean flag)
    {
    }

    /**
     * Sets the number of ticks until rain.
     */
    public void setRainTime(int i)
    {
    }

    /**
     * Get whether the map features (e.g. strongholds) generation is enabled or disabled.
     */
    public boolean isMapFeaturesEnabled()
    {
        return field_56518_a.isMapFeaturesEnabled();
    }

    /**
     * Returns true if hardcore mode is enabled, otherwise false
     */
    public boolean isHardcoreModeEnabled()
    {
        return field_56518_a.isHardcoreModeEnabled();
    }

    public WorldType getTerrainType()
    {
        return field_56518_a.getTerrainType();
    }

    public void setTerrainType(WorldType worldtype)
    {
    }

    public boolean func_56515_s()
    {
        return field_56518_a.func_56515_s();
    }

    public boolean func_56513_t()
    {
        return field_56518_a.func_56513_t();
    }

    public void func_56511_c(boolean flag)
    {
    }
}
