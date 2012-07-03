package net.minecraft.src;

import java.util.*;

public class CommandHandler implements ICommandManager
{
    private final Map field_55241_a = new HashMap();
    private final Set field_55240_b = new HashSet();

    public CommandHandler()
    {
    }

    public void func_55236_a(ICommandSender par1ICommandSender, String par2Str)
    {
        if (par2Str.startsWith("/"))
        {
            par2Str = par2Str.substring(1);
        }

        String as[] = par2Str.split(" ");
        String s = as[0];
        as = func_55238_a(as);
        ICommand icommand = (ICommand)field_55241_a.get(s);

        try
        {
            if (icommand == null)
            {
                throw new CommandNotFoundException();
            }

            if (icommand.func_55147_c(par1ICommandSender))
            {
                icommand.func_55146_a(par1ICommandSender, as);
            }
            else
            {
                par1ICommandSender.func_55072_b("\247cYou do not have permission to use this command.");
            }
        }
        catch (WrongUsageException wrongusageexception)
        {
            par1ICommandSender.func_55072_b((new StringBuilder()).append("\247c").append(par1ICommandSender.func_55069_a("commands.generic.usage", new Object[]
                    {
                        par1ICommandSender.func_55069_a(wrongusageexception.getMessage(), wrongusageexception.func_55227_a())
                    })).toString());
        }
        catch (CommandException commandexception)
        {
            par1ICommandSender.func_55072_b((new StringBuilder()).append("\247c").append(par1ICommandSender.func_55069_a(commandexception.getMessage(), commandexception.func_55227_a())).toString());
        }
        catch (Throwable throwable)
        {
            par1ICommandSender.func_55072_b((new StringBuilder()).append("\247c").append(par1ICommandSender.func_55069_a("commands.generic.exception", new Object[0])).toString());
            throwable.printStackTrace();
        }
    }

    public ICommand func_55239_a(ICommand par1ICommand)
    {
        List list = par1ICommand.func_55145_b();
        field_55241_a.put(par1ICommand.func_55148_a(), par1ICommand);
        field_55240_b.add(par1ICommand);

        if (list != null)
        {
            Iterator iterator = list.iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                String s = (String)iterator.next();
                ICommand icommand = (ICommand)field_55241_a.get(s);

                if (icommand == null || !icommand.func_55148_a().equals(s))
                {
                    field_55241_a.put(s, par1ICommand);
                }
            }
            while (true);
        }

        return par1ICommand;
    }

    private static String[] func_55238_a(String par0ArrayOfStr[])
    {
        String as[] = new String[par0ArrayOfStr.length - 1];

        for (int i = 1; i < par0ArrayOfStr.length; i++)
        {
            as[i - 1] = par0ArrayOfStr[i];
        }

        return as;
    }

    public List func_55237_b(ICommandSender par1ICommandSender, String par2Str)
    {
        String as[] = par2Str.split(" ", -1);
        String s = as[0];

        if (as.length == 1)
        {
            ArrayList arraylist = new ArrayList();
            Iterator iterator = field_55241_a.entrySet().iterator();

            do
            {
                if (!iterator.hasNext())
                {
                    break;
                }

                java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();

                if (CommandBase.func_55155_a(s, (String)entry.getKey()) && ((ICommand)entry.getValue()).func_55147_c(par1ICommandSender))
                {
                    arraylist.add(entry.getKey());
                }
            }
            while (true);

            return arraylist;
        }

        if (as.length > 1)
        {
            ICommand icommand = (ICommand)field_55241_a.get(s);

            if (icommand != null)
            {
                return icommand.func_55149_b(par1ICommandSender, func_55238_a(as));
            }
        }

        return null;
    }

    public List func_55235_a(ICommandSender par1ICommandSender)
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = field_55240_b.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            ICommand icommand = (ICommand)iterator.next();

            if (icommand.func_55147_c(par1ICommandSender))
            {
                arraylist.add(icommand);
            }
        }
        while (true);

        return arraylist;
    }

    public Map func_56557_a()
    {
        return field_55241_a;
    }
}
