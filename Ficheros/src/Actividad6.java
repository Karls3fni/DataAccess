import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Actividad6 {

        private File f;
        private List<Pair<String, Integer>> campos;
        private long longReg;
        private long numReg = 0;

        Actividad6(String nomFich, List<Pair<String, Integer>> campos)
                throws IOException {
            this.campos = campos;
            this.f = new File(nomFich);
            longReg = 0;
            for (Pair<String, Integer> campo : campos) {
                this.longReg += campo.getValue();
            }
            if (f.exists()) {
                this.numReg = f.length() / this.longReg;
            }
        }

        public long getNumReg() {
            return numReg;
        }

        public void insertar(Map<String, String> reg) throws IOException {
            insertar(reg, this.numReg++);
        }

        public void insertar(Map<String, String> reg, long pos) throws IOException {
            try (RandomAccessFile faa = new RandomAccessFile(f, "rws")) {
                faa.seek(pos * this.longReg);
                for (Pair<String, Integer> campo : this.campos) {
                    String nomCampo = campo.getKey();
                    Integer longCampo = campo.getValue();
                    String valorCampo = reg.get(nomCampo);
                    if (valorCampo == null) {
                        valorCampo = "";
                    }
                    String valorCampoForm = String.format("%1$-" + longCampo + "s", valorCampo);
                    faa.write(valorCampoForm.getBytes("UTF-8"), 0, longCampo);
                }
            }
        }

        /**
         * Método para leer y mostrar todos los registros del fichero
         */
        public void leerTodos() throws IOException {
            try (RandomAccessFile faa = new RandomAccessFile(f, "r")) {
                System.out.println("\n--- Contenido del fichero ---");
                for (int i = 0; i < faa.length() / this.longReg; i++) {
                    faa.seek(i * this.longReg);
                    StringBuilder sb = new StringBuilder();
                    for (Pair<String, Integer> campo : this.campos) {
                        byte[] buffer = new byte[campo.getValue()];
                        faa.read(buffer);
                        String valor = new String(buffer, "UTF-8").trim();
                        sb.append(campo.getKey()).append("=").append(valor).append(" | ");
                    }
                    System.out.println("Registro " + i + ": " + sb);
                }
                System.out.println("-----------------------------\n");
            }
        }

        public static void main(String[] args) {

            List<Pair<String, Integer>> campos = new ArrayList<>();
            campos.add(new Pair<>("DNI", 9));
            campos.add(new Pair<>("NOMBRE", 32));
            campos.add(new Pair<>("CP", 5));

            try {
                Actividad6 faa = new Actividad6("fic_acceso_aleat.dat", campos);

                Map<String, String> reg = new HashMap<>();
                reg.put("DNI", "56789012B");
                reg.put("NOMBRE", "SAMPER");
                reg.put("CP", "29730");
                faa.insertar(reg);

                reg.clear();
                reg.put("DNI", "89012345E");
                reg.put("NOMBRE", "ROJAS");
                faa.insertar(reg);

                reg.clear();
                reg.put("DNI", "23456789D");
                reg.put("NOMBRE", "DORCE");
                reg.put("CP", "13700");
                faa.insertar(reg);

                // Sobreescribe el registro en la posición 1
                reg.clear();
                reg.put("DNI", "78901234X");
                reg.put("NOMBRE", "NADALES");
                reg.put("CP", "44126");
                faa.insertar(reg, 1);

                // 🚨 Prueba: insertar en una posición mayor al número de registros actuales
                reg.clear();
                reg.put("DNI", "99999999Z");
                reg.put("NOMBRE", "FUERA_DE_RANGO");
                reg.put("CP", "50000");
                faa.insertar(reg, 10); // <<-- Insertamos en la posición 10

                // Mostrar el contenido del fichero
                faa.leerTodos();

            } catch (IOException e) {
                System.err.println("Error de E/S: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



}
