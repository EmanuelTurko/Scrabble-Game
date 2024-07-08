package test;

import java.util.HashSet;
import java.util.Objects;

public class CacheManager {

    private final Integer size;
    private final CacheReplacementPolicy crp;
    private Integer current = 0;
    CacheManager(int size , CacheReplacementPolicy crp)
    {
        this.size = size;
        this.crp = crp;



    }
    HashSet<String> CacheManagerSet = new HashSet<>();
    boolean query(String word)
    {
        if(CacheManagerSet.contains(word)) return true;

        return false;
    }
     void add(String word)
    {
        if(Objects.equals(current, size))
        {
            String s= crp.remove();
            CacheManagerSet.remove(s);
            current--;
        }
        if(query(word))
        {
            crp.remove();
            CacheManagerSet.remove(word);
        }
        crp.add(word);
        CacheManagerSet.add(word);
        current++;
    }
}
