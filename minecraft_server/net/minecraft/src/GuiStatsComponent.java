package net.minecraft.src;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JComponent;
import javax.swing.Timer;
import net.minecraft.server.MinecraftServer;

public class GuiStatsComponent extends JComponent
{
    private static final DecimalFormat field_40573_a = new DecimalFormat("########0.000");
    private int memoryUse[];

    /**
     * Counts the number of updates. Used as the index into the memoryUse array to display the latest value.
     */
    private int updateCounter;
    private String displayStrings[];
    private final MinecraftServer field_40572_e;

    public GuiStatsComponent(MinecraftServer par1MinecraftServer)
    {
        memoryUse = new int[256];
        updateCounter = 0;
        displayStrings = new String[10];
        field_40572_e = par1MinecraftServer;
        setPreferredSize(new Dimension(356, 246));
        setMinimumSize(new Dimension(356, 246));
        setMaximumSize(new Dimension(356, 246));
        (new Timer(500, new GuiStatsListener(this))).start();
        setBackground(Color.BLACK);
    }

    /**
     * Updates the stat values and calls paint to redraw the component.
     */
    private void updateStats()
    {
        int i;
        long l = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.gc();
        displayStrings[0] = (new StringBuilder()).append("Memory use: ").append(l / 1024L / 1024L).append(" mb (").append((Runtime.getRuntime().freeMemory() * 100L) / Runtime.getRuntime().maxMemory()).append("% free)").toString();
        displayStrings[1] = (new StringBuilder()).append("Threads: ").append(TcpConnection.field_56658_a.get()).append(" + ").append(TcpConnection.field_56656_b.get()).toString();
        displayStrings[2] = (new StringBuilder()).append("Avg tick: ").append(field_40573_a.format(func_48551_a(field_40572_e.field_40027_f) * 9.9999999999999995E-007D)).append(" ms").toString();
        displayStrings[3] = (new StringBuilder()).append("Avg sent: ").append((int)func_48551_a(field_40572_e.field_48080_u)).append(", Avg size: ").append((int)func_48551_a(field_40572_e.field_48079_v)).toString();
        displayStrings[4] = (new StringBuilder()).append("Avg rec: ").append((int)func_48551_a(field_40572_e.field_48078_w)).append(", Avg size: ").append((int)func_48551_a(field_40572_e.field_48082_x)).toString();

        if (field_40572_e.worldMngr == null)
        {
            return;
        }

        i = 0;

        while (!(i >= field_40572_e.worldMngr.length))
        {
            displayStrings[5 + i] = (new StringBuilder()).append("Lvl ").append(i).append(" tick: ").append(field_40573_a.format(func_48551_a(field_40572_e.field_40028_g[i]) * 9.9999999999999995E-007D)).append(" ms").toString();

            if (field_40572_e.worldMngr[i] == null || field_40572_e.worldMngr[i].chunkProviderServer == null)
            {
                i++;
                continue;
            }

            displayStrings[5 + i] += ", " + field_40572_e.worldMngr[i].chunkProviderServer.func_46040_d();
            i++;
        }

        memoryUse[updateCounter++ & 0xff] = (int)((func_48551_a(field_40572_e.field_48079_v) * 100D) / 12500D);
        repaint();
        return;
    }

    private double func_48551_a(long par1ArrayOfLong[])
    {
        long l = 0L;
        long al[] = par1ArrayOfLong;
        int i = al.length;

        for (int j = 0; j < i; j++)
        {
            long l1 = al[j];
            l += l1;
        }

        return (double)l / (double)par1ArrayOfLong.length;
    }

    public void paint(Graphics par1Graphics)
    {
        par1Graphics.setColor(new Color(0xffffff));
        par1Graphics.fillRect(0, 0, 356, 246);

        for (int i = 0; i < 256; i++)
        {
            int k = memoryUse[i + updateCounter & 0xff];
            par1Graphics.setColor(new Color(k + 28 << 16));
            par1Graphics.fillRect(i, 100 - k, 1, k);
        }

        par1Graphics.setColor(Color.BLACK);

        for (int j = 0; j < displayStrings.length; j++)
        {
            String s = displayStrings[j];

            if (s != null)
            {
                par1Graphics.drawString(s, 32, 116 + j * 16);
            }
        }
    }

    /**
     * Public static accessor to call updateStats.
     */
    static void update(GuiStatsComponent par0GuiStatsComponent)
    {
        par0GuiStatsComponent.updateStats();
    }
}
