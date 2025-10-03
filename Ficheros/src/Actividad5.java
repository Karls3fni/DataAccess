import java.io.*;

public class Actividad5 {
    static int TAM_FILA = 32;
    static int MAX_BYTES = 2048;
    InputStream is = null;
    PrintStream out = null; // Ahora volcado a cualquier PrintStream

    // Constructor que recibe InputStream y PrintStream
    public Actividad5(InputStream is, PrintStream out) {
        this.is = is;
        this.out = out;
    }

    public void volcar() throws IOException {
        byte buffer[] = new byte[TAM_FILA];
        int bytesLeidos;
        int offset = 0;
        do {
            bytesLeidos = is.read(buffer);
            if (bytesLeidos == -1) break; // fin de fichero
            out.format("[%5d] ", offset);
            for (int i = 0; i < bytesLeidos; i++) {
                out.format("%02x ", buffer[i]);
            }
            offset += bytesLeidos;
            out.println();
        } while (bytesLeidos == TAM_FILA && offset < MAX_BYTES);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Uso: java VolcadoBinario ficheroEntrada ficheroSalida");
            return;
        }

        String nomFich = args[0];
        String nomFichSalida = args[1];

        try (FileInputStream fis = new FileInputStream(nomFich);
             PrintStream ps = new PrintStream(new FileOutputStream(nomFichSalida))) {

            Actividad5 vb = new Actividad5(fis, ps);
            vb.volcar();
            System.out.println("Volcado realizado en " + nomFichSalida);

        } catch (FileNotFoundException e) {
            System.err.println("ERROR: no existe fichero " + nomFich);
        } catch (IOException e) {
            System.err.println("ERROR de E/S: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
