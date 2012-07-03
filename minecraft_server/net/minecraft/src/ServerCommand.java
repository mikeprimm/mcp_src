package net.minecraft.src;

public class ServerCommand
{
    /** The command string. */
    public final String command;
    public final ICommandSender field_55233_b;

    public ServerCommand(String par1Str, ICommandSender par2ICommandSender)
    {
        command = par1Str;
        field_55233_b = par2ICommandSender;
    }
}
