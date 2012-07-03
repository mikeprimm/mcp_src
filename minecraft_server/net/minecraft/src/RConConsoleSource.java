package net.minecraft.src;

public class RConConsoleSource implements ICommandSender
{
    /** Single instance of RConConsoleSource */
    public static final RConConsoleSource instance = new RConConsoleSource();

    /** RCon string buffer for log */
    private StringBuffer buffer;

    public RConConsoleSource()
    {
        buffer = new StringBuffer();
    }

    /**
     * Clears the RCon log
     */
    public void resetLog()
    {
        buffer.setLength(0);
    }

    /**
     * Gets the contents of the RCon log
     */
    public String getLogContents()
    {
        return buffer.toString();
    }

    public String func_55070_y_()
    {
        return "Rcon";
    }

    public void func_55072_b(String par1Str)
    {
        buffer.append(par1Str);
    }

    public boolean func_55071_c(String par1Str)
    {
        return true;
    }

    public String func_55069_a(String par1Str, Object par2ArrayOfObj[])
    {
        return StringTranslate.getInstance().translateKeyFormat(par1Str, par2ArrayOfObj);
    }
}
