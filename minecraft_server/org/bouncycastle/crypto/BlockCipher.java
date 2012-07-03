package org.bouncycastle.crypto;

public interface BlockCipher
{
    public abstract void func_56558_a(boolean flag, CipherParameters cipherparameters) throws IllegalArgumentException;

    public abstract String func_56559_a();

    public abstract int func_56561_b();

    public abstract int func_56562_a(byte abyte0[], int i, byte abyte1[], int j) throws DataLengthException, IllegalStateException;

    public abstract void func_56560_c();
}
