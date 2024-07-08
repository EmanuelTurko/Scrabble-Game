package test;



import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;


public class BloomFilter {

    String[] algorithms;
    BitSet bs;

    BloomFilter(int size, String... alg)
    {
        bs = new BitSet(size);
        algorithms = new String[alg.length];
        System.arraycopy(alg, 0, algorithms, 0, alg.length);
    }


    void add(String word)
    {
    for(String s : algorithms)
        {
            MessageDigest md;
            try {
                md = MessageDigest.getInstance(s);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            md.reset();
            md.update(word.getBytes());
            byte[] digest = md.digest();
            BigInteger bi = new BigInteger(1, (digest));
          int k =  bi.intValue();
          int index = Math.abs(k) % bs.size();
           bs.set(index);
        }
    }
    boolean contains(String word)
    {
        for(String s : algorithms) {

            MessageDigest md;
            try {
                md = MessageDigest.getInstance(s);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            md.reset();
            md.update(word.getBytes());
            byte[] digest = md.digest();
            BigInteger bi = new BigInteger(1, digest);
            int k = bi.intValue();
            int index = Math.abs(k) % bs.size();
            if (bs.get(index)) return true;
        }
        return false;
    }
    public String toString() {
       StringBuilder s = new StringBuilder();
       int index = 0;
       int i = 0;

       while((index = bs.nextSetBit(index)) != -1)
       {
         while(i<index)
         {
             s.append('0');
             i++;
         }
         s.append('1');
         i++;
         index++;
       }
        return s.toString();
    }

}
