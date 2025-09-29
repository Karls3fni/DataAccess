import java.io.File;

public class Actividad1 {
    public static void main(String[] args) {
        String ruta = ".";
        File fich = new File(ruta);

        if (!fich.exists()) {
            System.out.println(ruta + " no existe");
        } else if (fich.isFile()) {
            System.out.println(ruta + " es un fichero");
        } else if (fich.isDirectory()) {
            System.out.println(ruta + " es un directorio. Contenidos:");

            File[] ficheros = fich.listFiles();
            if (ficheros != null) {
                for (File f : ficheros) {
                    String textoDescr = f.isDirectory() ? "/" : f.isFile() ? "_" : "?";
                    System.out.println("(" + textoDescr + ") " + f.getName());
                }
            }
        }
    }
}


