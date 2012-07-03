package net.minecraft.src;

public class CreativeTabs
{
    public static final CreativeTabs field_56389_a[] = new CreativeTabs[12];
    public static final CreativeTabs field_56387_b = new CreativeTabBlock(0, "buildingBlocks");
    public static final CreativeTabs field_56388_c = new CreativeTabDeco(1, "decorations");
    public static final CreativeTabs field_56385_d = new CreativeTabRedstone(2, "redstone");
    public static final CreativeTabs field_56386_e = new CreativeTabTransport(3, "transportation");
    public static final CreativeTabs field_56383_f = new CreativeTabMisc(4, "misc");
    public static final CreativeTabs field_56384_g = (new CreativeTabSearch(5, "search")).func_56380_a("search.png");
    public static final CreativeTabs field_56396_h = new CreativeTabFood(6, "food");
    public static final CreativeTabs field_56397_i = new CreativeTabTools(7, "tools");
    public static final CreativeTabs field_56394_j = new CreativeTabCombat(8, "combat");
    public static final CreativeTabs field_56395_k = new CreativeTabBrewing(9, "brewing");
    public static final CreativeTabs field_56392_l = new CreativeTabMaterial(10, "materials");
    public static final CreativeTabs field_56393_m = (new CreativeTabInventory(11, "inventory")).func_56380_a("survival_inv.png").func_56382_b().func_56381_a();
    private final int field_56390_n;
    private final String field_56391_o;
    private String field_56400_p;
    private boolean field_56399_q;
    private boolean field_56398_r;

    public CreativeTabs(int par1, String par2Str)
    {
        field_56400_p = "list_items.png";
        field_56399_q = true;
        field_56398_r = true;
        field_56390_n = par1;
        field_56391_o = par2Str;
        field_56389_a[par1] = this;
    }

    public CreativeTabs func_56380_a(String par1Str)
    {
        field_56400_p = par1Str;
        return this;
    }

    public CreativeTabs func_56381_a()
    {
        field_56398_r = false;
        return this;
    }

    public CreativeTabs func_56382_b()
    {
        field_56399_q = false;
        return this;
    }
}
