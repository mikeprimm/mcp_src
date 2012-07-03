package net.minecraft.src;

import java.io.IOException;
import java.net.Socket;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import net.minecraft.server.MinecraftServer;

public class NetLoginHandler extends NetHandler
{
    private byte field_58074_d[];

    /** The Minecraft logger. */
    public static Logger logger = Logger.getLogger("Minecraft");

    /** The Random object used to generate serverId hex strings. */
    private static Random rand = new Random();
    public TcpConnection field_56681_b;

    /**
     * Returns if the login handler is finished and can be removed. It is set to true on either error or successful
     * login.
     */
    public boolean finishedProcessing;

    /** Reference to the MinecraftServer object. */
    private MinecraftServer mcServer;

    /** While waiting to login, if this field ++'s to 600 it will kick you. */
    private int loginTimer;
    private String field_55256_g;
    private volatile boolean field_55258_h;
    private String field_55259_i;
    private SecretKey field_55257_j;

    public NetLoginHandler(MinecraftServer par1MinecraftServer, Socket par2Socket, String par3Str) throws IOException
    {
        finishedProcessing = false;
        loginTimer = 0;
        field_55256_g = null;
        field_55258_h = false;
        field_55259_i = "";
        field_55257_j = null;
        mcServer = par1MinecraftServer;
        field_56681_b = new TcpConnection(par2Socket, par3Str, this, par1MinecraftServer.func_55087_t().getPrivate());
        field_56681_b.field_56655_e = 0;
    }

    /**
     * Logs the user in if a login packet is found, otherwise keeps processing network packets unless the timeout has
     * occurred.
     */
    public void tryLogin()
    {
        if (field_55258_h)
        {
            func_55254_c();
        }

        if (loginTimer++ == 600)
        {
            kickUser("Took too long to log in");
        }
        else
        {
            field_56681_b.processReadPackets();
        }
    }

    /**
     * Disconnects the user with the given reason.
     */
    public void kickUser(String par1Str)
    {
        try
        {
            logger.info((new StringBuilder()).append("Disconnecting ").append(func_55250_d()).append(": ").append(par1Str).toString());
            field_56681_b.addToSendQueue(new Packet255KickDisconnect(par1Str));
            field_56681_b.serverShutdown();
            finishedProcessing = true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void func_55247_a(Packet2ClientProtocol par1Packet2ClientProtocol)
    {
        field_55256_g = par1Packet2ClientProtocol.func_55113_c();

        if (!field_55256_g.equals(StringUtils.func_55306_a(field_55256_g)))
        {
            kickUser("Invalid username!");
            return;
        }

        java.security.PublicKey publickey = mcServer.func_55087_t().getPublic();

        if (par1Packet2ClientProtocol.func_55112_b() != 37)
        {
            if (par1Packet2ClientProtocol.func_55112_b() > 37)
            {
                kickUser("Outdated server!");
            }
            else
            {
                kickUser("Outdated client!");
            }

            return;
        }
        else
        {
            field_55259_i = mcServer.func_56211_Q() ? Long.toString(rand.nextLong(), 16) : "-";
            field_58074_d = new byte[4];
            rand.nextBytes(field_58074_d);
            field_56681_b.addToSendQueue(new Packet253ServerAuthData(field_55259_i, publickey, field_58074_d));
            return;
        }
    }

    public void func_55249_a(Packet252SharedKey par1Packet252SharedKey)
    {
        java.security.PrivateKey privatekey = mcServer.func_55087_t().getPrivate();
        field_55257_j = par1Packet252SharedKey.func_55107_a(privatekey);

        if (!Arrays.equals(field_58074_d, par1Packet252SharedKey.func_58028_b(privatekey)))
        {
            kickUser("Invalid client reply");
        }

        field_56681_b.addToSendQueue(new Packet252SharedKey());
    }

    public void func_56678_a(Packet205ClientCommand par1Packet205ClientCommand)
    {
        if (par1Packet205ClientCommand.field_56242_a == 0)
        {
            if (mcServer.func_56211_Q())
            {
                (new ThreadLoginVerifier(this)).start();
            }
            else
            {
                field_55258_h = true;
            }
        }
    }

    public void handleLogin(Packet1Login packet1login)
    {
    }

    public void func_55254_c()
    {
        String s = mcServer.func_56173_Y().func_56433_a(field_56681_b.getRemoteAddress(), field_55256_g);

        if (s != null)
        {
            kickUser(s);
        }
        else
        {
            EntityPlayerMP entityplayermp = mcServer.func_56173_Y().func_56431_a(field_55256_g);

            if (entityplayermp != null)
            {
                mcServer.func_56173_Y().func_56432_a(field_56681_b, entityplayermp);
            }
        }

        finishedProcessing = true;
    }

    public void handleErrorMessage(String par1Str, Object par2ArrayOfObj[])
    {
        logger.info((new StringBuilder()).append(func_55250_d()).append(" lost connection").toString());
        finishedProcessing = true;
    }

    /**
     * Handle a server ping packet.
     */
    public void handleServerPing(Packet254ServerPing par1Packet254ServerPing)
    {
        try
        {
            String s = (new StringBuilder()).append(mcServer.func_56155_V()).append("\247").append(mcServer.func_56173_Y().playersOnline()).append("\247").append(mcServer.func_56173_Y().getMaxPlayers()).toString();
            java.net.InetAddress inetaddress = null;

            if (field_56681_b.func_56640_f() != null)
            {
                inetaddress = field_56681_b.func_56640_f().getInetAddress();
            }

            field_56681_b.addToSendQueue(new Packet255KickDisconnect(s));
            field_56681_b.serverShutdown();

            if (inetaddress != null && (mcServer.func_56203_Z() instanceof DedicatedServerListenThread))
            {
                ((DedicatedServerListenThread)mcServer.func_56203_Z()).func_58050_a(inetaddress);
            }

            finishedProcessing = true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public void registerPacket(Packet par1Packet)
    {
        kickUser("Protocol error");
    }

    public String func_55250_d()
    {
        if (field_55256_g != null)
        {
            return (new StringBuilder()).append(field_55256_g).append(" [").append(field_56681_b.getRemoteAddress().toString()).append("]").toString();
        }
        else
        {
            return field_56681_b.getRemoteAddress().toString();
        }
    }

    /**
     * determine if it is a server handler
     */
    public boolean isServerHandler()
    {
        return true;
    }

    /**
     * Returns the server Id randomly generated by this login handler.
     */
    static String getServerId(NetLoginHandler par0NetLoginHandler)
    {
        return par0NetLoginHandler.field_55259_i;
    }

    static MinecraftServer func_55253_b(NetLoginHandler par0NetLoginHandler)
    {
        return par0NetLoginHandler.mcServer;
    }

    static SecretKey func_55255_c(NetLoginHandler par0NetLoginHandler)
    {
        return par0NetLoginHandler.field_55257_j;
    }

    static String func_55251_d(NetLoginHandler par0NetLoginHandler)
    {
        return par0NetLoginHandler.field_55256_g;
    }

    static boolean func_55252_a(NetLoginHandler par0NetLoginHandler, boolean par1)
    {
        return par0NetLoginHandler.field_55258_h = par1;
    }
}
