package net.minecraft.src;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.MinecraftServer;

public class DedicatedServer extends MinecraftServer implements IServer
{
    private final List field_56234_k = Collections.synchronizedList(new ArrayList());
    private RConThreadQuery field_56232_l;
    private RConThreadMain field_56233_m;
    private PropertyManager field_56230_n;
    private boolean field_56231_o;
    private EnumGameType field_56229_p;
    private NetworkListenThread field_56228_q;
    private boolean field_56227_r;

    public DedicatedServer(File par1File)
    {
        super(par1File);
        field_56227_r = false;
        new DedicatedServerSleepThread(this);
    }

    /**
     * Initialises the server and starts it.
     */
    protected boolean startServer() throws IOException
    {
        DedicatedServerCommandThread dedicatedservercommandthread = new DedicatedServerCommandThread(this);
        dedicatedservercommandthread.setDaemon(true);
        dedicatedservercommandthread.start();
        ConsoleLogManager.init();
        logger.info("Starting minecraft server version 12w26a");

        if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L)
        {
            logger.warning("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
        }

        logger.info("Loading properties");
        field_56230_n = new PropertyManager(new File("server.properties"));

        if (func_56187_H())
        {
            func_56166_d("127.0.0.1");
        }
        else
        {
            func_56168_c(field_56230_n.getBooleanProperty("online-mode", true));
            func_56166_d(field_56230_n.getStringProperty("server-ip", ""));
        }

        func_56209_d(field_56230_n.getBooleanProperty("spawn-animals", true));
        func_56176_e(field_56230_n.getBooleanProperty("spawn-npcs", true));
        func_56180_f(field_56230_n.getBooleanProperty("pvp", true));
        func_56157_g(field_56230_n.getBooleanProperty("allow-flight", false));
        func_56185_m(field_56230_n.getStringProperty("texture-pack", ""));
        func_56170_n(field_56230_n.getStringProperty("motd", "A Minecraft Server"));
        field_56231_o = field_56230_n.getBooleanProperty("generate-structures", true);
        int i = field_56230_n.getIntProperty("gamemode", EnumGameType.SURVIVAL.func_56607_a());
        field_56229_p = WorldSettings.func_56524_a(i);
        logger.info((new StringBuilder()).append("Default game type: ").append(field_56229_p).toString());
        InetAddress inetaddress = null;

        if (func_56162_i().length() > 0)
        {
            inetaddress = InetAddress.getByName(func_56162_i());
        }

        if (func_56189_F() < 0)
        {
            func_56181_b(field_56230_n.getIntProperty("server-port", 25565));
        }

        logger.info("Generating keypair");
        func_56178_a(CryptManager.func_55266_a());
        logger.info((new StringBuilder()).append("Starting Minecraft server on ").append(func_56162_i().length() != 0 ? func_56162_i() : "*").append(":").append(func_56189_F()).toString());

        try
        {
            field_56228_q = new DedicatedServerListenThread(this, inetaddress, func_56189_F());
        }
        catch (IOException ioexception)
        {
            logger.warning("**** FAILED TO BIND TO PORT!");
            logger.log(Level.WARNING, (new StringBuilder()).append("The exception was: ").append(ioexception.toString()).toString());
            logger.warning("Perhaps a server is already running on that port?");
            return false;
        }

        if (!func_56211_Q())
        {
            logger.warning("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
            logger.warning("The server will make no attempt to authenticate usernames. Beware.");
            logger.warning("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
            logger.warning("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
        }

        func_56197_a(new DedicatedPlayerList(this));
        long l = System.nanoTime();

        if (func_58018_H() == null)
        {
            func_58021_m(field_56230_n.getStringProperty("level-name", "world"));
        }

        String s = field_56230_n.getStringProperty("level-seed", "");
        String s1 = field_56230_n.getStringProperty("level-type", "DEFAULT");
        long l1 = (new Random()).nextLong();

        if (s.length() > 0)
        {
            try
            {
                long l2 = Long.parseLong(s);

                if (l2 != 0L)
                {
                    l1 = l2;
                }
            }
            catch (NumberFormatException numberformatexception)
            {
                l1 = s.hashCode();
            }
        }

        WorldType worldtype = WorldType.parseWorldType(s1);

        if (worldtype == null)
        {
            worldtype = WorldType.DEFAULT;
        }

        func_56164_d(field_56230_n.getIntProperty("max-build-height", 256));
        func_56164_d(((func_56175_W() + 8) / 16) * 16);
        func_56164_d(MathHelper.clamp_int(func_56175_W(), 64, 256));
        field_56230_n.setProperty("max-build-height", Integer.valueOf(func_56175_W()));
        logger.info((new StringBuilder()).append("Preparing level \"").append(func_58018_H()).append("\"").toString());
        func_58017_a(func_58018_H(), func_58018_H(), l1, worldtype);
        long l3 = System.nanoTime() - l;
        String s2 = String.format("%.3fs", new Object[]
                {
                    Double.valueOf((double)l3 / 1000000000D)
                });
        logger.info((new StringBuilder()).append("Done (").append(s2).append(")! For help, type \"help\" or \"?\"").toString());

        if (field_56230_n.getBooleanProperty("enable-query", false))
        {
            logger.info("Starting GS4 status listener");
            field_56232_l = new RConThreadQuery(this);
            field_56232_l.startThread();
        }

        if (field_56230_n.getBooleanProperty("enable-rcon", false))
        {
            logger.info("Starting remote control listener");
            field_56233_m = new RConThreadMain(this);
            field_56233_m.startThread();
        }

        return true;
    }

    public boolean func_56179_c()
    {
        return field_56231_o;
    }

    public EnumGameType func_56193_d()
    {
        return field_56229_p;
    }

    public int func_56198_e()
    {
        return field_56230_n.getIntProperty("difficulty", 1);
    }

    public boolean func_56206_f()
    {
        return field_56230_n.getBooleanProperty("hardcore", false);
    }

    protected void func_56182_a(CrashReport par1CrashReport)
    {
        while (func_56190_j())
        {
            func_56226_ac();

            try
            {
                Thread.sleep(10L);
            }
            catch (InterruptedException interruptedexception)
            {
                interruptedexception.printStackTrace();
            }
        }
    }

    protected void func_56188_m()
    {
        System.exit(0);
    }

    public void func_56160_o()
    {
        super.func_56160_o();
        func_56226_ac();
    }

    public boolean func_56196_p()
    {
        return field_56230_n.getBooleanProperty("allow-nether", true);
    }

    public boolean func_56208_J()
    {
        return field_56230_n.getBooleanProperty("spawn-monsters", true);
    }

    public void func_56154_a(PlayerUsageSnooper par1PlayerUsageSnooper)
    {
        par1PlayerUsageSnooper.func_52014_a("whitelist_enabled", Boolean.valueOf(func_56223_ad().func_56426_n()));
        par1PlayerUsageSnooper.func_52014_a("whitelist_count", Integer.valueOf(func_56223_ad().getWhiteListedIPs().size()));
        super.func_56154_a(par1PlayerUsageSnooper);
    }

    public void func_56225_a(String par1Str, ICommandSender par2ICommandSender)
    {
        field_56234_k.add(new ServerCommand(par1Str, par2ICommandSender));
    }

    public void func_56226_ac()
    {
        ServerCommand servercommand;

        for (; !field_56234_k.isEmpty(); func_55091_s().func_55236_a(servercommand.field_55233_b, servercommand.command))
        {
            servercommand = (ServerCommand)field_56234_k.remove(0);
        }
    }

    public boolean func_56172_P()
    {
        return true;
    }

    public DedicatedPlayerList func_56223_ad()
    {
        return (DedicatedPlayerList)super.func_56173_Y();
    }

    public NetworkListenThread func_56203_Z()
    {
        return field_56228_q;
    }

    /**
     * Returns the specified property value as an int, or a default if the property doesn't exist
     */
    public int getIntProperty(String par1Str, int par2)
    {
        return field_56230_n.getIntProperty(par1Str, par2);
    }

    /**
     * Returns the specified property value as a String, or a default if the property doesn't exist
     */
    public String getStringProperty(String par1Str, String par2Str)
    {
        return field_56230_n.getStringProperty(par1Str, par2Str);
    }

    public boolean func_56222_a(String par1Str, boolean par2)
    {
        return field_56230_n.getBooleanProperty(par1Str, par2);
    }

    /**
     * Saves an Object with the given property name
     */
    public void setProperty(String par1Str, Object par2Obj)
    {
        field_56230_n.setProperty(par1Str, par2Obj);
    }

    /**
     * Saves all of the server properties to the properties file
     */
    public void saveProperties()
    {
        field_56230_n.saveProperties();
    }

    /**
     * Returns the filename where server properties are stored
     */
    public String getSettingsFilename()
    {
        File file = field_56230_n.getPropertiesFile();

        if (file != null)
        {
            return file.getAbsolutePath();
        }
        else
        {
            return "No settings file";
        }
    }

    public void func_56224_ag()
    {
        ServerGUI.func_56550_a(this);
        field_56227_r = true;
    }

    public boolean func_56163_aa()
    {
        return field_56227_r;
    }

    public String func_58020_a(EnumGameType par1EnumGameType, boolean par2)
    {
        return "";
    }

    public ServerConfigurationManager func_56173_Y()
    {
        return func_56223_ad();
    }
}
