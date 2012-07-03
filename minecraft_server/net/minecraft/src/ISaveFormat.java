package net.minecraft.src;

public interface ISaveFormat
{
    /**
     * Returns back a loader for the specified save directory
     */
    public abstract ISaveHandler getSaveLoader(String s, boolean flag);

    public abstract void func_56408_b();

    public abstract void func_56409_b(String s);

    /**
     * gets if the map is old chunk saving (true) or McRegion (false)
     */
    public abstract boolean isOldMapFormat(String s);

    /**
     * converts the map to mcRegion
     */
    public abstract boolean convertMapFormat(String s, IProgressUpdate iprogressupdate);
}
