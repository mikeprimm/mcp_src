package net.minecraft.src;

import java.io.*;

public class Packet2ClientProtocol extends Packet
{
    private int field_55115_a;
    private String field_55114_b;
    private String field_56255_c;
    private int field_56254_d;

    public Packet2ClientProtocol()
    {
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_55115_a = par1DataInputStream.readByte();
        field_55114_b = readString(par1DataInputStream, 16);
        field_56255_c = readString(par1DataInputStream, 255);
        field_56254_d = par1DataInputStream.readInt();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeByte(field_55115_a);
        writeString(field_55114_b, par1DataOutputStream);
        writeString(field_56255_c, par1DataOutputStream);
        par1DataOutputStream.writeInt(field_56254_d);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_55247_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 3 + 2 * field_55114_b.length();
    }

    public int func_55112_b()
    {
        return field_55115_a;
    }

    public String func_55113_c()
    {
        return field_55114_b;
    }
}
