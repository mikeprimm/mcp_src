package org.bouncycastle.crypto;

public class CryptoException extends Exception
{
    private Throwable field_56707_a;

    public CryptoException()
    {
    }

    public CryptoException(String par1Str)
    {
        super(par1Str);
    }

    public CryptoException(String par1Str, Throwable par2Throwable)
    {
        super(par1Str);
        field_56707_a = par2Throwable;
    }

    public Throwable getCause()
    {
        return field_56707_a;
    }
}
