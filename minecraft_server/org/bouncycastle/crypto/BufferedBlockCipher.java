package org.bouncycastle.crypto;

public class BufferedBlockCipher
{
    protected byte field_56766_a[];
    protected int field_56764_b;
    protected boolean field_56765_c;
    protected BlockCipher field_56762_d;
    protected boolean field_56763_e;
    protected boolean field_56761_f;

    protected BufferedBlockCipher()
    {
    }

    public BufferedBlockCipher(BlockCipher par1BlockCipher)
    {
        field_56762_d = par1BlockCipher;
        field_56766_a = new byte[par1BlockCipher.func_56561_b()];
        field_56764_b = 0;
        String s = par1BlockCipher.func_56559_a();
        int i = s.indexOf('/') + 1;
        field_56761_f = i > 0 && s.startsWith("PGP", i);

        if (field_56761_f)
        {
            field_56763_e = true;
        }
        else
        {
            field_56763_e = i > 0 && (s.startsWith("CFB", i) || s.startsWith("OFB", i) || s.startsWith("OpenPGP", i) || s.startsWith("SIC", i) || s.startsWith("GCTR", i));
        }
    }

    public void func_56756_a(boolean par1, CipherParameters par2CipherParameters) throws IllegalArgumentException
    {
        field_56765_c = par1;
        func_56760_b();
        field_56762_d.func_56558_a(par1, par2CipherParameters);
    }

    public int func_56758_a()
    {
        return field_56762_d.func_56561_b();
    }

    public int func_56759_a(int par1)
    {
        int i = par1 + field_56764_b;
        int j;

        if (field_56761_f)
        {
            j = i % field_56766_a.length - (field_56762_d.func_56561_b() + 2);
        }
        else
        {
            j = i % field_56766_a.length;
        }

        return i - j;
    }

    public int func_56754_b(int par1)
    {
        return par1 + field_56764_b;
    }

    public int func_56757_a(byte par1ArrayOfByte[], int par2, int par3, byte par4ArrayOfByte[], int par5) throws DataLengthException, IllegalStateException
    {
        if (par3 < 0)
        {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }

        int i = func_56758_a();
        int j = func_56759_a(par3);

        if (j > 0 && par5 + j > par4ArrayOfByte.length)
        {
            throw new DataLengthException("output buffer too short");
        }

        int k = 0;
        int l = field_56766_a.length - field_56764_b;

        if (par3 > l)
        {
            System.arraycopy(par1ArrayOfByte, par2, field_56766_a, field_56764_b, l);
            k += field_56762_d.func_56562_a(field_56766_a, 0, par4ArrayOfByte, par5);
            field_56764_b = 0;
            par3 -= l;

            for (par2 += l; par3 > field_56766_a.length; par2 += i)
            {
                k += field_56762_d.func_56562_a(par1ArrayOfByte, par2, par4ArrayOfByte, par5 + k);
                par3 -= i;
            }
        }

        System.arraycopy(par1ArrayOfByte, par2, field_56766_a, field_56764_b, par3);
        field_56764_b += par3;

        if (field_56764_b == field_56766_a.length)
        {
            k += field_56762_d.func_56562_a(field_56766_a, 0, par4ArrayOfByte, par5 + k);
            field_56764_b = 0;
        }

        return k;
    }

    public int func_56755_a(byte par1ArrayOfByte[], int par2) throws DataLengthException, IllegalStateException, InvalidCipherTextException
    {
        try
        {
            int i = 0;

            if (par2 + field_56764_b > par1ArrayOfByte.length)
            {
                throw new DataLengthException("output buffer too short for doFinal()");
            }

            if (field_56764_b != 0)
            {
                if (!field_56763_e)
                {
                    throw new DataLengthException("data not block size aligned");
                }

                field_56762_d.func_56562_a(field_56766_a, 0, field_56766_a, 0);
                i = field_56764_b;
                field_56764_b = 0;
                System.arraycopy(field_56766_a, 0, par1ArrayOfByte, par2, i);
            }

            int j = i;
            return j;
        }
        finally
        {
            func_56760_b();
        }
    }

    public void func_56760_b()
    {
        for (int i = 0; i < field_56766_a.length; i++)
        {
            field_56766_a[i] = 0;
        }

        field_56764_b = 0;
        field_56762_d.func_56560_c();
    }
}
