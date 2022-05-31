
import java.util.*;

/**
 * En esta clase se implementa una tabla hash, que es una estructura de datos que permite almacenar
 * datos en las posiciones indicadas por una función hash. Para cumplir con ese cometido, se
 * utilizó un ArrayList de Listas, esto sirve para solucionar el problema de colisiones.
 */
public class HashTable <T>{
    private ArrayList<LinkedList<T>> table;

    public HashTable(){
        table = new ArrayList<>();
    }

    public ArrayList<LinkedList<T>> getTable(){
        return table;
    }

    /**
     * Con este método llenamos la tabla con los elementos
     * que se encuentran en una lista.
     *
     * @param list Lista de elementos a insertar.
     * */
    public void fill(List<T> list){
        for(T s : list)
            insert(s);
    }
    /**
     * Este método genera el índice en el que se insertará el elemento, para ello
     * se utiliza la función hashcode que posee el objeto y se realiza la
     * operación módulo entre 10000, para reducir el tamaño del índice.
     *
     * @param key Clave a asignar.
     * @return Posición en la tabla.
     */
    public int hash(T key){
        return Math.abs(key.hashCode()) % 10000;
    }

    public LinkedList<T> get(T key){
        return table.get(hash(key));
    }

    /**
     * Función que inserta un elemento en la tabla.
     * @param value Valor a insertar.
     */
    public void insert(T value){
        int position = hash(value);
        /* Si el índice generado es mayor que la capacidad de la tabla,
           se aumenta el tamaño de la tabla. */
        if(table.size() <= position)
            grow((position + 1));
        
        if (table.get(position).contains(value))
            return;

        /* Se agrega el elemento a un nodo de la lista que este
        * en la posición calculada. */
        table.get(position).add(value);
    }

    /*
    * Función que aumenta el tamaño de la tabla.
    *
    * @param newSize Nuevo tamaño de la tabla.
    * */
    private void grow(int newSize){
        table.ensureCapacity(newSize);

        for(int i = table.size(); i < newSize; i++)
            table.add(new LinkedList<>());
    }


}
