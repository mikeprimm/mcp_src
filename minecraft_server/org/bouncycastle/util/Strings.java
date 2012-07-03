package org.bouncycastle.util;

public final class Strings
{
    public Strings()
    {
    }

    public static String func_56401_a(String par0Str)
    {
        boolean flag = false;
        char ac[] = par0Str.toCharArray();

        for (int i = 0; i != ac.length; i++)
        {
            char c = ac[i];

            if ('A' <= c && 'Z' >= c)
            {
                flag = true;
                ac[i] = (char)((c - 65) + 97);
            }
        }

        if (flag)
        {
            return new String(ac);
        }
        else
        {
            return par0Str;
        }
    }
}
