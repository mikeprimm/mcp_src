package net.minecraft.src;

import net.minecraft.server.MinecraftServer;

public class WorldServerMulti extends WorldServer
{
    public WorldServerMulti(MinecraftServer par1MinecraftServer, ISaveHandler par2ISaveHandler, String par3Str, int par4, WorldSettings par5WorldSettings, WorldServer par6WorldServer)
    {
        super(par1MinecraftServer, par2ISaveHandler, par3Str, par4, par5WorldSettings);
        mapStorage = par6WorldServer.mapStorage;
        worldInfo = new DerivedWorldInfo(par6WorldServer.getWorldInfo());
    }

    protected void func_56370_A() throws MinecraftException
    {
    }
}
