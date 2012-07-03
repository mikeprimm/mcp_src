package net.minecraft.src;

public class CommandKill extends CommandBase
{
    public CommandKill()
    {
    }

    public String func_55148_a()
    {
        return "kill";
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        EntityPlayer entityplayer = func_55159_d(par1ICommandSender);
        par1ICommandSender.func_55072_b("Ouch. That look like it hurt.");
    }
}
