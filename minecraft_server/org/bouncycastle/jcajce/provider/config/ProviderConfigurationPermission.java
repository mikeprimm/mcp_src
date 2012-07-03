package org.bouncycastle.jcajce.provider.config;

import java.security.BasicPermission;
import java.security.Permission;
import java.util.StringTokenizer;
import org.bouncycastle.util.Strings;

public class ProviderConfigurationPermission extends BasicPermission
{
    private final String field_56634_a;
    private final int field_56633_b;

    public ProviderConfigurationPermission(String par1Str)
    {
        super(par1Str);
        field_56634_a = "all";
        field_56633_b = 15;
    }

    public ProviderConfigurationPermission(String par1Str, String par2Str)
    {
        super(par1Str, par2Str);
        field_56634_a = par2Str;
        field_56633_b = func_56632_a(par2Str);
    }

    private int func_56632_a(String par1Str)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(Strings.func_56401_a(par1Str), " ,");
        int i = 0;

        do
        {
            if (!stringtokenizer.hasMoreTokens())
            {
                break;
            }

            String s = stringtokenizer.nextToken();

            if (s.equals("threadlocalecimplicitlyca"))
            {
                i |= 1;
            }
            else if (s.equals("ecimplicitlyca"))
            {
                i |= 2;
            }
            else if (s.equals("threadlocaldhdefaultparams"))
            {
                i |= 4;
            }
            else if (s.equals("dhdefaultparams"))
            {
                i |= 8;
            }
            else if (s.equals("all"))
            {
                i |= 0xf;
            }
        }
        while (true);

        if (i == 0)
        {
            throw new IllegalArgumentException("unknown permissions passed to mask");
        }
        else
        {
            return i;
        }
    }

    public String getActions()
    {
        return field_56634_a;
    }

    public boolean implies(Permission par1Permission)
    {
        if (!(par1Permission instanceof ProviderConfigurationPermission))
        {
            return false;
        }

        if (!getName().equals(par1Permission.getName()))
        {
            return false;
        }
        else
        {
            ProviderConfigurationPermission providerconfigurationpermission = (ProviderConfigurationPermission)par1Permission;
            return (field_56633_b & providerconfigurationpermission.field_56633_b) == providerconfigurationpermission.field_56633_b;
        }
    }

    public boolean equals(Object par1Obj)
    {
        if (par1Obj == this)
        {
            return true;
        }

        if (par1Obj instanceof ProviderConfigurationPermission)
        {
            ProviderConfigurationPermission providerconfigurationpermission = (ProviderConfigurationPermission)par1Obj;
            return field_56633_b == providerconfigurationpermission.field_56633_b && getName().equals(providerconfigurationpermission.getName());
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return getName().hashCode() + field_56633_b;
    }
}
