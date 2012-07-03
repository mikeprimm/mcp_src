package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.*;
import org.bouncycastle.crypto.params.ParametersWithIV;

public class CFBBlockCipher implements BlockCipher
{
    private byte field_56570_a[];
    private byte field_56568_b[];
    private byte field_56569_c[];
    private int field_56566_d;
    private BlockCipher field_56567_e;
    private boolean field_56565_f;

    public CFBBlockCipher(BlockCipher par1BlockCipher, int par2)
    {
        field_56567_e = null;
        field_56567_e = par1BlockCipher;
        field_56566_d = par2 / 8;
        field_56570_a = new byte[par1BlockCipher.func_56561_b()];
        field_56568_b = new byte[par1BlockCipher.func_56561_b()];
        field_56569_c = new byte[par1BlockCipher.func_56561_b()];
    }

    public void func_56558_a(boolean par1, CipherParameters par2CipherParameters) throws IllegalArgumentException
    {
        field_56565_f = par1;

        if (par2CipherParameters instanceof ParametersWithIV)
        {
            ParametersWithIV parameterswithiv = (ParametersWithIV)par2CipherParameters;
            byte abyte0[] = parameterswithiv.func_56419_a();

            if (abyte0.length < field_56570_a.length)
            {
                System.arraycopy(abyte0, 0, field_56570_a, field_56570_a.length - abyte0.length, abyte0.length);

                for (int i = 0; i < field_56570_a.length - abyte0.length; i++)
                {
                    field_56570_a[i] = 0;
                }
            }
            else
            {
                System.arraycopy(abyte0, 0, field_56570_a, 0, field_56570_a.length);
            }

            func_56560_c();

            if (parameterswithiv.func_56418_b() != null)
            {
                field_56567_e.func_56558_a(true, parameterswithiv.func_56418_b());
            }
        }
        else
        {
            func_56560_c();
            field_56567_e.func_56558_a(true, par2CipherParameters);
        }
    }

    public String func_56559_a()
    {
        return (new StringBuilder()).append(field_56567_e.func_56559_a()).append("/CFB").append(field_56566_d * 8).toString();
    }

    public int func_56561_b()
    {
        return field_56566_d;
    }

    public int func_56562_a(byte par1ArrayOfByte[], int par2, byte par3ArrayOfByte[], int par4) throws DataLengthException, IllegalStateException
    {
        return field_56565_f ? func_56563_b(par1ArrayOfByte, par2, par3ArrayOfByte, par4) : func_56564_c(par1ArrayOfByte, par2, par3ArrayOfByte, par4);
    }

    public int func_56563_b(byte par1ArrayOfByte[], int par2, byte par3ArrayOfByte[], int par4) throws DataLengthException, IllegalStateException
    {
        if (par2 + field_56566_d > par1ArrayOfByte.length)
        {
            throw new DataLengthException("input buffer too short");
        }

        if (par4 + field_56566_d > par3ArrayOfByte.length)
        {
            throw new DataLengthException("output buffer too short");
        }

        field_56567_e.func_56562_a(field_56568_b, 0, field_56569_c, 0);

        for (int i = 0; i < field_56566_d; i++)
        {
            par3ArrayOfByte[par4 + i] = (byte)(field_56569_c[i] ^ par1ArrayOfByte[par2 + i]);
        }

        System.arraycopy(field_56568_b, field_56566_d, field_56568_b, 0, field_56568_b.length - field_56566_d);
        System.arraycopy(par3ArrayOfByte, par4, field_56568_b, field_56568_b.length - field_56566_d, field_56566_d);
        return field_56566_d;
    }

    public int func_56564_c(byte par1ArrayOfByte[], int par2, byte par3ArrayOfByte[], int par4) throws DataLengthException, IllegalStateException
    {
        if (par2 + field_56566_d > par1ArrayOfByte.length)
        {
            throw new DataLengthException("input buffer too short");
        }

        if (par4 + field_56566_d > par3ArrayOfByte.length)
        {
            throw new DataLengthException("output buffer too short");
        }

        field_56567_e.func_56562_a(field_56568_b, 0, field_56569_c, 0);
        System.arraycopy(field_56568_b, field_56566_d, field_56568_b, 0, field_56568_b.length - field_56566_d);
        System.arraycopy(par1ArrayOfByte, par2, field_56568_b, field_56568_b.length - field_56566_d, field_56566_d);

        for (int i = 0; i < field_56566_d; i++)
        {
            par3ArrayOfByte[par4 + i] = (byte)(field_56569_c[i] ^ par1ArrayOfByte[par2 + i]);
        }

        return field_56566_d;
    }

    public void func_56560_c()
    {
        System.arraycopy(field_56570_a, 0, field_56568_b, 0, field_56570_a.length);
        field_56567_e.func_56560_c();
    }
}
