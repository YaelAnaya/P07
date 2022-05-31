import java.text.Collator;
import java.util.LinkedList;
import java.util.Locale;

/**
 * El propósito general de esta clase es el de buscar las palabras utilizando los métodos de búsqueda
 * binaria y hash, estos mismos serán elegidos por el usuario.
 * */
public class SearchingMethods {
    private Dictionary dictionary;
    private HashTable hashTable;
    private static MethodName methodName;
    private Collator collator;

    public SearchingMethods(Dictionary dictionary, HashTable hashTable, Collator collator) {
        this.dictionary = dictionary;
        this.hashTable = hashTable;
        methodName = MethodName.BINARY_SEARCH;
        this.collator = collator;
    }

    public static void setMethodName(MethodName methodName) {
        SearchingMethods.methodName = methodName;
    }

    /**
     * Este método realiza una búsqueda de la palabra utilizando
     * el algoritmo indicado por el usuario.
     *
     * @param word palabra a buscar.
     * @return true si la palabra se encuentra en el diccionario, false en caso contrario.
     * */

    public boolean search(String word) {
        switch (methodName) {
            case BINARY_SEARCH: return binarySearch(word);

            case HASHING: return hashing(word);

            default: return false;
        }
    }

    /**
     * Este método implementa el algoritmo de búsqueda binaria, recibe una palabra y la busca en el diccionario.
     * @param word palabra a buscar.
     * @return la posición de la palabra en el diccionario.
     * */
    private boolean binarySearch(String word){
        int low = 0, high = dictionary.getSize() - 1,  mid;
        while (low <= high) {
            mid = (low + high) / 2;
            if (collator.compare(dictionary.getWord(mid), word) == 0) {
                return true;
            } else if (collator.compare(dictionary.getWord(mid), word) < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }

    /*
    * Este método implementa el algoritmo de búsqueda por hashing, recibe una palabra y la busca en la
    * tabla hash.
    *
    * @param word palabra a buscar.
    * @return la posición de la palabra en la tabla hash.
    **/
    private boolean hashing(String word){
        LinkedList<String> list = hashTable.get(word);

        for (String s : list) {
            if (collator.compare(s, word) == 0)
                return true;
        }
        return false;
    }

}
