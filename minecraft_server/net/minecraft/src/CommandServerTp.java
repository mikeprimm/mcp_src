package net.minecraft.src;

import java.util.List;
import net.minecraft.server.MinecraftServer;

public class CommandServerTp extends CommandBase
{
    public CommandServerTp()
    {
    }

    public String func_55148_a()
    {
        return "tp";
    }

    public String func_55150_a(ICommandSender par1ICommandSender)
    {
        return par1ICommandSender.func_55069_a("commands.tp.usage", new Object[0]);
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length >= 1)
        {
            MinecraftServer minecraftserver = MinecraftServer.func_55089_q();
            EntityPlayerMP entityplayermp;

            if (par2ArrayOfStr.length == 2 || par2ArrayOfStr.length == 4)
            {
                entityplayermp = minecraftserver.func_56173_Y().getPlayerEntity(par2ArrayOfStr[0]);

                if (entityplayermp == null)
                {
                    throw new PlayerNotFoundException();
                }
            }
            else
            {
                entityplayermp = (EntityPlayerMP)func_55159_d(par1ICommandSender);
            }

            if (par2ArrayOfStr.length == 3 || par2ArrayOfStr.length == 4)
            {
                if (entityplayermp.worldObj != null)
                {
                    int i = par2ArrayOfStr.length - 3;
                    int j = 0x1c9c380;
                    int k = func_55152_a(par1ICommandSender, par2ArrayOfStr[i++], -j, j);
                    int l = func_55152_a(par1ICommandSender, par2ArrayOfStr[i++], 0, 256);
                    int i1 = func_55152_a(par1ICommandSender, par2ArrayOfStr[i++], -j, j);
                    entityplayermp.setPositionAndUpdate(k, l, i1);
                    func_55154_a(par1ICommandSender, "commands.tp.coordinates", new Object[]
                            {
                                entityplayermp.getUsername(), Integer.valueOf(k), Integer.valueOf(l), Integer.valueOf(i1)
                            });
                }
            }
            else if (par2ArrayOfStr.length == 1 || par2ArrayOfStr.length == 2)
            {
                EntityPlayerMP entityplayermp1 = minecraftserver.func_56173_Y().getPlayerEntity(par2ArrayOfStr[par2ArrayOfStr.length - 1]);

                if (entityplayermp1 == null)
                {
                    throw new PlayerNotFoundException();
                }

                entityplayermp.playerNetServerHandler.teleportTo(entityplayermp1.posX, entityplayermp1.posY, entityplayermp1.posZ, entityplayermp1.rotationYaw, entityplayermp1.rotationPitch);
                func_55154_a(par1ICommandSender, "commands.tp.success", new Object[]
                        {
                            entityplayermp.getUsername(), entityplayermp1.getUsername()
                        });
            }

            return;
        }
        else
        {
            throw new WrongUsageException("commands.tp.usage", new Object[0]);
        }
    }

    public List func_55149_b(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        if (par2ArrayOfStr.length == 1 || par2ArrayOfStr.length == 2)
        {
            return func_55157_a(par2ArrayOfStr, MinecraftServer.func_55089_q().getPlayerNamesAsList());
        }
        else
        {
            return null;
        }
    }
}
