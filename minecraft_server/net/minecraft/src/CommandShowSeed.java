package net.minecraft.src;

public class CommandShowSeed extends CommandBase
{
    public CommandShowSeed()
    {
    }

    public String func_55148_a()
    {
        return "seed";
    }

    public void func_55146_a(ICommandSender par1ICommandSender, String par2ArrayOfStr[])
    {
        EntityPlayer entityplayer = func_55159_d(par1ICommandSender);
        par1ICommandSender.func_55072_b((new StringBuilder()).append("Seed: ").append(entityplayer.worldObj.getSeed()).toString());
    }
}
