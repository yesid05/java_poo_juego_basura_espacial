

package juego;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class Menu extends JMenuBar implements ActionListener{
    
    private InterfazJuego juego;
    
    private JMenu mnuJuego;
    
    private JMenu mnuAyuda;
    
    private JMenuItem mnuItemNuevoJuego;
    
    private JMenuItem mnuItemPuntaje;
    
    private JMenuItem mnuItemPause;
    
    private JMenuItem mnuItemSeguir;
    
    private JMenuItem mnuItemSalir;
    
    private JMenuItem mnuItemManual;
    
    private JMenuItem mnuItemCreditos;

    public Menu(InterfazJuego unJuego) {
        
        juego = unJuego;
        
        mnuJuego = new JMenu("Juego");
        mnuJuego.setMnemonic('J');
        
        mnuAyuda = new JMenu("Ayuda");
        mnuAyuda.setMnemonic('A');
        
        mnuItemNuevoJuego = new JMenuItem("Nuevo juego");
        mnuItemNuevoJuego.setMnemonic('N');
        mnuItemNuevoJuego.addActionListener(this);
        mnuJuego.add(mnuItemNuevoJuego);
        
        mnuItemPuntaje = new JMenuItem("Puntajes");
        mnuItemPuntaje.setMnemonic('P');
        mnuItemPuntaje.addActionListener(this);
        mnuJuego.add(mnuItemPuntaje);
        
        mnuItemPause = new JMenuItem("Pausar juego");
        mnuItemPause.setMnemonic('a');
        mnuItemPause.addActionListener(this);
        mnuJuego.add(mnuItemPause);
        
        mnuItemSeguir = new JMenuItem("Vover al juego");
        mnuItemSeguir.setMnemonic('V');
        mnuItemSeguir.addActionListener(this);
        mnuJuego.add(mnuItemSeguir);
        
        mnuItemSalir = new JMenuItem("Salir");
        mnuItemSalir.setMnemonic('S');
        mnuItemSalir.addActionListener(this);
        mnuJuego.add(mnuItemSalir);
        
        mnuItemManual = new JMenuItem("Como jugar");
        mnuItemManual.setMnemonic('C');
        mnuItemManual.addActionListener(this);
        mnuAyuda.add(mnuItemManual);
        
        mnuItemCreditos = new JMenuItem("Creditos");
        mnuItemCreditos.setMnemonic('r');
        mnuItemCreditos.addActionListener(this);
        mnuAyuda.add(mnuItemCreditos);
        
        add(mnuJuego);
        add(mnuAyuda);
    }
    
    public void desabilitarPause(){
        mnuItemPause.setEnabled(false);
    }

    public void habilitarPause(){
        mnuItemPause.setEnabled(true);
    }
    
    public void desabilitarContinuar(){
        mnuItemSeguir.setEnabled(false);
    }
    
    public void habilitarContinuar(){
        mnuItemSeguir.setEnabled(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == mnuItemNuevoJuego){
            juego.nuevoJuego();
        }
        if(e.getSource() == mnuItemPause){
            juego.pausarJuego();
        }
        if(e.getSource() == mnuItemSeguir){
            juego.seguirJuego();
        }
        if(e.getSource() == mnuItemSalir){
            juego.appDetener();
            juego.salir();
        }
        if(e.getSource() == mnuItemPuntaje){
            try {
                juego.verPuntaje();
            } catch (SQLException ex) {
                Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if(e.getSource()==mnuItemManual){
            juego.abrirArchivo();
            juego.pausarJuego();
        }
        if(e.getSource() == mnuItemCreditos){
            juego.verCreditos();
        }
    }

}
