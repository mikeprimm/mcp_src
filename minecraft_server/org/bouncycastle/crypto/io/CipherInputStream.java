package org.bouncycastle.crypto.io;

import java.io.*;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.StreamCipher;

public class CipherInputStream extends FilterInputStream
{
    private BufferedBlockCipher field_56774_a;
    private StreamCipher field_56772_b;
    private byte field_56773_c[];
    private byte field_56770_d[];
    private int field_56771_e;
    private int field_56768_f;
    private boolean field_56769_g;

    public CipherInputStream(InputStream par1InputStream, BufferedBlockCipher par2BufferedBlockCipher)
    {
        super(par1InputStream);
        field_56774_a = par2BufferedBlockCipher;
        field_56773_c = new byte[par2BufferedBlockCipher.func_56754_b(2048)];
        field_56770_d = new byte[2048];
    }

    private int func_56767_a() throws IOException
    {
        int i = super.available();

        if (i <= 0)
        {
            i = 1;
        }

        if (i > field_56770_d.length)
        {
            i = super.read(field_56770_d, 0, field_56770_d.length);
        }
        else
        {
            i = super.read(field_56770_d, 0, i);
        }

        if (i < 0)
        {
            if (field_56769_g)
            {
                return -1;
            }

            try
            {
                if (field_56774_a != null)
                {
                    field_56768_f = field_56774_a.func_56755_a(field_56773_c, 0);
                }
                else
                {
                    field_56768_f = 0;
                }
            }
            catch (Exception exception)
            {
                throw new IOException((new StringBuilder()).append("error processing stream: ").append(exception.toString()).toString());
            }

            field_56771_e = 0;
            field_56769_g = true;

            if (field_56771_e == field_56768_f)
            {
                return -1;
            }
        }
        else
        {
            field_56771_e = 0;

            try
            {
                if (field_56774_a != null)
                {
                    field_56768_f = field_56774_a.func_56757_a(field_56770_d, 0, i, field_56773_c, 0);
                }
                else
                {
                    field_56772_b.func_56785_a(field_56770_d, 0, i, field_56773_c, 0);
                    field_56768_f = i;
                }
            }
            catch (Exception exception1)
            {
                throw new IOException((new StringBuilder()).append("error processing stream: ").append(exception1.toString()).toString());
            }

            if (field_56768_f == 0)
            {
                return func_56767_a();
            }
        }

        return field_56768_f;
    }

    public int read() throws IOException
    {
        if (field_56771_e == field_56768_f && func_56767_a() < 0)
        {
            return -1;
        }
        else
        {
            return field_56773_c[field_56771_e++] & 0xff;
        }
    }

    public int read(byte par1ArrayOfByte[]) throws IOException
    {
        return read(par1ArrayOfByte, 0, par1ArrayOfByte.length);
    }

    public int read(byte par1ArrayOfByte[], int par2, int par3) throws IOException
    {
        if (field_56771_e == field_56768_f && func_56767_a() < 0)
        {
            return -1;
        }

        int i = field_56768_f - field_56771_e;

        if (par3 > i)
        {
            System.arraycopy(field_56773_c, field_56771_e, par1ArrayOfByte, par2, i);
            field_56771_e = field_56768_f;
            return i;
        }
        else
        {
            System.arraycopy(field_56773_c, field_56771_e, par1ArrayOfByte, par2, par3);
            field_56771_e += par3;
            return par3;
        }
    }

    public long skip(long par1) throws IOException
    {
        if (par1 <= 0L)
        {
            return 0L;
        }

        int i = field_56768_f - field_56771_e;

        if (par1 > (long)i)
        {
            field_56771_e = field_56768_f;
            return (long)i;
        }
        else
        {
            field_56771_e += (int)par1;
            return (long)(int)par1;
        }
    }

    public int available() throws IOException
    {
        return field_56768_f - field_56771_e;
    }

    public void close() throws IOException
    {
        super.close();
    }

    public boolean markSupported()
    {
        return false;
    }
}
