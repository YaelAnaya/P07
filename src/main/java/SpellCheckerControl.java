
import java.util.ArrayList;
import java.util.HashSet;



/*
La clase SpellCheckerControl es la encargada realizar las operaciones que el usuario decida con las
 palabras que no encuentra en el diccionario, como lo son a�adir al diccionario u omitir por el resto de la revisi�n.
 Para saber si la palabra est� en el diccionario, realiza el proceso de b�squeda de cada palabra del texto
 utilizando los m�todos que el usuario decida (binaria/hash).
 Tambi�n es la encargada de una vez terminada la revisi�n guardar, guardar todo el diccionario modicificado en memoria
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
     * A�ade una palabra al diccionario y a la tabla hash.
     *
     * @param word palabra a a�adir.
     * */
    public void addWord(String word) {
        dictionary.addNewWordToDictionary(word);
        hashTable.insert(word);
    }

    /**
     * A�ade la palabra a la lista de palabras omitidas.
     *
     * @param word palabra a omitir.
     * */
    public void addAsOmitted(String word) {
        omittedWords.add(word);
    }

    /**
     * Este m�todo nos ayuda a verificar si las palabras no est�n en el diccionario o tabla hash.
     * Es muy util al momento de remarcar las palabras que no est�n en el diccionario.
     *
     * @param contentText contenido del documento.
     * @return palabra que no est� en el diccionario.
     * */
    public String getNextMissingWord(String contentText) {
        for(String word: contentText.split("\\s+")) {
            word = word.replaceAll("[^a-zA-Z�-�]+","");
            if (!methods.search(word))
                if (!omittedWords.contains(word))
                    return word;
            
        }
        return "";
    }


    /**
     * Este m�todo sobreescribe el diccionario con las palabras que agreg� el usuario.
     * */
    public void saveDictionary(){
        FileManager.updateDictionaryContent(dictionary.getDictionary());
        omittedWords.clear();
    }

    /**
     * Este m�todo limpia la lista de palabras que se omitieron.
     * */
    public void resetOmittedWords(){
        omittedWords.clear();
    }

    /**
     * Est� m�todo cuenta todas las palabras que se leyeron del archivo.
     *
     * @param content contenido le�do.
     * @return cantidad de palabras le�das.
     * */
    public int countWords(String content){
        return content.split("\\s+").length;
    }

}
