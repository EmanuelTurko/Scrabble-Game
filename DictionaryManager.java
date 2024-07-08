package test;


import java.util.*;

public class DictionaryManager {

    static Map<String,Dictionary> DictionaryMap = new HashMap<>();
    private static DictionaryManager instance = null;

    boolean query(String... books) {
        boolean found = false;
        String[] bookNames = Arrays.copyOf(books, books.length - 1);
        for (String book : bookNames) {
            if (!DictionaryMap.containsKey(book)) DictionaryMap.put(book, new Dictionary(book));
        }
        String query = books[books.length - 1];
        for (String book : bookNames) {
            Dictionary dictionary = DictionaryMap.get(book);
            if (dictionary.query(query))found = true;
        }
        return found;
    }
    boolean challenge(String... books) {
        boolean found = false;
        String[] bookNames = Arrays.copyOf(books,books.length-1);
        for(String book : bookNames) {
            if(!DictionaryMap.containsKey(book)) DictionaryMap.put(book,new Dictionary(book));
        }
        String query = books[books.length-1];
        for(String book : bookNames) {
            Dictionary dictionary = DictionaryMap.get(book);
            if(IOSearcher.search(query,book)) found = true;
        }
        return found;
    }
    int getSize() {
        return DictionaryMap.size();
    }

    public static DictionaryManager get() {
        if(instance == null) instance = new DictionaryManager();
        return instance;
    }
}

