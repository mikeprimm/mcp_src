package net.minecraft.src;

import java.util.*;

public class PlayerManager
{
    private final WorldServer field_56692_a;

    /** players in the current instance */
    private final List players = new ArrayList();

    /** the hash of all playerInstances created */
    private final LongHashMap playerInstances = new LongHashMap();

    /** the playerInstances(chunks) that need to be updated */
    private final List playerInstancesToUpdate = new ArrayList();

    /**
     * Number of chunks the server sends to the client. Valid 3<=x<=15. In server.properties.
     */
    private final int playerViewRadius;
    private final int xzDirectionsConst[][] =
    {
        {
            1, 0
        }, {
            0, 1
        }, {
            -1, 0
        }, {
            0, -1
        }
    };

    public PlayerManager(WorldServer par1WorldServer, int par2)
    {
        if (par2 > 15)
        {
            throw new IllegalArgumentException("Too big view radius!");
        }

        if (par2 < 3)
        {
            throw new IllegalArgumentException("Too small view radius!");
        }
        else
        {
            playerViewRadius = par2;
            field_56692_a = par1WorldServer;
            return;
        }
    }

    /**
     * Returns the MinecraftServer associated with the PlayerManager.
     */
    public WorldServer getMinecraftServer()
    {
        return field_56692_a;
    }

    /**
     * updates all the player instances that need to be updated
     */
    public void updatePlayerInstances()
    {
        PlayerInstance playerinstance;

        for (Iterator iterator = playerInstancesToUpdate.iterator(); iterator.hasNext(); playerinstance.onUpdate())
        {
            playerinstance = (PlayerInstance)iterator.next();
        }

        playerInstancesToUpdate.clear();

        if (players.isEmpty())
        {
            WorldProvider worldprovider = field_56692_a.worldProvider;

            if (!worldprovider.canRespawnHere())
            {
                field_56692_a.chunkProviderServer.unloadAllChunks();
            }
        }
    }

    /**
     * passi n the chunk x and y and a flag as to whether or not the instance should be made if it doesnt exist
     */
    private PlayerInstance getPlayerInstance(int par1, int par2, boolean par3)
    {
        long l = (long)par1 + 0x7fffffffL | (long)par2 + 0x7fffffffL << 32;
        PlayerInstance playerinstance = (PlayerInstance)playerInstances.getValueByKey(l);

        if (playerinstance == null && par3)
        {
            playerinstance = new PlayerInstance(this, par1, par2);
            playerInstances.add(l, playerinstance);
        }

        return playerinstance;
    }

    public void markBlockNeedsUpdate(int par1, int par2, int par3)
    {
        int i = par1 >> 4;
        int j = par3 >> 4;
        PlayerInstance playerinstance = getPlayerInstance(i, j, false);

        if (playerinstance != null)
        {
            playerinstance.markBlockNeedsUpdate(par1 & 0xf, par2, par3 & 0xf);
        }
    }

    /**
     * Adds an EntityPlayerMP to the PlayerManager.
     */
    public void addPlayer(EntityPlayerMP par1EntityPlayerMP)
    {
        int i = (int)par1EntityPlayerMP.posX >> 4;
        int j = (int)par1EntityPlayerMP.posZ >> 4;
        par1EntityPlayerMP.managedPosX = par1EntityPlayerMP.posX;
        par1EntityPlayerMP.managedPosZ = par1EntityPlayerMP.posZ;

        for (int k = i - playerViewRadius; k <= i + playerViewRadius; k++)
        {
            for (int l = j - playerViewRadius; l <= j + playerViewRadius; l++)
            {
                getPlayerInstance(k, l, true).addPlayer(par1EntityPlayerMP);
            }
        }

        players.add(par1EntityPlayerMP);
        func_56691_b(par1EntityPlayerMP);
    }

    public void func_56691_b(EntityPlayerMP par1EntityPlayerMP)
    {
        ArrayList arraylist = new ArrayList(par1EntityPlayerMP.loadedChunks);
        int i = 0;
        int j = playerViewRadius;
        int k = (int)par1EntityPlayerMP.posX >> 4;
        int l = (int)par1EntityPlayerMP.posZ >> 4;
        int i1 = 0;
        int j1 = 0;
        ChunkCoordIntPair chunkcoordintpair = PlayerInstance.func_56548_a(getPlayerInstance(k, l, true));
        par1EntityPlayerMP.loadedChunks.clear();

        if (arraylist.contains(chunkcoordintpair))
        {
            par1EntityPlayerMP.loadedChunks.add(chunkcoordintpair);
        }

        for (int k1 = 1; k1 <= j * 2; k1++)
        {
            for (int i2 = 0; i2 < 2; i2++)
            {
                int ai[] = xzDirectionsConst[i++ % 4];

                for (int j2 = 0; j2 < k1; j2++)
                {
                    i1 += ai[0];
                    j1 += ai[1];
                    ChunkCoordIntPair chunkcoordintpair1 = PlayerInstance.func_56548_a(getPlayerInstance(k + i1, l + j1, true));

                    if (arraylist.contains(chunkcoordintpair1))
                    {
                        par1EntityPlayerMP.loadedChunks.add(chunkcoordintpair1);
                    }
                }
            }
        }

        i %= 4;

        for (int l1 = 0; l1 < j * 2; l1++)
        {
            i1 += xzDirectionsConst[i][0];
            j1 += xzDirectionsConst[i][1];
            ChunkCoordIntPair chunkcoordintpair2 = PlayerInstance.func_56548_a(getPlayerInstance(k + i1, l + j1, true));

            if (arraylist.contains(chunkcoordintpair2))
            {
                par1EntityPlayerMP.loadedChunks.add(chunkcoordintpair2);
            }
        }
    }

    /**
     * Removes an EntityPlayerMP from the PlayerManager.
     */
    public void removePlayer(EntityPlayerMP par1EntityPlayerMP)
    {
        int i = (int)par1EntityPlayerMP.managedPosX >> 4;
        int j = (int)par1EntityPlayerMP.managedPosZ >> 4;

        for (int k = i - playerViewRadius; k <= i + playerViewRadius; k++)
        {
            for (int l = j - playerViewRadius; l <= j + playerViewRadius; l++)
            {
                PlayerInstance playerinstance = getPlayerInstance(k, l, false);

                if (playerinstance != null)
                {
                    playerinstance.removePlayer(par1EntityPlayerMP);
                }
            }
        }

        players.remove(par1EntityPlayerMP);
    }

    private boolean func_55302_a(int par1, int par2, int par3, int par4, int par5)
    {
        int i = par1 - par3;
        int j = par2 - par4;

        if (i < -par5 || i > par5)
        {
            return false;
        }

        return j >= -par5 && j <= par5;
    }

    /**
     * update chunks around a player being moved by server logic (e.g. cart, boat)
     */
    public void updateMountedMovingPlayer(EntityPlayerMP par1EntityPlayerMP)
    {
        int i = (int)par1EntityPlayerMP.posX >> 4;
        int j = (int)par1EntityPlayerMP.posZ >> 4;
        double d = par1EntityPlayerMP.managedPosX - par1EntityPlayerMP.posX;
        double d1 = par1EntityPlayerMP.managedPosZ - par1EntityPlayerMP.posZ;
        double d2 = d * d + d1 * d1;

        if (d2 < 64D)
        {
            return;
        }

        int k = (int)par1EntityPlayerMP.managedPosX >> 4;
        int l = (int)par1EntityPlayerMP.managedPosZ >> 4;
        int i1 = playerViewRadius;
        int j1 = i - k;
        int k1 = j - l;

        if (j1 == 0 && k1 == 0)
        {
            return;
        }

        for (int l1 = i - i1; l1 <= i + i1; l1++)
        {
            for (int i2 = j - i1; i2 <= j + i1; i2++)
            {
                if (!func_55302_a(l1, i2, k, l, i1))
                {
                    getPlayerInstance(l1, i2, true).addPlayer(par1EntityPlayerMP);
                }

                if (func_55302_a(l1 - j1, i2 - k1, i, j, i1))
                {
                    continue;
                }

                PlayerInstance playerinstance = getPlayerInstance(l1 - j1, i2 - k1, false);

                if (playerinstance != null)
                {
                    playerinstance.removePlayer(par1EntityPlayerMP);
                }
            }
        }

        func_56691_b(par1EntityPlayerMP);
        par1EntityPlayerMP.managedPosX = par1EntityPlayerMP.posX;
        par1EntityPlayerMP.managedPosZ = par1EntityPlayerMP.posZ;
    }

    public static int func_55303_a(int par0)
    {
        return par0 * 16 - 16;
    }

    static WorldServer func_56689_a(PlayerManager par0PlayerManager)
    {
        return par0PlayerManager.field_56692_a;
    }

    static LongHashMap func_56688_b(PlayerManager par0PlayerManager)
    {
        return par0PlayerManager.playerInstances;
    }

    static List func_56690_c(PlayerManager par0PlayerManager)
    {
        return par0PlayerManager.playerInstancesToUpdate;
    }
}
