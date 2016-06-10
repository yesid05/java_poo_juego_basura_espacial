/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import bd.conexion.Conexion;
import bd.conexion.Operaciones;
import bd.modelo.Puntaje;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JApplet;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.Jugador;

public class InterfazJuego extends JApplet {

    private static InterfazJuego juego;

    private InterfazJuego applet;

    private JFrame ventana;

    private JFrame dialogoInicio;
    
    private JDialog dialogoPause;

    private Connection conexion;

    private Conexion con;

    private static Jugador jugador;

    private PanelLogin panelLogin;

    private PanelCreditos panelCreditos;

    private PanelPause panelPause;

    //Objeto que muestra el grafico animado
    private static PanelAnimar objAnimar;

    private static Menu menu;

    boolean seInserto = true;

    //Ejecutado si es aplet
    public void init() {
        controlesGraficos();
    }

    //ejecutado si es applet(Cuando cierra la página debe detener el hilo)
    public void destroy() {
        objAnimar.detener();
    }

    public static void main(String[] args) {
        juego = new InterfazJuego();
        juego.verLogin();
//        juego.conectarBD();
//        juego.ejecutaEscritorio();
    }

    private void controlesGraficos() {

        //El componente que grafica
        objAnimar = new PanelAnimar(this);
        objAnimar.cambiarJugador(jugador.getNombre());
        objAnimar.cambiarPuntaje(0);
        objAnimar.cambiarVidaPlaneta(20);
        add(objAnimar);
        objAnimar.iniciar();
//        objAnimar.iniciar();
        //Ubicacion y tamaño de la ventana
        setBounds(0, 0, 400, 400);

        //Muestra la ventana
        setVisible(true);
    }

    public int darVida() {
        return objAnimar.darVidaPlaneta();
    }

    public void vidaPlaneta() {
        if (objAnimar.darVidaPlaneta() <= 0) {
            objAnimar.detener();
            JOptionPane.showMessageDialog(this, "Perdiste..!", "Basura espacial", JOptionPane.WARNING_MESSAGE);

            if (seInserto) {
                Operaciones operaciones = new Operaciones();
                operaciones.insertarPuntaje(new Puntaje(objAnimar.darJugador(), objAnimar.darPuntaje()));
                seInserto = false;
            }
        }
    }

    private void ejecutaEscritorio() {
        ventana = new JFrame("Derrivar basura espacial " + jugador.getNombre());

        //Tamaño de la ventana 
        ventana.setSize(750, 570);

        //Instancia el applet
        applet = new InterfazJuego();
        //Inicializa el applet 
        applet.init();

        //Adiciona el applet al jframe
        menu = new Menu(this);
        menu.desabilitarContinuar();
        ventana.setJMenuBar(menu);
        ventana.add(applet, BorderLayout.CENTER);
        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);

        //Evento de cerrar ventana (detiene el hilo primero)
        ventana.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evento) {
                salir();
            }
        });

    }

    public void verLogin() {
        panelLogin = new PanelLogin(this);
        dialogoInicio = new JFrame();
        dialogoInicio.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dialogoInicio.setResizable(false);
        dialogoInicio.setSize(600, 400);
        dialogoInicio.add(panelLogin);
        dialogoInicio.setLocationRelativeTo(null);
        dialogoInicio.setVisible(true);
    }

    public void salirLogin() {
        dialogoInicio.dispose();
    }

    public void cambiarNombreJugador() {
        String nombre = panelLogin.darNombre();
        jugador = new Jugador(nombre);
    }

    public void inicioJuego() {
        juego.ejecutaEscritorio();
        juego.conectarBD();

    }

    public void nuevoJuego() {
        destroy();
        ventana.dispose();
        juego.ejecutaEscritorio();
    }

    public void appDetener() {
        objAnimar.detener();
    }

    public void appIniciar() {
        objAnimar.iniciar();
    }

    public void pausarJuego() {
        menu.desabilitarPause();
        applet.appDetener();
        menu.habilitarContinuar();
        panelPause = new PanelPause(this);
        panelPause.cambiarPuntaje("Puntaje: "+objAnimar.darJugador()+" "+objAnimar.darPuntaje());
        dialogoPause = new JDialog();
        dialogoPause.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent evento) {
                seguirJuego();
            }
        });
        dialogoPause.add(panelPause);
        dialogoPause.setSize(450, 300);
        dialogoPause.setModal(true);
        dialogoPause.setLocationRelativeTo(this);
        dialogoPause.setVisible(true);
    }
    
    public void salirDialogoPause(){
        dialogoPause.dispose();
    }
    
    public void seguirJuego() {
        menu.desabilitarContinuar();
        applet.appIniciar();
        menu.habilitarPause();

    }

    public void salir() {
//        appDetener();
        System.exit(0);
    }

    public void conectarBD() {
        con = Conexion.getInstance();
        if (con.accederBaseDeDatos() == null) {
            con.crearBaseDeDatos();
        }
    }

    public void verPuntaje() throws SQLException {
        appDetener();
        PanelPuntaje puntaje = new PanelPuntaje(this);
        Operaciones o = new Operaciones();
        puntaje.nuevaListaPuntaje(o.consultas());
        puntaje.setModal(true);
        puntaje.setLocationRelativeTo(this);
        puntaje.setVisible(true);
    }

    public void verCreditos() {
        panelCreditos = new PanelCreditos();
        panelCreditos.setLocationRelativeTo(this);
        panelCreditos.setVisible(true);
    }
    
    public void abrirArchivo(){
        try {
            File archivo = new File(getClass().getResource("/recursos/docs/Documento de Usuario  Software.pdf").toURI());
            Desktop.getDesktop().open(archivo);
        } catch (Exception e) {
        }
    }
}
