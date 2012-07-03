package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import net.minecraft.server.MinecraftServer;

public class WorldManager implements IWorldAccess
{
    /** Reference to the MinecraftServer object. */
    private MinecraftServer mcServer;

    /** The world itself. */
    private WorldServer world;

    public WorldManager(MinecraftServer par1MinecraftServer, WorldServer par2WorldServer)
    {
        mcServer = par1MinecraftServer;
        world = par2WorldServer;
    }

    /**
     * Spawns a particle. Arg: particleType, x, y, z, velX, velY, velZ
     */
    public void spawnParticle(String s, double d, double d1, double d2, double d3, double d4, double d5)
    {
    }

    /**
     * Start the skin for this entity downloading, if necessary, and increment its reference counter
     */
    public void obtainEntitySkin(Entity par1Entity)
    {
        world.func_55201_C().trackEntity(par1Entity);
    }

    /**
     * Decrement the reference counter for this entity's skin image data
     */
    public void releaseEntitySkin(Entity par1Entity)
    {
        world.func_55201_C().untrackEntity(par1Entity);
    }

    /**
     * Plays the specified sound. Arg: x, y, z, soundName, unknown1, unknown2
     */
    public void playSound(String par1Str, double par2, double par4, double par6, float par8, float par9)
    {
        mcServer.func_56173_Y().sendPacketToPlayersAroundPoint(par2, par4, par6, par8 <= 1.0F ? 16D : 16F * par8, world.worldProvider.worldType, new Packet62LevelSound(par1Str, par2, par4, par6, par8, par9));
    }

    /**
     * Called across all registered IWorldAccess instances when a block range is invalidated. Args: minX, minY, minZ,
     * maxX, maxY, maxZ
     */
    public void markBlockRangeNeedsUpdate(int i, int j, int k, int l, int i1, int j1)
    {
    }

    /**
     * Will mark the block and neighbors that their renderers need an update (could be all the same renderer
     * potentially) Args: x, y, z
     */
    public void markBlockNeedsUpdate(int par1, int par2, int par3)
    {
        world.func_56372_E().markBlockNeedsUpdate(par1, par2, par3);
    }

    /**
     * As of mc 1.2.3 this method has exactly the same signature and does exactly the same as markBlockNeedsUpdate
     */
    public void markBlockNeedsUpdate2(int i, int j, int k)
    {
    }

    /**
     * Plays the specified record. Arg: recordName, x, y, z
     */
    public void playRecord(String s, int i, int j, int k)
    {
    }

    /**
     * Plays a pre-canned sound effect along with potentially auxiliary data-driven one-shot behaviour (particles, etc).
     */
    public void playAuxSFX(EntityPlayer par1EntityPlayer, int par2, int par3, int par4, int par5, int par6)
    {
        mcServer.func_56173_Y().func_28171_a(par1EntityPlayer, par3, par4, par5, 64D, world.worldProvider.worldType, new Packet61DoorChange(par2, par3, par4, par5, par6));
    }

    public void func_56537_a(int par1, int par2, int par3, int par4, int par5)
    {
        Iterator iterator = mcServer.func_56173_Y().playerEntities.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp != null && entityplayermp.worldObj == world && entityplayermp.entityId != par1)
            {
                double d = (double)par2 - entityplayermp.posX;
                double d1 = (double)par3 - entityplayermp.posY;
                double d2 = (double)par4 - entityplayermp.posZ;

                if (d * d + d1 * d1 + d2 * d2 < 1024D)
                {
                    entityplayermp.playerNetServerHandler.sendPacket(new Packet55BlockDestroy(par1, par2, par3, par4, par5));
                }
            }
        }
        while (true);
    }
}
