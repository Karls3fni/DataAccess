import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class Actividad2 {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Uso: java dom_parser.Actividad2 <fichero.xml>");
            System.exit(1);
        }

        File ficheroXML = new File(args[0]);

        if (!ficheroXML.exists()) {
            System.err.println("Error: el fichero no existe.");
            System.exit(1);
        }

        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(ficheroXML);
            doc.getDocumentElement().normalize();

            Element raiz = doc.getDocumentElement();
            if (raiz == null) {
                System.err.println("Error: el documento XML está vacío.");
                System.exit(1);
            }

            NodeList hijosRaiz = raiz.getChildNodes();
            boolean contieneClientes = false;

            for (int i = 0; i < hijosRaiz.getLength(); i++) {
                if (hijosRaiz.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    contieneClientes = true;
                    break;
                }
            }

            if (!contieneClientes) {
                System.err.println("Error: el elemento raíz no contiene elementos de cliente.");
                System.exit(1);
            }

            Element nuevoCliente = doc.createElement("cliente");

            Element dni = doc.createElement("dni");
            dni.setTextContent("99999999Z");

            Element apellidos = doc.createElement("apellidos");
            apellidos.setTextContent("García Pérez");

            Element cp = doc.createElement("cp");
            cp.setTextContent("28080");

            nuevoCliente.appendChild(dni);
            nuevoCliente.appendChild(apellidos);
            nuevoCliente.appendChild(cp);

            Node primerCliente = null;
            for (int i = 0; i < raiz.getChildNodes().getLength(); i++) {
                Node nodo = raiz.getChildNodes().item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    primerCliente = nodo;
                    break;
                }
            }

            if (primerCliente != null) {
                raiz.insertBefore(nuevoCliente, primerCliente);
            } else {
                raiz.appendChild(nuevoCliente);
            }

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            transformer.transform(new DOMSource(doc), new StreamResult(System.out));

        } catch (ParserConfigurationException e) {
            System.err.println("Error de configuración del parser: " + e.getMessage());
        } catch (org.xml.sax.SAXException e) {
            System.err.println("Error: el fichero no es un XML bien formado.");
        } catch (TransformerException e) {
            System.err.println("Error al transformar el documento XML: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error general: " + e.getMessage());
        }
    }
}
