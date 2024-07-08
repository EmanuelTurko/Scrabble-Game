package test;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Dictionary {
    CacheReplacementPolicy LRU;
    CacheReplacementPolicy LFU;
    CacheManager lru;
    CacheManager lfu;
    BloomFilter bf;

    Dictionary(String...fileNames)
    {
        LRU = new LRU();
        LFU = new LFU();
        lru = new CacheManager(400,LRU);
        lfu = new CacheManager(100,LFU);
        bf = new BloomFilter(256,"MD5","SHA1");
        for(String fileName: fileNames)
        {
            FileReader FR;
            try{
               FR = new FileReader(fileName);
               Scanner scan = new Scanner(FR);
               while(scan.hasNext())
               {
                   bf.add(scan.next());
               }
               scan.close();
               FR.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    boolean query(String word)
    {
        if(lru.query(word))
        {
            if(!lfu.query(word))
            {
                lfu.add(word);
            }
            return true;
        }
        else if(lfu.query(word))
        {
            lru.add(word);
            return true;
        }
        if(bf.contains(word))
        {
            lfu.add(word);
            lru.add(word);
            return true;
        }
        return false;
    }
    boolean challenge(String word)
    {
        try{
            if(IOSearcher.search(word))
            {
                if(!query(word))
                {
                    lfu.add(word);
                    lru.add(word);
                }
                return true;
            }

        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
