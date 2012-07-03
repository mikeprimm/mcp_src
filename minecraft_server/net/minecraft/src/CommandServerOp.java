package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandServerOp extends CommandBase
{
    public CommandServerOp()
    {
    }

    public String func_55148_a()
    {
        return "op";
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.op.usage", new Object[0]);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1 && par2ArrayOfStr[0].length() > 0)
        {
            MinecraftServer.func_55089_q().func_56173_Y().addOp(par2ArrayOfStr[0]);
            func_55154_a(par1ICommandSender, "commands.op.success", new Object[]
                    {
                        par2ArrayOfStr[0]
                    });
            return;
        }
        else
        {
            throw new WrongUsageException("commands.op.usage", new Object[0]);
        }
    }

    public List func_55149_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            String s = par2ArrayOfStr[par2ArrayOfStr.length - 1];
            ArrayList arraylist = new ArrayList();
            String as[] = MinecraftServer.func_55089_q().getPlayerNamesAsList();
            int i = as.length;

            for (int j = 0; j < i; j++)
            {
                String s1 = as[j];

                if (!MinecraftServer.func_55089_q().func_56173_Y().isOp(s1) && func_55155_a(s, s1))
                {
                    arraylist.add(s1);
                }
            }

            return arraylist;
        }
        else
        {
            return null;
        }
    }
}
