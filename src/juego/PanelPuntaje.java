

package juego;

import bd.modelo.Puntaje;
import com.sun.jndi.toolkit.dir.SearchFilter;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JScrollPane;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class PanelPuntaje extends JDialog implements ActionListener{
    
    private InterfazJuego juego;
    
    private JList listaPuntaje;
    
    private JButton btnOk;
    
    public PanelPuntaje(InterfazJuego unJuego){
        
        juego = unJuego;
        
        setTitle("Puntaje");
        setLocationRelativeTo(juego);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        setSize(400, 200);
        setResizable(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        listaPuntaje = new JList();
        listaPuntaje.setFixedCellWidth(100);
        listaPuntaje.setFixedCellHeight(30);
        listaPuntaje.setVisibleRowCount(10);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(5, 5, 0, 5);
        add(new JScrollPane(listaPuntaje),gbc);
        
        
        
        btnOk = new JButton("Aceptar");
        btnOk.addActionListener(this);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(btnOk,gbc);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evento){
                juego.seguirJuego();
            }
        });
    }
    
    public void nuevaListaPuntaje(Vector<Puntaje> unaListaPuntaje){
        Object[] auxLista = new Object[unaListaPuntaje.size()];
        for (int i = 0; i < auxLista.length; i++) {
            auxLista[i] = String.format("   Jugador: %s    Puntaje: %d", unaListaPuntaje.get(i).getNombre(), unaListaPuntaje.get(i).getPuntaje());
        }
        listaPuntaje.setListData(auxLista);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnOk){
            juego.seguirJuego();
            dispose();
        }
    }
    
}
