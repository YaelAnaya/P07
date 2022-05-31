import java.awt.Component;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.Collator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 La clase FileManager se encarga de manejar todos los procesos relacionados con el manejo de archivos,
 como abrir, guardar, etc.
* */

public class FileManager {
    private static final String FILE_PATH = "src/main/resources/listado-general.txt";
    private static File choosedFile;

    /**
     * Este método permite leer el archivo de texto especificado en la constante FILE_PATH
     * y retorna una lista con los datos leídos.
     */
    public static List<String> readDictionaryContent() {
        File file = new File(FILE_PATH);
        try (var reader = new BufferedReader(new FileReader(file, Charset.forName("UTF-8")))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Este método es utilizado para escribir las palabras que se agreguen al Dictionary.
     *
     * @param content es la lista de palabras que se desean agregar al archivo.
     * */
    public static void updateDictionaryContent(List<String> content) {
        File file = new File(FILE_PATH);
        try (var writer = new BufferedWriter(new FileWriter(file, Charset.forName("UTF-8")))) {
            for (String line : content)
                writer.append(line).append("\n");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Este método es utilizado para guardar el contenido del archivo
     * modificado por el usuario al finalizar la revisión.
     * */
    public static void updateFileContent(String content){
        var correctedFileName = choosedFile.getName().replaceFirst("[.][^.]+$","");
        File file = new File("src/main/resources/" + correctedFileName + "_corregido.txt");
        try(var writer = new BufferedWriter(new FileWriter(file, Charset.forName("UTF-8")))){
            writer.append(content);
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    /**
     * Este método crea un JFileChooser para que el usuario pueda seleccionar un archivo .txt
     * y concatena su contenido en una cadena de texto, para mostrarlo en el JTextArea.
     *
     * @param component es el componente padre del JFileChooser, en este caso el JFrame.
     * @return la cadena de texto con el contenido del archivo seleccionado.
     * */
    public static String readTextFile(Component component) {
        StringBuilder contentBuilder = new StringBuilder(); //Cadena con el contenido del archivo seleccionado.
        BufferedReader in; // Variable para leer el archivo.
        /* Se crea el objeto JFileChooser */
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo de texto(*.txt)", "txt");
        fileChooser.setAcceptAllFileFilterUsed(Boolean.FALSE);
        fileChooser.setFileFilter(filter);

        /* abre el popup del JFileChooser y retorna si se seleccionó un archivo */
        var selection = fileChooser.showOpenDialog(component);
        /* Si se seleccionó un archivo retornará su contenido, si no un string vacío */
        if (selection == JFileChooser.APPROVE_OPTION) {
            choosedFile = fileChooser.getSelectedFile();
            try {
                /* Se lee y guarda el contenido del archivo seleccionado */
                in = new BufferedReader(new FileReader(choosedFile, StandardCharsets.UTF_8));
                String line = in.readLine();
                while (line != null) {
                    contentBuilder.append(line).append("\n");
                    line = in.readLine();
                }
                return contentBuilder.toString();
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
                
        }
        return "";
    } 
}
