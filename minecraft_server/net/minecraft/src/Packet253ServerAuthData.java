package net.minecraft.src;

import java.io.*;
import java.security.PublicKey;

public class Packet253ServerAuthData extends Packet
{
    private String field_55105_a;
    private PublicKey field_55104_b;
    private byte field_58027_c[];

    public Packet253ServerAuthData()
    {
        field_58027_c = new byte[0];
    }

    public Packet253ServerAuthData(String par1Str, PublicKey par2PublicKey, byte par3ArrayOfByte[])
    {
        field_58027_c = new byte[0];
        field_55105_a = par1Str;
        field_55104_b = par2PublicKey;
        field_58027_c = par3ArrayOfByte;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_55105_a = readString(par1DataInputStream, 20);
        field_55104_b = CryptManager.func_55268_a(func_55103_b(par1DataInputStream));
        field_58027_c = func_55103_b(par1DataInputStream);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(field_55105_a, par1DataOutputStream);
        func_55102_a(par1DataOutputStream, field_55104_b.getEncoded());
        func_55102_a(par1DataOutputStream, field_58027_c);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_55246_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 2 + field_55105_a.length() * 2 + 2 + field_55104_b.getEncoded().length + 2 + field_58027_c.length;
    }
}
