/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import modelo.*;

/**
 *
 * @author Sammy Guergachi <sguergachi at gmail.com>
 */
public class PanelBaseLanzador extends JPanel {

    public static String IMAGEN_BASE_LANZADOR = "base_lanzador.png";

    public static int POS_X_INICIAL = 10;

    public static int DESPLAZAMIENTO = 3;

    //posicion de la base del lanzador 
    int posX = 10, posY = 350;

    //Imagen del lanzador
    GestionRecursos recursos = new GestionRecursos();
    BufferedImage imagen;

    //carga la imagen del lanzador
    public PanelBaseLanzador() {

        imagen = recursos.retornaImagen(IMAGEN_BASE_LANZADOR);

    }

    public int getAncho() {
        return imagen.getWidth();
    }

    public int getAlto() {
        return imagen.getHeight();
    }

    public void setPosX(int unaPosX) {
        this.posX = unaPosX;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosY(int unaPosY) {
        this.posY = unaPosY;
    }

    public int getPosY() {
        return this.posY;
    }

    public void moverDerecha(int unDesplazamiento, int movimientoMaximo) {

        if ((posX + getAncho()) >= movimientoMaximo) {
            posX = movimientoMaximo - getAncho();
        } else {
            posX += unDesplazamiento;
        }
    }

    public void moverIzquierda(int unDesplazamiento) {
        if (posX <= POS_X_INICIAL) {
            posX = POS_X_INICIAL;
        } else {
            posX -= unDesplazamiento;
        }
    }

    public void pintar(Graphics objGrafico) {

        //muestra la imagen de la base del lanzador
        objGrafico.drawImage(imagen, posX, posY, this);
    }

}
