package net.minecraft.src;

import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;

public abstract class ServerConfigurationManager
{
    private static final SimpleDateFormat field_56436_e = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

    /** Reference to the logger. */
    public static final Logger logger = Logger.getLogger("Minecraft");

    /** Reference to the MinecraftServer object. */
    private final MinecraftServer mcServer;

    /** A list of player entities that exist on this server. */
    public final List playerEntities = new ArrayList();
    private final BanList field_56434_g = new BanList(new File("banned-players.txt"));
    private final BanList field_56437_h = new BanList(new File("banned-ips.txt"));

    /** A set containing the OPs. */
    private Set ops;

    /** the set of all white listed IP addresses */
    private Set whiteListedIPs;

    /** Reference to the PlayerNBTManager object. */
    private IPlayerFileData playerNBTManagerObj;

    /**
     * Server setting to only allow OP's and whitelisted players to join the server
     */
    private boolean whiteListEnforced;

    /** the maximum amount of players that can be connected */
    protected int maxPlayers;
    protected int field_56435_d;
    private EnumGameType field_58048_m;
    private boolean field_58047_n;
    private int field_35482_p;

    public ServerConfigurationManager(MinecraftServer par1MinecraftServer)
    {
        ops = new HashSet();
        whiteListedIPs = new HashSet();
        field_35482_p = 0;
        mcServer = par1MinecraftServer;
        field_56434_g.func_56744_a(false);
        field_56437_h.func_56744_a(false);
        maxPlayers = 8;
    }

    public void func_56432_a(NetworkManager par1NetworkManager, EntityPlayerMP par2EntityPlayerMP)
    {
        readPlayerDataFromFile(par2EntityPlayerMP);
        par2EntityPlayerMP.setWorld(mcServer.getWorldManager(par2EntityPlayerMP.dimension));
        par2EntityPlayerMP.itemInWorldManager.setWorld((WorldServer)par2EntityPlayerMP.worldObj);
        logger.info((new StringBuilder()).append(par2EntityPlayerMP.username).append(" logged in with entity id ").append(par2EntityPlayerMP.entityId).append(" at (").append(par2EntityPlayerMP.posX).append(", ").append(par2EntityPlayerMP.posY).append(", ").append(par2EntityPlayerMP.posZ).append(")").toString());
        WorldServer worldserver = mcServer.getWorldManager(par2EntityPlayerMP.dimension);
        ChunkCoordinates chunkcoordinates = worldserver.getSpawnPoint();
        func_58045_a(par2EntityPlayerMP, null, worldserver);
        NetServerHandler netserverhandler = new NetServerHandler(mcServer, par1NetworkManager, par2EntityPlayerMP);
        netserverhandler.sendPacket(new Packet1Login(par2EntityPlayerMP.entityId, worldserver.getWorldInfo().getTerrainType(), par2EntityPlayerMP.itemInWorldManager.func_56695_b(), worldserver.getWorldInfo().isHardcoreModeEnabled(), worldserver.worldProvider.worldType, worldserver.difficultySetting, worldserver.getHeight(), getMaxPlayers()));
        netserverhandler.sendPacket(new Packet6SpawnPosition(chunkcoordinates.posX, chunkcoordinates.posY, chunkcoordinates.posZ));
        netserverhandler.sendPacket(new Packet202PlayerAbilities(par2EntityPlayerMP.capabilities));
        func_28170_a(par2EntityPlayerMP, worldserver);
        sendPacketToAllPlayers(new Packet3Chat((new StringBuilder()).append("\247e").append(par2EntityPlayerMP.username).append(" joined the game.").toString()));
        playerLoggedIn(par2EntityPlayerMP);
        netserverhandler.teleportTo(par2EntityPlayerMP.posX, par2EntityPlayerMP.posY, par2EntityPlayerMP.posZ, par2EntityPlayerMP.rotationYaw, par2EntityPlayerMP.rotationPitch);
        mcServer.func_56203_Z().addPlayer(netserverhandler);
        netserverhandler.sendPacket(new Packet4UpdateTime(worldserver.getWorldTime()));

        if (mcServer.func_56159_N().length() > 0)
        {
            par2EntityPlayerMP.func_56151_a(mcServer.func_56159_N(), mcServer.func_56165_O());
        }

        PotionEffect potioneffect;

        for (Iterator iterator = par2EntityPlayerMP.getActivePotionEffects().iterator(); iterator.hasNext(); netserverhandler.sendPacket(new Packet41EntityEffect(par2EntityPlayerMP.entityId, potioneffect)))
        {
            potioneffect = (PotionEffect)iterator.next();
        }

        par2EntityPlayerMP.func_20057_k();
    }

    /**
     * Sets the NBT manager to the one for the worldserver given
     */
    public void setPlayerManager(WorldServer par1ArrayOfWorldServer[])
    {
        playerNBTManagerObj = par1ArrayOfWorldServer[0].getSaveHandler().getPlayerNBTManager();
    }

    public void func_56423_a(EntityPlayerMP par1EntityPlayerMP, WorldServer par2WorldServer)
    {
        WorldServer worldserver = par1EntityPlayerMP.func_55080_H();

        if (par2WorldServer != null)
        {
            par2WorldServer.func_56372_E().removePlayer(par1EntityPlayerMP);
        }

        worldserver.func_56372_E().addPlayer(par1EntityPlayerMP);
        worldserver.chunkProviderServer.loadChunk((int)par1EntityPlayerMP.posX >> 4, (int)par1EntityPlayerMP.posZ >> 4);
    }

    public int func_56424_a()
    {
        return PlayerManager.func_55303_a(func_56428_o());
    }

    /**
     * called during player login. reads the player information from disk.
     */
    public void readPlayerDataFromFile(EntityPlayerMP par1EntityPlayerMP)
    {
        NBTTagCompound nbttagcompound = mcServer.worldMngr[0].getWorldInfo().func_56509_g();

        if (par1EntityPlayerMP.func_55070_y_().equals(mcServer.func_56186_G()) && nbttagcompound != null)
        {
            par1EntityPlayerMP.readFromNBT(nbttagcompound);
        }
        else
        {
            playerNBTManagerObj.readPlayerData(par1EntityPlayerMP);
        }
    }

    protected void func_56430_b(EntityPlayerMP par1EntityPlayerMP)
    {
        playerNBTManagerObj.writePlayerData(par1EntityPlayerMP);
    }

    /**
     * Called when a player successfully logs in. Reads player data from disk and inserts the player into the world.
     */
    public void playerLoggedIn(EntityPlayerMP par1EntityPlayerMP)
    {
        sendPacketToAllPlayers(new Packet201PlayerInfo(par1EntityPlayerMP.username, true, 1000));
        playerEntities.add(par1EntityPlayerMP);
        WorldServer worldserver;

        for (worldserver = mcServer.getWorldManager(par1EntityPlayerMP.dimension); !worldserver.getCollidingBoundingBoxes(par1EntityPlayerMP, par1EntityPlayerMP.boundingBox).isEmpty(); par1EntityPlayerMP.setPosition(par1EntityPlayerMP.posX, par1EntityPlayerMP.posY + 1.0D, par1EntityPlayerMP.posZ)) { }

        worldserver.spawnEntityInWorld(par1EntityPlayerMP);
        func_56423_a(par1EntityPlayerMP, null);
        EntityPlayerMP entityplayermp;

        for (Iterator iterator = playerEntities.iterator(); iterator.hasNext(); par1EntityPlayerMP.playerNetServerHandler.sendPacket(new Packet201PlayerInfo(entityplayermp.username, true, entityplayermp.ping)))
        {
            entityplayermp = (EntityPlayerMP)iterator.next();
        }
    }

    /**
     * using player's dimension, update their movement when in a vehicle (e.g. cart, boat)
     */
    public void serverUpdateMountedMovingPlayer(EntityPlayerMP par1EntityPlayerMP)
    {
        par1EntityPlayerMP.func_55080_H().func_56372_E().updateMountedMovingPlayer(par1EntityPlayerMP);
    }

    /**
     * Called when a player disconnects from the game. Writes player data to disk and removes them from the world.
     */
    public void playerLoggedOut(EntityPlayerMP par1EntityPlayerMP)
    {
        func_56430_b(par1EntityPlayerMP);
        WorldServer worldserver = par1EntityPlayerMP.func_55080_H();
        worldserver.setEntityDead(par1EntityPlayerMP);
        worldserver.func_56372_E().removePlayer(par1EntityPlayerMP);
        playerEntities.remove(par1EntityPlayerMP);
        sendPacketToAllPlayers(new Packet201PlayerInfo(par1EntityPlayerMP.username, false, 9999));
    }

    public String func_56433_a(SocketAddress par1SocketAddress, String par2Str)
    {
        if (field_56434_g.func_56740_a(par2Str))
        {
            BanEntry banentry = (BanEntry)field_56434_g.func_56741_b().get(par2Str);
            String s1 = (new StringBuilder()).append("You are banned from this server!\nReason: ").append(banentry.func_56716_f()).toString();

            if (banentry.func_56709_d() != null)
            {
                s1 = (new StringBuilder()).append(s1).append("\nYour ban will be removed on ").append(field_56436_e.format(banentry.func_56709_d())).toString();
            }

            return s1;
        }

        if (!isAllowedToLogin(par2Str))
        {
            return "You are not white-listed on this server!";
        }

        String s = par1SocketAddress.toString();
        s = s.substring(s.indexOf("/") + 1);
        s = s.substring(0, s.indexOf(":"));

        if (field_56437_h.func_56740_a(s))
        {
            BanEntry banentry1 = (BanEntry)field_56437_h.func_56741_b().get(s);
            String s2 = (new StringBuilder()).append("Your IP address is banned from this server!\nReason: ").append(banentry1.func_56716_f()).toString();

            if (banentry1.func_56709_d() != null)
            {
                s2 = (new StringBuilder()).append(s2).append("\nYour ban will be removed on ").append(field_56436_e.format(banentry1.func_56709_d())).toString();
            }

            return s2;
        }

        if (playerEntities.size() >= maxPlayers)
        {
            return "The server is full!";
        }
        else
        {
            return null;
        }
    }

    public EntityPlayerMP func_56431_a(String par1Str)
    {
        ArrayList arraylist = new ArrayList();
        Object obj = playerEntities.iterator();

        do
        {
            if (!((Iterator)(obj)).hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)((Iterator)(obj)).next();

            if (entityplayermp.username.equalsIgnoreCase(par1Str))
            {
                arraylist.add(entityplayermp);
            }
        }
        while (true);

        EntityPlayerMP entityplayermp1;

        for (obj = arraylist.iterator(); ((Iterator)(obj)).hasNext(); entityplayermp1.playerNetServerHandler.kickPlayer("You logged in from another location"))
        {
            entityplayermp1 = (EntityPlayerMP)((Iterator)(obj)).next();
        }

        if (mcServer.func_56167_K())
        {
            obj = new DemoWorldManager(mcServer.getWorldManager(0));
        }
        else
        {
            obj = new ItemInWorldManager(mcServer.getWorldManager(0));
        }

        return new EntityPlayerMP(mcServer, mcServer.getWorldManager(0), par1Str, ((ItemInWorldManager)(obj)));
    }

    /**
     * Called on respawn
     */
    public EntityPlayerMP recreatePlayerEntity(EntityPlayerMP par1EntityPlayerMP, int par2, boolean par3)
    {
        par1EntityPlayerMP.func_55080_H().func_55201_C().removeTrackedPlayerSymmetric(par1EntityPlayerMP);
        par1EntityPlayerMP.func_55080_H().func_55201_C().untrackEntity(par1EntityPlayerMP);
        par1EntityPlayerMP.func_55080_H().func_56372_E().removePlayer(par1EntityPlayerMP);
        playerEntities.remove(par1EntityPlayerMP);
        mcServer.getWorldManager(par1EntityPlayerMP.dimension).removePlayer(par1EntityPlayerMP);
        ChunkCoordinates chunkcoordinates = par1EntityPlayerMP.getSpawnChunk();
        par1EntityPlayerMP.dimension = par2;
        Object obj;

        if (mcServer.func_56167_K())
        {
            obj = new DemoWorldManager(mcServer.getWorldManager(par1EntityPlayerMP.dimension));
        }
        else
        {
            obj = new ItemInWorldManager(mcServer.getWorldManager(par1EntityPlayerMP.dimension));
        }

        EntityPlayerMP entityplayermp = new EntityPlayerMP(mcServer, mcServer.getWorldManager(par1EntityPlayerMP.dimension), par1EntityPlayerMP.username, ((ItemInWorldManager)(obj)));
        entityplayermp.func_58016_a(par1EntityPlayerMP, par3);
        entityplayermp.entityId = par1EntityPlayerMP.entityId;
        entityplayermp.playerNetServerHandler = par1EntityPlayerMP.playerNetServerHandler;
        WorldServer worldserver = mcServer.getWorldManager(par1EntityPlayerMP.dimension);
        func_58045_a(entityplayermp, par1EntityPlayerMP, worldserver);

        if (chunkcoordinates != null)
        {
            ChunkCoordinates chunkcoordinates1 = EntityPlayer.verifyRespawnCoordinates(mcServer.getWorldManager(par1EntityPlayerMP.dimension), chunkcoordinates);

            if (chunkcoordinates1 != null)
            {
                entityplayermp.setLocationAndAngles((float)chunkcoordinates1.posX + 0.5F, (float)chunkcoordinates1.posY + 0.1F, (float)chunkcoordinates1.posZ + 0.5F, 0.0F, 0.0F);
                entityplayermp.setSpawnChunk(chunkcoordinates);
            }
            else
            {
                entityplayermp.playerNetServerHandler.sendPacket(new Packet70GameEvent(0, 0));
            }
        }

        worldserver.chunkProviderServer.loadChunk((int)entityplayermp.posX >> 4, (int)entityplayermp.posZ >> 4);

        for (; !worldserver.getCollidingBoundingBoxes(entityplayermp, entityplayermp.boundingBox).isEmpty(); entityplayermp.setPosition(entityplayermp.posX, entityplayermp.posY + 1.0D, entityplayermp.posZ)) { }

        entityplayermp.playerNetServerHandler.sendPacket(new Packet9Respawn(entityplayermp.dimension, (byte)entityplayermp.worldObj.difficultySetting, entityplayermp.worldObj.getWorldInfo().getTerrainType(), entityplayermp.worldObj.getHeight(), entityplayermp.itemInWorldManager.func_56695_b()));
        entityplayermp.playerNetServerHandler.teleportTo(entityplayermp.posX, entityplayermp.posY, entityplayermp.posZ, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
        func_28170_a(entityplayermp, worldserver);
        worldserver.func_56372_E().addPlayer(entityplayermp);
        worldserver.spawnEntityInWorld(entityplayermp);
        playerEntities.add(entityplayermp);
        entityplayermp.func_20057_k();
        return entityplayermp;
    }

    /**
     * moves provided player from overworld to nether or vice versa
     */
    public void sendPlayerToOtherDimension(EntityPlayerMP par1EntityPlayerMP, int par2)
    {
        int i = par1EntityPlayerMP.dimension;
        WorldServer worldserver = mcServer.getWorldManager(par1EntityPlayerMP.dimension);
        par1EntityPlayerMP.dimension = par2;
        WorldServer worldserver1 = mcServer.getWorldManager(par1EntityPlayerMP.dimension);
        par1EntityPlayerMP.playerNetServerHandler.sendPacket(new Packet9Respawn(par1EntityPlayerMP.dimension, (byte)par1EntityPlayerMP.worldObj.difficultySetting, worldserver1.getWorldInfo().getTerrainType(), worldserver1.getHeight(), par1EntityPlayerMP.itemInWorldManager.func_56695_b()));
        worldserver.removePlayer(par1EntityPlayerMP);
        par1EntityPlayerMP.isDead = false;
        double d = par1EntityPlayerMP.posX;
        double d1 = par1EntityPlayerMP.posZ;
        double d2 = 8D;

        if (par1EntityPlayerMP.dimension == -1)
        {
            d /= d2;
            d1 /= d2;
            par1EntityPlayerMP.setLocationAndAngles(d, par1EntityPlayerMP.posY, d1, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);

            if (par1EntityPlayerMP.isEntityAlive())
            {
                worldserver.updateEntityWithOptionalForce(par1EntityPlayerMP, false);
            }
        }
        else if (par1EntityPlayerMP.dimension == 0)
        {
            d *= d2;
            d1 *= d2;
            par1EntityPlayerMP.setLocationAndAngles(d, par1EntityPlayerMP.posY, d1, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);

            if (par1EntityPlayerMP.isEntityAlive())
            {
                worldserver.updateEntityWithOptionalForce(par1EntityPlayerMP, false);
            }
        }
        else
        {
            ChunkCoordinates chunkcoordinates = worldserver1.func_56367_z();
            d = chunkcoordinates.posX;
            par1EntityPlayerMP.posY = chunkcoordinates.posY;
            d1 = chunkcoordinates.posZ;
            par1EntityPlayerMP.setLocationAndAngles(d, par1EntityPlayerMP.posY, d1, 90F, 0.0F);

            if (par1EntityPlayerMP.isEntityAlive())
            {
                worldserver.updateEntityWithOptionalForce(par1EntityPlayerMP, false);
            }
        }

        if (i != 1 && par1EntityPlayerMP.isEntityAlive())
        {
            worldserver1.spawnEntityInWorld(par1EntityPlayerMP);
            par1EntityPlayerMP.setLocationAndAngles(d, par1EntityPlayerMP.posY, d1, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);
            worldserver1.updateEntityWithOptionalForce(par1EntityPlayerMP, false);
            (new Teleporter()).placeInPortal(worldserver1, par1EntityPlayerMP);
        }

        par1EntityPlayerMP.setWorld(worldserver1);
        func_56423_a(par1EntityPlayerMP, worldserver);
        par1EntityPlayerMP.playerNetServerHandler.teleportTo(par1EntityPlayerMP.posX, par1EntityPlayerMP.posY, par1EntityPlayerMP.posZ, par1EntityPlayerMP.rotationYaw, par1EntityPlayerMP.rotationPitch);
        par1EntityPlayerMP.itemInWorldManager.setWorld(worldserver1);
        func_28170_a(par1EntityPlayerMP, worldserver1);
        func_30008_g(par1EntityPlayerMP);
    }

    /**
     * self explanitory
     */
    public void onTick()
    {
        if (++field_35482_p > 200)
        {
            field_35482_p = 0;
        }

        if (field_35482_p < playerEntities.size())
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)playerEntities.get(field_35482_p);
            sendPacketToAllPlayers(new Packet201PlayerInfo(entityplayermp.username, true, entityplayermp.ping));
        }
    }

    /**
     * sends a packet to all players
     */
    public void sendPacketToAllPlayers(Packet par1Packet)
    {
        EntityPlayerMP entityplayermp;

        for (Iterator iterator = playerEntities.iterator(); iterator.hasNext(); entityplayermp.playerNetServerHandler.sendPacket(par1Packet))
        {
            entityplayermp = (EntityPlayerMP)iterator.next();
        }
    }

    /**
     * Sends a packet to all players in the specified Dimension
     */
    public void sendPacketToAllPlayersInDimension(Packet par1Packet, int par2)
    {
        Iterator iterator = playerEntities.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp.dimension == par2)
            {
                entityplayermp.playerNetServerHandler.sendPacket(par1Packet);
            }
        }
        while (true);
    }

    /**
     * returns a string containing a comma-seperated list of player names
     */
    public String getPlayerList()
    {
        String s = "";

        for (int i = 0; i < playerEntities.size(); i++)
        {
            if (i > 0)
            {
                s = (new StringBuilder()).append(s).append(", ").toString();
            }

            s = (new StringBuilder()).append(s).append(((EntityPlayerMP)playerEntities.get(i)).username).toString();
        }

        return s;
    }

    /**
     * Returns a list of usernames of all connected players
     */
    public String[] getPlayerNamesAsList()
    {
        String as[] = new String[playerEntities.size()];

        for (int i = 0; i < playerEntities.size(); i++)
        {
            as[i] = ((EntityPlayerMP)playerEntities.get(i)).username;
        }

        return as;
    }

    public BanList func_56429_e()
    {
        return field_56434_g;
    }

    public BanList func_56427_f()
    {
        return field_56437_h;
    }

    /**
     * This adds a username to the ops list, then saves the op list
     */
    public void addOp(String par1Str)
    {
        ops.add(par1Str.toLowerCase());
    }

    /**
     * This removes a username from the ops list, then saves the op list
     */
    public void removeOp(String par1Str)
    {
        ops.remove(par1Str.toLowerCase());
    }

    /**
     * Determine if the player is allowed to connect based on current server settings
     */
    public boolean isAllowedToLogin(String par1Str)
    {
        par1Str = par1Str.trim().toLowerCase();
        return !whiteListEnforced || ops.contains(par1Str) || whiteListedIPs.contains(par1Str);
    }

    /**
     * Returns true if the player is an OP, false otherwise.
     */
    public boolean isOp(String par1Str)
    {
        return ops.contains(par1Str.trim().toLowerCase()) || mcServer.func_56187_H() && mcServer.worldMngr[0].getWorldInfo().func_56515_s() && mcServer.func_56186_G().equalsIgnoreCase(par1Str) || field_58047_n;
    }

    /**
     * gets the player entity for the player with the name specified
     */
    public EntityPlayerMP getPlayerEntity(String par1Str)
    {
        for (Iterator iterator = playerEntities.iterator(); iterator.hasNext();)
        {
            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp.username.equalsIgnoreCase(par1Str))
            {
                return entityplayermp;
            }
        }

        return null;
    }

    /**
     * sends a packet to players within d3 of point (x,y,z)
     */
    public void sendPacketToPlayersAroundPoint(double par1, double par3, double par5, double par7, int par9, Packet par10Packet)
    {
        func_28171_a(null, par1, par3, par5, par7, par9, par10Packet);
    }

    public void func_28171_a(EntityPlayer par1EntityPlayer, double par2, double par4, double par6, double par8, int par10, Packet par11Packet)
    {
        Iterator iterator = playerEntities.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp != par1EntityPlayer && entityplayermp.dimension == par10)
            {
                double d = par2 - entityplayermp.posX;
                double d1 = par4 - entityplayermp.posY;
                double d2 = par6 - entityplayermp.posZ;

                if (d * d + d1 * d1 + d2 * d2 < par8 * par8)
                {
                    entityplayermp.playerNetServerHandler.sendPacket(par11Packet);
                }
            }
        }
        while (true);
    }

    /**
     * Saves all of the player's states
     */
    public void savePlayerStates()
    {
        EntityPlayerMP entityplayermp;

        for (Iterator iterator = playerEntities.iterator(); iterator.hasNext(); func_56430_b(entityplayermp))
        {
            entityplayermp = (EntityPlayerMP)iterator.next();
        }
    }

    /**
     * add the specified player to the white list
     */
    public void addToWhiteList(String par1Str)
    {
        whiteListedIPs.add(par1Str);
    }

    /**
     * remove the specified player from the whitelist
     */
    public void removeFromWhiteList(String par1Str)
    {
        whiteListedIPs.remove(par1Str);
    }

    /**
     * returns the set of whitelisted ip addresses
     */
    public Set getWhiteListedIPs()
    {
        return whiteListedIPs;
    }

    public Set func_55207_h()
    {
        return ops;
    }

    /**
     * reloads the whitelist
     */
    public void reloadWhiteList()
    {
    }

    public void func_28170_a(EntityPlayerMP par1EntityPlayerMP, WorldServer par2WorldServer)
    {
        par1EntityPlayerMP.playerNetServerHandler.sendPacket(new Packet4UpdateTime(par2WorldServer.getWorldTime()));

        if (par2WorldServer.isRaining())
        {
            par1EntityPlayerMP.playerNetServerHandler.sendPacket(new Packet70GameEvent(1, 0));
        }
    }

    public void func_30008_g(EntityPlayerMP par1EntityPlayerMP)
    {
        par1EntityPlayerMP.func_28017_a(par1EntityPlayerMP.inventorySlots);
        par1EntityPlayerMP.func_30001_B();
    }

    /**
     * Returns the number of players on the server
     */
    public int playersOnline()
    {
        return playerEntities.size();
    }

    /**
     * Returns maximum amount of players that can join the server
     */
    public int getMaxPlayers()
    {
        return maxPlayers;
    }

    public String[] func_52019_t()
    {
        return mcServer.worldMngr[0].getSaveHandler().getPlayerNBTManager().func_52007_g();
    }

    public boolean func_56426_n()
    {
        return whiteListEnforced;
    }

    public void func_55206_a(boolean par1)
    {
        whiteListEnforced = par1;
    }

    public List func_55208_l(String par1Str)
    {
        ArrayList arraylist = new ArrayList();
        Iterator iterator = playerEntities.iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            EntityPlayerMP entityplayermp = (EntityPlayerMP)iterator.next();

            if (entityplayermp.func_55082_I().equals(par1Str))
            {
                arraylist.add(entityplayermp);
            }
        }
        while (true);

        return arraylist;
    }

    public int func_56428_o()
    {
        return field_56435_d;
    }

    public MinecraftServer func_56422_p()
    {
        return mcServer;
    }

    public NBTTagCompound func_56425_q()
    {
        return null;
    }

    private void func_58045_a(EntityPlayerMP par1EntityPlayerMP, EntityPlayerMP par2EntityPlayerMP, World par3World)
    {
        if (par2EntityPlayerMP != null)
        {
            par1EntityPlayerMP.itemInWorldManager.func_56697_a(par2EntityPlayerMP.itemInWorldManager.func_56695_b());
        }
        else if (field_58048_m != null)
        {
            par1EntityPlayerMP.itemInWorldManager.func_56697_a(field_58048_m);
        }

        par1EntityPlayerMP.itemInWorldManager.func_56694_b(par3World.getWorldInfo().func_56510_o());
    }

    public void func_58046_r()
    {
        for (; !playerEntities.isEmpty(); ((EntityPlayerMP)playerEntities.get(0)).playerNetServerHandler.kickPlayer("Server closed")) { }
    }
}
