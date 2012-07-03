package org.bouncycastle.jce.provider;

import java.security.PrivilegedAction;

class BouncyCastleProviderAction implements PrivilegedAction
{
    final BouncyCastleProvider field_56235_a;

    BouncyCastleProviderAction(BouncyCastleProvider par1BouncyCastleProvider)
    {
        field_56235_a = par1BouncyCastleProvider;
    }

    public Object run()
    {
        BouncyCastleProvider.func_56476_a(field_56235_a);
        return null;
    }
}
