import java.io.*;

public class Actividad4 {

    public static void main(String[] args) {

        String ficheroEntrada = "entrada.txt";
        String ficheroISO = "salida_iso.txt";
        String ficheroUTF16 = "salida_utf16";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(ficheroEntrada), "UTF-8"));
             BufferedWriter bwISO = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ficheroISO), "ISO-8859-1"));
             BufferedWriter bwUTF16 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ficheroUTF16), "UTF-16"));
        ) {
            String linea;
            while ((linea = br.readLine()) != null) {

                bwISO.write(linea);
                bwISO.newLine();

                bwUTF16.write(linea);
                bwUTF16.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
