package net.minecraft.src;

import java.io.*;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.AESFastEngine;
import org.bouncycastle.crypto.io.CipherInputStream;
import org.bouncycastle.crypto.io.CipherOutputStream;
import org.bouncycastle.crypto.modes.CFBBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CryptManager
{
    public static final Charset field_55270_a = Charset.forName("ISO_8859_1");

    public CryptManager()
    {
    }

    public static KeyPair func_55266_a()
    {
        try
        {
            KeyPairGenerator keypairgenerator = KeyPairGenerator.getInstance("RSA");
            keypairgenerator.initialize(1024);
            return keypairgenerator.generateKeyPair();
        }
        catch (NoSuchAlgorithmException nosuchalgorithmexception)
        {
            nosuchalgorithmexception.printStackTrace();
        }

        System.err.println("Key pair generation failed!");
        return null;
    }

    public static byte[] func_55269_a(String par0Str, PublicKey par1PublicKey, SecretKey par2SecretKey)
    {
        try
        {
            return func_55260_a("SHA-1", new byte[][]
                    {
                        par0Str.getBytes("ISO_8859_1"), par2SecretKey.getEncoded(), par1PublicKey.getEncoded()
                    });
        }
        catch (UnsupportedEncodingException unsupportedencodingexception)
        {
            unsupportedencodingexception.printStackTrace();
        }

        return null;
    }

    private static byte[] func_55260_a(String par0Str, byte par1ArrayOfByte[][])
    {
        try
        {
            MessageDigest messagedigest = MessageDigest.getInstance(par0Str);
            byte abyte0[][] = par1ArrayOfByte;
            int i = abyte0.length;

            for (int j = 0; j < i; j++)
            {
                byte abyte1[] = abyte0[j];
                messagedigest.update(abyte1);
            }

            return messagedigest.digest();
        }
        catch (NoSuchAlgorithmException nosuchalgorithmexception)
        {
            nosuchalgorithmexception.printStackTrace();
        }

        return null;
    }

    public static PublicKey func_55268_a(byte par0ArrayOfByte[])
    {
        try
        {
            X509EncodedKeySpec x509encodedkeyspec = new X509EncodedKeySpec(par0ArrayOfByte);
            KeyFactory keyfactory = KeyFactory.getInstance("RSA");
            return keyfactory.generatePublic(x509encodedkeyspec);
        }
        catch (NoSuchAlgorithmException nosuchalgorithmexception)
        {
            nosuchalgorithmexception.printStackTrace();
        }
        catch (InvalidKeySpecException invalidkeyspecexception)
        {
            invalidkeyspecexception.printStackTrace();
        }

        System.err.println("Public key reconstitute failed!");
        return null;
    }

    public static SecretKey func_55264_a(PrivateKey par0PrivateKey, byte par1ArrayOfByte[])
    {
        return new SecretKeySpec(func_55263_a(par0PrivateKey, par1ArrayOfByte), "AES");
    }

    public static byte[] func_55263_a(Key par0Key, byte par1ArrayOfByte[])
    {
        return func_55261_a(2, par0Key, par1ArrayOfByte);
    }

    private static byte[] func_55261_a(int par0, Key par1Key, byte par2ArrayOfByte[])
    {
        try
        {
            return func_55262_a(par0, par1Key.getAlgorithm(), par1Key).doFinal(par2ArrayOfByte);
        }
        catch (IllegalBlockSizeException illegalblocksizeexception)
        {
            illegalblocksizeexception.printStackTrace();
        }
        catch (BadPaddingException badpaddingexception)
        {
            badpaddingexception.printStackTrace();
        }

        System.err.println("Cipher data failed!");
        return null;
    }

    private static Cipher func_55262_a(int par0, String par1Str, Key par2Key)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(par1Str);
            cipher.init(par0, par2Key);
            return cipher;
        }
        catch (InvalidKeyException invalidkeyexception)
        {
            invalidkeyexception.printStackTrace();
        }
        catch (NoSuchAlgorithmException nosuchalgorithmexception)
        {
            nosuchalgorithmexception.printStackTrace();
        }
        catch (NoSuchPaddingException nosuchpaddingexception)
        {
            nosuchpaddingexception.printStackTrace();
        }

        System.err.println("Cipher creation failed!");
        return null;
    }

    private static BufferedBlockCipher func_56682_a(boolean par0, Key par1Key)
    {
        BufferedBlockCipher bufferedblockcipher = new BufferedBlockCipher(new CFBBlockCipher(new AESFastEngine(), 8));
        bufferedblockcipher.func_56756_a(par0, new ParametersWithIV(new KeyParameter(par1Key.getEncoded()), par1Key.getEncoded(), 0, 16));
        return bufferedblockcipher;
    }

    public static OutputStream func_55267_a(SecretKey par0SecretKey, OutputStream par1OutputStream)
    {
        return new CipherOutputStream(par1OutputStream, func_56682_a(true, par0SecretKey));
    }

    public static InputStream func_55265_a(SecretKey par0SecretKey, InputStream par1InputStream)
    {
        return new CipherInputStream(par1InputStream, func_56682_a(false, par0SecretKey));
    }

    static
    {
        Security.addProvider(new BouncyCastleProvider());
    }
}
