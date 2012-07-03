package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandGive extends CommandBase
{
    public CommandGive()
    {
    }

    public String func_55148_a()
    {
        return "give";
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.give.usage", new Object[0]);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 2)
        {
            EntityPlayer entityplayer = func_55170_a(par2ArrayOfStr[0]);
            int i = func_55160_a(par1ICommandSender, par2ArrayOfStr[1], 1);
            int j = 1;
            int k = 0;

            if (Item.itemsList[i] == null)
            {
                throw new NumberInvalidException("commands.give.notFound", new Object[]
                        {
                            Integer.valueOf(i)
                        });
            }

            if (par2ArrayOfStr.length >= 3)
            {
                j = func_55152_a(par1ICommandSender, par2ArrayOfStr[2], 1, 64);
            }

            if (par2ArrayOfStr.length >= 4)
            {
                k = func_55156_a(par1ICommandSender, par2ArrayOfStr[3]);
            }

            ItemStack itemstack = new ItemStack(i, j, k);
            entityplayer.dropPlayerItem(itemstack);
            func_55154_a(par1ICommandSender, "commands.give.success", new Object[]
                    {
                        Item.itemsList[i].func_56453_e(itemstack), Integer.valueOf(i), Integer.valueOf(j), entityplayer.getUsername()
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.give.usage", new Object[0]);
        }
    }

    public List func_55149_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_55157_a(par2ArrayOfStr, func_55169_c());
        }
        else
        {
            return null;
        }
    }

    protected EntityPlayer func_55170_a(String par1Str)
    {
        EntityPlayerMP entityplayermp = MinecraftServer.func_55089_q().func_56173_Y().getPlayerEntity(par1Str);

        if (entityplayermp == null)
        {
            throw new PlayerNotFoundException();
        }
        else
        {
            return entityplayermp;
        }
    }

    protected String[] func_55169_c()
    {
        return MinecraftServer.func_55089_q().getPlayerNamesAsList();
    }
}
