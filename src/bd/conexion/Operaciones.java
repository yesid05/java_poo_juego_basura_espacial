
package bd.conexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import bd.modelo.*;
import javax.swing.JOptionPane;

public class Operaciones {
    
    private Connection conexion = null;
    
    private Statement instruccion = null;
    
    private ResultSet conjuntoResultados = null;
    
    private Vector<Puntaje> listaPuntaje = null;
    
    private Conexion con = Conexion.getInstance();
    
    public void insertarPuntaje(Puntaje unPuntaje) {
        try {
            conexion = con.accederBaseDeDatos();
            instruccion = conexion.createStatement();
            instruccion.execute("insert into puntuacion(nombre,puntaje) values('" + unPuntaje.getNombre() + "'," + unPuntaje.getPuntaje() + ")");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error en la base de datos, no se pudo guardar su puntaje:\n"+e.getLocalizedMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public Vector<Puntaje> consultas() throws SQLException {
        conexion = con.accederBaseDeDatos();
        
        instruccion = conexion.createStatement();
        
        conjuntoResultados = instruccion.executeQuery("select * from puntuacion order by puntaje desc");
        
        listaPuntaje = new Vector<Puntaje>();
        
        ResultSetMetaData metaDatos = conjuntoResultados.getMetaData();
        int numeroDeColumnas = metaDatos.getColumnCount();
        
        for (int i = 1; i <= numeroDeColumnas; i++) {
            System.out.printf("%-8s\t", metaDatos.getColumnName(i));
        }
        while (conjuntoResultados.next()) {
            if (listaPuntaje.add(
                    new Puntaje(
                            conjuntoResultados.getString("nombre"),
                            conjuntoResultados.getInt("puntaje")))) {
            } else {
                JOptionPane.showMessageDialog(null, "No se puede ver el/los puntaje(s) ","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        return listaPuntaje;
    }
    
}
