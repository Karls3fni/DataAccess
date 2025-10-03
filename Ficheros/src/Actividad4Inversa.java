import java.io.*;

public class Actividad4Inversa {

    public static void main(String[] args) {
        String ficheroISO = "salida_iso.txt";
        String ficheroUTF16 = "salida_utf16.txt";
        String salidaUTF8_ISO = "recuperado_desde_iso.txt";
        String salidaUTF8_UTF16 = "recuperado_desde_utf16.txt";

        convertirAFicheroUTF8(ficheroISO, "ISO-8859-1", salidaUTF8_ISO);
        convertirAFicheroUTF8(ficheroUTF16, "UTF-16", salidaUTF8_UTF16);
    }

    public static void convertirAFicheroUTF8(String ficheroEntrada, String codEntrada, String ficheroSalida) {
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        new FileInputStream(ficheroEntrada), codEntrada));
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(ficheroSalida), "UTF-8"));
        ) {
            String linea;
            while ((linea = br.readLine()) != null) {
                bw.write(linea);
                bw.newLine();
            }
            System.out.println("Fichero " + ficheroSalida + " generado desde " + ficheroEntrada);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
