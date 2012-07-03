package net.minecraft.src;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils
{
    private static final Pattern field_55307_a = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");

    public StringUtils()
    {
    }

    public static String func_55306_a(String par0Str)
    {
        return field_55307_a.matcher(par0Str).replaceAll("");
    }
}
