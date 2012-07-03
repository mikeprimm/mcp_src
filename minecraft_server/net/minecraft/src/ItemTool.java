package net.minecraft.src;

public class ItemTool extends Item
{
    private Block blocksEffectiveAgainst[];
    protected float efficiencyOnProperMaterial;

    /** Damage versus entities. */
    private int damageVsEntity;

    /** The material this tool is made from. */
    protected EnumToolMaterial toolMaterial;

    protected ItemTool(int par1, int par2, EnumToolMaterial par3EnumToolMaterial, Block par4ArrayOfBlock[])
    {
        super(par1);
        efficiencyOnProperMaterial = 4F;
        toolMaterial = par3EnumToolMaterial;
        blocksEffectiveAgainst = par4ArrayOfBlock;
        maxStackSize = 1;
        setMaxDamage(par3EnumToolMaterial.getMaxUses());
        efficiencyOnProperMaterial = par3EnumToolMaterial.getEfficiencyOnProperMaterial();
        damageVsEntity = par2 + par3EnumToolMaterial.getDamageVsEntity();
        func_56455_a(CreativeTabs.field_56397_i);
    }

    /**
     * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
     * sword
     */
    public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
    {
        Block ablock[] = blocksEffectiveAgainst;
        int i = ablock.length;

        for (int j = 0; j < i; j++)
        {
            Block block = ablock[j];

            if (block == par2Block)
            {
                return efficiencyOnProperMaterial;
            }
        }

        return 1.0F;
    }

    /**
     * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
     * the damage on the stack.
     */
    public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
    {
        par1ItemStack.damageItem(2, par3EntityLiving);
        return true;
    }

    public boolean func_58049_a(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
    {
        if ((double)Block.blocksList[par3].func_58033_f(par2World, par4, par5, par6) != 0.0D)
        {
            par1ItemStack.damageItem(1, par7EntityLiving);
        }

        return true;
    }

    /**
     * Returns the damage against a given entity.
     */
    public int getDamageVsEntity(Entity par1Entity)
    {
        return damageVsEntity;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on material.
     */
    public int getItemEnchantability()
    {
        return toolMaterial.getEnchantability();
    }

    public String func_56458_b()
    {
        return toolMaterial.toString();
    }
}
