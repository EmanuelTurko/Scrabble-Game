package test;


import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class IOSearcher {
    static boolean search(String word,String...Filenames) {
        for (String Filename : Filenames) {
            FileReader FR;
            try {
                FR = new FileReader(Filename);
                Scanner scan = new Scanner(FR);
                while (scan.hasNext()) {
                    if (Objects.equals(scan.next(), word)) {
                        scan.close();
                        FR.close();
                        return true;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
