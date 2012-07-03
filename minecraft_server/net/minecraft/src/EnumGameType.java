package net.minecraft.src;

public enum EnumGameType
{
    NOT_SET(-1, ""),
    SURVIVAL(0, "survival"),
    CREATIVE(1, "creative"),
    ADVENTURE(2, "adventure");

    int field_56613_e;
    String field_56610_f;

    private EnumGameType(int par3, String par4Str)
    {
        field_56613_e = par3;
        field_56610_f = par4Str;
    }

    public int func_56607_a()
    {
        return field_56613_e;
    }

    public String func_56608_b()
    {
        return field_56610_f;
    }

    public void func_56605_a(PlayerCapabilities par1PlayerCapabilities)
    {
        if (this == CREATIVE)
        {
            par1PlayerCapabilities.allowFlying = true;
            par1PlayerCapabilities.isCreativeMode = true;
            par1PlayerCapabilities.disableDamage = true;
        }
        else
        {
            par1PlayerCapabilities.allowFlying = false;
            par1PlayerCapabilities.isCreativeMode = false;
            par1PlayerCapabilities.disableDamage = false;
            par1PlayerCapabilities.isFlying = false;
        }

        par1PlayerCapabilities.field_56602_e = !func_56609_c();
    }

    public boolean func_56609_c()
    {
        return this == ADVENTURE;
    }

    public boolean func_56606_d()
    {
        return this == CREATIVE;
    }

    public static EnumGameType func_56604_a(int par0)
    {
        EnumGameType aenumgametype[] = values();
        int i = aenumgametype.length;

        for (int j = 0; j < i; j++)
        {
            EnumGameType enumgametype = aenumgametype[j];

            if (enumgametype.field_56613_e == par0)
            {
                return enumgametype;
            }
        }

        return SURVIVAL;
    }
}
