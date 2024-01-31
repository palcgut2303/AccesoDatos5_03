/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.basex.core.BaseXException;
import org.basex.core.Context;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.ShowUsers;
import org.basex.core.cmd.XQuery;

/**
 *
 * @author manana
 */
public class Controlador {

    private Context contextoCoches;
    private Context contextoVentas;
    private String directorioBDCoches = "src/recurso/coches.xml";
    private String directorioBDVentas = "src/recurso/venta.xml";

    public void crearBD() {
        if (contextoCoches == null || contextoVentas == null) {
            contextoCoches = new Context();
            contextoVentas = new Context();
            try {
                CreateDB baseDatosCoches = new CreateDB("Coches", directorioBDCoches);
                CreateDB baseDatosVentas = new CreateDB("Venta", directorioBDVentas);

                baseDatosCoches.execute(contextoCoches);
                baseDatosVentas.execute(contextoVentas);

                System.out.print(new ShowUsers().execute(contextoCoches));
                System.out.print(new ShowUsers().execute(contextoVentas));

            } catch (BaseXException ex) {
                System.out.println("-->LA BASE DE DATOS NO HA PODIDO CREARSE:");
                System.err.println(ex);
            }
        }

    }

    public String consultaCochesSustituir() {
        try {
            if (contextoCoches == null) {
                crearBD();
            }
            String xq = "let $coches := //coche[plazas=\"9\"]\n"
                    + "return\n"
                    + "      <coche>\n"
                    + "        {\n"
                    + "          $coches/marca,\n"
                    + "          $coches/precio,\n"
                    + "          $coches/plazas,\n"
                    + "          $coches/year-fabricacion,\n"
                    + "          $coches/comercial,\n"
                    + "          <Ciudad>Córdoba</Ciudad>\n"
                    + "        }\n"
                    + "      </coche>";
            String resultado = new XQuery(xq).execute(contextoCoches);
            return resultado;
        } catch (BaseXException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";

    }

    public String consultaCochesEliminar() {
        try {
            if (contextoCoches == null) {
                crearBD();
            }
            String xq = "for $coches in //coche[ciudad=\"Málaga\"]\n"
                    + "return\n"
                    + "      <coche>\n"
                    + "        {\n"
                    + "          $coches/marca,\n"
                    + "          $coches/precio,\n"
                    + "          $coches/plazas,\n"
                    + "          $coches/year-fabricacion,\n"
                    + "          $coches/ciudad\n"
                    + "        }\n"
                    + "      </coche>";
            String resultado = new XQuery(xq).execute(contextoCoches);
            return resultado;
        } catch (BaseXException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";

    }

    public String consultaCochesAcceso() {
        try {
            if (contextoCoches == null) {
                crearBD();
            }
            String xq = "let $ventas := doc(\"src/Recurso/venta.xml\")//venta\n"
                    + "let $coches := doc(\"src/Recurso/coches.xml\")//coche\n"
                    + "\n"
                    + "let $ids_vendidos := $ventas/vendidos/id\n"
                    + "let $coches_vendidos := $coches[@id = $ids_vendidos]\n"
                    + "\n"
                    + "return\n"
                    + "    for $coche in $coches_vendidos\n"
                    + "    where $coche/ciudad = \"Málaga\"\n"
                    + "    return $coche/marca";
            String resultado = new XQuery(xq).execute(contextoCoches);
            return resultado;
        } catch (BaseXException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";

    }
    
    public String consultaOriginalCoche() {
        try {
            if (contextoCoches == null) {
                crearBD();
            }
            String xq = "for $coche in //coche return $coche";
            String resultado = new XQuery(xq).execute(contextoCoches);
            return resultado;
        } catch (BaseXException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";

    }
}
