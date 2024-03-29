package net.minecraft.src;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostHttp
{
    private PostHttp()
    {
    }

    public static String func_52008_a(Map par0Map)
    {
        StringBuilder stringbuilder = new StringBuilder();
        Iterator iterator = par0Map.entrySet().iterator();

        do
        {
            if (!iterator.hasNext())
            {
                break;
            }

            java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();

            if (stringbuilder.length() > 0)
            {
                stringbuilder.append('&');
            }

            try
            {
                stringbuilder.append(URLEncoder.encode((String)entry.getKey(), "UTF-8"));
            }
            catch (UnsupportedEncodingException unsupportedencodingexception)
            {
                unsupportedencodingexception.printStackTrace();
            }

            if (entry.getValue() != null)
            {
                stringbuilder.append('=');

                try
                {
                    stringbuilder.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
                }
                catch (UnsupportedEncodingException unsupportedencodingexception1)
                {
                    unsupportedencodingexception1.printStackTrace();
                }
            }
        }
        while (true);

        return stringbuilder.toString();
    }

    public static String func_52010_a(URL par0URL, Map par1Map, boolean par2)
    {
        return func_52009_a(par0URL, func_52008_a(par1Map), par2);
    }

    public static String func_52009_a(URL par0URL, String par1Str, boolean par2)
    {
        try
        {
            HttpURLConnection httpurlconnection = (HttpURLConnection)par0URL.openConnection();
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpurlconnection.setRequestProperty("Content-Length", (new StringBuilder()).append("").append(par1Str.getBytes().length).toString());
            httpurlconnection.setRequestProperty("Content-Language", "en-US");
            httpurlconnection.setUseCaches(false);
            httpurlconnection.setDoInput(true);
            httpurlconnection.setDoOutput(true);
            DataOutputStream dataoutputstream = new DataOutputStream(httpurlconnection.getOutputStream());
            dataoutputstream.writeBytes(par1Str);
            dataoutputstream.flush();
            dataoutputstream.close();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
            StringBuffer stringbuffer = new StringBuffer();
            String s;

            while ((s = bufferedreader.readLine()) != null)
            {
                stringbuffer.append(s);
                stringbuffer.append('\r');
            }

            bufferedreader.close();
            return stringbuffer.toString();
        }
        catch (Exception exception)
        {
            if (!par2)
            {
                Logger.getLogger("Minecraft").log(Level.SEVERE, (new StringBuilder()).append("Could not post to ").append(par0URL).toString(), exception);
            }
        }

        return "";
    }
}
