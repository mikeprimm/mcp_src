package net.minecraft.src;

import java.util.*;

public abstract class CommandBase implements ICommand
{
    private static IAdminCommand field_55162_a = null;

    public CommandBase()
    {
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return (new StringBuilder()).append("/").append(func_55148_a()).toString();
    }

    public List func_55145_b()
    {
        return null;
    }

    public boolean func_55147_c(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55071_c(func_55148_a());
    }

    public List func_55149_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        return null;
    }

    public static int func_55156_a(ICommandSender par0ICommandSender, String par1Str)
    {
        try
        {
            return Integer.parseInt(par1Str);
        }
        catch (NumberFormatException numberformatexception)
        {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[]
                    {
                        par1Str
                    });
        }
    }

    public static int func_55160_a(ICommandSender par0ICommandSender, String par1Str, int par2)
    {
        return func_55152_a(par0ICommandSender, par1Str, par2, 0x7fffffff);
    }

    public static int func_55152_a(ICommandSender par0ICommandSender, String par1Str, int par2, int par3)
    {
        int i = func_55156_a(par0ICommandSender, par1Str);

        if (i < par2)
        {
            throw new NumberInvalidException("commands.generic.num.tooSmall", new Object[]
                    {
                        Integer.valueOf(i), Integer.valueOf(par2)
                    });
        }

        if (i > par3)
        {
            throw new NumberInvalidException("commands.generic.num.tooBig", new Object[]
                    {
                        Integer.valueOf(i), Integer.valueOf(par3)
                    });
        }
        else
        {
            return i;
        }
    }

    public static EntityPlayer func_55159_d(ICommandSender par0ICommandSender)
    {
        if (par0ICommandSender instanceof EntityPlayer)
        {
            return (EntityPlayer)par0ICommandSender;
        }
        else
        {
            throw new PlayerNotFoundException("You must specify which player you wish to perform this action on.", new Object[0]);
        }
    }

    public static String func_55153_a(String par0ArrayOfStr[], int par1)
    {
        StringBuilder stringbuilder = new StringBuilder();

        for (int i = par1; i < par0ArrayOfStr.length; i++)
        {
            if (i > par1)
            {
                stringbuilder.append(" ");
            }

            stringbuilder.append(par0ArrayOfStr[i]);
        }

        return stringbuilder.toString();
    }

    public static String func_56236_a(Object par0ArrayOfObj[])
    {
        StringBuilder stringbuilder = new StringBuilder();

        for (int i = 0; i < par0ArrayOfObj.length; i++)
        {
            String s = par0ArrayOfObj[i].toString();

            if (i > 0)
            {
                if (i == par0ArrayOfObj.length - 1)
                {
                    stringbuilder.append(" and ");
                }
                else
                {
                    stringbuilder.append(", ");
                }
            }

            stringbuilder.append(s);
        }

        return stringbuilder.toString();
    }

    public static boolean func_55155_a(String par0Str, String par1Str)
    {
        return par1Str.regionMatches(true, 0, par0Str, 0, par0Str.length());
    }

    public static List func_55157_a(String par0ArrayOfStr[], String par1ArrayOfStr[])
    {
        String s = par0ArrayOfStr[par0ArrayOfStr.length - 1];
        ArrayList arraylist = new ArrayList();
        String as[] = par1ArrayOfStr;
        int i = as.length;

        for (int j = 0; j < i; j++)
        {
            String s1 = as[j];

            if (func_55155_a(s, s1))
            {
                arraylist.add(s1);
            }
        }

        return arraylist;
    }

    public static List func_55158_a(String par0ArrayOfStr[], Iterable par1Iterable)
    {
        String s = par0ArrayOfStr[par0ArrayOfStr.length - 1];
        ArrayList arraylist = new ArrayList();
        Iterator iterator = par1Iterable.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            String s1 = (String)iterator.next();

            if (func_55155_a(s, s1))
            {
                arraylist.add(s1);
            }
        }
        while (true);

        return arraylist;
    }

    public static void func_55154_a(ICommandSender par0ICommandSender, String par1Str, Object par2ArrayOfObj[])
    {
        if (field_55162_a != null)
        {
            field_55162_a.func_55242_a(par0ICommandSender, par1Str, par2ArrayOfObj);
        }
    }

    public static void func_55151_a(IAdminCommand par0IAdminCommand)
    {
        field_55162_a = par0IAdminCommand;
    }

    public int func_56237_a(ICommand par1ICommand)
    {
        return func_55148_a().compareTo(par1ICommand.func_55148_a());
    }

    public int compareTo(Object par1Obj)
    {
        return func_56237_a((ICommand)par1Obj);
    }
}
