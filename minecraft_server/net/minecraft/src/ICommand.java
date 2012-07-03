package net.minecraft.src;

import java.util.List;

public interface ICommand extends Comparable
{
    public abstract String func_55148_a();

    public abstract String func_55150_a(ICommandSender icommandsender);

    public abstract List func_55145_b();

    public abstract void func_55146_a(ICommandSender icommandsender, String as[]);

    public abstract boolean func_55147_c(ICommandSender icommandsender);

    public abstract List func_55149_b(ICommandSender icommandsender, String as[]);
}
