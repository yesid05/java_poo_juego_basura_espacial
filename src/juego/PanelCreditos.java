/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package juego;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class PanelCreditos extends JDialog{
    
    private static Font fuente = new Font("Arial", Font.BOLD, 11);
    
    private JLabel lblRealizado;
    
    private JLabel lblNombre1;
    
    private JLabel lblNombre2;
    
    private JLabel lblNombre3;
    
    private JLabel lblNombre4;

    public PanelCreditos() {
        
        setLayout(new GridBagLayout());
        setSize(350, 200);
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        
        
        lblRealizado = new JLabel("Realizado por:");
        lblRealizado.setFont(new Font("Arial", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5,10);
        add(lblRealizado,gbc);
        
        lblNombre1 = new JLabel("Astrid Lorena Serna Trochez");
        lblNombre1.setFont(fuente);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 0);
        add(lblNombre1,gbc);
        
        lblNombre2 = new JLabel("Lizeth Dayana Garces");
        lblNombre2.setFont(fuente);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 5, 0);
        add(lblNombre2,gbc);
        
        lblNombre3 = new JLabel("Edith Marcela López Ortega");
        lblNombre3.setFont(fuente);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 0, 5, 0);
        add(lblNombre3,gbc);
        
        lblNombre4 = new JLabel("Yesid Caicedo Añazco");
        lblNombre4.setFont(fuente);
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 0, 5, 0);
        add(lblNombre4,gbc);
    }
    
    

}
