package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

public class ParametersWithIV implements CipherParameters
{
    private byte field_56421_a[];
    private CipherParameters field_56420_b;

    public ParametersWithIV(CipherParameters par1CipherParameters, byte par2ArrayOfByte[], int par3, int par4)
    {
        field_56421_a = new byte[par4];
        field_56420_b = par1CipherParameters;
        System.arraycopy(par2ArrayOfByte, par3, field_56421_a, 0, par4);
    }

    public byte[] func_56419_a()
    {
        return field_56421_a;
    }

    public CipherParameters func_56418_b()
    {
        return field_56420_b;
    }
}
