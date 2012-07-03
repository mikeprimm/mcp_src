package net.minecraft.src;

import java.util.*;

public class LowerStringMap implements Map
{
    private final Map field_56536_a = new LinkedHashMap();

    public LowerStringMap()
    {
    }

    public int size()
    {
        return field_56536_a.size();
    }

    public boolean isEmpty()
    {
        return field_56536_a.isEmpty();
    }

    public boolean containsKey(Object par1Obj)
    {
        return field_56536_a.containsKey(par1Obj.toString().toLowerCase());
    }

    public boolean containsValue(Object par1Obj)
    {
        return field_56536_a.containsKey(par1Obj);
    }

    public Object get(Object par1Obj)
    {
        return field_56536_a.get(par1Obj.toString().toLowerCase());
    }

    public Object func_56535_a(String par1Str, Object par2Obj)
    {
        return field_56536_a.put(par1Str.toLowerCase(), par2Obj);
    }

    public Object remove(Object par1Obj)
    {
        return field_56536_a.remove(par1Obj.toString().toLowerCase());
    }

    public void putAll(Map par1Map)
    {
        java.util.Map.Entry entry;

        for (Iterator iterator = par1Map.entrySet().iterator(); iterator.hasNext(); func_56535_a((String)entry.getKey(), entry.getValue()))
        {
            entry = (java.util.Map.Entry)iterator.next();
        }
    }

    public void clear()
    {
        field_56536_a.clear();
    }

    public Set keySet()
    {
        return field_56536_a.keySet();
    }

    public Collection values()
    {
        return field_56536_a.values();
    }

    public Set entrySet()
    {
        return field_56536_a.entrySet();
    }

    public Object put(Object par1Obj, Object par2Obj)
    {
        return func_56535_a((String)par1Obj, par2Obj);
    }
}
