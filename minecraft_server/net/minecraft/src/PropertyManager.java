package net.minecraft.src;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyManager
{
    /** Reference to the logger. */
    public static Logger logger = Logger.getLogger("Minecraft");

    /** The server properties object. */
    private Properties serverProperties;

    /** The server properties file. */
    private File serverPropertiesFile;

    public PropertyManager(File par1File)
    {
        serverProperties = new Properties();
        serverPropertiesFile = par1File;

        if (par1File.exists())
        {
            FileInputStream fileinputstream = null;

            try
            {
                fileinputstream = new FileInputStream(par1File);
                serverProperties.load(fileinputstream);
            }
            catch (Exception exception)
            {
                logger.log(Level.WARNING, (new StringBuilder()).append("Failed to load ").append(par1File).toString(), exception);
                generateNewProperties();
            }
            finally
            {
                if (fileinputstream != null)
                {
                    try
                    {
                        fileinputstream.close();
                    }
                    catch (IOException ioexception) { }
                }
            }
        }
        else
        {
            logger.log(Level.WARNING, (new StringBuilder()).append(par1File).append(" does not exist").toString());
            generateNewProperties();
        }
    }

    /**
     * Generates a new properties file.
     */
    public void generateNewProperties()
    {
        logger.log(Level.INFO, "Generating new properties file");
        saveProperties();
    }

    /**
     * Writes the properties to the properties file.
     */
    public void saveProperties()
    {
        FileOutputStream fileoutputstream = null;

        try
        {
            fileoutputstream = new FileOutputStream(serverPropertiesFile);
            serverProperties.store(fileoutputstream, "Minecraft server properties");
        }
        catch (Exception exception)
        {
            logger.log(Level.WARNING, (new StringBuilder()).append("Failed to save ").append(serverPropertiesFile).toString(), exception);
            generateNewProperties();
        }
        finally
        {
            if (fileoutputstream != null)
            {
                try
                {
                    fileoutputstream.close();
                }
                catch (IOException ioexception) { }
            }
        }
    }

    /**
     * Returns this PropertyManager's file object used for property saving.
     */
    public File getPropertiesFile()
    {
        return serverPropertiesFile;
    }

    /**
     * Returns a string property. If the property doesn't exist the default is returned.
     */
    public String getStringProperty(String par1Str, String par2Str)
    {
        if (!serverProperties.containsKey(par1Str))
        {
            serverProperties.setProperty(par1Str, par2Str);
            saveProperties();
        }

        return serverProperties.getProperty(par1Str, par2Str);
    }

    /**
     * Returns an integer property. If the property doesn't exist the default is returned.
     */
    public int getIntProperty(String par1Str, int par2)
    {
        try
        {
            return Integer.parseInt(getStringProperty(par1Str, (new StringBuilder()).append("").append(par2).toString()));
        }
        catch (Exception exception)
        {
            serverProperties.setProperty(par1Str, (new StringBuilder()).append("").append(par2).toString());
        }

        return par2;
    }

    /**
     * Returns a boolean property. If the property doesn't exist the default is returned.
     */
    public boolean getBooleanProperty(String par1Str, boolean par2)
    {
        try
        {
            return Boolean.parseBoolean(getStringProperty(par1Str, (new StringBuilder()).append("").append(par2).toString()));
        }
        catch (Exception exception)
        {
            serverProperties.setProperty(par1Str, (new StringBuilder()).append("").append(par2).toString());
        }

        return par2;
    }

    /**
     * Saves an Object with the given property name
     */
    public void setProperty(String par1Str, Object par2Obj)
    {
        serverProperties.setProperty(par1Str, (new StringBuilder()).append("").append(par2Obj).toString());
    }
}
