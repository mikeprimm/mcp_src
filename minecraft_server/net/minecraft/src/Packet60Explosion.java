package net.minecraft.src;

import java.io.*;
import java.util.*;

public class Packet60Explosion extends Packet
{
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public float explosionSize;
    public List field_58030_e;
    private float field_56266_f;
    private float field_56267_g;
    private float field_56268_h;

    public Packet60Explosion()
    {
    }

    public Packet60Explosion(double par1, double par3, double par5, float par7, List par8List, Vec3 par9Vec3)
    {
        explosionX = par1;
        explosionY = par3;
        explosionZ = par5;
        explosionSize = par7;
        field_58030_e = new ArrayList(par8List);

        if (par9Vec3 != null)
        {
            field_56266_f = (float)par9Vec3.xCoord;
            field_56267_g = (float)par9Vec3.yCoord;
            field_56268_h = (float)par9Vec3.zCoord;
        }
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        explosionX = par1DataInputStream.readDouble();
        explosionY = par1DataInputStream.readDouble();
        explosionZ = par1DataInputStream.readDouble();
        explosionSize = par1DataInputStream.readFloat();
        int i = par1DataInputStream.readInt();
        field_58030_e = new ArrayList(i);
        int j = (int)explosionX;
        int k = (int)explosionY;
        int l = (int)explosionZ;

        for (int i1 = 0; i1 < i; i1++)
        {
            int j1 = par1DataInputStream.readByte() + j;
            int k1 = par1DataInputStream.readByte() + k;
            int l1 = par1DataInputStream.readByte() + l;
            field_58030_e.add(new ChunkPosition(j1, k1, l1));
        }

        field_56266_f = par1DataInputStream.readFloat();
        field_56267_g = par1DataInputStream.readFloat();
        field_56268_h = par1DataInputStream.readFloat();
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeDouble(explosionX);
        par1DataOutputStream.writeDouble(explosionY);
        par1DataOutputStream.writeDouble(explosionZ);
        par1DataOutputStream.writeFloat(explosionSize);
        par1DataOutputStream.writeInt(field_58030_e.size());
        int i = (int)explosionX;
        int j = (int)explosionY;
        int k = (int)explosionZ;
        int j1;

        for (Iterator iterator = field_58030_e.iterator(); iterator.hasNext(); par1DataOutputStream.writeByte(j1))
        {
            ChunkPosition chunkposition = (ChunkPosition)iterator.next();
            int l = chunkposition.x - i;
            int i1 = chunkposition.y - j;
            j1 = chunkposition.z - k;
            par1DataOutputStream.writeByte(l);
            par1DataOutputStream.writeByte(i1);
        }

        par1DataOutputStream.writeFloat(field_56266_f);
        par1DataOutputStream.writeFloat(field_56267_g);
        par1DataOutputStream.writeFloat(field_56268_h);
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleExplosion(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 32 + field_58030_e.size() * 3 + 3;
    }
}
