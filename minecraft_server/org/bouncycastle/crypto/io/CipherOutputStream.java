package org.bouncycastle.crypto.io;

import java.io.*;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.StreamCipher;

public class CipherOutputStream extends FilterOutputStream
{
    private BufferedBlockCipher field_56547_a;
    private StreamCipher field_56545_b;
    private byte field_56546_c[];
    private byte field_56544_d[];

    public CipherOutputStream(OutputStream par1OutputStream, BufferedBlockCipher par2BufferedBlockCipher)
    {
        super(par1OutputStream);
        field_56546_c = new byte[1];
        field_56547_a = par2BufferedBlockCipher;
        field_56544_d = new byte[par2BufferedBlockCipher.func_56758_a()];
    }

    public void write(int par1ArrayOfByte) throws IOException
    {
        field_56546_c[0] = (byte)par1ArrayOfByte;

        if (field_56547_a != null)
        {
            int i = field_56547_a.func_56757_a(field_56546_c, 0, 1, field_56544_d, 0);

            if (i != 0)
            {
                out.write(field_56544_d, 0, i);
            }
        }
        else
        {
            out.write(field_56545_b.func_56786_a((byte)par1ArrayOfByte));
        }
    }

    public void write(byte par1ArrayOfByte[]) throws IOException
    {
        write(par1ArrayOfByte, 0, par1ArrayOfByte.length);
    }

    public void write(byte par1ArrayOfByte[], int par2, int par3) throws IOException
    {
        if (field_56547_a != null)
        {
            byte abyte0[] = new byte[field_56547_a.func_56754_b(par3)];
            int i = field_56547_a.func_56757_a(par1ArrayOfByte, par2, par3, abyte0, 0);

            if (i != 0)
            {
                out.write(abyte0, 0, i);
            }
        }
        else
        {
            byte abyte1[] = new byte[par3];
            field_56545_b.func_56785_a(par1ArrayOfByte, par2, par3, abyte1, 0);
            out.write(abyte1, 0, par3);
        }
    }

    public void flush() throws IOException
    {
        super.flush();
    }

    public void close() throws IOException
    {
        try
        {
            if (field_56547_a != null)
            {
                byte abyte0[] = new byte[field_56547_a.func_56754_b(0)];
                int i = field_56547_a.func_56755_a(abyte0, 0);

                if (i != 0)
                {
                    out.write(abyte0, 0, i);
                }
            }
        }
        catch (Exception exception)
        {
            throw new IOException((new StringBuilder()).append("Error closing stream: ").append(exception.toString()).toString());
        }

        flush();
        super.close();
    }
}
