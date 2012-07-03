package net.minecraft.src;

import java.io.*;

public class Packet15Place extends Packet
{
    private int xPosition;
    private int yPosition;
    private int zPosition;

    /** The offset to use for block/item placement. */
    private int direction;
    private ItemStack itemStack;
    private float field_56251_f;
    private float field_56252_g;
    private float field_56253_h;

    public Packet15Place()
    {
    }

    /**
     * Abstract. Reads the raw packet data from the data stream.
     */
    public void readPacketData(DataInputStream par1DataInputStream) throws IOException
    {
        xPosition = par1DataInputStream.readInt();
        yPosition = par1DataInputStream.read();
        zPosition = par1DataInputStream.readInt();
        direction = par1DataInputStream.read();
        itemStack = readItemStack(par1DataInputStream);
        field_56251_f = (float)par1DataInputStream.read() / 16F;
        field_56252_g = (float)par1DataInputStream.read() / 16F;
        field_56253_h = (float)par1DataInputStream.read() / 16F;
    }

    /**
     * Abstract. Writes the raw packet data to the data stream.
     */
    public void writePacketData(DataOutputStream par1DataOutputStream) throws IOException
    {
        par1DataOutputStream.writeInt(xPosition);
        par1DataOutputStream.write(yPosition);
        par1DataOutputStream.writeInt(zPosition);
        par1DataOutputStream.write(direction);
        writeItemStack(itemStack, par1DataOutputStream);
        par1DataOutputStream.write((int)(field_56251_f * 16F));
        par1DataOutputStream.write((int)(field_56252_g * 16F));
        par1DataOutputStream.write((int)(field_56253_h * 16F));
    }

    /**
     * Passes this Packet on to the NetHandler for processing.
     */
    public void processPacket(NetHandler par1NetHandler)
    {
        par1NetHandler.handlePlace(this);
    }

    /**
     * Abstract. Return the size of the packet (not counting the header).
     */
    public int getPacketSize()
    {
        return 19;
    }

    public int func_56250_b()
    {
        return xPosition;
    }

    public int func_56248_c()
    {
        return yPosition;
    }

    public int func_56246_d()
    {
        return zPosition;
    }

    public int func_56245_e()
    {
        return direction;
    }

    public ItemStack func_56249_f()
    {
        return itemStack;
    }

    public float func_56243_g()
    {
        return field_56251_f;
    }

    public float func_56247_h()
    {
        return field_56252_g;
    }

    public float func_56244_i()
    {
        return field_56253_h;
    }
}
