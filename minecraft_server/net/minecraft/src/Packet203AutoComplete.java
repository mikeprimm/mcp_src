package net.minecraft.src;

import java.io.*;

public class Packet203AutoComplete extends Packet
{
    private String field_55111_a;

    public Packet203AutoComplete()
    {
    }

    public Packet203AutoComplete(String par1Str)
    {
        field_55111_a = par1Str;
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_55111_a = readString(par1DataInputStream, Packet3Chat.field_52004_b);
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(field_55111_a, par1DataOutputStream);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_55248_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 2 + field_55111_a.length() * 2;
    }

    public String func_55110_b()
    {
        return field_55111_a;
    }
}
