package bd.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {

    private static Conexion INSTANCE = null;

    private static final String CONTROLADOR = "org.apache.derby.jdbc.EmbeddedDriver";

    private static final String URL_BASEDATOS = "jdbc:derby:.\\DB\\Derby.DB";

    private Connection conexion = null;

//    private PreparedStatement crearTabala = null;
    private Conexion() {

    }

    public static Conexion getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Conexion();
        }
        return INSTANCE;
    }

    public void crearBaseDeDatos() {
        try {
            Class.forName(CONTROLADOR);
            conexion = DriverManager.getConnection(URL_BASEDATOS + ";create=true");
            if (conexion != null) {
                PreparedStatement crearTabala = conexion.prepareStatement("create table puntuacion(nombre varchar(50), puntaje int)");
                crearTabala.execute();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Existe un error en la base de datos:\n " + e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Existe un error en el controlador para la base de datos:\n" + e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Connection accederBaseDeDatos() {
        try {
            Class.forName(CONTROLADOR);
            conexion = DriverManager.getConnection(URL_BASEDATOS);
        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(null, "Existe un error en la base de datos:\n " + e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        } catch (ClassNotFoundException e) {
//            JOptionPane.showMessageDialog(null, "Existe un error en el controlador para la base de datos:\n" + e.getLocalizedMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return conexion;
    }

    public void cerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
    }

}
