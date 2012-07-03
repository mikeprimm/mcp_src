package net.minecraft.src;

public class CommandException extends RuntimeException
{
    private Object field_55228_a[];

    public CommandException(String par1Str, Object par2ArrayOfObj[])
    {
        super(par1Str);
        field_55228_a = par2ArrayOfObj;
    }

    public Object[] func_55227_a()
    {
        return field_55228_a;
    }
}
