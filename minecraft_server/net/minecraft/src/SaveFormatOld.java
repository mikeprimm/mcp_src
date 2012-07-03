package net.minecraft.src;

import java.io.*;

public class SaveFormatOld implements ISaveFormat
{
    /**
     * Reference to the File object representing the directory for the world saves
     */
    protected final File savesDirectory;

    public SaveFormatOld(File par1File)
    {
        if (!par1File.exists())
        {
            par1File.mkdirs();
        }

        savesDirectory = par1File;
    }

    public void func_56408_b()
    {
    }

    /**
     * gets the world info
     */
    public WorldInfo getWorldInfo(String par1Str)
    {
        File file = new File(savesDirectory, par1Str);

        if (!file.exists())
        {
            return null;
        }

        File file1 = new File(file, "level.dat");

        if (file1.exists())
        {
            try
            {
                NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file1));
                NBTTagCompound nbttagcompound2 = nbttagcompound.getCompoundTag("Data");
                return new WorldInfo(nbttagcompound2);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }

        file1 = new File(file, "level.dat_old");

        if (file1.exists())
        {
            try
            {
                NBTTagCompound nbttagcompound1 = CompressedStreamTools.readCompressed(new FileInputStream(file1));
                NBTTagCompound nbttagcompound3 = nbttagcompound1.getCompoundTag("Data");
                return new WorldInfo(nbttagcompound3);
            }
            catch (Exception exception1)
            {
                exception1.printStackTrace();
            }
        }

        return null;
    }

    public void func_56409_b(String par1Str)
    {
        File file = new File(savesDirectory, par1Str);

        if (!file.exists())
        {
            return;
        }
        else
        {
            func_56410_a(file.listFiles());
            file.delete();
            return;
        }
    }

    protected static void func_56410_a(File par0ArrayOfFile[])
    {
        File afile[] = par0ArrayOfFile;
        int i = afile.length;

        for (int j = 0; j < i; j++)
        {
            File file = afile[j];

            if (file.isDirectory())
            {
                System.out.println((new StringBuilder()).append("Deleting ").append(file).toString());
                func_56410_a(file.listFiles());
            }

            file.delete();
        }
    }

    /**
     * Returns back a loader for the specified save directory
     */
    public ISaveHandler getSaveLoader(String par1Str, boolean par2)
    {
        return new SaveHandler(savesDirectory, par1Str, par2);
    }

    /**
     * gets if the map is old chunk saving (true) or McRegion (false)
     */
    public boolean isOldMapFormat(String par1Str)
    {
        return false;
    }

    /**
     * converts the map to mcRegion
     */
    public boolean convertMapFormat(String par1Str, IProgressUpdate par2IProgressUpdate)
    {
        return false;
    }
}
