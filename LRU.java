package test;


import java.util.Iterator;
import java.util.LinkedHashMap;

public class LRU implements CacheReplacementPolicy {
    private static Integer Index = 0;
    private final LinkedHashMap<String,Integer> LeastRecentlyUsed = new LinkedHashMap<String, Integer>();

    public void add(String word)
    {
        if(LeastRecentlyUsed.containsKey(word))
        {
            LeastRecentlyUsed.remove(word);
            LeastRecentlyUsed.put(word,++Index);
        }
        else
        {
            LeastRecentlyUsed.put(word,++Index);
        }
    }
    public String remove()
    {
        if(LeastRecentlyUsed.isEmpty())
        {
            return null;
        }
        Iterator<String> it = LeastRecentlyUsed.keySet().iterator();
        String Head = it.next();
        it.remove();

        return Head;

    }
}
