package net.minecraft.src;

import java.util.*;

class PlayerInstance
{
    /** the list of all players in this instance (chunk) */
    private final List players = new ArrayList();

    /** the chunk the player currently resides in */
    private final ChunkCoordIntPair currentChunk;
    private short blocksToUpdate[];

    /** the number of blocks that need to be updated next tick */
    private int numBlocksToUpdate;
    private int field_48475_h;
    final PlayerManager playerManager;

    public PlayerInstance(PlayerManager par1PlayerManager, int par2, int par3)
    {
        playerManager = par1PlayerManager;
        blocksToUpdate = new short[64];
        numBlocksToUpdate = 0;
        currentChunk = new ChunkCoordIntPair(par2, par3);
        par1PlayerManager.getMinecraftServer().chunkProviderServer.loadChunk(par2, par3);
    }

    /**
     * adds this player to the playerInstance
     */
    public void addPlayer(EntityPlayerMP par1EntityPlayerMP)
    {
        if (players.contains(par1EntityPlayerMP))
        {
            throw new IllegalStateException((new StringBuilder()).append("Failed to add player. ").append(par1EntityPlayerMP).append(" already is in chunk ").append(currentChunk.chunkXPos).append(", ").append(currentChunk.chunkZPosition).toString());
        }
        else
        {
            players.add(par1EntityPlayerMP);
            par1EntityPlayerMP.loadedChunks.add(currentChunk);
            return;
        }
    }

    /**
     * remove player from this instance
     */
    public void removePlayer(EntityPlayerMP par1EntityPlayerMP)
    {
        if (!players.contains(par1EntityPlayerMP))
        {
            return;
        }

        par1EntityPlayerMP.playerNetServerHandler.sendPacket(new Packet51MapChunk(PlayerManager.func_56689_a(playerManager).getChunkFromChunkCoords(currentChunk.chunkXPos, currentChunk.chunkZPosition), true, 0));
        players.remove(par1EntityPlayerMP);
        par1EntityPlayerMP.loadedChunks.remove(currentChunk);

        if (players.isEmpty())
        {
            long l = (long)currentChunk.chunkXPos + 0x7fffffffL | (long)currentChunk.chunkZPosition + 0x7fffffffL << 32;
            PlayerManager.func_56688_b(playerManager).remove(l);

            if (numBlocksToUpdate > 0)
            {
                PlayerManager.func_56690_c(playerManager).remove(this);
            }

            playerManager.getMinecraftServer().chunkProviderServer.dropChunk(currentChunk.chunkXPos, currentChunk.chunkZPosition);
        }
    }

    /**
     * mark the block as changed so that it will update clients who need to know about it
     */
    public void markBlockNeedsUpdate(int par1, int par2, int par3)
    {
        if (numBlocksToUpdate == 0)
        {
            PlayerManager.func_56690_c(playerManager).add(this);
        }

        field_48475_h |= 1 << (par2 >> 4);

        if (numBlocksToUpdate < 64)
        {
            short word0 = (short)(par1 << 12 | par3 << 8 | par2);

            for (int i = 0; i < numBlocksToUpdate; i++)
            {
                if (blocksToUpdate[i] == word0)
                {
                    return;
                }
            }

            blocksToUpdate[numBlocksToUpdate++] = word0;
        }
    }

    /**
     * sends the packet to all players in the current instance
     */
    public void sendPacketToPlayersInInstance(Packet par1Packet)
    {
        Iterator iterator = players.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (!entityplayermp.loadedChunks.contains(currentChunk))
            {
                entityplayermp.playerNetServerHandler.sendPacket(par1Packet);
            }
        }
        while (true);
    }

    public void onUpdate()
    {
        if (numBlocksToUpdate == 0)
        {
            return;
        }

        if (numBlocksToUpdate == 1)
        {
            int i = currentChunk.chunkXPos * 16 + (blocksToUpdate[0] >> 12 & 0xf);
            int l = blocksToUpdate[0] & 0xff;
            int k1 = currentChunk.chunkZPosition * 16 + (blocksToUpdate[0] >> 8 & 0xf);
            sendPacketToPlayersInInstance(new Packet53BlockChange(i, l, k1, PlayerManager.func_56689_a(playerManager)));

            if (PlayerManager.func_56689_a(playerManager).func_48084_h(i, l, k1))
            {
                updateTileEntity(PlayerManager.func_56689_a(playerManager).getBlockTileEntity(i, l, k1));
            }
        }
        else if (numBlocksToUpdate == 64)
        {
            int j = currentChunk.chunkXPos * 16;
            int i1 = currentChunk.chunkZPosition * 16;
            sendPacketToPlayersInInstance(new Packet51MapChunk(PlayerManager.func_56689_a(playerManager).getChunkFromChunkCoords(currentChunk.chunkXPos, currentChunk.chunkZPosition), false, field_48475_h));

            for (int l1 = 0; l1 < 16; l1++)
            {
                if ((field_48475_h & 1 << l1) != 0)
                {
                    int j2 = l1 << 4;
                    List list = PlayerManager.func_56689_a(playerManager).getTileEntityList(j, j2, i1, j + 16, j2 + 16, i1 + 16);
                    TileEntity tileentity;

                    for (Iterator iterator = list.iterator(); iterator.hasNext(); updateTileEntity(tileentity))
                    {
                        tileentity = (TileEntity)iterator.next();
                    }
                }
            }
        }
        else
        {
            sendPacketToPlayersInInstance(new Packet52MultiBlockChange(currentChunk.chunkXPos, currentChunk.chunkZPosition, blocksToUpdate, numBlocksToUpdate, PlayerManager.func_56689_a(playerManager)));

            for (int k = 0; k < numBlocksToUpdate; k++)
            {
                int j1 = currentChunk.chunkXPos * 16 + (blocksToUpdate[k] >> 12 & 0xf);
                int i2 = blocksToUpdate[k] & 0xff;
                int k2 = currentChunk.chunkZPosition * 16 + (blocksToUpdate[k] >> 8 & 0xf);

                if (PlayerManager.func_56689_a(playerManager).func_48084_h(j1, i2, k2))
                {
                    updateTileEntity(PlayerManager.func_56689_a(playerManager).getBlockTileEntity(j1, i2, k2));
                }
            }
        }

        numBlocksToUpdate = 0;
        field_48475_h = 0;
    }

    /**
     * sends players update packet about the given entity
     */
    private void updateTileEntity(TileEntity par1TileEntity)
    {
        if (par1TileEntity != null)
        {
            Packet packet = par1TileEntity.getDescriptionPacket();

            if (packet != null)
            {
                sendPacketToPlayersInInstance(packet);
            }
        }
    }

    static ChunkCoordIntPair func_56548_a(PlayerInstance par0PlayerInstance)
    {
        return par0PlayerInstance.currentChunk;
    }
}
