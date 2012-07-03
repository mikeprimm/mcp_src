package net.minecraft.src;

import java.io.*;

public class Packet9Respawn extends Packet
{
    public int respawnDimension;

    /** The difficulty setting of the server. */
    public int difficulty;

    /** Defaults to 128 */
    public int worldHeight;
    public EnumGameType field_56258_d;
    public WorldType terrainType;

    public Packet9Respawn()
    {
    }

    public Packet9Respawn(int par1, byte par2, WorldType par3WorldType, int par4, EnumGameType par5EnumGameType)
    {
        respawnDimension = par1;
        difficulty = par2;
        worldHeight = par4;
        field_56258_d = par5EnumGameType;
        terrainType = par3WorldType;
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handleRespawn(this);
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        respawnDimension = par1DataInputStream.readInt();
        difficulty = par1DataInputStream.readByte();
        field_56258_d = EnumGameType.func_56604_a(par1DataInputStream.readByte());
        worldHeight = par1DataInputStream.readShort();
        String s = readString(par1DataInputStream, 16);
        terrainType = WorldType.parseWorldType(s);

        if (terrainType == null)
        {
            terrainType = WorldType.DEFAULT;
        }
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(respawnDimension);
        par1DataOutputStream.writeByte(difficulty);
        par1DataOutputStream.writeByte(field_56258_d.func_56607_a());
        par1DataOutputStream.writeShort(worldHeight);
        writeString(terrainType.func_48449_a(), par1DataOutputStream);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 8 + (terrainType != null ? terrainType.func_48449_a().length() : 0);
    }
}
