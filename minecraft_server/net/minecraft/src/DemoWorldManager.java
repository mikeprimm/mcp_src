package net.minecraft.src;

public class DemoWorldManager extends ItemInWorldManager
{
    private boolean field_56706_c;
    private boolean field_56704_d;
    private int field_56705_e;
    private int field_56703_f;

    public DemoWorldManager(World par1World)
    {
        super(par1World);
        field_56706_c = false;
        field_56704_d = false;
        field_56705_e = 0;
        field_56703_f = 0;
    }

    public void updateBlockRemoving()
    {
        super.updateBlockRemoving();
        field_56703_f++;
        long l = thisWorld.getWorldTime();
        long l1 = l / 24000L + 1L;

        if (!field_56706_c && field_56703_f > 20)
        {
            field_56706_c = true;
            field_56699_b.playerNetServerHandler.sendPacket(new Packet70GameEvent(5, 0));
        }

        field_56704_d = l > 0x1d6b4L;

        if (field_56704_d)
        {
            field_56705_e++;
        }

        if (l % 24000L == 500L)
        {
            if (l1 <= 6L)
            {
                field_56699_b.func_55072_b(field_56699_b.func_55069_a((new StringBuilder()).append("demo.day.").append(l1).toString(), new Object[0]));
            }
        }
        else if (l1 == 1L)
        {
            if (l == 100L)
            {
                field_56699_b.playerNetServerHandler.sendPacket(new Packet70GameEvent(5, 101));
            }
            else if (l == 175L)
            {
                field_56699_b.playerNetServerHandler.sendPacket(new Packet70GameEvent(5, 102));
            }
            else if (l == 250L)
            {
                field_56699_b.playerNetServerHandler.sendPacket(new Packet70GameEvent(5, 103));
            }
        }
        else if (l1 == 5L && l % 24000L == 22000L)
        {
            field_56699_b.func_55072_b(field_56699_b.func_55069_a("demo.day.warning", new Object[0]));
        }
    }

    private void func_56702_d()
    {
        if (field_56705_e > 100)
        {
            field_56699_b.func_55072_b(field_56699_b.func_55069_a("demo.reminder", new Object[0]));
            field_56705_e = 0;
        }
    }

    public void blockClicked(int par1, int par2, int par3, int par4)
    {
        if (field_56704_d)
        {
            func_56702_d();
            return;
        }
        else
        {
            super.blockClicked(par1, par2, par3, par4);
            return;
        }
    }

    public void blockRemoving(int par1, int par2, int par3)
    {
        if (field_56704_d)
        {
            return;
        }
        else
        {
            super.blockRemoving(par1, par2, par3);
            return;
        }
    }

    public boolean blockHarvessted(int par1, int par2, int par3)
    {
        if (field_56704_d)
        {
            return false;
        }
        else
        {
            return super.blockHarvessted(par1, par2, par3);
        }
    }

    public boolean itemUsed(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack)
    {
        if (field_56704_d)
        {
            func_56702_d();
            return false;
        }
        else
        {
            return super.itemUsed(par1EntityPlayer, par2World, par3ItemStack);
        }
    }

    public boolean func_56696_a(EntityPlayer par1EntityPlayer, World par2World, ItemStack par3ItemStack, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (field_56704_d)
        {
            func_56702_d();
            return false;
        }
        else
        {
            return super.func_56696_a(par1EntityPlayer, par2World, par3ItemStack, par4, par5, par6, par7, par8, par9, par10);
        }
    }
}
