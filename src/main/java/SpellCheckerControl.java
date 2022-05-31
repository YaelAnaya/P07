
import java.util.ArrayList;
import java.util.HashSet;



/*
La clase SpellCheckerControl es la encargada realizar las operaciones que el usuario decida con las
 palabras que no encuentra en el diccionario, como lo son añadir al diccionario u omitir por el resto de la revisión.
 Para saber si la palabra está en el diccionario, realiza el proceso de búsqueda de cada palabra del texto
 utilizando los métodos que el usuario decida (binaria/hash).
 También es la encargada de una vez terminada la revisión guardar, guardar todo el diccionario modicificado en memoria
 en el archivo de texto del diccionario original y hacer el conteo de las palabras dentro del texto.
 */
public class SpellCheckerControl {
    private Dictionary dictionary;
    private SearchingMethods methods;
    private HashTable<String> hashTable;
    private HashSet<String> omittedWords;

    public SpellCheckerControl() {
        dictionary = new Dictionary();
        hashTable = new HashTable<>();
        hashTable.fill(dictionary.getDictionary());
        methods = new SearchingMethods(dictionary, hashTable, dictionary.getCollator());
        omittedWords = new HashSet<>();
        
    }

    /**
     * Añade una palabra al diccionario y a la tabla hash.
     *
     * @param word palabra a añadir.
     * */
    public void addWord(String word) {
        dictionary.addNewWordToDictionary(word);
        hashTable.insert(word);
    }

    /**
     * Añade la palabra a la lista de palabras omitidas.
     *
     * @param word palabra a omitir.
     * */
    public void addAsOmitted(String word) {
        omittedWords.add(word);
    }

    /**
     * Este método nos ayuda a verificar si las palabras no están en el diccionario o tabla hash.
     * Es muy util al momento de remarcar las palabras que no están en el diccionario.
     *
     * @param contentText contenido del documento.
     * @return palabra que no está en el diccionario.
     * */
    public String getNextMissingWord(String contentText) {
        for(String word: contentText.split("\\s+")) {
            word = word.replaceAll("[^a-zA-ZÀ-ÿ]+","");
            if (!methods.search(word))
                if (!omittedWords.contains(word))
                    return word;
            
        }
        return "";
    }


    /**
     * Este método sobreescribe el diccionario con las palabras que agregó el usuario.
     * */
    public void saveDictionary(){
        FileManager.updateDictionaryContent(dictionary.getDictionary());
        omittedWords.clear();
    }

    /**
     * Este método limpia la lista de palabras que se omitieron.
     * */
    public void resetOmittedWords(){
        omittedWords.clear();
    }

    /**
     * Esté método cuenta todas las palabras que se leyeron del archivo.
     *
     * @param content contenido leído.
     * @return cantidad de palabras leídas.
     * */
    public int countWords(String content){
        return content.split("\\s+").length;
    }

}
