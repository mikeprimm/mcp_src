package net.minecraft.src;

import java.io.*;
import java.security.PrivateKey;
import javax.crypto.SecretKey;

public class Packet252SharedKey extends Packet
{
    private byte field_55109_a[];
    private byte field_58029_b[];
    private SecretKey field_55108_b;

    public Packet252SharedKey()
    {
        field_55109_a = new byte[0];
        field_58029_b = new byte[0];
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_55109_a = func_55103_b(par1DataInputStream);
        field_58029_b = func_55103_b(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        func_55102_a(par1DataOutputStream, field_55109_a);
        func_55102_a(par1DataOutputStream, field_58029_b);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_55249_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 2 + field_55109_a.length + 2 + field_58029_b.length;
    }

    public SecretKey func_55107_a(PrivateKey par1PrivateKey)
    {
        if (par1PrivateKey == null)
        {
            return field_55108_b;
        }
        else
        {
            return field_55108_b = CryptManager.func_55264_a(par1PrivateKey, field_55109_a);
        }
    }

    public SecretKey func_55106_b()
    {
        return func_55107_a(null);
    }

    public byte[] func_58028_b(PrivateKey par1PrivateKey)
    {
        if (par1PrivateKey == null)
        {
            return field_58029_b;
        }
        else
        {
            return CryptManager.func_55263_a(par1PrivateKey, field_58029_b);
        }
    }
}
