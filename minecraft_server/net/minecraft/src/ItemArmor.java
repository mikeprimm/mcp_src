package net.minecraft.src;

public class ItemArmor extends Item
{
    private static final int maxDamageArray[] =
    {
        11, 16, 15, 13
    };

    /**
     * Stores the armor type: 0 is helmet, 1 is plate, 2 is legs and 3 is boots
     */
    public final int armorType;

    /** Holds the amount of damage that the armor reduces at full durability. */
    public final int damageReduceAmount;

    /**
     * Used on RenderPlayer to select the correspondent armor to be rendered on the player: 0 is cloth, 1 is chain, 2 is
     * iron, 3 is diamond and 4 is gold.
     */
    public final int renderIndex;

    /** The EnumArmorMaterial used for this ItemArmor */
    private final EnumArmorMaterial material;

    public ItemArmor(int par1, EnumArmorMaterial par2EnumArmorMaterial, int par3, int par4)
    {
        super(par1);
        material = par2EnumArmorMaterial;
        armorType = par4;
        renderIndex = par3;
        damageReduceAmount = par2EnumArmorMaterial.getDamageReductionAmount(par4);
        setMaxDamage(par2EnumArmorMaterial.getDurability(par4));
        maxStackSize = 1;
        func_56455_a(CreativeTabs.field_56394_j);
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return material.getEnchantability();
    }

    /**
     * Returns the 'max damage' factor array for the armor, each piece of armor have a durability factor (that gets
     * multiplied by armor material factor)
     */
    static int[] getMaxDamageArray()
    {
        return maxDamageArray;
    }
}
