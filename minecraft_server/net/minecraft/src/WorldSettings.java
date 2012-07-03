package net.minecraft.src;

public final class WorldSettings
{
    /** The seed for the map. */
    private final long seed;
    private final EnumGameType field_56527_b;

    /**
     * Switch for the map features. 'true' for enabled, 'false' for disabled.
     */
    private final boolean mapFeaturesEnabled;

    /** True if hardcore mode is enabled */
    private final boolean hardcoreEnabled;
    private final WorldType terrainType;
    private boolean field_55212_f;
    private boolean field_55213_g;

    public WorldSettings(long par1, EnumGameType par3EnumGameType, boolean par4, boolean par5, WorldType par6WorldType)
    {
        seed = par1;
        field_56527_b = par3EnumGameType;
        mapFeaturesEnabled = par4;
        hardcoreEnabled = par5;
        terrainType = par6WorldType;
    }

    public WorldSettings(WorldInfo par1WorldInfo)
    {
        this(par1WorldInfo.getSeed(), par1WorldInfo.func_56510_o(), par1WorldInfo.isMapFeaturesEnabled(), par1WorldInfo.isHardcoreModeEnabled(), par1WorldInfo.getTerrainType());
    }

    public WorldSettings func_56526_a()
    {
        field_55213_g = true;
        return this;
    }

    public boolean func_55211_b()
    {
        return field_55213_g;
    }

    /**
     * Get the seed of the map.
     */
    public long getSeed()
    {
        return seed;
    }

    public EnumGameType func_56525_d()
    {
        return field_56527_b;
    }

    /**
     * Returns true if hardcore mode is enabled, otherwise false
     */
    public boolean getHardcoreEnabled()
    {
        return hardcoreEnabled;
    }

    /**
     * Get whether the map features (e.g. strongholds) generation is enabled or disabled.
     */
    public boolean isMapFeaturesEnabled()
    {
        return mapFeaturesEnabled;
    }

    public WorldType getTerrainType()
    {
        return terrainType;
    }

    public boolean func_55209_h()
    {
        return field_55212_f;
    }

    public static EnumGameType func_56524_a(int par0)
    {
        return EnumGameType.func_56604_a(par0);
    }
}
