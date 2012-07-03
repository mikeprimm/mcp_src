package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class DemoWorldServer extends WorldServer
{
    private static final long field_56379_L;
    public static final WorldSettings field_56378_a;

    public DemoWorldServer(MinecraftServer par1MinecraftServer, ISaveHandler par2ISaveHandler, String par3Str, int par4)
    {
        super(par1MinecraftServer, par2ISaveHandler, par3Str, par4, field_56378_a);
    }

    static
    {
        field_56379_L = "North Carolina".hashCode();
        field_56378_a = (new WorldSettings(field_56379_L, EnumGameType.SURVIVAL, true, false, WorldType.DEFAULT)).func_56526_a();
    }
}
