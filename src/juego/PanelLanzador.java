/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juego;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import modelo.*;

public class PanelLanzador extends JPanel {

    public static String IMAGEN_LANZADOR = "lanzador.png";

    public static int DESPLAZAMIENTO = 3;

    public static int POS_X_INICIAL = 80;

    //posicion del lanzador al inicio
    int posX = 80, posY = 28, incX = 80;

    //angulo del lanzador en grados
    int angulo = 0;

    //imagen del lanzador
    GestionRecursos recurso = new GestionRecursos();
    BufferedImage imagenLanzador;

    //retorna valores
    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getAngulo() {
        return angulo;
    }

    //carga la imagen del lanzador
    public PanelLanzador() {
        imagenLanzador = recurso.retornaImagen(IMAGEN_LANZADOR);
        incX = getPosX();
    }

    public void setPosX(int unaPosX) {
        this.posX = unaPosX;
    }

    public void setPosY(int unaPosY) {
        this.posY = unaPosY;
    }

    public int getAlto() {
        return imagenLanzador.getHeight();
    }

    public int getAncho() {
        return imagenLanzador.getWidth();
    }

    public void moverDerecha(int unDesplazamiento, int movivientoMaximo) {
        if ((posX + getAncho()) >= movivientoMaximo) {
            posX = movivientoMaximo - getAncho();
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

    public void pintar(Graphics objGrafico, int unAngulo) {

        this.angulo = unAngulo;

        //las siguientes lineas sirven para transformar la imagen (girarla)
        AffineTransform giraImagen = new AffineTransform();
        giraImagen.rotate(this.angulo * Math.PI / 180, imagenLanzador.getWidth() / 2, imagenLanzador.getHeight() / 2);
        AffineTransformOp transformacion = new AffineTransformOp(giraImagen, AffineTransformOp.TYPE_BILINEAR);

        //se requiere java 2d
        Graphics2D graficos2d = (Graphics2D) objGrafico;
        graficos2d.drawImage(imagenLanzador, transformacion, posX, posY);
    }
}
