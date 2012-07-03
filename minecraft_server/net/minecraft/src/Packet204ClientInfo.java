package net.minecraft.src;

import java.io.*;

public class Packet204ClientInfo extends Packet
{
    private String field_55123_a;
    private int field_55121_b;
    private int field_55122_c;
    private boolean field_55120_d;
    private int field_56260_e;

    public Packet204ClientInfo()
    {
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_55123_a = readString(par1DataInputStream, 7);
        field_55121_b = par1DataInputStream.readByte();
        byte byte0 = par1DataInputStream.readByte();
        field_55122_c = byte0 & 7;
        field_55120_d = (byte0 & 8) == 8;
        field_56260_e = par1DataInputStream.readByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(field_55123_a, par1DataOutputStream);
        par1DataOutputStream.writeByte(field_55121_b);
        par1DataOutputStream.writeByte(field_55122_c | (field_55120_d ? 1 : 0) << 3);
        par1DataOutputStream.writeByte(field_56260_e);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_55245_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 0;
    }

    public String func_55118_b()
    {
        return field_55123_a;
    }

    public int func_55119_c()
    {
        return field_55121_b;
    }

    public int func_55117_d()
    {
        return field_55122_c;
    }

    public boolean func_55116_e()
    {
        return field_55120_d;
    }

    public int func_56259_f()
    {
        return field_56260_e;
    }
}
