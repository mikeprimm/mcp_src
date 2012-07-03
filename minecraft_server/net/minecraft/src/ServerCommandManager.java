package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;

public class ServerCommandManager extends CommandHandler implements IAdminCommand
{
    public ServerCommandManager()
    {
        func_55239_a(new CommandTime());
        func_55239_a(new CommandGameMode());
        func_55239_a(new CommandDefaultGameMode());
        func_55239_a(new CommandKill());
        func_55239_a(new CommandToggleDownfall());
        func_55239_a(new CommandXP());
        func_55239_a(new CommandServerTp());
        func_55239_a(new CommandGive());
        func_55239_a(new CommandServerEmote());
        func_55239_a(new CommandShowSeed());
        func_55239_a(new CommandHelp());

        if (MinecraftServer.func_55089_q().func_56172_P())
        {
            func_55239_a(new CommandServerOp());
            func_55239_a(new CommandServerDeop());
            func_55239_a(new CommandServerStop());
            func_55239_a(new CommandServerSaveAll());
            func_55239_a(new CommandServerSaveOff());
            func_55239_a(new CommandServerSaveOn());
            func_55239_a(new CommandServerBanIp());
            func_55239_a(new CommandServerPardonIp());
            func_55239_a(new CommandServerBan());
            func_55239_a(new CommandServerBanlist());
            func_55239_a(new CommandServerPardon());
            func_55239_a(new CommandServerKick());
            func_55239_a(new CommandServerList());
            func_55239_a(new CommandServerSay());
            func_55239_a(new CommandServerWhitelist());
        }
        else
        {
            func_55239_a(new CommandServerPublishLocal());
        }

        CommandBase.func_55151_a(this);
    }

    public void func_55242_a(ICommandSender par1ICommandSender, String par2Str, Object par3ArrayOfObj[])
    {
        Iterator iterator = MinecraftServer.func_55089_q().func_56173_Y().playerEntities.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp != par1ICommandSender && MinecraftServer.func_55089_q().func_56173_Y().isOp(((EntityPlayer)(entityplayermp)).username))
            {
                entityplayermp.func_55072_b((new StringBuilder()).append("\2477\247o[").append(par1ICommandSender.func_55070_y_()).append(": ").append(entityplayermp.func_55069_a(par2Str, par3ArrayOfObj)).append("]").toString());
            }
        }
        while (true);

        if (par1ICommandSender != MinecraftServer.func_55089_q())
        {
            MinecraftServer.logger.info((new StringBuilder()).append("[").append(par1ICommandSender.func_55070_y_()).append(": ").append(MinecraftServer.func_55089_q().func_55069_a(par2Str, par3ArrayOfObj)).append("]").toString());
        }

        par1ICommandSender.func_55072_b(par1ICommandSender.func_55069_a(par2Str, par3ArrayOfObj));
    }
}
