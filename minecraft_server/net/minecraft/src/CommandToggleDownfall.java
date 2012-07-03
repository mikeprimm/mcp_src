package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class CommandToggleDownfall extends CommandBase
{
    public CommandToggleDownfall()
    {
    }

    public String func_55148_a()
    {
        return "toggledownfall";
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        func_55163_c();
        func_55154_a(par1ICommandSender, "commands.downfall.success", new Object[0]);
    }

    protected void func_55163_c()
    {
        MinecraftServer.func_55089_q().worldMngr[0].commandToggleDownfall();
        MinecraftServer.func_55089_q().worldMngr[0].getWorldInfo().setThundering(true);
    }
}
