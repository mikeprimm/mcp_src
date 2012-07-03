package net.minecraft.src;

import java.io.*;

public class Packet55BlockDestroy extends Packet
{
    private int field_56265_a;
    private int field_56263_b;
    private int field_56264_c;
    private int field_56261_d;
    private int field_56262_e;

    public Packet55BlockDestroy()
    {
    }

    public Packet55BlockDestroy(int par1, int par2, int par3, int par4, int par5)
    {
        field_56265_a = par1;
        field_56263_b = par2;
        field_56264_c = par3;
        field_56261_d = par4;
        field_56262_e = par5;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_56265_a = par1DataInputStream.readInt();
        field_56263_b = par1DataInputStream.readInt();
        field_56264_c = par1DataInputStream.readInt();
        field_56261_d = par1DataInputStream.readInt();
        field_56262_e = par1DataInputStream.read();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(field_56265_a);
        par1DataOutputStream.writeInt(field_56263_b);
        par1DataOutputStream.writeInt(field_56264_c);
        par1DataOutputStream.writeInt(field_56261_d);
        par1DataOutputStream.write(field_56262_e);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_56680_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 13;
    }
}
