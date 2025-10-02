import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Actividad3 {

        public static void main(String[] args) {

         if (args.length < 2){
             System.out.println("Indicar por favor el nombre del fichero.");
             return;
         }

         String nombreFichero = args[0];
         String palabraBuscada = args[1];

         try (BufferedReader fbr = new BufferedReader(new FileReader(nombreFichero))) {
             String linea;
             int numeroLinea = 0;
             while ((linea = fbr.readLine()) != null) {
                 int index = 0;
                 numeroLinea++;
                 while ((index = linea.indexOf(palabraBuscada, index)) != -1) {
                     System.out.println("Línea " + numeroLinea + ", columna " + (index + 1));
                     index += palabraBuscada.length();
                 }
             }
         }
         catch (FileNotFoundException e) {
             System.out.println("No existe fichero " + nombreFichero);
         }
         catch (IOException e) {
             System.out.println("Error de E/S: " + e.getMessage());
         }
         catch (Exception e) {
             e.printStackTrace();
         }

        }
    }

