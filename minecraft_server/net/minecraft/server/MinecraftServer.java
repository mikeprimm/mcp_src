package net.minecraft.server;

import java.io.File;
import java.io.IOException;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.src.AABBPool;
import net.minecraft.src.AnvilSaveConverter;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.CallableMCS5;
import net.minecraft.src.CallablePlayers;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ChunkProviderServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.ConvertProgressUpdater;
import net.minecraft.src.CrashReport;
import net.minecraft.src.DedicatedServer;
import net.minecraft.src.DemoWorldServer;
import net.minecraft.src.EntityTracker;
import net.minecraft.src.EnumGameType;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.ICommandManager;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.IPlayerUsage;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.IUpdatePlayerListBox;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MinecraftException;
import net.minecraft.src.NetworkListenThread;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet4UpdateTime;
import net.minecraft.src.PlayerUsageSnooper;
import net.minecraft.src.Profiler;
import net.minecraft.src.RConConsoleSource;
import net.minecraft.src.ServerCommandManager;
import net.minecraft.src.ServerConfigurationManager;
import net.minecraft.src.StatList;
import net.minecraft.src.StringTranslate;
import net.minecraft.src.StringUtils;
import net.minecraft.src.ThreadServerApplication;
import net.minecraft.src.Vec3;
import net.minecraft.src.Vec3Pool;
import net.minecraft.src.World;
import net.minecraft.src.WorldInfo;
import net.minecraft.src.WorldManager;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldServer;
import net.minecraft.src.WorldServerMulti;
import net.minecraft.src.WorldSettings;
import net.minecraft.src.WorldType;

public abstract class MinecraftServer implements ICommandSender, IPlayerUsage, Runnable
{
    /** The logging system. */
    public static Logger logger = Logger.getLogger("Minecraft");
    private static MinecraftServer field_55095_w = null;
    private final ISaveFormat field_56220_l;
    private final PlayerUsageSnooper field_56221_m = new PlayerUsageSnooper("server", this);
    private final File field_56219_n;

    /** List of names of players who are online. */
    private final List playersOnline = new ArrayList();
    private final ICommandManager field_55093_I;

    /** The server's hostname */
    private String hostname;

    /** The server's port */
    private int serverPort;
    public WorldServer worldMngr[];

    /** the server config manager for this server */
    private ServerConfigurationManager configManager;

    /**
     * Indicates whether the server is running or not. Set to false to initiate a shutdown.
     */
    private boolean serverRunning;

    /** Indicates to other classes that the server is safely stopped. */
    private boolean serverStopped;
    private int deathTime;

    /**
     * the task the server is currently working on(and will output on ouputPercentRemaining)
     */
    public String currentTask;

    /** the percentage of the current task finished so far */
    public int percentDone;

    /** True if the server is in online mode. */
    private boolean onlineMode;

    /** True if server has animals turned on */
    private boolean spawnPeacefulMobs;
    private boolean field_44002_p;

    /** Indicates whether PvP is active on the server or not. */
    private boolean pvpOn;

    /** Determines if flight is Allowed or not */
    private boolean allowFlight;

    /** The server MOTD string. */
    private String motd;

    /** Maximum build height */
    private int buildLimit;
    private long field_48074_E;
    private long field_48075_F;
    private long field_48076_G;
    private long field_48077_H;
    public final long field_48080_u[] = new long[100];
    public final long field_48079_v[] = new long[100];
    public final long field_48078_w[] = new long[100];
    public final long field_48082_x[] = new long[100];
    public final long field_40027_f[] = new long[100];
    public long field_40028_g[][];
    private KeyPair field_55094_J;
    private String field_56216_I;
    private String field_58024_J;
    private boolean field_56218_K;
    private boolean field_56212_L;
    private boolean field_56213_M;
    private String field_56214_N;
    private boolean field_56215_O;
    private long field_58026_P;
    private String field_58025_Q;

    public MinecraftServer(File par1File)
    {
        serverPort = -1;
        serverRunning = true;
        serverStopped = false;
        deathTime = 0;
        field_56214_N = "";
        field_56215_O = false;
        field_55095_w = this;
        field_56219_n = par1File;
        field_56220_l = new AnvilSaveConverter(par1File);
        field_55093_I = new ServerCommandManager();
    }

    /**
     * Initialises the server and starts it.
     */
    protected abstract boolean startServer() throws IOException;

    protected void func_56161_a(String par1Str)
    {
        if (func_56204_L().isOldMapFormat(par1Str))
        {
            logger.info("Converting map!");
            func_58019_d("menu.convertingLevel");
            func_56204_L().convertMapFormat(par1Str, new ConvertProgressUpdater(this));
        }
    }

    protected synchronized void func_58019_d(String par1Str)
    {
        field_58025_Q = par1Str;
    }

    protected void func_58017_a(String par1Str, String par2Str, long par3, WorldType par5WorldType)
    {
        func_56161_a(par1Str);
        func_58019_d("menu.loadingLevel");
        worldMngr = new WorldServer[3];
        field_40028_g = new long[worldMngr.length][100];
        ISaveHandler isavehandler = field_56220_l.getSaveLoader(par1Str, true);
        WorldInfo worldinfo = isavehandler.loadWorldInfo();
        WorldSettings worldsettings;

        if (worldinfo == null)
        {
            worldsettings = new WorldSettings(par3, func_56193_d(), func_56179_c(), func_56206_f(), par5WorldType);
        }
        else
        {
            worldsettings = new WorldSettings(worldinfo);
        }

        if (field_56212_L)
        {
            worldsettings.func_56526_a();
        }

        for (int i = 0; i < worldMngr.length; i++)
        {
            byte byte0 = 0;

            if (i == 1)
            {
                byte0 = -1;
            }

            if (i == 2)
            {
                byte0 = 1;
            }

            if (i == 0)
            {
                if (func_56167_K())
                {
                    worldMngr[i] = new DemoWorldServer(this, isavehandler, par2Str, byte0);
                }
                else
                {
                    worldMngr[i] = new WorldServer(this, isavehandler, par2Str, byte0, worldsettings);
                }
            }
            else
            {
                worldMngr[i] = new WorldServerMulti(this, isavehandler, par2Str, byte0, worldsettings, worldMngr[0]);
            }

            worldMngr[i].addWorldAccess(new WorldManager(this, worldMngr[i]));

            if (!func_56187_H())
            {
                worldMngr[i].getWorldInfo().func_56512_a(func_56193_d());
            }

            configManager.setPlayerManager(worldMngr);
        }

        func_56207_c(func_56198_e());
        func_56169_b();
    }

    protected void func_56169_b()
    {
        char c = '\304';
        long l = System.currentTimeMillis();
        func_58019_d("menu.generatingTerrain");

        for (int i = 0; i < 1; i++)
        {
            logger.info((new StringBuilder()).append("Preparing start region for level ").append(i).toString());
            WorldServer worldserver = worldMngr[i];
            ChunkCoordinates chunkcoordinates = worldserver.getSpawnPoint();

            for (int j = -c; j <= c && func_56190_j(); j += 16)
            {
                for (int k = -c; k <= c && func_56190_j(); k += 16)
                {
                    long l1 = System.currentTimeMillis();

                    if (l1 < l)
                    {
                        l = l1;
                    }

                    if (l1 > l + 1000L)
                    {
                        int i1 = (c * 2 + 1) * (c * 2 + 1);
                        int j1 = (j + c) * (c * 2 + 1) + (k + 1);
                        outputPercentRemaining("Preparing spawn area", (j1 * 100) / i1);
                        l = l1;
                    }

                    worldserver.chunkProviderServer.loadChunk(chunkcoordinates.posX + j >> 4, chunkcoordinates.posZ + k >> 4);

                    while (worldserver.updatingLighting() && func_56190_j()) ;
                }
            }
        }

        clearCurrentTask();
    }

    public abstract boolean func_56179_c();

    public abstract EnumGameType func_56193_d();

    public abstract int func_56198_e();

    public abstract boolean func_56206_f();

    /**
     * used to display a percent remaining given text and the percentage
     */
    protected void outputPercentRemaining(String par1Str, int par2)
    {
        currentTask = par1Str;
        percentDone = par2;
        logger.info((new StringBuilder()).append(par1Str).append(": ").append(par2).append("%").toString());
    }

    /**
     * set current task to null and set its percentage to 0
     */
    protected void clearCurrentTask()
    {
        currentTask = null;
        percentDone = 0;
    }

    protected void func_58022_a(boolean par1)
    {
        if (field_56213_M)
        {
            return;
        }

        WorldServer aworldserver[] = worldMngr;
        int i = aworldserver.length;

        for (int j = 0; j < i; j++)
        {
            WorldServer worldserver = aworldserver[j];

            if (worldserver == null)
            {
                continue;
            }

            if (!par1)
            {
                logger.info((new StringBuilder()).append("Saving chunks for level '").append(worldserver.getWorldInfo().func_55310_h()).append("'/").append(worldserver.worldProvider).toString());
            }

            try
            {
                worldserver.func_56365_a(true, null);
            }
            catch (MinecraftException minecraftexception)
            {
                logger.warning(minecraftexception.getMessage());
            }
        }
    }

    /**
     * Saves all necessary data as preparation for stopping the server.
     */
    protected void stopServer()
    {
        if (field_56213_M)
        {
            return;
        }

        logger.info("Stopping server");

        if (func_56203_Z() != null)
        {
            func_56203_Z().func_56504_a();
        }

        if (configManager != null)
        {
            logger.info("Saving players");
            configManager.savePlayerStates();
            configManager.func_58046_r();
        }

        logger.info("Saving worlds");
        func_58022_a(false);
        WorldServer aworldserver[] = worldMngr;
        int i = aworldserver.length;

        for (int j = 0; j < i; j++)
        {
            WorldServer worldserver = aworldserver[j];
            worldserver.func_30006_w();
        }

        if (field_56221_m != null && field_56221_m.func_56624_c())
        {
            field_56221_m.func_56617_d();
        }
    }

    public String func_56162_i()
    {
        return hostname;
    }

    public void func_56166_d(String par1Str)
    {
        hostname = par1Str;
    }

    public boolean func_56190_j()
    {
        return serverRunning;
    }

    /**
     * Sets the serverRunning variable to false, in order to get the server to shut down.
     */
    public void initiateShutdown()
    {
        serverRunning = false;
    }

    public void run()
    {
        try
        {
            if (startServer())
            {
                long l = System.currentTimeMillis();
                long l1 = 0L;

                while (serverRunning)
                {
                    long l2 = System.currentTimeMillis();
                    long l3 = l2 - l;

                    if (l3 > 2000L && l - field_58026_P >= 15000L)
                    {
                        logger.warning("Can't keep up! Did the system time change, or is the server overloaded?");
                        l3 = 2000L;
                        field_58026_P = l;
                    }

                    if (l3 < 0L)
                    {
                        logger.warning("Time ran backwards! Did the system time change?");
                        l3 = 0L;
                    }

                    l1 += l3;
                    l = l2;

                    if (worldMngr[0].func_56366_x())
                    {
                        func_56174_n();
                        l1 = 0L;
                    }
                    else
                    {
                        while (l1 > 50L)
                        {
                            l1 -= 50L;
                            func_56174_n();
                        }
                    }

                    Thread.sleep(1L);
                    field_56215_O = true;
                }
            }
            else
            {
                func_56182_a(null);
            }
        }
        catch (Throwable throwable1)
        {
            throwable1.printStackTrace();
            logger.log(Level.SEVERE, (new StringBuilder()).append("Encountered an unexpected exception ").append(throwable1.getClass().getSimpleName()).toString(), throwable1);
            CrashReport crashreport = func_55090_a(new CrashReport("Exception in server tick loop", throwable1));
            File file = new File(new File(func_56200_l(), "crash-reports"), (new StringBuilder()).append("crash-").append((new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date())).append("-server.txt").toString());

            if (crashreport.func_55290_a(file))
            {
                logger.severe((new StringBuilder()).append("This crash report has been saved to: ").append(file.getAbsolutePath()).toString());
            }
            else
            {
                logger.severe("We were unable to save this crash report to disk.");
            }

            func_56182_a(crashreport);
        }
        finally
        {
            try
            {
                stopServer();
                serverStopped = true;
            }
            catch (Throwable throwable2)
            {
                throwable2.printStackTrace();
            }
            finally
            {
                func_56188_m();
            }
        }
    }

    protected File func_56200_l()
    {
        return new File(".");
    }

    protected void func_56182_a(CrashReport crashreport)
    {
    }

    protected void func_56188_m()
    {
    }

    protected void func_56174_n()
    {
        long l = System.nanoTime();
        AxisAlignedBB.func_58089_a().func_58066_a();
        Vec3.func_58052_a().func_58075_a();
        deathTime++;
        func_56160_o();

        if (deathTime % 100 == 0)
        {
            Profiler.endStartSection("save");
            configManager.savePlayerStates();
            func_58022_a(true);
        }

        field_40027_f[deathTime % 100] = System.nanoTime() - l;
        field_48080_u[deathTime % 100] = Packet.field_48099_n - field_48074_E;
        field_48074_E = Packet.field_48099_n;
        field_48079_v[deathTime % 100] = Packet.field_48100_o - field_48075_F;
        field_48075_F = Packet.field_48100_o;
        field_48078_w[deathTime % 100] = Packet.field_48101_l - field_48076_G;
        field_48076_G = Packet.field_48101_l;
        field_48082_x[deathTime % 100] = Packet.field_48102_m - field_48077_H;
        field_48077_H = Packet.field_48102_m;

        if (!field_56221_m.func_56624_c() && deathTime > 100)
        {
            field_56221_m.func_56619_a();
        }

        if (deathTime % 6000 == 0)
        {
            field_56221_m.func_56622_b();
        }
    }

    public void func_56160_o()
    {
        for (int i = 0; i < worldMngr.length; i++)
        {
            long l = System.nanoTime();

            if (i == 0 || func_56196_p())
            {
                WorldServer worldserver = worldMngr[i];

                if (deathTime % 20 == 0)
                {
                    configManager.sendPacketToAllPlayersInDimension(new Packet4UpdateTime(worldserver.getWorldTime()), worldserver.worldProvider.worldType);
                }

                worldserver.tick();

                while (worldserver.updatingLighting()) ;

                worldserver.updateEntities();
                worldserver.func_55201_C().updateTrackedEntities();
            }

            field_40028_g[i][deathTime % 100] = System.nanoTime() - l;
        }

        func_56203_Z().handleNetworkListenThread();
        configManager.onTick();
        IUpdatePlayerListBox iupdateplayerlistbox;

        for (Iterator iterator = playersOnline.iterator(); iterator.hasNext(); iupdateplayerlistbox.update())
        {
            iupdateplayerlistbox = (IUpdatePlayerListBox)iterator.next();
        }
    }

    public boolean func_56196_p()
    {
        return true;
    }

    /**
     * Adds a player's name to the list of online players.
     */
    public void addToOnlinePlayerList(IUpdatePlayerListBox par1IUpdatePlayerListBox)
    {
        playersOnline.add(par1IUpdatePlayerListBox);
    }

    public static void main(String par0ArrayOfStr[])
    {
        StatList.func_27092_a();

        try
        {
            boolean flag = !java.awt.GraphicsEnvironment.isHeadless();
            String s = null;
            String s1 = ".";
            String s2 = null;
            boolean flag1 = false;
            boolean flag2 = false;
            int i = -1;

            for (int j = 0; j < par0ArrayOfStr.length; j++)
            {
                String s3 = par0ArrayOfStr[j];
                String s4 = j != par0ArrayOfStr.length - 1 ? par0ArrayOfStr[j + 1] : null;
                boolean flag3 = false;

                if (s3.equals("nogui") || s3.equals("--nogui"))
                {
                    flag = false;
                }
                else if (s3.equals("--port") && s4 != null)
                {
                    flag3 = true;

                    try
                    {
                        i = Integer.parseInt(s4);
                    }
                    catch (NumberFormatException numberformatexception) { }
                }
                else if (s3.equals("--singleplayer") && s4 != null)
                {
                    flag3 = true;
                    s = s4;
                }
                else if (s3.equals("--universe") && s4 != null)
                {
                    flag3 = true;
                    s1 = s4;
                }
                else if (s3.equals("--world") && s4 != null)
                {
                    flag3 = true;
                    s2 = s4;
                }
                else if (s3.equals("--demo"))
                {
                    flag1 = true;
                }
                else if (s3.equals("--bonusChest"))
                {
                    flag2 = true;
                }

                if (flag3)
                {
                    j++;
                }
            }

            DedicatedServer dedicatedserver = new DedicatedServer(new File(s1));

            if (s != null)
            {
                dedicatedserver.func_56171_k(s);
            }

            if (s2 != null)
            {
                dedicatedserver.func_58021_m(s2);
            }

            if (i >= 0)
            {
                dedicatedserver.func_56181_b(i);
            }

            if (flag1)
            {
                dedicatedserver.func_56194_a(true);
            }

            if (flag2)
            {
                dedicatedserver.func_56177_b(true);
            }

            if (flag)
            {
                dedicatedserver.func_56224_ag();
            }

            dedicatedserver.func_56158_q();
        }
        catch (Exception exception)
        {
            logger.log(Level.SEVERE, "Failed to start the minecraft server", exception);
        }
    }

    public void func_56158_q()
    {
        (new ThreadServerApplication(this, "Server thread")).start();
    }

    /**
     * Returns a File object from the specified string.
     */
    public File getFile(String par1Str)
    {
        return new File(par1Str);
    }

    /**
     * Logs the message with a level of INFO.
     */
    public void log(String par1Str)
    {
        logger.info(par1Str);
    }

    /**
     * logs the warning same as: logger.warning(String);
     */
    public void logWarning(String par1Str)
    {
        logger.warning(par1Str);
    }

    /**
     * gets the worldServer by the given dimension
     */
    public WorldServer getWorldManager(int par1)
    {
        if (par1 == -1)
        {
            return worldMngr[1];
        }

        if (par1 == 1)
        {
            return worldMngr[2];
        }
        else
        {
            return worldMngr[0];
        }
    }

    /**
     * Returns the server hostname
     */
    public String getHostname()
    {
        return hostname;
    }

    /**
     * Returns the server port
     */
    public int getPort()
    {
        return serverPort;
    }

    /**
     * Returns the server message of the day
     */
    public String getMotd()
    {
        return motd;
    }

    /**
     * Returns the server version string
     */
    public String getVersionString()
    {
        return "12w26a";
    }

    /**
     * Returns the number of players on the server
     */
    public int playersOnline()
    {
        return configManager.playersOnline();
    }

    /**
     * Returns the maximum number of players allowed on the server
     */
    public int getMaxPlayers()
    {
        return configManager.getMaxPlayers();
    }

    /**
     * Returns a list of usernames of all connected players
     */
    public String[] getPlayerNamesAsList()
    {
        return configManager.getPlayerNamesAsList();
    }

    public String getPlugin()
    {
        return "";
    }

    /**
     * Handle a command received by an RCon instance
     */
    public String handleRConCommand(String par1Str)
    {
        RConConsoleSource.instance.resetLog();
        field_55093_I.func_55236_a(RConConsoleSource.instance, par1Str);
        return RConConsoleSource.instance.getLogContents();
    }

    /**
     * Returns true if debugging is enabled, false otherwise
     */
    public boolean isDebuggingEnabled()
    {
        return false;
    }

    /**
     * Log severe error message
     */
    public void logSevere(String par1Str)
    {
        logger.log(Level.SEVERE, par1Str);
    }

    public void logIn(String par1Str)
    {
        if (isDebuggingEnabled())
        {
            logger.log(Level.INFO, par1Str);
        }
    }

    public String func_52003_getServerModName()
    {
        return "vanilla";
    }

    protected CrashReport func_55090_a(CrashReport par1CrashReport)
    {
        par1CrashReport.func_55285_a("Is Modded", new CallableMCS5(this));

        if (configManager != null)
        {
            par1CrashReport.func_55285_a("Player Count", new CallablePlayers(this));
        }

        if (worldMngr != null)
        {
            WorldServer aworldserver[] = worldMngr;
            int i = aworldserver.length;

            for (int j = 0; j < i; j++)
            {
                WorldServer worldserver = aworldserver[j];

                if (worldserver != null)
                {
                    worldserver.func_55198_a(par1CrashReport);
                }
            }
        }

        return par1CrashReport;
    }

    public List func_55088_a(ICommandSender par1ICommandSender, String par2Str)
    {
        ArrayList arraylist = new ArrayList();

        if (par2Str.startsWith("/"))
        {
            par2Str = par2Str.substring(1);
            boolean flag = !par2Str.contains(" ");
            List list = field_55093_I.func_55237_b(par1ICommandSender, par2Str);

            if (list != null)
            {
                for (Iterator iterator = list.iterator(); iterator.hasNext();)
                {
                    String s1 = (String)iterator.next();

                    if (flag)
                    {
                        arraylist.add((new StringBuilder()).append("/").append(s1).toString());
                    }
                    else
                    {
                        arraylist.add(s1);
                    }
                }
            }

            return arraylist;
        }

        String as[] = par2Str.split(" ", -1);
        String s = as[as.length - 1];
        String as1[] = configManager.getPlayerNamesAsList();
        int i = as1.length;

        for (int j = 0; j < i; j++)
        {
            String s2 = as1[j];

            if (CommandBase.func_55155_a(s, s2))
            {
                arraylist.add(s2);
            }
        }

        return arraylist;
    }

    public static MinecraftServer func_55089_q()
    {
        return field_55095_w;
    }

    public String func_55070_y_()
    {
        return "Server";
    }

    public void func_55072_b(String par1Str)
    {
        logger.info(StringUtils.func_55306_a(par1Str));
    }

    public boolean func_55071_c(String par1Str)
    {
        return true;
    }

    public String func_55069_a(String par1Str, Object par2ArrayOfObj[])
    {
        return StringTranslate.getInstance().translateKeyFormat(par1Str, par2ArrayOfObj);
    }

    public ICommandManager func_55091_s()
    {
        return field_55093_I;
    }

    public KeyPair func_55087_t()
    {
        return field_55094_J;
    }

    public int func_56189_F()
    {
        return serverPort;
    }

    public void func_56181_b(int par1)
    {
        serverPort = par1;
    }

    public String func_56186_G()
    {
        return field_56216_I;
    }

    public void func_56171_k(String par1Str)
    {
        field_56216_I = par1Str;
    }

    public boolean func_56187_H()
    {
        return field_56216_I != null;
    }

    public String func_58018_H()
    {
        return field_58024_J;
    }

    public void func_58021_m(String par1Str)
    {
        field_58024_J = par1Str;
    }

    public void func_56178_a(KeyPair par1KeyPair)
    {
        field_55094_J = par1KeyPair;
    }

    public void func_56207_c(int par1)
    {
        for (int i = 0; i < worldMngr.length; i++)
        {
            WorldServer worldserver = worldMngr[i];

            if (worldserver == null)
            {
                continue;
            }

            if (worldserver.getWorldInfo().isHardcoreModeEnabled())
            {
                worldserver.difficultySetting = 3;
                worldserver.setAllowedSpawnTypes(true, true);
                continue;
            }

            if (func_56187_H())
            {
                worldserver.difficultySetting = par1;
                worldserver.setAllowedSpawnTypes(((World)(worldserver)).difficultySetting > 0, true);
            }
            else
            {
                worldserver.difficultySetting = par1;
                worldserver.setAllowedSpawnTypes(func_56208_J(), spawnPeacefulMobs);
            }
        }
    }

    protected boolean func_56208_J()
    {
        return true;
    }

    public boolean func_56167_K()
    {
        return field_56218_K;
    }

    public void func_56194_a(boolean par1)
    {
        field_56218_K = par1;
    }

    public void func_56177_b(boolean par1)
    {
        field_56212_L = par1;
    }

    public ISaveFormat func_56204_L()
    {
        return field_56220_l;
    }

    public void func_56201_M()
    {
        field_56213_M = true;
        func_56204_L().func_56408_b();

        for (int i = 0; i < worldMngr.length; i++)
        {
            WorldServer worldserver = worldMngr[i];

            if (worldserver != null)
            {
                worldMngr[0].func_30006_w();
            }
        }

        func_56204_L().func_56409_b(worldMngr[0].getSaveHandler().func_56406_g());
        initiateShutdown();
    }

    public String func_56159_N()
    {
        return field_56214_N;
    }

    public void func_56185_m(String par1Str)
    {
        field_56214_N = par1Str;
    }

    public void func_56154_a(PlayerUsageSnooper par1PlayerUsageSnooper)
    {
        par1PlayerUsageSnooper.func_52014_a("whitelist_enabled", Boolean.valueOf(false));
        par1PlayerUsageSnooper.func_52014_a("whitelist_count", Integer.valueOf(0));
        par1PlayerUsageSnooper.func_52014_a("players_current", Integer.valueOf(playersOnline()));
        par1PlayerUsageSnooper.func_52014_a("players_max", Integer.valueOf(getMaxPlayers()));
        par1PlayerUsageSnooper.func_52014_a("players_seen", Integer.valueOf(configManager.func_52019_t().length));
        par1PlayerUsageSnooper.func_52014_a("uses_auth", Boolean.valueOf(onlineMode));
        par1PlayerUsageSnooper.func_52014_a("gui_state", func_56163_aa() ? "enabled" : "disabled");
        par1PlayerUsageSnooper.func_52014_a("avg_tick_ms", Integer.valueOf((int)(MathHelper.func_56405_a(field_40027_f) * 9.9999999999999995E-007D)));
        par1PlayerUsageSnooper.func_52014_a("avg_sent_packet_count", Integer.valueOf((int)MathHelper.func_56405_a(field_48080_u)));
        par1PlayerUsageSnooper.func_52014_a("avg_sent_packet_size", Integer.valueOf((int)MathHelper.func_56405_a(field_48079_v)));
        par1PlayerUsageSnooper.func_52014_a("avg_rec_packet_count", Integer.valueOf((int)MathHelper.func_56405_a(field_48078_w)));
        par1PlayerUsageSnooper.func_52014_a("avg_rec_packet_size", Integer.valueOf((int)MathHelper.func_56405_a(field_48082_x)));
        int i = 0;

        for (int j = 0; j < worldMngr.length; j++)
        {
            if (worldMngr[j] != null)
            {
                WorldServer worldserver = worldMngr[j];
                WorldInfo worldinfo = worldserver.getWorldInfo();
                par1PlayerUsageSnooper.func_52014_a((new StringBuilder()).append("world[").append(i).append("][dimension]").toString(), Integer.valueOf(worldserver.worldProvider.worldType));
                par1PlayerUsageSnooper.func_52014_a((new StringBuilder()).append("world[").append(i).append("][mode]").toString(), worldinfo.func_56510_o());
                par1PlayerUsageSnooper.func_52014_a((new StringBuilder()).append("world[").append(i).append("][difficulty]").toString(), Integer.valueOf(worldserver.difficultySetting));
                par1PlayerUsageSnooper.func_52014_a((new StringBuilder()).append("world[").append(i).append("][hardcore]").toString(), Boolean.valueOf(worldinfo.isHardcoreModeEnabled()));
                par1PlayerUsageSnooper.func_52014_a((new StringBuilder()).append("world[").append(i).append("][generator_name]").toString(), worldinfo.getTerrainType().func_48449_a());
                par1PlayerUsageSnooper.func_52014_a((new StringBuilder()).append("world[").append(i).append("][generator_version]").toString(), Integer.valueOf(worldinfo.getTerrainType().getGeneratorVersion()));
                par1PlayerUsageSnooper.func_52014_a((new StringBuilder()).append("world[").append(i).append("][height]").toString(), Integer.valueOf(buildLimit));
                par1PlayerUsageSnooper.func_52014_a((new StringBuilder()).append("world[").append(i).append("][chunks_loaded]").toString(), Integer.valueOf(worldserver.getChunkProvider().func_56538_d()));
                i++;
            }
        }

        par1PlayerUsageSnooper.func_52014_a("worlds", Integer.valueOf(i));
    }

    public void func_56153_b(PlayerUsageSnooper par1PlayerUsageSnooper)
    {
        par1PlayerUsageSnooper.func_52014_a("singleplayer", Boolean.valueOf(func_56187_H()));
        par1PlayerUsageSnooper.func_52014_a("server_brand", func_52003_getServerModName());
        par1PlayerUsageSnooper.func_52014_a("gui_supported", java.awt.GraphicsEnvironment.isHeadless() ? "headless" : "supported");
        par1PlayerUsageSnooper.func_52014_a("dedicated", Boolean.valueOf(func_56172_P()));
    }

    public int func_56165_O()
    {
        return 16;
    }

    public abstract boolean func_56172_P();

    public boolean func_56211_Q()
    {
        return onlineMode;
    }

    public void func_56168_c(boolean par1)
    {
        onlineMode = par1;
    }

    public boolean func_56183_R()
    {
        return spawnPeacefulMobs;
    }

    public void func_56209_d(boolean par1)
    {
        spawnPeacefulMobs = par1;
    }

    public boolean func_56184_S()
    {
        return field_44002_p;
    }

    public void func_56176_e(boolean par1)
    {
        field_44002_p = par1;
    }

    public boolean func_56192_T()
    {
        return pvpOn;
    }

    public void func_56180_f(boolean par1)
    {
        pvpOn = par1;
    }

    public boolean func_56195_U()
    {
        return allowFlight;
    }

    public void func_56157_g(boolean par1)
    {
        allowFlight = par1;
    }

    public String func_56155_V()
    {
        return motd;
    }

    public void func_56170_n(String par1Str)
    {
        motd = par1Str;
    }

    public int func_56175_W()
    {
        return buildLimit;
    }

    public void func_56164_d(int par1)
    {
        buildLimit = par1;
    }

    public boolean func_56210_X()
    {
        return serverStopped;
    }

    public ServerConfigurationManager func_56173_Y()
    {
        return configManager;
    }

    public void func_56197_a(ServerConfigurationManager par1ServerConfigurationManager)
    {
        configManager = par1ServerConfigurationManager;
    }

    public void func_58023_a(EnumGameType par1EnumGameType)
    {
        for (int i = 0; i < worldMngr.length; i++)
        {
            func_55089_q().worldMngr[i].getWorldInfo().func_56512_a(par1EnumGameType);
        }
    }

    public abstract NetworkListenThread func_56203_Z();

    public boolean func_56163_aa()
    {
        return false;
    }

    public abstract String func_58020_a(EnumGameType enumgametype, boolean flag);

    public static ServerConfigurationManager func_56156_a(MinecraftServer par0MinecraftServer)
    {
        return par0MinecraftServer.configManager;
    }
}
