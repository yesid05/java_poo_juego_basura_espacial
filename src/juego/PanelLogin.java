/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import modelo.*;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class PanelLogin extends JPanel implements ActionListener{
    
    public static String IMAGEN_FONDO_LOGIN = "fondo_login.png";

    private InterfazJuego juego;
    
    private JPanel panelAux;

    private JLabel lblNombre;

    private JTextField txtNombre;

    private JButton btnCancelar;

    private JButton btnAceptar;

    private GestionRecursos recursos = new GestionRecursos();

    public PanelLogin(InterfazJuego unJuego) {
        
        juego = unJuego;
        
        setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        lblNombre = new JLabel("Nombre: ");
        lblNombre.setForeground(Color.BLUE);
        lblNombre.setFont(new Font("Arial", Font.BOLD, 32));
        gbc.gridx = 0;
        gbc.gridy= 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 0, 10, 5);
        add(lblNombre,gbc);
        
        txtNombre = new JTextField(15);
        txtNombre.setForeground(Color.BLACK);
        txtNombre.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(0, 5, 10, 0);
        add(txtNombre,gbc);
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(this);
        btnCancelar.setBackground(Color.red);
        btnCancelar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        btnCancelar.setForeground(Color.white);
        btnCancelar.setFont(new Font("Arial", Font.BOLD, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill= GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(10, 0, 0, 7);
        add(btnCancelar,gbc);
        
        btnAceptar = new JButton("Acceder");
        btnAceptar.addActionListener(this);
        btnAceptar.setBackground(Color.blue);
        btnAceptar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        btnAceptar.setForeground(Color.white);
        btnAceptar.setFont(new Font("Arial", Font.BOLD, 25));
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.insets = new Insets(10, 7, 0, 0);
        add(btnAceptar,gbc);
        
    }
    
    public String darNombre(){
        return txtNombre.getText();
    }

    @Override
    protected void paintComponent(Graphics g) {
        ImageIcon imagen = new ImageIcon(getClass().getResource(GestionRecursos.URL_RECURSO_IMAGEN+IMAGEN_FONDO_LOGIN));
        g.drawImage(imagen.getImage(), 0, 0, getSize().width, getSize().height, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==btnAceptar){
            if(!txtNombre.getText().isEmpty()){
                juego.cambiarNombreJugador();
                juego.inicioJuego();
                juego.salirLogin();
            }else{
                JOptionPane.showMessageDialog(this, "Error, escriba un nombre","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        if(e.getSource()==btnCancelar){
            juego.salir();
        }
    }
}
