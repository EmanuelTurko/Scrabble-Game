package test;

import java.util.Arrays;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public class Tile {

    final char letter;
    final int score;

    Tile(int score, char letter)
    {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Tile)) return false; // Remove "Tile"
        Tile tile = (Tile) object; // Declare and cast the variable
        if (letter != tile.letter) return false;
        return score == tile.score;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (int) letter;
        result = 31 * result + score;
        return result;
    }

    public static class Bag{
        final int[] countsArray;
         final int[] scoresArray;
        private int totalTiles = 0;

        private static Bag instance = null;

        private Bag()
        {

            BagGenerator bagGenerator = new BagGenerator();
            countsArray = new int[BagGenerator.letterValuesMap.size()];
            scoresArray = new int[BagGenerator.letterValuesMap.size()];
            totalTiles = GenerateBag();
        }

        private int GenerateBag()
        {
            int i = 0;
            int total = 0;
            for(Integer[] values : BagGenerator.letterValuesMap.values())
                {
                    countsArray[i] = values[0];
                    scoresArray[i] = values[1];
                    total += values[0];
                    i++;
                }
            return total;
        }
        public int[] getCountsArray()
        {
            return countsArray;
        }
        public int[] getScoresArray()
        {
            return scoresArray;
        }
        public Tile getRand()
        {
            if (totalTiles == 0)
                return null;

            Random random = new Random();
            int RandomIndex = random.nextInt(totalTiles);
            int count = 0;

            for(int i = 0 ;i < countsArray.length;i++)
            {
                count += countsArray[i];
                if(RandomIndex < count)
                {
                    if(countsArray[i] == 0)
                    {
                        return null;
                    }
                    countsArray[i]--;
                    totalTiles--;
                    return new Tile(scoresArray[i], (char) ('A'+i));
                }
            }
            return null;
        }
        public Tile getTile(char c)
        {
            if(c > 'Z' || c < 'A')
                return null;

            int tile = (c-'A');
            if(countsArray[tile] == 0)
            {
                return null;
            }
            countsArray[tile]--;
            totalTiles--;
            return new Tile(scoresArray[tile], (char) ('A'+tile));
        }
        public void put(Tile tile)
        {
             char letter = tile.letter;
             int i = (letter - 'A');
             if(countsArray[i] == BagGenerator.getCountIndex(letter))
             {
                 return;
             }
             countsArray[i]++;
             totalTiles++;
        }
        public int size()
        {
            return totalTiles;
        }

        public int[] getQuantities() {
            return Arrays.copyOf(countsArray, countsArray.length);
        }

        public static Bag getBag()
        {
           if(instance == null)
           {
                instance = new Bag();
           }
           return instance;
        }
    }

    static class BagGenerator {
        static Map<Character,Integer[]> letterValuesMap;

        public BagGenerator()
        {
            letterValuesMap = new HashMap<>();
            letterValuesMap.put('A', new Integer[]{9,1});
            letterValuesMap.put('B', new Integer[]{2,3});
            letterValuesMap.put('C', new Integer[]{2,3});
            letterValuesMap.put('D', new Integer[]{4,2});
            letterValuesMap.put('E', new Integer[]{12,1});
            letterValuesMap.put('F', new Integer[]{2,4});
            letterValuesMap.put('G', new Integer[]{3,2});
            letterValuesMap.put('H', new Integer[]{2,4});
            letterValuesMap.put('I', new Integer[]{9,1});
            letterValuesMap.put('J', new Integer[]{1,8});
            letterValuesMap.put('K', new Integer[]{1,5});
            letterValuesMap.put('L', new Integer[]{4,1});
            letterValuesMap.put('M', new Integer[]{2,3});
            letterValuesMap.put('N', new Integer[]{6,1});
            letterValuesMap.put('O', new Integer[]{8,1});
            letterValuesMap.put('P', new Integer[]{2,3});
            letterValuesMap.put('Q', new Integer[]{1,10});
            letterValuesMap.put('R', new Integer[]{6,1});
            letterValuesMap.put('S', new Integer[]{4,1});
            letterValuesMap.put('T', new Integer[]{6,1});
            letterValuesMap.put('U', new Integer[]{4,1});
            letterValuesMap.put('V', new Integer[]{2,4});
            letterValuesMap.put('W', new Integer[]{2,4});
            letterValuesMap.put('X', new Integer[]{1,8});
            letterValuesMap.put('Y', new Integer[]{2,4});
            letterValuesMap.put('Z', new Integer[]{1,10});
        }
        private void addLetterCount(char letter,int count, int score)
        {
            Integer[] values = new Integer[]{count,score};
            letterValuesMap.put(letter,values);
        }
        public static int getCountIndex(char letter)
        {
            Integer[] values = letterValuesMap.get(letter);
            return values[0];
        }
    }
}


