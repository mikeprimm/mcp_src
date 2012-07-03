package net.minecraft.src;

import java.io.*;

public class Packet62LevelSound extends Packet
{
    private String field_56288_a;
    private int field_56286_b;
    private int field_56287_c;
    private int field_56284_d;
    private float field_58031_e;
    private int field_56283_f;

    public Packet62LevelSound()
    {
        field_56287_c = 0x7fffffff;
    }

    public Packet62LevelSound(String par1Str, double par2, double par4, double par6, float par8, float par9)
    {
        field_56287_c = 0x7fffffff;
        field_56288_a = par1Str;
        field_56286_b = (int)(par2 * 8D);
        field_56287_c = (int)(par4 * 8D);
        field_56284_d = (int)(par6 * 8D);
        field_58031_e = par8;
        field_56283_f = (int)(par9 * 63F);

        if (field_56283_f < 0)
        {
            field_56283_f = 0;
        }

        if (field_56283_f > 255)
        {
            field_56283_f = 255;
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        field_56288_a = readString(par1DataInputStream, 32);
        field_56286_b = par1DataInputStream.readInt();
        field_56287_c = par1DataInputStream.readInt();
        field_56284_d = par1DataInputStream.readInt();
        field_58031_e = par1DataInputStream.readFloat();
        field_56283_f = par1DataInputStream.readUnsignedByte();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        writeString(field_56288_a, par1DataOutputStream);
        par1DataOutputStream.writeInt(field_56286_b);
        par1DataOutputStream.writeInt(field_56287_c);
        par1DataOutputStream.writeInt(field_56284_d);
        par1DataOutputStream.writeFloat(field_58031_e);
        par1DataOutputStream.writeByte(field_56283_f);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.func_56679_a(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 24;
    }
}
