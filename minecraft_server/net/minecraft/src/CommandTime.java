package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandTime extends CommandBase
{
    public CommandTime()
    {
    }

    public String func_55148_a()
    {
        return "time";
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.time.usage", new Object[0]);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length > 1)
        {
            if (par2ArrayOfStr[0].equals("set"))
            {
                int i;

                if (par2ArrayOfStr[1].equals("day"))
                {
                    i = 0;
                }
                else if (par2ArrayOfStr[1].equals("night"))
                {
                    i = 12500;
                }
                else
                {
                    i = func_55160_a(par1ICommandSender, par2ArrayOfStr[1], 0);
                }

                func_55172_a(par1ICommandSender, i);
                func_55154_a(par1ICommandSender, "commands.time.set", new Object[]
                        {
                            Integer.valueOf(i)
                        });
                return;
            }

            if (par2ArrayOfStr[0].equals("add"))
            {
                int j = func_55160_a(par1ICommandSender, par2ArrayOfStr[1], 0);
                func_55171_b(par1ICommandSender, j);
                func_55154_a(par1ICommandSender, "commands.time.added", new Object[]
                        {
                            Integer.valueOf(j)
                        });
                return;
            }
        }

        throw new WrongUsageException("commands.time.usage", new Object[0]);
    }

    public List func_55149_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_55157_a(par2ArrayOfStr, new String[]
                    {
                        "set", "add"
                    });
        }

        if (par2ArrayOfStr.length == 2 && par2ArrayOfStr[0].equals("set"))
        {
            return func_55157_a(par2ArrayOfStr, new String[]
                    {
                        "day", "night"
                    });
        }
        else
        {
            return null;
        }
    }

    protected void func_55172_a(ICommandSender par1ICommandSender, int par2)
    {
        for (int i = 0; i < MinecraftServer.func_55089_q().worldMngr.length; i++)
        {
            MinecraftServer.func_55089_q().worldMngr[i].func_56368_b(par2);
        }
    }

    protected void func_55171_b(ICommandSender par1ICommandSender, int par2)
    {
        for (int i = 0; i < MinecraftServer.func_55089_q().worldMngr.length; i++)
        {
            WorldServer worldserver = MinecraftServer.func_55089_q().worldMngr[i];
            worldserver.func_56368_b(worldserver.getWorldTime() + (long)par2);
        }
    }
}
