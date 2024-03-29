package net.minecraft.src;

public class WorldType
{
    public static final WorldType worldTypes[] = new WorldType[16];

    /** Default world type. */
    public static final WorldType DEFAULT = (new WorldType(0, "default", 1)).func_48448_d();

    /** Flat world type. */
    public static final WorldType FLAT = new WorldType(1, "flat");
    public static final WorldType field_56782_d = new WorldType(2, "largeBiomes");

    /** Default (1.1) world type. */
    public static final WorldType DEFAULT_1_1 = (new WorldType(8, "default_1_1", 0)).setCanBeCreated(false);
    private final String worldType;

    /** The int version of the ChunkProvider that generated this world. */
    private final int generatorVersion;

    /**
     * Whether this world type can be generated. Normally true; set to false for out-of-date generator versions.
     */
    private boolean canBeCreated;
    private boolean field_48460_h;

    private WorldType(int par1, String par2Str)
    {
        this(par1, par2Str, 0);
    }

    private WorldType(int par1, String par2Str, int par3)
    {
        worldType = par2Str;
        generatorVersion = par3;
        canBeCreated = true;
        worldTypes[par1] = this;
    }

    public String func_48449_a()
    {
        return worldType;
    }

    /**
     * Returns generatorVersion.
     */
    public int getGeneratorVersion()
    {
        return generatorVersion;
    }

    public WorldType func_48451_a(int par1)
    {
        if (this == DEFAULT && par1 == 0)
        {
            return DEFAULT_1_1;
        }
        else
        {
            return this;
        }
    }

    /**
     * Sets canBeCreated to the provided value, and returns this.
     */
    private WorldType setCanBeCreated(boolean par1)
    {
        canBeCreated = par1;
        return this;
    }

    private WorldType func_48448_d()
    {
        field_48460_h = true;
        return this;
    }

    public boolean func_48453_c()
    {
        return field_48460_h;
    }

    public static WorldType parseWorldType(String par0Str)
    {
        WorldType aworldtype[] = worldTypes;
        int i = aworldtype.length;

        for (int j = 0; j < i; j++)
        {
            WorldType worldtype = aworldtype[j];

            if (worldtype != null && worldtype.worldType.equalsIgnoreCase(par0Str))
            {
                return worldtype;
            }
        }

        return null;
    }
}
