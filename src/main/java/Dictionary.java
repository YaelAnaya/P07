import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

/**
 * El objetivo de esta clase es crear un diccionario de palabras en español, estas
 * se guardan en una lista de tipo ArrayList.
 * */
public class Dictionary {
    private ArrayList<String> dictionary;
    private Collator collator;

    public Dictionary() {
        dictionary = new ArrayList<>();
        /* Se crea un collator para ordenar las palabras en espa�ol. */
        collator = Collator.getInstance(new Locale("es"));
        collator.setStrength(Collator.TERTIARY);
        fillDictionary();
    }


    public int getSize() {
        return dictionary.size();
    }

    public ArrayList<String> getDictionary() {
        return dictionary;
    }

    public Collator getCollator() {
        return collator;
    }
    

    /**
     * Este m�todo lee un archivo de texto con las palabras del diccionario,
     * y las a�ade al ArrayList. Al terminar de añadir las palabras, se ordena
     * el ArrayList usando el collator.
     * */
    public void fillDictionary() {
        dictionary.addAll(FileManager.readDictionaryContent());
        Collections.sort(dictionary, collator);
    }


    /*
     * Este método devuelve la palabra que se encuentra en la posición indicada
     * por el parámetro.
     *
     * @param index posición de la palabra en el diccionario.
     * @return palabra en la posición indicada.
    * */
    public String getWord(int index) {
        return dictionary.get(index);
    }

    /**
     * Con este método se añade una palabra nueva, una vez añadida,
     * se ordena de nuevo el diccionario.
     *
     * @param word palabra a añadir
     * */
    public void addNewWordToDictionary(String word) {
        if(dictionary.contains(word))
            return;
        
        dictionary.add(word);
        /* Para la ordenación se hizo uso de un collator,
           esto para ordenar correctamente las palabras. */
        Collections.sort(dictionary, collator);
    }

}



