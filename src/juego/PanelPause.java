package juego;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modelo.GestionRecursos;

public class PanelPause extends JPanel implements ActionListener {

    public static String IMAGEN_FONDO = "fondo.png";

    private InterfazJuego interfazJuego;

    private JLabel lblPuntaje;

    private JButton btnSalir;

    private JButton btnReiniciar;

    public PanelPause(InterfazJuego unJuego) {

        interfazJuego = unJuego;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        lblPuntaje = new JLabel("Yesid caicedo");
        lblPuntaje.setForeground(Color.red);
        lblPuntaje.setFont(new Font("Arial", Font.BOLD, 35));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 0, 10, 0);
        gbc.anchor = GridBagConstraints.NORTH;
        add(lblPuntaje, gbc);

        btnSalir = new JButton("Salir");
        btnSalir.addActionListener(this);
        btnSalir.setBackground(Color.red);
        btnSalir.setBorder(BorderFactory.createLineBorder(Color.black, 4));
        btnSalir.setForeground(Color.white);
        btnSalir.setFont(new Font("Arial", Font.BOLD, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.ipadx = 50;
        gbc.insets = new Insets(60, 5, 5, 40);
        gbc.anchor = GridBagConstraints.SOUTH;
        add(btnSalir, gbc);

        btnReiniciar = new JButton("Reiniciar");
        btnReiniciar.addActionListener(this);
        btnReiniciar.setBackground(Color.BLUE);
        btnReiniciar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        btnReiniciar.setForeground(Color.WHITE);
        btnReiniciar.setFont(new Font("Arial", Font.BOLD, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.ipadx = 10;
        gbc.insets = new Insets(60, 60, 5, 5);
        gbc.anchor = GridBagConstraints.SOUTH;
        add(btnReiniciar, gbc);

    }

    public void cambiarPuntaje(String unPuntaje) {
        lblPuntaje.setText(unPuntaje);
    }

    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon imagen = new ImageIcon(getClass().getResource(GestionRecursos.URL_RECURSO_IMAGEN+IMAGEN_FONDO));
        g.drawImage(imagen.getImage(), 0, 0, getSize().width, getSize().height, this);
    }
    
 

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnReiniciar) {
            interfazJuego.salirDialogoPause();
            interfazJuego.seguirJuego();
        }
        if (e.getSource() == btnSalir) {
            interfazJuego.salir();
        }
    }

}
