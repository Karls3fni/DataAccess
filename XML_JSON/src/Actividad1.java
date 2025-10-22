import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
import org.xml.sax.SAXException;

public class Actividad1 {

    private static final String INDENT_NIVEL = "  ";

    /**
     * Método recursivo que recorre el árbol DOM y escribe la información
     * de cada nodo en el PrintStream indicado.
     */
    public static void muestraNodo(Node nodo, int nivel, PrintStream ps) {
        // Filtrar nodos de texto vacíos
        if (nodo.getNodeType() == Node.TEXT_NODE) {
            String text = nodo.getNodeValue();
            if (text.trim().isEmpty()) {
                return;
            }
        }

        // Indentación según nivel
        for (int i = 0; i < nivel; i++) {
            ps.print(INDENT_NIVEL);
        }

        // Procesar según tipo de nodo
        switch (nodo.getNodeType()) {
            case Node.DOCUMENT_NODE:
                Document doc = (Document) nodo;
                ps.println("Documento DOM, versión: " + doc.getXmlVersion()
                        + ", codificación: " + doc.getXmlEncoding());
                break;

            case Node.ELEMENT_NODE:
                ps.print("<" + nodo.getNodeName());
                NamedNodeMap atributos = nodo.getAttributes();
                for (int i = 0; i < atributos.getLength(); i++) {
                    Node atr = atributos.item(i);
                    ps.print(" @" + atr.getNodeName() + "[" + atr.getNodeValue() + "]");
                }
                ps.println(">");
                break;

            case Node.TEXT_NODE:
                ps.println(nodo.getNodeName() + "[" + nodo.getNodeValue() + "]");
                break;

            default:
                ps.println("(nodo de tipo: " + nodo.getNodeType() + ")");
        }

        // Procesar hijos
        NodeList hijos = nodo.getChildNodes();
        for (int i = 0; i < hijos.getLength(); i++) {
            muestraNodo(hijos.item(i), nivel + 1, ps);
        }
    }

    /**
     * Método principal. Recibe un fichero XML y genera un fichero de salida
     * con la información del DOM y la fecha/hora en el nombre.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Uso: java Actividad1 <nombre_fichero_xml>");
            return;
        }

        String nombreXML = args[0];

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setIgnoringComments(true);
        dbf.setIgnoringElementContentWhitespace(true);

        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document domDoc = db.parse(new File(nombreXML));

            // Crear nombre del archivo con fecha y hora
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String fechaHora = sdf.format(new Date());
            String nombreSalida = "parsing_dom_" + fechaHora + ".txt";

            // Crear flujo de salida
            PrintStream ps = new PrintStream(new File(nombreSalida));

            // Mostrar el árbol DOM en el fichero
            muestraNodo(domDoc, 0, ps);

            ps.close();
            System.out.println("Salida escrita en el fichero: " + nombreSalida);

        } catch (FileNotFoundException | ParserConfigurationException | SAXException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

