package org.bouncycastle.jce.provider;

import java.security.Permission;
import org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission;

class BouncyCastleProviderConfiguration implements ProviderConfiguration
{
    private static Permission field_56533_a;
    private static Permission field_56531_b;
    private static Permission field_56532_c;
    private static Permission field_56529_d;
    private ThreadLocal field_56530_e;
    private ThreadLocal field_56528_f;

    BouncyCastleProviderConfiguration()
    {
        field_56530_e = new ThreadLocal();
        field_56528_f = new ThreadLocal();
    }

    static
    {
        field_56533_a = new ProviderConfigurationPermission(BouncyCastleProvider.field_56484_a, "threadLocalEcImplicitlyCa");
        field_56531_b = new ProviderConfigurationPermission(BouncyCastleProvider.field_56484_a, "ecImplicitlyCa");
        field_56532_c = new ProviderConfigurationPermission(BouncyCastleProvider.field_56484_a, "threadLocalDhDefaultParams");
        field_56529_d = new ProviderConfigurationPermission(BouncyCastleProvider.field_56484_a, "DhDefaultParams");
    }
}
