package net.minecraft.src;

public class ItemDoor extends Item
{
    private Material doorMaterial;

    public ItemDoor(int par1, Material par2Material)
    {
        super(par1);
        doorMaterial = par2Material;
        maxStackSize = 1;
        func_56455_a(CreativeTabs.field_56385_d);
    }

    public boolean func_56454_a(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par7 != 1)
        {
            return false;
        }

        par5++;
        Block block;

        if (doorMaterial == Material.wood)
        {
            block = Block.doorWood;
        }
        else
        {
            block = Block.doorSteel;
        }

        if (!par2EntityPlayer.canPlayerEdit(par4, par5, par6) || !par2EntityPlayer.canPlayerEdit(par4, par5 + 1, par6))
        {
            return false;
        }

        if (!block.canPlaceBlockAt(par3World, par4, par5, par6))
        {
            return false;
        }
        else
        {
            int i = MathHelper.floor_double((double)(((par2EntityPlayer.rotationYaw + 180F) * 4F) / 360F) - 0.5D) & 3;
            placeDoorBlock(par3World, par4, par5, par6, i, block);
            par1ItemStack.stackSize--;
            return true;
        }
    }

    public static void placeDoorBlock(World par0World, int par1, int par2, int par3, int par4, Block par5Block)
    {
        byte byte0 = 0;
        byte byte1 = 0;

        if (par4 == 0)
        {
            byte1 = 1;
        }

        if (par4 == 1)
        {
            byte0 = -1;
        }

        if (par4 == 2)
        {
            byte1 = -1;
        }

        if (par4 == 3)
        {
            byte0 = 1;
        }

        int i = (par0World.isBlockNormalCube(par1 - byte0, par2, par3 - byte1) ? 1 : 0) + (par0World.isBlockNormalCube(par1 - byte0, par2 + 1, par3 - byte1) ? 1 : 0);
        int j = (par0World.isBlockNormalCube(par1 + byte0, par2, par3 + byte1) ? 1 : 0) + (par0World.isBlockNormalCube(par1 + byte0, par2 + 1, par3 + byte1) ? 1 : 0);
        boolean flag = par0World.getBlockId(par1 - byte0, par2, par3 - byte1) == par5Block.blockID || par0World.getBlockId(par1 - byte0, par2 + 1, par3 - byte1) == par5Block.blockID;
        boolean flag1 = par0World.getBlockId(par1 + byte0, par2, par3 + byte1) == par5Block.blockID || par0World.getBlockId(par1 + byte0, par2 + 1, par3 + byte1) == par5Block.blockID;
        boolean flag2 = false;

        if (flag && !flag1)
        {
            flag2 = true;
        }
        else if (j > i)
        {
            flag2 = true;
        }

        par0World.editingBlocks = true;
        par0World.setBlockAndMetadataWithNotify(par1, par2, par3, par5Block.blockID, par4);
        par0World.setBlockAndMetadataWithNotify(par1, par2 + 1, par3, par5Block.blockID, 8 | (flag2 ? 1 : 0));
        par0World.editingBlocks = false;
        par0World.notifyBlocksOfNeighborChange(par1, par2, par3, par5Block.blockID);
        par0World.notifyBlocksOfNeighborChange(par1, par2 + 1, par3, par5Block.blockID);
    }
}
