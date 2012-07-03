package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.server.MinecraftServer;

public class CommandServerBanIp extends CommandBase
{
    public static final Pattern field_55168_a = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

    public CommandServerBanIp()
    {
    }

    public String func_55148_a()
    {
        return "ban-ip";
    }

    public boolean func_55147_c(ICommandSender par1ICommandSender)
    {
        return MinecraftServer.func_55089_q().func_56173_Y().func_56427_f().func_56745_a() && super.func_55147_c(par1ICommandSender);
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.banip.usage", new Object[0]);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1 && par2ArrayOfStr[0].length() > 1)
        {
            Matcher matcher = field_55168_a.matcher(par2ArrayOfStr[0]);
            String s = null;

            if (par2ArrayOfStr.length >= 2)
            {
                s = func_55153_a(par2ArrayOfStr, 1);
            }

            if (matcher.matches())
            {
                func_56239_a(par1ICommandSender, par2ArrayOfStr[0], s);
            }
            else
            {
                EntityPlayerMP entityplayermp = MinecraftServer.func_55089_q().func_56173_Y().getPlayerEntity(par2ArrayOfStr[0]);

                if (entityplayermp == null)
                {
                    throw new PlayerNotFoundException("commands.banip.invalid", new Object[0]);
                }

                func_56239_a(par1ICommandSender, entityplayermp.func_55082_I(), s);
            }

            return;
        }
        else
        {
            throw new WrongUsageException("commands.banip.usage", new Object[0]);
        }
    }

    public List func_55149_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1)
        {
            return func_55157_a(par2ArrayOfStr, MinecraftServer.func_55089_q().getPlayerNamesAsList());
        }
        else
        {
            return null;
        }
    }

    protected void func_56239_a(ICommandSender par1ICommandSender, String par2Str, String par3Str)
    {
        BanEntry banentry = new BanEntry(par2Str);
        banentry.func_56717_a(par1ICommandSender.func_55070_y_());

        if (par3Str != null)
        {
            banentry.func_56718_b(par3Str);
        }

        MinecraftServer.func_55089_q().func_56173_Y().func_56427_f().func_56748_a(banentry);
        List list = MinecraftServer.func_55089_q().func_56173_Y().func_55208_l(par2Str);
        String as[] = new String[list.size()];
        int i = 0;

        for (Iterator iterator = list.iterator(); iterator.hasNext();)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();
            entityplayermp.playerNetServerHandler.kickPlayer("You have been IP banned.");
            as[i++] = entityplayermp.getUsername();
        }

        if (list.isEmpty())
        {
            func_55154_a(par1ICommandSender, "commands.banip.success", new Object[]
                    {
                        par2Str
                    });
        }
        else
        {
            func_55154_a(par1ICommandSender, "commands.banip.success.players", new Object[]
                    {
                        par2Str, func_56236_a(as)
                    });
        }
    }
}
