package net.minecraft.src;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class BanEntry
{
    public static final SimpleDateFormat field_56727_a = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    public static Logger field_56725_b = Logger.getLogger("Minecraft");
    private final String field_56726_c;
    private Date field_56723_d;
    private String field_56724_e;
    private Date field_56721_f;
    private String field_56722_g;

    public BanEntry(String par1Str)
    {
        field_56723_d = new Date();
        field_56724_e = "(Unknown)";
        field_56721_f = null;
        field_56722_g = "Banned by an operator.";
        field_56726_c = par1Str;
    }

    public String func_56713_a()
    {
        return field_56726_c;
    }

    public Date func_56712_b()
    {
        return field_56723_d;
    }

    public void func_56710_a(Date par1Date)
    {
        field_56723_d = par1Date == null ? new Date() : par1Date;
    }

    public String func_56719_c()
    {
        return field_56724_e;
    }

    public void func_56717_a(String par1Str)
    {
        field_56724_e = par1Str;
    }

    public Date func_56709_d()
    {
        return field_56721_f;
    }

    public void func_56720_b(Date par1Date)
    {
        field_56721_f = par1Date;
    }

    public boolean func_56711_e()
    {
        if (field_56721_f == null)
        {
            return false;
        }
        else
        {
            return field_56721_f.before(new Date());
        }
    }

    public String func_56716_f()
    {
        return field_56722_g;
    }

    public void func_56718_b(String par1Str)
    {
        field_56722_g = par1Str;
    }

    public String func_56714_g()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(func_56713_a());
        stringbuilder.append("|");
        stringbuilder.append(field_56727_a.format(func_56712_b()));
        stringbuilder.append("|");
        stringbuilder.append(func_56719_c());
        stringbuilder.append("|");
        stringbuilder.append(func_56709_d() != null ? field_56727_a.format(func_56709_d()) : "Forever");
        stringbuilder.append("|");
        stringbuilder.append(func_56716_f());
        return stringbuilder.toString();
    }

    public static BanEntry func_56715_c(String par0Str)
    {
        if (par0Str.trim().length() < 2)
        {
            return null;
        }

        String as[] = par0Str.trim().split(Pattern.quote("|"), 5);
        BanEntry banentry = new BanEntry(as[0].trim());
        int i = 0;

        if (as.length <= ++i)
        {
            return banentry;
        }

        try
        {
            banentry.func_56710_a(field_56727_a.parse(as[i].trim()));
        }
        catch (ParseException parseexception)
        {
            field_56725_b.log(Level.WARNING, (new StringBuilder()).append("Could not read creation date format for ban entry '").append(banentry.func_56713_a()).append("' (was: '").append(as[i]).append("')").toString(), parseexception);
        }

        if (as.length <= ++i)
        {
            return banentry;
        }

        banentry.func_56717_a(as[i].trim());

        if (as.length <= ++i)
        {
            return banentry;
        }

        try
        {
            String s = as[i].trim();

            if (!s.equalsIgnoreCase("Forever") && s.length() > 0)
            {
                banentry.func_56720_b(field_56727_a.parse(s));
            }
        }
        catch (ParseException parseexception1)
        {
            field_56725_b.log(Level.WARNING, (new StringBuilder()).append("Could not read expiry date format for ban entry '").append(banentry.func_56713_a()).append("' (was: '").append(as[i]).append("')").toString(), parseexception1);
        }

        if (as.length <= ++i)
        {
            return banentry;
        }
        else
        {
            banentry.func_56718_b(as[i].trim());
            return banentry;
        }
    }
}
