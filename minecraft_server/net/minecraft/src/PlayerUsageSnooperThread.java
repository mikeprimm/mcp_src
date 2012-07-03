package net.minecraft.src;

import java.util.*;

class PlayerUsageSnooperThread extends TimerTask
{
    final PlayerUsageSnooper field_52017_a;

    PlayerUsageSnooperThread(PlayerUsageSnooper par1PlayerUsageSnooper)
    {
        field_52017_a = par1PlayerUsageSnooper;
    }

    public void run()
    {
        HashMap hashmap;

        synchronized (PlayerUsageSnooper.func_56618_a(field_52017_a))
        {
            hashmap = new HashMap(PlayerUsageSnooper.func_52011_b(field_52017_a));
        }

        hashmap.put("snooper_count", Integer.valueOf(PlayerUsageSnooper.func_56623_c(field_52017_a)));
        hashmap.put("snooper_time", Long.valueOf(System.currentTimeMillis()));
        PostHttp.func_52010_a(PlayerUsageSnooper.func_56621_d(field_52017_a), hashmap, true);
    }
}
