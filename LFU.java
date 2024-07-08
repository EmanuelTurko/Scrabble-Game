package test;


import java.util.*;

public class LFU implements CacheReplacementPolicy{
    private LinkedHashMap<String,Integer> LeastFrequentlyUsed = new LinkedHashMap<>();


    public void add(String word)
    {
        if(LeastFrequentlyUsed.containsKey(word))
        {

            int Count = LeastFrequentlyUsed.get(word);
            LeastFrequentlyUsed.remove(word);
            Count++;
            LeastFrequentlyUsed.put(word,Count);
        }
        else
        {
            LeastFrequentlyUsed.put(word,1);
            Order();
        }
    }
    private void Order()
    {
        LinkedHashMap<String,Integer> sorted = new LinkedHashMap<>();
        LeastFrequentlyUsed.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEach(x->sorted.put(x.getKey(),x.getValue()));
        LeastFrequentlyUsed = sorted;
    }
    public String remove()
    {
        if(LeastFrequentlyUsed.isEmpty())
            return null;

        Iterator<String> it = LeastFrequentlyUsed.keySet().iterator();
        String Head = it.next();
        it.remove();

        return Head;
    }

}
